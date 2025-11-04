package au.com.mycompany.movieticketsapi.service;

import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.ADULT_TICKET_COST;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.CHILDREN_TICKET_COST_PER_CHILD;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.DECIMAL_SCALE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.EIGHTEEN;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.ELEVEN;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.GROUP_OF_CHILDREN_MIN_RULE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.GROUP_OF_CHILDREN_TICKET_COST_PER_CHILD;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.ONE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.SENIOR_TICKET_COST;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.SIXTY_FIVE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.TEEN_TICKET_COST;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.TICKET_TYPE_IS_INVALID_ERROR_MESSAGE;
import static au.com.mycompany.movieticketsapi.model.TicketType.Children;
import static java.util.Optional.ofNullable;
import static au.com.mycompany.movieticketsapi.model.TicketType.Adult;
import static au.com.mycompany.movieticketsapi.model.TicketType.Senior;
import static au.com.mycompany.movieticketsapi.model.TicketType.Teen;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.AGE_IS_INVALID_ERROR_MESSAGE;
import static java.math.RoundingMode.UP;

import au.com.mycompany.movieticketsapi.exceptions.SystemException;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsRequest;
import au.com.mycompany.movieticketsapi.model.ReportTransactionTicketsCostsResponse;
import au.com.mycompany.movieticketsapi.model.Ticket;
import au.com.mycompany.movieticketsapi.model.TicketType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieTicketsService {

    public ReportTransactionTicketsCostsResponse reportTransactionTicketsCosts(ReportTransactionTicketsCostsRequest reportTransactionTicketsCostsRequest) {
        Map<TicketType, Integer> ticketTypeAndQuantity = getTicketTypeAndQuantity(reportTransactionTicketsCostsRequest);
        ReportTransactionTicketsCostsResponse reportTransactionTicketsCostsResponse = new ReportTransactionTicketsCostsResponse();
        reportTransactionTicketsCostsResponse.setTransactionId(reportTransactionTicketsCostsRequest.getTransactionId());

        final BigDecimal[] totalCost = {BigDecimal.ZERO};
        ticketTypeAndQuantity.forEach((key, value) -> {
            Ticket ticket = getTicket(key, value);
            reportTransactionTicketsCostsResponse.getTickets().add(ticket);
            totalCost[0] = totalCost[0].add(ticket.getTotalCost());
        });
        reportTransactionTicketsCostsResponse.setTotalCost(totalCost[0].setScale(DECIMAL_SCALE, UP));
        return reportTransactionTicketsCostsResponse;
    }

    protected Map<TicketType, Integer> getTicketTypeAndQuantity(ReportTransactionTicketsCostsRequest reportTransactionTicketsCostsRequest) {
        Map<TicketType, Integer> ticketTypeAndQuantity = new TreeMap<>();
        ofNullable(reportTransactionTicketsCostsRequest)
            .map(ReportTransactionTicketsCostsRequest::getCustomers)
            .filter(ObjectUtils::isNotEmpty)
            .stream()
            .flatMap(Collection::stream)
            .forEach(customer -> addTicketToMapAndIncrementQuantity(determineTicketType(customer.getAge()), ticketTypeAndQuantity));
        return ticketTypeAndQuantity;
    }

    protected TicketType determineTicketType(Integer customerAge) {
        return switch (customerAge) {
            case Integer age when (age >= EIGHTEEN && age < SIXTY_FIVE) -> Adult;
            case Integer age when (age >= SIXTY_FIVE) -> Senior;
            case Integer age when (age >= ELEVEN && age < EIGHTEEN) -> Teen;
            case Integer age when (age < ELEVEN) -> Children;
            default -> throw new SystemException(AGE_IS_INVALID_ERROR_MESSAGE);
        };
    }

    protected Ticket getTicket(TicketType ticketType, Integer ticketQuantity) {
        BigDecimal quantity = BigDecimal.valueOf(ticketQuantity);
        BigDecimal totalCost = switch (ticketType) {
            case TicketType type when (Children.equals(type) && ticketQuantity >= GROUP_OF_CHILDREN_MIN_RULE) ->
                GROUP_OF_CHILDREN_TICKET_COST_PER_CHILD.multiply(quantity);
            case TicketType type when (Children.equals(type) && ticketQuantity < GROUP_OF_CHILDREN_MIN_RULE) ->
                CHILDREN_TICKET_COST_PER_CHILD.multiply(quantity);
            case Adult -> ADULT_TICKET_COST.multiply(quantity);
            case Senior -> SENIOR_TICKET_COST.multiply(quantity);
            case Teen -> TEEN_TICKET_COST.multiply(quantity);
            default -> throw new SystemException(TICKET_TYPE_IS_INVALID_ERROR_MESSAGE);
        };
        return new Ticket(ticketType, ticketQuantity, totalCost.setScale(DECIMAL_SCALE, UP));
    }

    protected void addTicketToMapAndIncrementQuantity(TicketType ticketType, Map<TicketType, Integer> ticketTypeAndQuantity) {
        if (ticketTypeAndQuantity.containsKey(ticketType)) {
            ticketTypeAndQuantity.put(ticketType, ticketTypeAndQuantity.get(ticketType) + ONE);
        } else {
            ticketTypeAndQuantity.put(ticketType, ONE);
        }
    }
}
