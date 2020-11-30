package shared.expenses.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import shared.expenses.dto.ConsumerDTO;
import shared.expenses.service.ConsumerService;

import java.util.List;

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

    @Get("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConsumerDTO> getAllConsumers() {
        return consumerService.findAll();
    }
}
