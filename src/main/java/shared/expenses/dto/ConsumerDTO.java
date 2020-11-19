package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ConsumerDTO {
    private Long id;
    private String name;
    private Map<Long, String> groups;
}
