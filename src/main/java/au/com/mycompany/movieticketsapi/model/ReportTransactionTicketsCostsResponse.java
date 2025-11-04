package au.com.mycompany.movieticketsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportTransactionTicketsCostsResponse {
    private Integer transactionId;
    private List<Ticket> tickets = new ArrayList<>();
    private BigDecimal totalCost;
}
