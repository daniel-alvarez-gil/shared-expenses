package shared.expenses.service;

import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Expense;

public interface GroupExpensesService {
    GroupExpensesInfoDTO getGroupExpenseInfo(Long groupExpensesId);

    GroupExpensesInfoDTO addConsumerToGroup(Long groupExpensesId,  String consumerName);

    GroupExpensesInfoDTO addExpense(Long groupExpensesId, Expense expense);

}
