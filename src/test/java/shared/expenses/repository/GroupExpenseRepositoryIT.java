package shared.expenses.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import shared.expenses.pojo.GroupExpenses;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class GroupExpenseRepositoryIT {

    @Inject
    GroupExpenseRepository groupExpenseRepository;

    @Test
    void shouldReturnGroupExpense() {
        GroupExpenses expected = createGroupExpenses();

        Optional<GroupExpenses> result = groupExpenseRepository.findById(4L);

        assertEquals(expected, result.get());
    }

    @Test
    void shouldReturnTwoItems() {
        List<GroupExpenses> result = (List<GroupExpenses>) groupExpenseRepository.findAll();
        assertEquals(3, result.size());
    }

    private GroupExpenses createGroupExpenses() {
        return GroupExpenses.builder()
                .id(4L)
                .name("Grupo 3")
                .consumers(new HashSet<>())
                .build();
    }


}
