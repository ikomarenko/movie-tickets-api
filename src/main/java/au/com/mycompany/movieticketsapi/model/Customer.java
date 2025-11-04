package au.com.mycompany.movieticketsapi.model;

import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.AGE_IS_REQUIRED_ERROR_MESSAGE;
import static au.com.mycompany.movieticketsapi.constants.MovieTicketsConstants.NAME_IS_REQUIRED_ERROR_MESSAGE;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Customer {
    @NotEmpty(message = NAME_IS_REQUIRED_ERROR_MESSAGE)
    private String name;

    @NotNull(message = AGE_IS_REQUIRED_ERROR_MESSAGE)
    @Min(value = 0, message = "Age must be at least 0 years")
    @Max(value = 125, message = "Age must be at most 125 years")
    private Integer age;
}
