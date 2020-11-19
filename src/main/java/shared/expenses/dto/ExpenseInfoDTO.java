package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExpenseInfoDTO {

    private Float amount;
    private String description;
    private Date createTime;
    private ConsumerDTO payer;

}
