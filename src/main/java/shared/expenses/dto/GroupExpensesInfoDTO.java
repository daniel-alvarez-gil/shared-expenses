package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class GroupExpensesInfoDTO {
    private Long groupId;
    private String groupName;
    private LinkedList<ConsumerDTO> consumers;
    private List<ExpenseInfoDTO> expensesList;
    private HashMap<String, Float> balance;
    private List<String> debts;
}
