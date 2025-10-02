package org.TaskManagerProgram.com.Models;

import org.TaskManagerProgram.com.Exceptions.*;
import org.TaskManagerProgram.com.JavaFood.AdminPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Restaurant restaurant;
    private Food food1;
    private Food food2;
    private Order order;

    @BeforeEach
    void setup() {
        restaurant = new Restaurant();
        restaurant.isOpen = true;
        food1 = new Food(1, "Pizza", 10.0);
        food2 = new Food(2, "Burger", 5.0);
        restaurant.foods.put(food1, 10);
        restaurant.foods.put(food2, 5);

        AdminPanel.discounts.clear();
        AdminPanel.todayDate = LocalDate.now();

        order = new Order(1, 100, restaurant, Order.ReceivingType.IN_PERSON);
    }

    @Test
    void testAddFoodToOrder_success() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose {
        order.addFoodToOrder(1, 2);
        assertEquals(1, order.foods.size());
        assertEquals(2, order.foods.get(food1));
        assertEquals(20.0, order.totalPrice);
    }

    @Test
    void testAddFoodToOrder_foodNotFound() {
        assertThrows(FoodNotFound.class, () -> order.addFoodToOrder(999, 1));
    }

    @Test
    void testAddFoodToOrder_restaurantClosed() {
        restaurant.isOpen = false;
        assertThrows(RestaurantIsClose.class, () -> order.addFoodToOrder(1, 1));
    }

    @Test
    void testAddDiscount_success() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose {
        Discount discount = new Discount();
        discount.code = "SAVE10";
        discount.amount = 5.0;
        discount.userId = 100;
        discount.isUsed = false;
        discount.expireDate = LocalDate.now().plusDays(1);
        AdminPanel.discounts.add(discount);

        order.addFoodToOrder(1, 2);
        order.addDiscount("SAVE10");

        assertEquals(5.0, order.discountPrice);
        assertEquals(15.0, order.totalPrice);
        assertTrue(discount.isUsed);
    }

    @Test
    void testAddDiscount_invalidCode() {
        assertThrows(InvalidDiscountCode.class, () -> order.addDiscount("INVALID"));
    }

    @Test
    void testPay_success() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose, PayException {
        order.addFoodToOrder(1, 1);
        order.pay(10.0);
        assertTrue(order.isPaid);
        assertNotNull(order.orderDateTime);
    }

    @Test
    void testPay_wrongAmount() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose {
        order.addFoodToOrder(1, 1);
        assertThrows(PayException.class, () -> order.pay(5.0));
    }

    @Test
    void testScoreOrder_success() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose, PayException {
        order.addFoodToOrder(1, 1);
        order.pay(10.0);
        order.scoreOrder(4);
        assertEquals(4, order.score);
        assertTrue(order.isScored);
    }

    @Test
    void testScoreOrder_alreadyScored() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose, PayException {
        order.addFoodToOrder(1, 1);
        order.pay(10.0);
        order.scoreOrder(3);
        assertThrows(InvalidScore.class, () -> order.scoreOrder(5));
    }

    @Test
    void testScoreOrder_notPaid() {
        assertThrows(InvalidScore.class, () -> order.scoreOrder(4));
    }

    @Test
    void testScoreOrder_invalidScore() throws Exception, FoodOutOfStock, FoodNotFound, RestaurantIsClose, PayException {
        order.addFoodToOrder(1, 1);
        order.pay(10.0);
        assertThrows(InvalidScore.class, () -> order.scoreOrder(6));
        assertThrows(InvalidScore.class, () -> order.scoreOrder(0));
    }
}
