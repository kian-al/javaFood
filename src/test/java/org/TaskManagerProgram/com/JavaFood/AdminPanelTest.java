package org.TaskManagerProgram.com.JavaFood;

import static org.junit.jupiter.api.Assertions.*;
import org.TaskManagerProgram.com.Exceptions.InvalidDiscountCode;
import org.TaskManagerProgram.com.Models.Discount;
import org.TaskManagerProgram.com.Models.Food;
import org.TaskManagerProgram.com.Models.Order;
import org.TaskManagerProgram.com.Models.Restaurant;
import org.TaskManagerProgram.com.Users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
class AdminPanelTest {
    private AdminPanel admin;
    private User user;
    private Restaurant restaurant;
    private Food food;

    @BeforeEach
    void setup() throws Exception {
        admin = new AdminPanel();
        AdminPanel.restaurants.clear();
        AdminPanel.users.clear();
        AdminPanel.discounts.clear();
        AdminPanel.orders.clear();

        user = new User(1, "Kian");
        admin.addUser(user);

        restaurant = new Restaurant(1, "Test Restaurant", "Test Address", 8, 22, Restaurant.RestaurantTypes.FASTFOOD);
        food = new Food(1, "Pizza", 10.0);
        restaurant.foods.put(food, 10);
        admin.addRestaurant(restaurant);

        AdminPanel.todayDate = LocalDate.now();
    }

    @Test
    void testAddUser_duplicateAndNull() throws Exception {
        // اضافه کردن کاربر با همان ID باعث Exception می‌شود
        Exception e1 = assertThrows(Exception.class, () -> admin.addUser(new User(1, "Another")));
        assertEquals("User with this ID already exists", e1.getMessage());

        // اضافه کردن null
        Exception e2 = assertThrows(Exception.class, () -> admin.addUser(null));
        assertEquals("User can't be null", e2.getMessage());
    }

    @Test
    void testAddRestaurant_duplicateAndNull() throws Exception {
        // اضافه کردن null نباید Exception بدهد
        admin.addRestaurant(null);

        // اضافه کردن رستوران با همان ID باعث Exception می‌شود
        Exception e = assertThrows(Exception.class, () -> admin.addRestaurant(restaurant));
        assertEquals("Restaurant already exists", e.getMessage());
    }

    @Test
    void testAddDiscount_duplicate() throws InvalidDiscountCode {
        Discount discount = new Discount();
        discount.code = "SAVE10";
        discount.amount = 5.0;
        discount.userId = 1;
        discount.isUsed = false;
        discount.expireDate = LocalDate.now().plusDays(1);

        admin.addDiscount(discount);

        Exception e = assertThrows(InvalidDiscountCode.class, () -> admin.addDiscount(discount));
        assertEquals("This discount code already exists", e.getMessage());
    }

    @Test
    void testCreateOrder_successAndErrors() throws Exception {
        Order order = admin.createOrder(1, user.getId(), restaurant.id, Order.ReceivingType.IN_PERSON);
        assertNotNull(order);
        assertEquals(user.getId(), order.userId);
        assertEquals(restaurant, order.restaurant);

        // ایجاد سفارش با userId ناموجود
        Exception e1 = assertThrows(Exception.class, () -> admin.createOrder(1, 999, restaurant.id, Order.ReceivingType.IN_PERSON));
        assertEquals("We don't have a user with this ID", e1.getMessage());

        // ایجاد سفارش با restaurant_id ناموجود
        Exception e2 = assertThrows(Exception.class, () -> admin.createOrder(1, user.getId(), 999, Order.ReceivingType.IN_PERSON));
        assertEquals("We don't have a restaurant with this ID", e2.getMessage());
    }

    @Test
    void testGetBestRestaurant() throws Exception {
        restaurant.score = 4.5;
        Restaurant r2 = new Restaurant(2, "R2", "Addr", 8, 22, Restaurant.RestaurantTypes.IRANI);
        r2.score = 4.8;
        admin.addRestaurant(r2);

        Restaurant best = admin.getBestRestaurant();
        assertEquals(r2, best);

        AdminPanel.restaurants.clear();
        Exception e = assertThrows(Exception.class, admin::getBestRestaurant);
        assertEquals("Restaurant list is empty.", e.getMessage());
    }

    @Test
    void testGetMostOrderedRestaurant() throws Exception {
        Order o1 = admin.createOrder(1, user.getId(), restaurant.id, Order.ReceivingType.IN_PERSON);
        Order o2 = admin.createOrder(2, user.getId(), restaurant.id, Order.ReceivingType.IN_PERSON);

        Restaurant mostOrdered = admin.getMostOrderedRestaurant();
        assertEquals(restaurant, mostOrdered);

        AdminPanel.restaurants.clear();
        Exception e = assertThrows(Exception.class, admin::getMostOrderedRestaurant);
        assertEquals("Restaurant list is empty.", e.getMessage());
    }
}