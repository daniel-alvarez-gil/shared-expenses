package shared.expenses.repository;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    @Query(value = "SELECT * FROM expense ORDER BY create_time DESC", nativeQuery = true)
    List<Expense> findAllOrderByCreateTimeDesc(GroupExpenses groupExpenses);
}
