package shared.expenses.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.pojo.Consumer;

import java.util.List;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
    List<Consumer> findAll();
}
