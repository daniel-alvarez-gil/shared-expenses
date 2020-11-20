package shared.expenses.repository;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.pojo.Expense;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    @Query(value = "SELECT * FROM expense e WHERE e.group_expense_id = :groupExpensesId ORDER BY create_time DESC",
            nativeQuery = true)
    List<Expense> findAllByGroupExpensesOrderByCreateTimeDesc(@Parameter(value = "groupExpensesId") long groupExpensesId);
}
