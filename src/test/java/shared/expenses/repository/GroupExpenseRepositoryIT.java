package shared.expenses.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.expenses.pojo.Consumer;
import shared.expenses.pojo.GroupExpenses;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@MicronautTest
public class GroupExpenseRepositoryIT {

    @Inject
    GroupExpenseRepository groupExpenseRepository;
    @Inject
    ConsumerRepository consumerRepository;

    @Test
    void shouldReturnGroupExpense() {
        GroupExpenses expected = createGroupExpenses();

        Optional<GroupExpenses> result = groupExpenseRepository.findById(4L);

        Assertions.assertEquals(expected, result.get());
    }

    @Test
    void shouldReturnTwoItems() {
        List<GroupExpenses> result = (List<GroupExpenses>) groupExpenseRepository.findAll();
        Assertions.assertEquals(3, result.size());
    }

    private GroupExpenses createGroupExpenses() {
        return GroupExpenses.builder()
                .id(4L)
                .name("Grupo 3")
                .consumers(new HashSet<>())
                .build();
    }


}
