package org.TaskManagerProgram.com.Models;

import org.TaskManagerProgram.com.Exceptions.FoodNotFound;
import org.TaskManagerProgram.com.JavaFood.AdminPanel;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Restaurant {
    public RestaurantTypes type;
    public Integer id;
    public String name;
    public Double score;
    public Integer scoreCounts;
    public String address;
    public int openHour;
    public int closeHour;
    public final LinkedHashMap<Food,Integer> foods = new LinkedHashMap<>();
    public Boolean isOpen;
    public static LocalDate todayDate;

    // Constructor
    public Restaurant(Integer id, String name, String address,
                      int openHour, int closeHour, RestaurantTypes type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.type = type;
        this.isOpen = false;
        this.score = 0.0;
        this.scoreCounts = 0;
    }

    public Restaurant() {

    }

    // Open restaurant
    public void openRestaurant() {
        isOpen = true;
    }

    // Close restaurant
    public void closeRestaurant() {
        isOpen = false;
    }

    // Add food or update quantity
    public void addFood(Food food, Integer quantity) throws FoodNotFound {
        if (foods.containsKey(food)) {
            foods.put(food, foods.get(food) + quantity);
        } else {
            foods.put(food, quantity);
        }
    }

    // Update food quantity
    public void updateFoodQuantity(Food food, Integer quantity) {
        if (foods.containsKey(food)) {
            foods.put(food, quantity);
        } else {
            System.out.println("Food not found in this restaurant");
        }
    }

    // Get count of today orders
    public int getTodayOrdersCount() {
        int count = 0;
        for (Order order : AdminPanel.orders) {
            if (order.restaurant.id.equals(this.id) &&
                    order.orderDateTime.toLocalDate().equals(todayDate)) {
                count++;
            }
        }
        return count;
    }

    // Get total amount of today orders
    public Double getTodayOrdersAmount() {
        Double total = 0.0;
        for (Order order : AdminPanel.orders) {
            if (order.restaurant.id.equals(this.id) &&
                    order.orderDateTime.toLocalDate().equals(todayDate)) {
                total += order.totalPrice;
            }
        }
        return total;
    }

    // Get the most ordered food
    public Food getMostOrderedFood() {
        Food maxFood = null;
        int maxQuantity = 0;

        for (Order order : AdminPanel.orders) {
            if (order.restaurant.id.equals(this.id)) {
                for (Map.Entry<Food, Integer> entry : order.foods.entrySet()) {
                    Food food = entry.getKey();
                    int quantity = entry.getValue();

                    if (quantity > maxQuantity) {
                        maxQuantity = quantity;
                        maxFood = food;
                    }
                }
            }
        }

        return maxFood;
    }

    // Set today's date
    public void setDate(LocalDate todayDate) {
        Restaurant.todayDate = todayDate;
    }

    // Restaurant types
    public enum RestaurantTypes {
        FASTFOOD,
        IRANI,
        VEGETARIAN,
        KEBAB,
        SALAD,
        CAFE,
        SUPERMARKET,
        COFFEE
    }
}
