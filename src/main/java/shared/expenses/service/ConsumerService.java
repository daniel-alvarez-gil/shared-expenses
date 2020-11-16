package shared.expenses.service;

import shared.expenses.dto.ConsumerDTO;

import java.util.Set;

public interface ConsumerService {
    Set<ConsumerDTO> findAll();
}
