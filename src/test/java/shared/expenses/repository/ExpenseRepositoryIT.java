package shared.expenses.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import shared.expenses.pojo.Consumer;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class ExpenseRepositoryIT {

    @Inject
    ExpenseRepository expenseRepository;

    @Test
    void shouldReturnFiveItems() {
        List<Expense> result = expenseRepository.findAllByGroupExpensesOrderByCreateTimeDesc(2L);
        assertEquals(5, result.size());
    }

    @Test
    void shouldReturnExpensesOrderByCreateTimeDesc() {
        List<Expense> result = expenseRepository.findAllByGroupExpensesOrderByCreateTimeDesc(2L);
        assertTrue(result.get(0).getCreateTime().after(result.get(4).getCreateTime()));
    }

    @Test
    void checkIfExpenseIsInserted() {
        Expense expected = createExpense();
        Expense result =  expenseRepository.save(expected);
        assertEquals(expected, result);
    }

    private Expense createExpense(){
        return Expense.builder()
                .description("")
                .createTime(new Date())
                .payer(Consumer.builder().id(1L).build())
                .groupExpenses(GroupExpenses.builder().id(2L).build())
                .build();
    }
}
