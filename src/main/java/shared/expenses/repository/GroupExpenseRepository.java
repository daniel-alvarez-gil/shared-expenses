package shared.expenses.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.pojo.GroupExpenses;

@Repository
public interface GroupExpenseRepository extends CrudRepository<GroupExpenses, Long> {
}
