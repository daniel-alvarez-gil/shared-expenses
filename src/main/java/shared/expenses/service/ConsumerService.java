package shared.expenses.service;

import shared.expenses.dto.ConsumerDTO;

public interface ConsumerService {
    ConsumerDTO findById(Long consumerId);
}
