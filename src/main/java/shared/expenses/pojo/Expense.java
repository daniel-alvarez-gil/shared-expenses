package shared.expenses.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor(staticName = "of")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Expense() {
    }
}
