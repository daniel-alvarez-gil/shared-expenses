package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Hashtable;

@Data
@Builder
public class GroupExpensesInfoDTO {
    private Long groupId;
    private String groupName;
    private Hashtable<Long, ExpenseInfoDTO> expensesList;
}
