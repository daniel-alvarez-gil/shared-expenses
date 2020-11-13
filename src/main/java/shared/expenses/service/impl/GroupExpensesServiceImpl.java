package shared.expenses.service.impl;

import shared.expenses.dto.ExpenseInfoDTO;
import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;
import shared.expenses.repository.ExpenseRepository;
import shared.expenses.repository.GroupExpenseRepository;
import shared.expenses.service.GroupExpensesService;

import javax.inject.Singleton;
import java.util.Hashtable;
import java.util.Optional;

@Singleton
public class GroupExpensesServiceImpl implements GroupExpensesService {

    private final ExpenseRepository expenseRepository;
    private final GroupExpenseRepository groupExpenseRepository;

    public GroupExpensesServiceImpl(ExpenseRepository expenseRepository, GroupExpenseRepository groupExpenseRepository) {
        this.expenseRepository = expenseRepository;
        this.groupExpenseRepository = groupExpenseRepository;
    }

    public GroupExpensesInfoDTO listOrderByCreateTime(Long groupExpensesId) {
        Hashtable<Long, ExpenseInfoDTO> expensesList = new Hashtable<>();

        Optional<GroupExpenses> groupExpense = groupExpenseRepository.findById(groupExpensesId);
        if (!groupExpense.isPresent())
            return null;

        for (Expense expense : expenseRepository.listOrderByCreateTime(groupExpense.get())) {
            ExpenseInfoDTO expenseInfoDTO = ExpenseInfoDTO.builder()
                    .amount(expense.getAmount())
                    .description(expense.getDescription())
                    .createTime(expense.getCreateTime())
                    .payer(expense.getPayer())
                    .build();
            expensesList.put(expense.getId(), expenseInfoDTO);
        }

        return GroupExpensesInfoDTO.builder()
                .groupId(groupExpense.get().getId())
                .groupName(groupExpense.get().getName())
                .expensesList(expensesList)
                .build();
    }
}
