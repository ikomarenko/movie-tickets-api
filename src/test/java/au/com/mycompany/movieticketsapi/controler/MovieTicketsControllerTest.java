package au.com.mycompany.movieticketsapi.controler;

import static au.com.mycompany.movieticketsapi.TestFixtures.getReportTransactionTicketsCostsResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static au.com.mycompany.movieticketsapi.TestFixtures.getReportTransactionTicketsCostsRequest;

import au.com.mycompany.movieticketsapi.controller.MovieTicketsController;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsRequest;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsResponse;
import au.com.mycompany.movieticketsapi.service.MovieTicketsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieTicketsControllerTest {

    @Mock
    private MovieTicketsService movieTicketsService;

    @InjectMocks
    private MovieTicketsController controller;

    @Test
    void testReportTransactionTicketsCosts() {
        ReportTransactionTicketsCostsRequest request = getReportTransactionTicketsCostsRequest();
        ReportTransactionTicketsCostsResponse response = getReportTransactionTicketsCostsResponse();

        when(movieTicketsService.reportTransactionTicketsCosts(request)).thenReturn(response);
        ReportTransactionTicketsCostsResponse actualResponse = controller.reportTransactionTicketsCosts(request);

        assertEquals(response, actualResponse);
        verify(movieTicketsService).reportTransactionTicketsCosts(request);
    }
}
