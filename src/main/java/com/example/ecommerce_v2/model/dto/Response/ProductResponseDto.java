package com.example.ecommerce_v2.model.dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data

@Schema(description = "Product response")
public class ProductResponseDto {

    @Schema(description = "Product ID", example = "1")
    private Long id;
    private String name;
    private String description;
    private Long count;
    private BigDecimal price;
    private Boolean active;
}
