package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
@Builder
public class GroupExpensesInfoDTO {
    private Long groupId;
    private String groupName;
    private HashMap<Long, String> consumers;
    private HashMap<Long, ExpenseInfoDTO> expensesList;
    private HashMap<String, Float> balance;
    private List<String> debts;
}
