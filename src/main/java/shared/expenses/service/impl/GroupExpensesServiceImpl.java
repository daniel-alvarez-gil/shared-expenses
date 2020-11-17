package shared.expenses.service.impl;

import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
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
import javax.swing.text.html.Option;
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
        if (!groupExpense.isPresent())
            return null;

        return getGroupInfoOrderByCreateTime(groupExpense.get());
    }

    @Transactional
    public GroupExpensesInfoDTO addConsumerToGroup(Long groupExpensesId, Long consumerId){
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

        GroupExpensesInfoDTO groupExpensesInfoDTO = GroupExpensesInfoDTO.builder()
                .groupId(optional.get().getId())
                .groupName(optional.get().getName())
                .consumers(consumers)
                .build();

        return groupExpensesInfoDTO;
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
        for (Expense expense : expenseRepository.findAllOrderByCreateTimeDesc(groupExpense)) {
            ExpenseInfoDTO expenseInfoDTO = ExpenseInfoDTO.builder()
                    .amount(expense.getAmount())
                    .description(expense.getDescription())
                    .createTime(expense.getCreateTime())
                    .payer(expense.getPayer())
                    .build();
            expensesList.put(expense.getId(), expenseInfoDTO);
        }

        return GroupExpensesInfoDTO.builder()
                .groupId(groupExpense.getId())
                .groupName(groupExpense.getName())
                .expensesList(expensesList)
                .build();
    }

}
