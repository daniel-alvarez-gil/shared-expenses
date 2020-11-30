package shared.expenses.repository;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import shared.expenses.pojo.Consumer;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class ConsumerRepositoryIT {

    @Inject
    ConsumerRepository consumerRepository;

    @Test
    void shouldReturnConsumer() {
        Consumer expected = Consumer.builder()
                .id(7L).name("Francisco Buyo").groups(new HashSet<>()).build();

        Optional<Consumer> result = consumerRepository.findById(7L);

        assertEquals(expected, result.get());
    }

    @Test
    void checkIfConsumerIsUpdated() {
        //Cambiamos el nombre al usuario con ID:7 -> default name = Francisco Buyo
        Consumer expected = Consumer.builder().id(7L).name("Laura Buyo").build();

        Consumer result = consumerRepository.update(expected);

        assertEquals(expected, result);
    }
}
