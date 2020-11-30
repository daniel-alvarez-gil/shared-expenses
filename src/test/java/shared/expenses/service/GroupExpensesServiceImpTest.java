package shared.expenses.service;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GroupExpensesServiceImpTest {

    GroupExpensesService groupExpensesService;
    GroupExpenseRepository groupExpenseRepository = mock(GroupExpenseRepository.class);
    ExpenseRepository expenseRepository = mock(ExpenseRepository.class);
    ConsumerRepository consumerRepository = mock(ConsumerRepository.class);

    @BeforeEach
    void init() {
        groupExpensesService = new GroupExpensesServiceImpl(expenseRepository, groupExpenseRepository, consumerRepository);
    }

    @Test
    void shouldReturnGroupExpensesInfoWithoutExpenses() {
        GroupExpensesInfoDTO expected = createGroupExpensesInfo(false);

        when(groupExpenseRepository.findById(3L)).thenReturn(Optional.of(this.createGroupExpenses()));

        GroupExpensesInfoDTO result = groupExpensesService.getGroupExpenseInfo(3L);

        assertEquals(expected.getGroupName(), result.getGroupName());
    }

    @Test
    void shouldReturnGroupExpensesInfoWithExpenses() {
        GroupExpensesInfoDTO expected = createGroupExpensesInfo(true);

        when(groupExpenseRepository.findById(3L))
                .thenReturn(Optional.of(this.createGroupExpenses()));
        when(expenseRepository.findAllByGroupExpensesOrderByCreateTimeDesc(3L))
                .thenReturn(this.createExpensesList());

        GroupExpensesInfoDTO result = groupExpensesService.getGroupExpenseInfo(3L);

        assertEquals(expected.getExpensesList(), result.getExpensesList());
    }

    @Test
    void shouldReturnGroupExpensesInfoWithANewConsumer() {
        GroupExpensesInfoDTO group = createGroupExpensesInfo(false);
        Consumer newConsumer = Consumer.of(8L, "Juan Lopez", new HashSet<>());
        group.getConsumers().addLast(ConsumerDTO.builder().id(newConsumer.getId()).name(newConsumer.getName()).build());

        when(groupExpenseRepository.findById(3L))
                .thenReturn(Optional.of(this.createGroupExpenses()));
        when(consumerRepository.save(newConsumer))
                .thenReturn(newConsumer);

        GroupExpensesInfoDTO result = groupExpensesService.addConsumerToGroup(3L, newConsumer.getName());

        assertEquals(3, result.getConsumers().size());

    }

    @Test
    void shouldReturnNullBecauseGroupExpenseNotExist() {
        when(groupExpenseRepository.findById(3L))
                .thenReturn(Optional.empty());

        GroupExpensesInfoDTO result = groupExpensesService.getGroupExpenseInfo(3L);

        assertNull(result);

    }

    @Test
    void shouldReturnGroupExpensesInfoWithANewExpense() {
        GroupExpensesInfoDTO expected = createGroupExpensesInfo(true);

        when(groupExpenseRepository.findById(3L))
                .thenReturn(Optional.of(this.createGroupExpenses()));

        when(expenseRepository.findAllByGroupExpensesOrderByCreateTimeDesc(3L))
                .thenReturn(createExpensesList());

        GroupExpensesInfoDTO result = groupExpensesService.addExpense(3L, Expense.builder().build());

        Assertions.assertEquals(expected.getExpensesList().size(), result.getExpensesList().size());
    }

    private GroupExpensesInfoDTO createGroupExpensesInfo(boolean hasExpenses) {
        if (hasExpenses) {
            LinkedList<ConsumerDTO> consumers = new LinkedList<>();
            List<ExpenseInfoDTO> expensesInfoDTOList = new LinkedList<>();

            consumers.add(0, ConsumerDTO.builder().id(1L).name("Alfonso Pérez").build());
            consumers.add(1, ConsumerDTO.builder().id(6L).name("Raúl González").build());

            expensesInfoDTOList.add(ExpenseInfoDTO.builder()
                    .id(12L)
                    .amount(1f)
                    .description("dd")
                    .createTime(new Date(1602922874000L))
                    .payer(ConsumerDTO.builder().id(1L).name("Alfonso Pérez").build())
                    .build());
            expensesInfoDTOList.add(ExpenseInfoDTO.builder()
                    .id(13L)
                    .amount(1f)
                    .description("dd")
                    .createTime(new Date(1602922874000L)) //Timespamp
                    .payer(ConsumerDTO.builder().id(6L).name("Raúl González").build())
                    .build());

            HashMap<String, Float> balance = new HashMap<>();
            balance.put("Raúl González", 0f);
            balance.put("Alfonso Pérez", 0f);

            return GroupExpensesInfoDTO.builder()
                    .groupId(3L)
                    .groupName("Grupo 2")
                    .consumers(consumers)
                    .expensesList(expensesInfoDTOList)
                    .balance(balance)
                    .debts(new ArrayList<>())
                    .build();
        } else {
            LinkedList<ConsumerDTO> consumers = new LinkedList<>();
            consumers.addLast(ConsumerDTO.builder().id(6L).name("Raúl González").build());
            consumers.addLast(ConsumerDTO.builder().id(1L).name("Alfonso Pérez").build());

            return GroupExpensesInfoDTO.builder()
                    .groupId(3L)
                    .groupName("Grupo 2")
                    .consumers(consumers)
                    .build();
        }
    }

    private GroupExpenses createGroupExpenses() {
        HashSet<Consumer> consumers = new HashSet<>();
        consumers.add(new Consumer(6L, "Raúl González"));
        consumers.add(new Consumer(1L, "Alfonso Pérez"));

        return GroupExpenses.builder()
                .id(3L)
                .name("Grupo 2")
                .consumers(consumers)
                .build();
    }

    private LinkedList<Expense> createExpensesList() {
        LinkedList<Expense> expenseList = new LinkedList<>();
        expenseList.addLast(Expense.builder()
                .id(12L)
                .amount(1)
                .description("dd")
                .createTime(new Date(1602922874000L))
                .payer(new Consumer(1L, "Alfonso Pérez"))
                .groupExpenses(createGroupExpenses())
                .build());
        expenseList.addLast(Expense.builder()
                .id(13L)
                .amount(1)
                .description("dd")
                .createTime(new Date(1602922874000L))
                .payer(new Consumer(6L, "Raúl González"))
                .groupExpenses(createGroupExpenses())
                .build());

        return expenseList;
    }
}
