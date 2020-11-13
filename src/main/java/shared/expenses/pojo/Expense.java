package shared.expenses.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Expense {

    @Id
    private long id;

    private float amount;
    private String description;
    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Consumer payer;

    @ManyToOne
    @JoinColumn(name = "group_expense_id")
    private GroupExpenses groupExpenses;
}
