package com.example.demo.validation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@lombok.AllArgsConstructor
@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
public class AddCategory {
    private int id;

    @NotBlank(message = "Thông tin bắt buộc")
    private String categoryName;

    private Boolean categoryStatus;

}
