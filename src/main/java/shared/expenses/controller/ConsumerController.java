package shared.expenses.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import shared.expenses.pojo.Consumer;
import shared.expenses.repository.ConsumerRepository;

import java.util.ArrayList;
import java.util.List;

@Controller("/consumer")
public class ConsumerController {

    private final ConsumerRepository consumerRepository;

    public ConsumerController(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Consumer> getConsumers() {
        List<Consumer> result = consumerRepository.findAll();
        return (!result.isEmpty()) ? result : new ArrayList<>();
    }

    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String createConsumer(Consumer consumer) {
        return null;
    }
}
