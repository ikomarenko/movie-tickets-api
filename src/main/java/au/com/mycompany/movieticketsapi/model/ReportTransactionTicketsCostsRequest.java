package au.com.mycompany.movieticketsapi.model;

import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.CUSTOMERS_IS_REQUIRED_ERROR_MESSAGE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.TRANSACTION_ID_IS_REQUIRED_ERROR_MESSAGE;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportTransactionTicketsCostsRequest {
    @NotNull(message = TRANSACTION_ID_IS_REQUIRED_ERROR_MESSAGE)
    private Integer transactionId;

    @Valid
    @NotEmpty(message = CUSTOMERS_IS_REQUIRED_ERROR_MESSAGE)
    private List<Customer> customers;
}
