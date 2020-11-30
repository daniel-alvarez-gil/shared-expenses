package shared.expenses.service;

import shared.expenses.dto.ConsumerDTO;

import java.util.List;

public interface ConsumerService {
    ConsumerDTO findById(Long consumerId);
    List<ConsumerDTO> findAll();
}
