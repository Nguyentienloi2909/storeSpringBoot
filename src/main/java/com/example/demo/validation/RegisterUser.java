package com.example.demo.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@lombok.AllArgsConstructor
@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
public class RegisterUser {
    private int id;
    @NotBlank(message = "Thông tin bắt buộc")
    private String fullName;

    @NotBlank(message = "Thông tin bắt buộc")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Thông tin bắt buộc")
    @Pattern(regexp = "^(0[3|5|7|8|9]{2}[0-9]{7,9})$",
            message = "vui lòng nhập đúng số điện thoại (vd: 0971722345)")
    private String phoneNumber;

    @Size(min = 8, message = "Độ dài tối thiểu là 8")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).+$",
            message = "Mật khẩu phải chứa chữ cái, chữ số và ký tự đặc biệt")
    private String password;

    private boolean enble;
}
