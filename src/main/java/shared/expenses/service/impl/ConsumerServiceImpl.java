package shared.expenses.service.impl;

import shared.expenses.dto.ConsumerDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.repository.ConsumerRepository;
import shared.expenses.service.ConsumerService;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Optional;

@Singleton
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Transactional
    public ConsumerDTO findById(Long consumerId) {
        Optional<Consumer> optimal = consumerRepository.findById(consumerId);
        if (!optimal.isPresent()) return null;

        HashMap<Long, String> groups = new HashMap<>();
        optimal.get().getGroups().forEach(group -> groups.put(group.getId(), group.getName()));

        return ConsumerDTO.builder()
                .id(optimal.get().getId())
                .name(optimal.get().getName())
                .groups(groups)
                .build();
    }
}
