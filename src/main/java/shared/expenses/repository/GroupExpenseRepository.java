package shared.expenses.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;

import java.util.Optional;

@Repository
public interface GroupExpenseRepository extends CrudRepository<GroupExpenses, Long> {

    Optional<GroupExpenses> findById(Long group_id);
}
