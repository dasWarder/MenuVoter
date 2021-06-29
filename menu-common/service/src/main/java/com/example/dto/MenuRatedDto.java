package com.example.dto;

import com.example.menu.Dish;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MenuRatedDto extends MenuDto {

    @JsonProperty(index = 1)
    @NotNull(message =
            "The id must be not null")
    private String id;

    @NotNull(message =
            "Creating date must be not Null")
    @JsonFormat(pattern = "yyyy-MM-dd",
            shape = JsonFormat.Shape.STRING)
    private LocalDate creatingDate;


    @NotNull(message =
            "The rate must be not null")
    @Min(value = 1, message =
            "The rate can't be less that 1")
    @Max(value = 10, message =
            "The rate can't be greater than 10")
    private Double rate;


    @NotNull(message =
            "The votes must be not null")
    @Min(value = 0, message =
            "The rate can't be less that 0")
    private Long votesCount;


    @Builder
    public MenuRatedDto(LocalDate creatingDate, List<Dish> dishes, String id,
                        Double rate, Long votesCount) {
        super(dishes);
        this.id = id;
        this.creatingDate = creatingDate;
        this.rate = rate;
        this.votesCount = votesCount;
    }
}
