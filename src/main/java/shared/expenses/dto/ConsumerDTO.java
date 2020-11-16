package shared.expenses.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ConsumerDTO {
    private Long id;
    private String name;
    private Map<Long, String> groups;
}
