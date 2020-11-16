package shared.expenses.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import shared.expenses.dto.ConsumerDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.service.ConsumerService;

import java.util.Set;

@Controller("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ConsumerDTO> getConsumers() {
        return consumerService.findAll();
    }

    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String createConsumer(Consumer consumer) {
        return null;
    }
}
