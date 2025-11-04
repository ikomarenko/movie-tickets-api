package au.com.mycompany.movieticketsapi.service;

import static au.com.mycompany.movieticketsapi.TestFixtures.CUSTOMER_1_AGE;
import static au.com.mycompany.movieticketsapi.TestFixtures.CUSTOMER_2_AGE;
import static au.com.mycompany.movieticketsapi.TestFixtures.NUMBER_OF_CHILDREN;
import static au.com.mycompany.movieticketsapi.TestFixtures.NUMBER_OF_SENIORS;
import static au.com.mycompany.movieticketsapi.TestFixtures.getChildrenTicket;
import static au.com.mycompany.movieticketsapi.TestFixtures.getReportTransactionTicketsCostsRequest;
import static au.com.mycompany.movieticketsapi.TestFixtures.getReportTransactionTicketsCostsResponse;
import static au.com.mycompany.movieticketsapi.TestFixtures.getSeniorTicket;
import static au.com.mycompany.movieticketsapi.TestFixtures.getTicketTypeAndQuantity;
import static au.com.mycompany.movieticketsapi.model.TicketType.Children;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static au.com.mycompany.movieticketsapi.model.TicketType.Senior;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.ONE;

import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsRequest;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsResponse;
import au.com.mycompany.movieticketsapi.model.Ticket;
import au.com.mycompany.movieticketsapi.model.TicketType;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieTicketsServiceTest {
    public static final Integer FOUR = 4;
    public static final Integer FIVE = 5;

    @InjectMocks
    private MovieTicketsService service;

    static Stream<Pair<Integer, TicketType>> ageTicketTypeData() {
        return Stream.of(
            Pair.of(CUSTOMER_1_AGE, Senior),
            Pair.of(CUSTOMER_2_AGE, Children)
        );
    }

    static Stream<Triple<TicketType, Integer, Ticket>> ticketData() {
        return Stream.of(
            Triple.of(Senior, NUMBER_OF_SENIORS, getSeniorTicket()),
            Triple.of(Children, NUMBER_OF_CHILDREN, getChildrenTicket())
        );
    }

    @Test
    void testReportTransactionTicketsCosts() {
        ReportTransactionTicketsCostsRequest request = getReportTransactionTicketsCostsRequest();
        ReportTransactionTicketsCostsResponse expectedResponse = getReportTransactionTicketsCostsResponse();

        ReportTransactionTicketsCostsResponse actualResponse = service.reportTransactionTicketsCosts(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetTicketTypeAndQuantity() {
        ReportTransactionTicketsCostsRequest request = getReportTransactionTicketsCostsRequest();
        Map<TicketType, Integer> expectedResponse = getTicketTypeAndQuantity();

        Map<TicketType, Integer> actualResponse = service.getTicketTypeAndQuantity(request);
        assertEquals(expectedResponse, actualResponse);
    }

    @ParameterizedTest()
    @MethodSource("ageTicketTypeData")
    void testDetermineTicketType(Pair<Integer, TicketType> ageTicketTypeData) {
        TicketType actualTicketType = service.determineTicketType(ageTicketTypeData.getLeft());
        assertEquals(ageTicketTypeData.getRight(), actualTicketType);
    }

    @ParameterizedTest()
    @MethodSource("ticketData")
    void testGetTicket(Triple<TicketType, Integer, Ticket> ticketData) {
        Ticket actualTicket = service.getTicket(ticketData.getLeft(), ticketData.getMiddle());
        assertEquals(ticketData.getRight(), actualTicket);
    }

    @Test
    void testAddTicketToMapAndIncrementQuantityWhenDoesNotExistInMap() {
        Map<TicketType, Integer> ticketTypeAndQuantity = new TreeMap<>();
        service.addTicketToMapAndIncrementQuantity(Senior, ticketTypeAndQuantity);

        assertEquals(ONE, ticketTypeAndQuantity.size());
        assertEquals(Senior, ticketTypeAndQuantity.keySet().stream().findFirst().orElse(null));
        assertEquals(ONE, ticketTypeAndQuantity.values().stream().findFirst().orElse(null));
    }

    @Test
    void testAddTicketToMapAndIncrementQuantityWhenAlreadyExistsInMap() {
        Map<TicketType, Integer> ticketTypeAndQuantity = new TreeMap<>();
        ticketTypeAndQuantity.put(Senior, FOUR);
        service.addTicketToMapAndIncrementQuantity(Senior, ticketTypeAndQuantity);

        assertEquals(ONE, ticketTypeAndQuantity.size());
        assertEquals(Senior, ticketTypeAndQuantity.keySet().stream().findFirst().orElse(null));
        assertEquals(FIVE, ticketTypeAndQuantity.values().stream().findFirst().orElse(null));
    }
}
