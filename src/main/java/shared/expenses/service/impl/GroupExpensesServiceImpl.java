package shared.expenses.service.impl;

import shared.expenses.dto.ExpenseInfoDTO;
import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;
import shared.expenses.repository.ConsumerRepository;
import shared.expenses.repository.ExpenseRepository;
import shared.expenses.repository.GroupExpenseRepository;
import shared.expenses.service.GroupExpensesService;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

@Singleton
public class GroupExpensesServiceImpl implements GroupExpensesService {

    private final ExpenseRepository expenseRepository;
    private final GroupExpenseRepository groupExpenseRepository;
    private final ConsumerRepository consumerRepository;

    public GroupExpensesServiceImpl(ExpenseRepository expenseRepository, GroupExpenseRepository groupExpenseRepository, ConsumerRepository consumerRepository) {
        this.expenseRepository = expenseRepository;
        this.groupExpenseRepository = groupExpenseRepository;
        this.consumerRepository = consumerRepository;
    }

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

        HashMap<Long, String> consumers = new HashMap<>();
        optional.get().getConsumers().forEach(c -> consumers.put(c.getId(), c.getName()));
        consumers.put(consumer.get().getId(), consumer.get().getName());

        return GroupExpensesInfoDTO.builder()
                .groupId(optional.get().getId())
                .groupName(optional.get().getName())
                .consumers(consumers)
                .build();
    }

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
            String key = expense.getPayer().getName();

            if (balance.containsKey(key))
                balance.replace(key, balance.get(key) + expense.getAmount());
            else
                balance.put(key, expense.getAmount());

            ExpenseInfoDTO expenseInfoDTO = ExpenseInfoDTO.builder()
                    .amount(expense.getAmount())
                    .description(expense.getDescription())
                    .createTime(expense.getCreateTime())
                    .payer(expense.getPayer())
                    .build();
            expensesList.put(expense.getId(), expenseInfoDTO);
        }

        final float mean = totalExpenses / balance.size();
        balance.forEach((k, v) -> balance.replace(k, balance.get(k) - mean));

        return GroupExpensesInfoDTO.builder()
                .groupId(groupExpense.getId())
                .groupName(groupExpense.getName())
                .expensesList(expensesList)
                .balance(balance)
                .build();
    }

}
