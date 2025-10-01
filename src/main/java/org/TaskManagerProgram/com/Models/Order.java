package org.TaskManagerProgram.com.Models;

import org.TaskManagerProgram.com.Exceptions.*;
import org.TaskManagerProgram.com.JavaFood.AdminPanel;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public class Order {

    public Integer id;
    public final HashMap<Food, Integer> foods = new HashMap<>();
    public Double discountPrice = 0.0;
    public Integer userId;
    public Restaurant restaurant;
    public LocalDateTime orderDateTime;
    public Double totalPrice = 0.0;
    public ReceivingType receivingType;
    public Boolean isPaid = false;
    public Boolean isScored = false;
    public Integer score = 0;

    public enum ReceivingType {
        IN_PERSON,
        COURIER
    }

    // Constructor
    public Order(Integer id, Integer userId, Restaurant restaurant,
                 ReceivingType receivingType) {
        this.id = id;
        this.userId = userId;
        this.restaurant = restaurant;
        this.receivingType = receivingType;
    }

    // Add food to order
    public void addFoodToOrder(Integer foodId, int orderQuantity) throws FoodNotFound, FoodOutOfStock, RestaurantIsClose {
        if (!restaurant.isOpen) {
            throw new RestaurantIsClose();
        }

        Food targetFood = null;
        for (Food food : restaurant.foods.keySet()) {
            if (food.id.equals(foodId)) {
                targetFood = food;
                break;
            }
        }

        if (targetFood == null) {
            throw new FoodNotFound();
        }

        if (orderQuantity > targetFood.price) {
            throw new FoodOutOfStock();
        }

        // Add or update food in the order
        foods.put(targetFood, orderQuantity);
        totalPrice += targetFood.price * orderQuantity;
    }

    // Add discount to order
    public void addDiscount(String code) throws InvalidDiscountCode {
        Discount targetDiscount = null;

        for (Discount discount : AdminPanel.discounts) {
            if (discount.code.equals(code)) {
                targetDiscount = discount;
                break;
            }
        }

        if (targetDiscount == null) {
            throw new InvalidDiscountCode("Discount code not found");
        }

        if (targetDiscount.isUsed) {
            throw new InvalidDiscountCode("Discount code already used");
        }

        if (AdminPanel.todayDate.isAfter(targetDiscount.expireDate)) {
            throw new InvalidDiscountCode("Discount is over");
        }

        if (!targetDiscount.userId.equals(this.userId)) {
            throw new InvalidDiscountCode("This discount is not for you");
        }

        // Apply discount
        discountPrice += targetDiscount.amount;
        totalPrice -= targetDiscount.amount;

        // Mark discount as used
        targetDiscount.isUsed = true;
    }

    // Pay for order
    public void pay(Double amount) throws PayException {
        if (Math.abs(amount - totalPrice) > 0.001) {
            throw new PayException();
        }

        isPaid = true;
        orderDateTime = LocalDateTime.of(AdminPanel.todayDate, LocalTime.now());
    }

    // Score the order
    public void scoreOrder(Integer score) throws InvalidScore {
        if (isScored) {
            throw new InvalidScore("Already scored");
        }

        if (!isPaid) {
            throw new InvalidScore("Not paid yet");
        }

        if (score < 1 || score > 5) {
            throw new InvalidScore("Invalid score");
        }

        this.score = score;
        isScored = true;
    }
}
