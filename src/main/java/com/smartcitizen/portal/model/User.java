package com.smartcitizen.portal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Data
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long userid;

@NotBlank(message ="Fullname can not be blank")
private String fullname;

@Email(message = "Invalid email")
@Column(unique = true)
@NotBlank(message ="email can not be blank")
private String email;

@NotBlank(message="password can not br blank")
private String password; 

@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
@NotBlank(message="phoneno can not br blank")
private String phone;

@NotBlank(message="Role can not br blank")
private String role;

}