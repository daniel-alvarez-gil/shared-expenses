package shared.expenses.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import shared.expenses.dto.ConsumerDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.service.ConsumerService;

@Controller("/consumer")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Get("/{consumerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ConsumerDTO getConsumer(Long consumerId) {
        return consumerService.findById(consumerId);
    }

    @Post("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String createConsumer(Consumer consumer) {
        return null;
    }
}
