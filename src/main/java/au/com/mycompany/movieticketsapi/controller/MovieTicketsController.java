package au.com.mycompany.movieticketsapi.controller;

import static org.springframework.http.HttpStatus.OK;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.TRANSACTIONS_REPORT_ENDPOINT_NAME;

import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsRequest;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsResponse;
import au.com.mycompany.movieticketsapi.service.MovieTicketsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class MovieTicketsController {
    private MovieTicketsService movieTicketsService;

    @PostMapping(path = TRANSACTIONS_REPORT_ENDPOINT_NAME)
    @ResponseStatus(OK)
    public ReportTransactionTicketsCostsResponse reportTransactionTicketsCosts(
        @Valid @RequestBody final ReportTransactionTicketsCostsRequest reportTransactionTicketsCostsRequest) {
        return movieTicketsService.reportTransactionTicketsCosts(reportTransactionTicketsCostsRequest);
    }
}
