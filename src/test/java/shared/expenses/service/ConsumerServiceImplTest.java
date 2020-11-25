package shared.expenses.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.expenses.dto.ConsumerDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.pojo.GroupExpenses;
import shared.expenses.repository.ConsumerRepository;
import shared.expenses.service.impl.ConsumerServiceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ConsumerServiceImplTest {

    static ConsumerService consumerService;
    static ConsumerRepository consumerRepository = mock(ConsumerRepository.class);

    @BeforeEach
    void init() {
        consumerService = new ConsumerServiceImpl(consumerRepository);
    }

    @AfterAll
    static void resetMock() {
        reset(consumerRepository);
    }

    @Test
    void shouldReturnNullBecauseConsumerNotExist() {
        when(consumerRepository.findById(8L))
                .thenReturn(Optional.empty());

        ConsumerDTO result = consumerService.findById(8L);

        Assertions.assertNull(result);

    }

    @Test
    void shouldReturnConsumerDTO() {
        ConsumerDTO expected = createConsumerDTO();

        when(consumerRepository.findById(8L))
                .thenReturn(Optional.of(createConsumer()));

        ConsumerDTO result = consumerService.findById(8L);

        Assertions.assertEquals(expected, result);

    }

    private ConsumerDTO createConsumerDTO() {
        HashMap<Long, String> groups = new HashMap<>();
        groups.put(1L, "Grupo 1");
        groups.put(2L, "Grupo 2");

        return ConsumerDTO.builder()
                .id(1L)
                .name("Pepe García")
                .groups(groups)
                .build();
    }

    private Consumer createConsumer() {
        HashSet<GroupExpenses> groupExpenses = new HashSet<>();
        groupExpenses.add(GroupExpenses.builder().id(1L).name("Grupo 1").build());
        groupExpenses.add(GroupExpenses.builder().id(2L).name("Grupo 2").build());

        return Consumer.builder()
                .id(1L)
                .name("Pepe García")
                .groups(groupExpenses)
                .build();
    }
}
