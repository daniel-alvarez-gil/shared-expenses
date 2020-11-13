package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;
import shared.expenses.pojo.Consumer;

import java.util.Date;

@Data
@Builder
public class ExpenseInfoDTO {

    private Float amount;
    private String description;
    private Date createTime;
    private Consumer payer;

}
