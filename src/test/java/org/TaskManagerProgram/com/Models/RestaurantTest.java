package org.TaskManagerProgram.com.Models;

import org.TaskManagerProgram.com.Exceptions.*;
import org.TaskManagerProgram.com.JavaFood.AdminPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    private Restaurant restaurant;
    private Food pizza;
    private Food burger;

    @BeforeEach
    void setup() {
        restaurant = new Restaurant(1, "Test Restaurant", "Test Address", 8, 22, Restaurant.RestaurantTypes.FASTFOOD);
        restaurant.openRestaurant();
        pizza = new Food(1, "Pizza", 10.0);
        burger = new Food(2, "Burger", 5.0);

        AdminPanel.orders.clear();
        Restaurant.todayDate = LocalDate.now();
    }

    @Test
    void testOpenAndCloseRestaurant() {
        restaurant.openRestaurant();
        assertTrue(restaurant.isOpen);

        restaurant.closeRestaurant();
        assertFalse(restaurant.isOpen);
    }

    @Test
    void testAddFood_newFood() throws FoodNotFound {
        restaurant.openRestaurant();
        restaurant.addFood(pizza, 5);
        assertEquals(5, restaurant.foods.get(pizza));

        restaurant.addFood(burger, 3);
        assertEquals(3, restaurant.foods.get(burger));
    }

    @Test
    void testAddFood_existingFood() throws FoodNotFound {
        restaurant.openRestaurant();
        restaurant.addFood(pizza, 5);
        restaurant.addFood(pizza, 2);
        assertEquals(7, restaurant.foods.get(pizza));
    }

    @Test
    void testUpdateFoodQuantity_existingFood() throws FoodNotFound {
        restaurant.openRestaurant();
        restaurant.addFood(pizza, 5);
        restaurant.updateFoodQuantity(pizza, 10);
        assertEquals(10, restaurant.foods.get(pizza));
    }

    @Test
    void testUpdateFoodQuantity_nonExistingFood() {
        restaurant.updateFoodQuantity(burger, 5);
        assertFalse(restaurant.foods.containsKey(burger));
    }

    @Test
    void testGetTodayOrdersCountAndAmount() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose, PayException {
        restaurant.openRestaurant();
        Order order1 = new Order(1, 100, restaurant, Order.ReceivingType.IN_PERSON);
        order1.addFoodToOrder(pizza.id, 2);
        order1.pay(order1.totalPrice);
        order1.orderDateTime = LocalDateTime.of(Restaurant.todayDate, LocalTime.now());

        Order order2 = new Order(2, 101, restaurant, Order.ReceivingType.IN_PERSON);
        order2.addFoodToOrder(burger.id, 3);
        order2.pay(order2.totalPrice);
        order2.orderDateTime = LocalDateTime.of(Restaurant.todayDate, LocalTime.now());

        AdminPanel.orders.add(order1);
        AdminPanel.orders.add(order2);

        assertEquals(2, restaurant.getTodayOrdersCount());
        assertEquals(order1.totalPrice + order2.totalPrice, restaurant.getTodayOrdersAmount());
    }

    @Test
    void testGetMostOrderedFood() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose, PayException {
        restaurant.openRestaurant();
        Order order1 = new Order(1, 100, restaurant, Order.ReceivingType.IN_PERSON);
        order1.addFoodToOrder(pizza.id, 2);
        order1.pay(order1.totalPrice);
        order1.orderDateTime = LocalDateTime.of(Restaurant.todayDate, LocalTime.now());

        Order order2 = new Order(2, 101, restaurant, Order.ReceivingType.IN_PERSON);
        order2.addFoodToOrder(burger.id, 3);
        order2.pay(order2.totalPrice);
        order2.orderDateTime = LocalDateTime.of(Restaurant.todayDate, LocalTime.now());

        AdminPanel.orders.add(order1);
        AdminPanel.orders.add(order2);

        Food mostOrdered = restaurant.getMostOrderedFood();
        assertEquals(burger, mostOrdered);
    }
}
