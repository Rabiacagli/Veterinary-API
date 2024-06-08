package dev.patika.veterinarymanagementsystem.dto.request.customer;

import dev.patika.veterinarymanagementsystem.entities.Animal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {
    @NotNull(message = "Müşteri ismi boş veya null olamaz")
    private String name;
    @Email(message = "Lütfen mail adresinizi doğru karakterlerle giriniz")
    private String mail;
    @NotNull(message = "Adres kısmı boş veya null olamaz")
    private String address;
    @NotNull(message = "Şehir kısmı boş veya null olamaz")
    private String city;
    @NotNull(message = "Telefon numarası kısmı boş veya null olamaz")
    private String phone;

}
