package shared.expenses.repository;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import shared.expenses.pojo.Consumer;

import java.util.List;

@Repository
public interface ConsumerRepository extends CrudRepository<Consumer, Long> {
    @NonNull
    List<Consumer> findAll();
}
