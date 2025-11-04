package au.com.mycompany.movieticketsapi.controler;

import static au.com.mycompany.movieticketsapi.TestFixtures.getReportTransactionTicketsCostsResponse;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.TRANSACTIONS_REPORT_ENDPOINT_NAME;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static au.com.mycompany.movieticketsapi.TestFixtures.getReportTransactionTicketsCostsRequest;

import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsRequest;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MovieTicketsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testReportTransactionTicketsCostsSuccess() throws Exception {
        ReportTransactionTicketsCostsRequest request = getReportTransactionTicketsCostsRequest();
        ReportTransactionTicketsCostsResponse expectedResponse = getReportTransactionTicketsCostsResponse();

        String requestStr = objectMapper.writeValueAsString(request);
        String expectedResponseStr = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(post(TRANSACTIONS_REPORT_ENDPOINT_NAME)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(requestStr))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponseStr));
    }

    @Test
    void testReportTransactionTicketsCostsFailure() throws Exception {
        ReportTransactionTicketsCostsRequest request = getReportTransactionTicketsCostsRequest();
        request.setTransactionId(null);
        String requestStr = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(TRANSACTIONS_REPORT_ENDPOINT_NAME)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(requestStr))
            .andExpect(status().isBadRequest());
    }
}
