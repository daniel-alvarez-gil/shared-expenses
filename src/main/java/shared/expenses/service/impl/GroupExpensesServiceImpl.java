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
    public GroupExpensesInfoDTO listOrderByCreateTime(Long groupExpensesId) {
        Optional<GroupExpenses> groupExpense = groupExpenseRepository.findById(groupExpensesId);
        return groupExpense.map(this::getGroupInfoOrderByCreateTime).orElse(null);

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

    private HashMap<Long, String> getConsumersGroup(GroupExpenses groupExpenses, Consumer consumer) {
        HashMap<Long, String> consumers = new HashMap<>();
        groupExpenses.getConsumers().forEach(c -> consumers.put(c.getId(), c.getName()));
        consumers.put(consumer.getId(), consumer.getName());
        return consumers;
    }

    @Transactional
    public GroupExpensesInfoDTO addExpense(Long groupExpensesId, Expense expense) {
        Optional<GroupExpenses> groupExpense = groupExpenseRepository.findById(groupExpensesId);
        if (!groupExpense.isPresent())
            return null;

        expenseRepository.save(expense);

        return getGroupInfoOrderByCreateTime(groupExpense.get());
    }


    private GroupExpensesInfoDTO getGroupInfoOrderByCreateTime(GroupExpenses groupExpense) {
        LinkedHashMap<Long, ExpenseInfoDTO> expensesList = new LinkedHashMap<>();
        HashMap<String, Float> balance = new HashMap<>();
        float totalExpenses = 0;

        for (Expense expense : expenseRepository.findAllOrderByCreateTimeDesc(groupExpense)) {
            totalExpenses += expense.getAmount();
            addExpenseToBalance(balance, expense);
            addExpenseToList(expensesList, expense);
        }

        getBalance(balance, totalExpenses);
        debts = new ArrayList<>();
        calculateDebts(new HashMap<>(balance));

        return GroupExpensesInfoDTO.builder()
                .groupId(groupExpense.getId())
                .groupName(groupExpense.getName())
                .expensesList(expensesList)
                .balance(balance)
                .debts(debts)
                .build();
    }

    private void getBalance(HashMap<String, Float> balance, float totalExpenses) {
        final float mean = totalExpenses / balance.size();
        balance.forEach((k, v) -> balance.replace(k, Utils.round(balance.get(k) - mean, 2)));
    }

    private void addExpenseToList(LinkedHashMap<Long, ExpenseInfoDTO> expensesList, Expense expense) {

        ConsumerDTO consumerDTO = ConsumerDTO.builder()
                .id(expense.getPayer().getId())
                .name(expense.getPayer().getName())
                .build();

        ExpenseInfoDTO expenseInfoDTO = ExpenseInfoDTO.builder()
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .createTime(expense.getCreateTime())
                .payer(consumerDTO)
                .build();
        expensesList.put(expense.getId(), expenseInfoDTO);
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
                debts.add(Min_Key + " -> " + Max_Key + ": " + Utils.round(Math.abs(Min_Value), 2));
                balance.put(Max_Key, result);
                balance.put(Min_Key, ZERO);
            } else {
                debts.add(Min_Key + " -> " + Max_Key + ": " + Utils.round(Math.abs(Max_Value), 2));
                balance.put(Max_Key, ZERO);
                balance.put(Min_Key, result);
            }
            calculateDebts(balance);
        }

    }


}
