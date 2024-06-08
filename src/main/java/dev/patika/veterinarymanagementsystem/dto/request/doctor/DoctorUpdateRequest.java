package dev.patika.veterinarymanagementsystem.dto.request.doctor;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateRequest {
    @Positive(message = "Id pozitif bir sayı olmalıdır")
    private Long doctorId;
    @NotNull(message = "Doktorun ismi boş veya null olamaz")
    private String name;
    @NotNull(message = "Telefon numarası boş veya null olamaz")
    private String phone;
    @Email(message = "Lütfen mail adresinizi doğru karakterler ile giriniz")
    private String mail;
    @NotNull(message = "Adres boş veya null olamaz")
    private String address;
    @NotNull(message = "Şehir boş veya null olamaz")
    private String city;

}
