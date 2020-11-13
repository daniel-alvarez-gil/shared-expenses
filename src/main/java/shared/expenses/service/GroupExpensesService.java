package shared.expenses.service;

import shared.expenses.dto.GroupExpensesInfoDTO;

import java.util.List;

public interface GroupExpensesService {
    GroupExpensesInfoDTO listOrderByCreateTime(Long groupExpensesId);
}
