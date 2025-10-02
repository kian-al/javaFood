package org.TaskManagerProgram.com.Users;

import static org.junit.jupiter.api.Assertions.*;
import org.TaskManagerProgram.com.Models.Food;
import org.TaskManagerProgram.com.Models.Order;
import org.TaskManagerProgram.com.Models.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
class UserTest {
    private User user;
    private Restaurant restaurant;
    private Food food;
    private Order order;

    @BeforeEach
    void setup() {
        user = new User(1, "Kian");
        restaurant = new Restaurant(1, "Test Restaurant", "Test Address", 8, 22, Restaurant.RestaurantTypes.FASTFOOD);
        food = new Food(1, "Pizza", 10.0);
        restaurant.foods.put(food, 10);
        order = new Order(1, user.getId(), restaurant, Order.ReceivingType.IN_PERSON);
    }

    @Test
    void testGetters() {
        assertEquals(1, user.getId());
        assertEquals("Kian", user.getName());
        assertNotNull(user.getOrders());
        assertTrue(user.getOrders().isEmpty());
    }

    @Test
    void testAddOrder_success() {
        user.addOrder(order);
        ArrayList<Order> orders = user.getOrders();
        assertEquals(1, orders.size());
        assertEquals(order, orders.get(0));
    }

    @Test
    void testAddOrder_nullOrder() {
        user.addOrder(null);
        assertTrue(user.getOrders().isEmpty());
    }
}