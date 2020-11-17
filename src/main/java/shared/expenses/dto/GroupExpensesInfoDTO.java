package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;
import shared.expenses.pojo.Consumer;

import java.util.HashMap;

@Data
@Builder
public class GroupExpensesInfoDTO {
    private Long groupId;
    private String groupName;
    private HashMap<Long, String> consumers;
    private HashMap<Long, ExpenseInfoDTO> expensesList;
}
