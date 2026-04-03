package com.example.ecommerce_v2.model.dto.Request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Product create request")
public class ProductRequestDto  {
    @Schema(description = "Product name", example = "Laptop")
    private String name;
    private String description;
    private Long count;
    @Schema(description = "Product price", example = "1500.0")
    private BigDecimal price;
    private Boolean active;

}

