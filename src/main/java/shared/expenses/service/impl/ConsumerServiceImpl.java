package shared.expenses.service.impl;

import shared.expenses.pojo.Consumer;
import shared.expenses.repository.ConsumerRepository;
import shared.expenses.service.ConsumerService;

import java.util.ArrayList;
import java.util.List;

public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public List<Consumer> findAll() {
        List<Consumer> result = consumerRepository.findAll();
        return (!result.isEmpty()) ? result : new ArrayList<>();
    }
}
