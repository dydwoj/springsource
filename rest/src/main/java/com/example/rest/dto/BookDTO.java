package com.example.rest.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BookDTO {

    private Long code;
    @NotBlank(message = "Please, Fill in the title")
    private String title;

    @NotBlank(message = "Please, Fill in the Author")
    private String author;

    @NotNull(message = "Please, Fill in the Price")
    private int price;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
