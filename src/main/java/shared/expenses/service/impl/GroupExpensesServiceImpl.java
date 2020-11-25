package shared.expenses.service.impl;

import shared.expenses.dto.ConsumerDTO;
import shared.expenses.dto.ExpenseInfoDTO;
import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;
import shared.expenses.repository.ConsumerRepository;
import shared.expenses.repository.ExpenseRepository;
import shared.expenses.repository.GroupExpenseRepository;
import shared.expenses.service.GroupExpensesService;
import shared.expenses.utils.Utils;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.*;

@Singleton
public class GroupExpensesServiceImpl implements GroupExpensesService {

    private final ExpenseRepository expenseRepository;
    private final GroupExpenseRepository groupExpenseRepository;
    private final ConsumerRepository consumerRepository;

    private static final float ZERO = 0;

    private List<String> debts;

    public GroupExpensesServiceImpl(ExpenseRepository expenseRepository, GroupExpenseRepository groupExpenseRepository, ConsumerRepository consumerRepository) {
        this.expenseRepository = expenseRepository;
        this.groupExpenseRepository = groupExpenseRepository;
        this.consumerRepository = consumerRepository;
    }

    @Transactional
    public GroupExpensesInfoDTO getGroupExpenseInfo(Long groupExpensesId) {
        Optional<GroupExpenses> optional = groupExpenseRepository.findById(groupExpensesId);
        if (!optional.isPresent())
            return null;

        LinkedList<Expense> expenseList = expenseRepository.findAllByGroupExpensesOrderByCreateTimeDesc(optional.get().getId());
        if(expenseList.isEmpty())
            return GroupExpensesInfoDTO.builder()
                    .groupId(optional.get().getId())
                    .groupName(optional.get().getName())
                    .consumers(getConsumersGroup(optional.get()))
                    .build();

        return getGroupExpenseInfoWithExpenses(optional.get(), expenseList);
    }

    @Transactional
    public GroupExpensesInfoDTO addConsumerToGroup(Long groupExpensesId, Long consumerId) {
        Optional<GroupExpenses> optional = groupExpenseRepository.findById(groupExpensesId);
        if (!optional.isPresent())
            return null;

        Optional<Consumer> consumer = consumerRepository.findById(consumerId);
        if (!consumer.isPresent())
            return null;

        consumer.get().getGroups().add(optional.get());
        consumerRepository.update(consumer.get());

        return GroupExpensesInfoDTO.builder()
                .groupId(optional.get().getId())
                .groupName(optional.get().getName())
                .consumers(getConsumersGroup(optional.get(), consumer.get()))
                .build();
    }

    private LinkedList<ConsumerDTO> getConsumersGroup(GroupExpenses groupExpenses, Consumer consumer) {
        LinkedList<ConsumerDTO> consumers = getConsumersGroup(groupExpenses);
        consumers.add(ConsumerDTO.builder().id(consumer.getId()).name(consumer.getName()).build());
        return consumers;
    }

    private LinkedList<ConsumerDTO> getConsumersGroup(GroupExpenses groupExpenses){
        LinkedList<ConsumerDTO> consumers = new LinkedList<>();
        groupExpenses.getConsumers().forEach(c ->
                consumers.add(ConsumerDTO.builder().id(c.getId()).name(c.getName()).build())
        );
        return consumers;
    }

    @Transactional
    public GroupExpensesInfoDTO addExpense(Long groupExpensesId, Expense expense) {
        Optional<GroupExpenses> optional = groupExpenseRepository.findById(groupExpensesId);
        if (!optional.isPresent())
            return null;

        expense.setCreateTime(new Date());
        expenseRepository.save(expense);

        List<Expense> expenseList = expenseRepository.findAllByGroupExpensesOrderByCreateTimeDesc(optional.get().getId());
        return getGroupExpenseInfoWithExpenses(optional.get(), expenseList);
    }


    private GroupExpensesInfoDTO getGroupExpenseInfoWithExpenses(GroupExpenses groupExpense, List<Expense> expenseList) {
        List<ExpenseInfoDTO> expensesInfoDTOList = new LinkedList<>();
        HashMap<String, Float> balance = new HashMap<>();
        float totalExpenses = 0;

        for (Expense expense : expenseList) {
            totalExpenses += expense.getAmount();
            addExpenseToBalance(balance, expense);
            addExpenseToList(expensesInfoDTOList, expense);
        }

        getBalance(balance, totalExpenses);
        debts = new ArrayList<>();
        calculateDebts(new HashMap<>(balance));

        return GroupExpensesInfoDTO.builder()
                .groupId(groupExpense.getId())
                .groupName(groupExpense.getName())
                .expensesList(expensesInfoDTOList)
                .balance(balance)
                .debts(debts)
                .build();
    }

    private void getBalance(HashMap<String, Float> balance, float totalExpenses) {
        final float mean = totalExpenses / balance.size();
        balance.forEach((k, v) -> balance.replace(k, Utils.round(balance.get(k) - mean, 2)));
    }

    private void addExpenseToList(List<ExpenseInfoDTO> expensesInfoDTOList, Expense expense) {

        ConsumerDTO consumerDTO = ConsumerDTO.builder()
                .id(expense.getPayer().getId())
                .name(expense.getPayer().getName())
                .build();

        ExpenseInfoDTO expenseInfoDTO = ExpenseInfoDTO.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .createTime(expense.getCreateTime())
                .payer(consumerDTO)
                .build();

        expensesInfoDTOList.add(expenseInfoDTO);
    }

    private void addExpenseToBalance(HashMap<String, Float> balance, Expense expense) {
        String key = expense.getPayer().getName();

        if (balance.containsKey(key))
            balance.replace(key, balance.get(key) + expense.getAmount());
        else
            balance.put(key, expense.getAmount());
    }

    private void calculateDebts(HashMap<String, Float> balance) {
        Float Max_Value = Collections.max(balance.values());
        Float Min_Value = Collections.min(balance.values());
        if (!Max_Value.equals(Min_Value)) {
            String Max_Key = Utils.getKeyFromValue(balance, Max_Value);
            String Min_Key = Utils.getKeyFromValue(balance, Min_Value);
            float result = Utils.round(Max_Value + Min_Value, 1);
            balance.remove(Max_Key);
            balance.remove(Min_Key);
            if (result >= ZERO) {
                debts.add(String.format("%s -> %s (%.2f)", Min_Key, Max_Key, Utils.round(Math.abs(Min_Value), 2)));
                balance.put(Max_Key, result);
                balance.put(Min_Key, ZERO);
            } else {
                debts.add(String.format("%s -> %s (%.2f)", Min_Key, Max_Key, Utils.round(Math.abs(Max_Value), 2)));
                balance.put(Max_Key, ZERO);
                balance.put(Min_Key, result);
            }
            calculateDebts(balance);
        }

    }


}
