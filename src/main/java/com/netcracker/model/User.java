package com.netcracker.model;


import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.InitBinder;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@Entity
@Table(name="testusers" ,schema = "public")
public class User {
    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "Field name can't be empty")
    @Size(min=2,max=25, message = "Minsize=2 , maxsize=25")
    @CsvBindByName(column = "f_name")
    private String fname;


    @NotEmpty(message = "Field lastname can't be empty")
    @Size(min=2,max=40, message = "Minsize=2 , maxsize=40")
    @CsvBindByName(column = "l_name")
    private String lname;


    @CsvBindByName(column = "patronymic")
    private String patronymic;


    @Min(value=0,message = "age should be upper 0")
    @CsvBindByName(column = "age")
    private int age;


    @NotEmpty(message = "Please, enter your email address")
    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b",message = "It is not email")
    @CsvBindByName(column = "mailadress")
    private String mailadress;


    @CsvBindByName(column = "workplace")
    private String workplace;


    @CsvBindByName(column = "salary")
    private double salary;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + age +
                ", mailadress='" + mailadress + '\'' +
                ", workplace='" + workplace + '\'' +
                ", salary=" + salary +
                '}';
    }
}
