package shared.expenses.service;

import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;

import java.util.List;

public interface GroupExpensesService {
    GroupExpensesInfoDTO listOrderByCreateTime(Long groupExpensesId);
    GroupExpensesInfoDTO addConsumerToGroup(Long groupExpensesId, Long consumerId);
    GroupExpensesInfoDTO addExpense(Long groupExpensesId, Expense expense);

}
