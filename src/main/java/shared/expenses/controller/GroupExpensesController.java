package shared.expenses.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import shared.expenses.dto.GroupExpensesInfoDTO;
import shared.expenses.pojo.Expense;
import shared.expenses.service.GroupExpensesService;

@Controller("/ge/{groupExpensesId}")
public class GroupExpensesController {

    private final GroupExpensesService groupExpensesService;

    public GroupExpensesController(GroupExpensesService groupExpensesService) {
        this.groupExpensesService = groupExpensesService;
    }

    @Get("/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public GroupExpensesInfoDTO getExpenses(Long groupExpensesId) {
        return groupExpensesService.getGroupExpenseInfo(groupExpensesId);
    }

    @Post("/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<GroupExpensesInfoDTO> createExpense(Long groupExpensesId, @Body Expense expense) {
        expense.setId(null);
        return HttpResponse
                .created(groupExpensesService.addExpense(groupExpensesId, expense));
    }

    @Put("/consumer/{consumerName}")
    public HttpResponse<GroupExpensesInfoDTO> addConsumerToGroup(Long groupExpensesId, String consumerName) {
        return HttpResponse
                .created(groupExpensesService.addConsumerToGroup(groupExpensesId, consumerName));
    }
}
