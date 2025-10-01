package org.TaskManagerProgram.com.Models;

import java.time.LocalDate;

public class Discount {
    public int id;
    public String code;
    public Integer userId;
    public DiscountType discountType;
    public LocalDate expireDate;
    public Double amount;
    public Integer percentage;
    public Boolean isUsed;

    public Discount(Integer id, String code,
                    Double amount, LocalDate expireDate,
                    Integer userId){}
    public Discount(Integer id, String code,
                    Integer percentage, LocalDate expireDate) {}

    public Discount() {

    }

    public enum DiscountType{
        PERCENTAGE,
        AMOUNT
    }

}
