package com.example.crs.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name = "student")
public class Student {
 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private Integer id;
 @Column(name = "name")
 @NotBlank(message = "Name cannot be blank or empty.")
 private String name;
 @Column(name = "email")
 @NotBlank(message = "Email cannot be blank.")
 @Email(message = "Invalid email format. Please provide a valid email address.")
 private String email;
 @Column(name= "department")
 @NotBlank(message = "Department cannot be blank.")
 private String department;
 public Student() {}
 public Student(String name, String email, String department) {
    this.name = name;
    this.email = email;
    this.department=department;
    }
    public Integer getId() {
    return id;
    }
    public void setId(Integer id) {
    this.id = id;
    }
    public String getName() {
    return name;
    }
    public void setName(String name) {
    this.name = name;
    }
    public String getEmail(){
    return email;
    }
    public void setEmail(String email){
    this.email=email;
    }
    public String getDepartment(){
    return department;
    }
    public void setDepartment(String department){
    this.department=department;
    }
   }
   