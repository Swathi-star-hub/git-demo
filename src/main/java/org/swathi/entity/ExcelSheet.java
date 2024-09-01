package org.swathi.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class ExcelSheet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer sNO;

    @NotBlank(message = "id cant not be empty!!")
    @Size(min = 6, max = 6, message = "id must be 6 digits")
    private int id;

    @NotNull
    @Size(min = 3, max = 20, message = "name must be not null")
    private String customerName;

    @NotBlank(message = "is required")
    private String Qualification;

    @NotBlank(message = "is required")
    private String gender;

    @NotBlank(message = "is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Long dateOfBirth;

    @NotBlank(message = "is required")
    private String addressLine1;

    @NotBlank(message = "is required")
    private String addressLine2;
    @NotBlank(message = "is required")
    private String city;
    @NotBlank(message = "is required")
    private String state;
    @NotBlank(message = "is required")
    private String pincode;
    @NotBlank(message = "is required")
    private String maritalStatus;


    public Integer getsNO() {
        return sNO;
    }
    public void setsNO(Integer sNO) {
        this.sNO = sNO;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getQualification() {
        return Qualification;
    }
    public void setQualification(String qualification) {
        Qualification = qualification;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Long getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getAddressLine1() {
        return addressLine1;
    }
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }
    public String getAddressLine2() {
        return addressLine2;
    }
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPincode() {
        return pincode;
    }
    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


}
