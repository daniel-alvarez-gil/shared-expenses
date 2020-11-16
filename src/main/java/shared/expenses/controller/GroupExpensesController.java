package shared.expenses.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Expense;
import shared.expenses.service.GroupExpensesService;

import java.util.List;

@Controller("/ge/{groupExpensesId}")
public class GroupExpensesController {

    private final GroupExpensesService groupExpensesService;

    public GroupExpensesController(GroupExpensesService groupExpensesService) {
        this.groupExpensesService = groupExpensesService;
    }

    @Get("/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public GroupExpensesInfoDTO getExpenses(Long groupExpensesId) {
        return groupExpensesService.listOrderByCreateTime(groupExpensesId);
    }

    @Post("/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public String createExpense(Expense expense) {
        return null;
    }
}
