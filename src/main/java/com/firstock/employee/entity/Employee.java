package com.firstock.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
@Data
@AllArgsConstructor(staticName ="build")
@NoArgsConstructor
@Validated
@Table(name = "employees")
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "First Name not be null ")
    private String firstName;
    @NotNull(message = "Last Name not be null ")
    private String lsatName;
    @Email(message = "enter the valid Email address")
    private String email;
    @NotNull(message = "Enter the salary")
    private String salary;

}
