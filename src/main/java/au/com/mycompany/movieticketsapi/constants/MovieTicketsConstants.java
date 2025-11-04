package au.com.mycompany.movieticketsapi.constants;

import java.math.BigDecimal;

public class MovieTicketsConstants {

    public static final String TRANSACTIONS_REPORT_ENDPOINT_NAME = "/transactions/report";

    public static final Integer ONE = 1;

    public static final Integer DECIMAL_SCALE = 2;
    private static final BigDecimal A_100_PERCENT = BigDecimal.valueOf(100);
    private static final BigDecimal SENIOR_TICKET_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(30);
    private static final BigDecimal GROUP_OF_CHILDREN_DISCOUNT_PERCENTAGE = BigDecimal.valueOf(25);
    public static final Integer GROUP_OF_CHILDREN_MIN_RULE = 3;

    public static final BigDecimal ADULT_TICKET_COST = BigDecimal.valueOf(25);
    public static final BigDecimal SENIOR_TICKET_COST = ADULT_TICKET_COST.subtract(ADULT_TICKET_COST.multiply(SENIOR_TICKET_DISCOUNT_PERCENTAGE).divide(A_100_PERCENT));
    public static final BigDecimal TEEN_TICKET_COST = BigDecimal.valueOf(12);
    public static final BigDecimal CHILDREN_TICKET_COST_PER_CHILD = BigDecimal.valueOf(5);
    public static final BigDecimal GROUP_OF_CHILDREN_TICKET_COST_PER_CHILD = CHILDREN_TICKET_COST_PER_CHILD.subtract(CHILDREN_TICKET_COST_PER_CHILD.multiply(GROUP_OF_CHILDREN_DISCOUNT_PERCENTAGE).divide(A_100_PERCENT));

    public static final String AGE_IS_INVALID_ERROR_MESSAGE = "Age is Invalid";
    public static final String TICKET_TYPE_IS_INVALID_ERROR_MESSAGE = "Ticket Type is Invalid";

    public static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected Error";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation Error";
    public static final String SYSTEM_ERROR_MESSAGE = "System Error";

    public static final String NAME_IS_REQUIRED_ERROR_MESSAGE = "Name field is required";
    public static final String AGE_IS_REQUIRED_ERROR_MESSAGE = "Age field is required";
    public static final String TRANSACTION_ID_IS_REQUIRED_ERROR_MESSAGE = "Transaction Id field is required";
    public static final String CUSTOMERS_IS_REQUIRED_ERROR_MESSAGE = "Customers field is required";

    public static final String COLON = ":";
    public static final String SEMI_COLON = ";";

    public static final Integer SIXTY_FIVE = 65;
    public static final Integer ELEVEN = 11;
    public static final Integer EIGHTEEN = 18;
}
