package com.example.demo.validation;

import jakarta.validation.constraints.*;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class AddProduct {
    private int idProduct;

    @NotBlank(message = "Thông tin bắt buộc")
    private String productName;

    @Min(value = 1, message = "Giá trị đơn giá không được nhỏ hơn 0")
    @Digits(integer = 10, fraction = 2, message = "Giá trị không chính xác, tối đa 2 chữ số thập phân")
    private double unitPrice;

    @Min(value = 1, message = "Giá trị số lượng không được nhỏ hơn 0")
    private int stockQuantity;

    private String imageUrl;

    @Size(max = 500, message = "tối đa 500 ký tự")
    private String description;

    private boolean productStatus;

    private int idCategory;
}
