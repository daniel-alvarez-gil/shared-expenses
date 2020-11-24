package shared.expenses.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.pojo.Consumer;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
}
