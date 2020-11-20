package shared.expenses.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.expenses.dto.ConsumerDTO;
import shared.expenses.dto.ExpenseInfoDTO;
import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Consumer;
import shared.expenses.pojo.Expense;
import shared.expenses.pojo.GroupExpenses;
import shared.expenses.repository.ConsumerRepository;
import shared.expenses.repository.ExpenseRepository;
import shared.expenses.repository.GroupExpenseRepository;
import shared.expenses.service.impl.GroupExpensesServiceImpl;

import java.util.*;

import static org.mockito.Mockito.*;

public class GroupExpensesServiceImpTest {

    static GroupExpensesService groupExpensesService;
    static GroupExpenseRepository groupExpenseRepository = mock(GroupExpenseRepository.class);
    static ExpenseRepository expenseRepository = mock(ExpenseRepository.class);
    static ConsumerRepository consumerRepository = mock(ConsumerRepository.class);

    @BeforeEach
    void init() {
        groupExpensesService = new GroupExpensesServiceImpl(expenseRepository, groupExpenseRepository, consumerRepository);
    }

    @AfterEach
    void resetMock(){
        reset(groupExpenseRepository);
        reset(expenseRepository);
        reset(consumerRepository);
    }

    @Test
    void shouldReturnGroupExpensesInfoWithoutExpenses() {
        GroupExpensesInfoDTO expected = createGroupExpensesInfo(false);

        when(groupExpenseRepository.findById(3L)).thenReturn(Optional.of(this.createGroupExpenses()));

        GroupExpensesInfoDTO result = groupExpensesService.getGroupExpenseInfo(3L);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnGroupExpensesInfoWithExpenses() {
        GroupExpensesInfoDTO expected = createGroupExpensesInfo(true);

        when(groupExpenseRepository.findById(3L))
                .thenReturn(Optional.of(this.createGroupExpenses()));
        when(expenseRepository.findAllByGroupExpensesOrderByCreateTimeDesc(3L))
                .thenReturn(this.createExpensesList());

        GroupExpensesInfoDTO result = groupExpensesService.getGroupExpenseInfo(3L);

        Assertions.assertEquals(expected, result);
    }

    private GroupExpensesInfoDTO createGroupExpensesInfo(boolean hasExpenses) {
        if (hasExpenses) {
            LinkedHashMap<Long, ExpenseInfoDTO> expensesInfoDTOList = new LinkedHashMap<>();
            expensesInfoDTOList.put(12L, ExpenseInfoDTO.builder()
                    .amount(1f)
                    .description("dd")
                    .createTime(new Date(1602922874000L))
                    .payer(ConsumerDTO.builder().id(1L).name("Alfonso Pérez").build())
                    .build());
            expensesInfoDTOList.put(13L, ExpenseInfoDTO.builder()
                    .amount(1f)
                    .description("dd")
                    .createTime(new Date(1602922874000L))
                    .payer(ConsumerDTO.builder().id(6L).name("Raúl González").build())
                    .build());

            HashMap<String, Float> balance = new HashMap<>();
            balance.put("Raúl González", 0f);
            balance.put("Alfonso Pérez", 0f);

            return GroupExpensesInfoDTO.builder()
                    .groupId(3L)
                    .groupName("Grupo 2")
                    .expensesList(expensesInfoDTOList)
                    .balance(balance)
                    .debts(new ArrayList<>())
                    .build();
        } else {
            HashMap<Long, String> consumers = new HashMap<>();
            consumers.put(1L, "Alfonso Pérez");
            consumers.put(6L, "Raúl González");

            return GroupExpensesInfoDTO.builder()
                    .groupId(3L)
                    .groupName("Grupo 2")
                    .consumers(consumers)
                    .build();
        }
    }

    private GroupExpenses createGroupExpenses() {
        HashSet<Consumer> consumers = new HashSet<>();
        consumers.add(new Consumer(1L, "Alfonso Pérez"));
        consumers.add(new Consumer(6L, "Raúl González"));

        return GroupExpenses.builder()
                .id(3L)
                .name("Grupo 2")
                .consumers(consumers)
                .build();
    }

    private List<Expense> createExpensesList() {
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(Expense.builder()
                .id(12)
                .amount(1)
                .description("dd")
                .createTime(new Date(1602922874000L))
                .payer(new Consumer(1L, "Alfonso Pérez"))
                .build());
        expenseList.add(Expense.builder()
                .id(13)
                .amount(1)
                .description("dd")
                .createTime(new Date(1602922874000L))
                .payer(new Consumer(6L, "Raúl González"))
                .build());

        return expenseList;
    }
}
