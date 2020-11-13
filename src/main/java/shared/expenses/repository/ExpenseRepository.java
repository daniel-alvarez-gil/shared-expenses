package shared.expenses.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> listOrderByCreateTime(GroupExpenses groupExpenses);
}
