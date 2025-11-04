package au.com.mycompany.movieticketsapi;

import static au.com.mycompany.movieticketsapi.model.TicketType.Children;
import static au.com.mycompany.movieticketsapi.model.TicketType.Senior;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.DECIMAL_SCALE;
import static java.math.RoundingMode.UP;

import au.com.mycompany.movieticketsapi.model.Customer;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsRequest;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsResponse;
import au.com.mycompany.movieticketsapi.model.Ticket;
import au.com.mycompany.movieticketsapi.model.TicketType;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestFixtures {
    public static final Integer TRANSACTION_ID = 1;
    public static final BigDecimal TOTAL_COST = BigDecimal.valueOf(27.50);
    public static final BigDecimal TOTAL_COST_CHILDREN = BigDecimal.valueOf(10.00).setScale(DECIMAL_SCALE, UP);
    public static final Integer NUMBER_OF_CHILDREN = 2;

    public static final BigDecimal TOTAL_COST_SENIORS = BigDecimal.valueOf(17.50).setScale(DECIMAL_SCALE, UP);
    public static final Integer NUMBER_OF_SENIORS = 1;

    public static final String CUSTOMER_1_NAME = "John Smith";
    public static final Integer CUSTOMER_1_AGE = 70;

    public static final String CUSTOMER_2_NAME = "Jane Doe";
    public static final Integer CUSTOMER_2_AGE = 5;

    public static final String CUSTOMER_3_NAME = "Bob Doe";
    public static final Integer CUSTOMER_3_AGE = 6;

    public static ReportTransactionTicketsCostsRequest getReportTransactionTicketsCostsRequest() {
        ReportTransactionTicketsCostsRequest request = new ReportTransactionTicketsCostsRequest();
        request.setTransactionId(TRANSACTION_ID);
        request.setCustomers(getCustomers());
        return request;
    }

    public static List<Customer> getCustomers() {
        Customer customer1 = new Customer(CUSTOMER_1_NAME, CUSTOMER_1_AGE);
        Customer customer2 = new Customer(CUSTOMER_2_NAME, CUSTOMER_2_AGE);
        Customer customer3 = new Customer(CUSTOMER_3_NAME, CUSTOMER_3_AGE);
        return List.of(customer1, customer2, customer3);
    }

    public static ReportTransactionTicketsCostsResponse getReportTransactionTicketsCostsResponse() {
        ReportTransactionTicketsCostsResponse response = new ReportTransactionTicketsCostsResponse();
        response.setTransactionId(TRANSACTION_ID);
        response.setTotalCost(TOTAL_COST.setScale(DECIMAL_SCALE, UP));
        response.setTickets(getTickets());
        return response;
    }

    public static List<Ticket> getTickets() {
        return List.of(getChildrenTicket(), getSeniorTicket());
    }

    public static Ticket getChildrenTicket() {
        return new Ticket(Children, NUMBER_OF_CHILDREN, TOTAL_COST_CHILDREN);
    }

    public static Ticket getSeniorTicket() {
        return new Ticket(Senior, NUMBER_OF_SENIORS, TOTAL_COST_SENIORS);
    }

    public static Map<TicketType, Integer> getTicketTypeAndQuantity() {
        Map<TicketType, Integer> ticketTypeAndQuantity = new TreeMap<>();
        ticketTypeAndQuantity.put(Children, NUMBER_OF_CHILDREN);
        ticketTypeAndQuantity.put(Senior, NUMBER_OF_SENIORS);
        return ticketTypeAndQuantity;
    }
}
