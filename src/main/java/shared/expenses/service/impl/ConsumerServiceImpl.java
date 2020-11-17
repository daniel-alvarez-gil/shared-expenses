package shared.expenses.service.impl;

import shared.expenses.dto.ConsumerDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.repository.ConsumerRepository;
import shared.expenses.service.ConsumerService;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Singleton
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerServiceImpl(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    @Transactional
    public Set<ConsumerDTO> findAll() {
        Set<ConsumerDTO> result = new HashSet<>();
        for(Consumer consumer: consumerRepository.findAll()){
            HashMap<Long, String> groups = new HashMap<>();
            consumer.getGroups().forEach(group -> groups.put(group.getId(), group.getName()));

            ConsumerDTO consumerDTO = ConsumerDTO.builder()
                    .id(consumer.getId())
                    .name(consumer.getName())
                    .groups(groups)
                    .build();

            result.add(consumerDTO);
        }

        return result;
    }
}
