package org.TaskManagerProgram.com.JavaFood;

import com.google.gson.*;
import org.TaskManagerProgram.com.Exceptions.InvalidDiscountCode;
import org.TaskManagerProgram.com.Models.Discount;
import org.TaskManagerProgram.com.Models.Order;
import org.TaskManagerProgram.com.Models.Restaurant;
import org.TaskManagerProgram.com.Users.User;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdminPanel {

    public static LocalDate todayDate;
    public static ArrayList<Restaurant> restaurants = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Discount> discounts = new ArrayList<>();
    public static ArrayList<Order> orders = new ArrayList<>();

    public void loadFromJSONFile(String fileAddress) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(Paths.get(fileAddress).toFile())) {
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            JsonArray jsonArrayRestaurants = jsonObject.getAsJsonArray("restaurants");
            if (jsonArrayRestaurants != null) {
                for (JsonElement element : jsonArrayRestaurants) {
                    restaurants.add(gson.fromJson(element, Restaurant.class));
                }
            }

            JsonArray jsonArrayUsers = jsonObject.getAsJsonArray("users");
            if (jsonArrayUsers != null) {
                for (JsonElement element : jsonArrayUsers) {
                    users.add(gson.fromJson(element, User.class));
                }
            }

            JsonArray jsonArrayDiscounts = jsonObject.getAsJsonArray("discounts");
            if (jsonArrayDiscounts != null) {
                for (JsonElement element : jsonArrayDiscounts) {
                    discounts.add(gson.fromJson(element, Discount.class));
                }
            }

            JsonArray jsonArrayOrders = jsonObject.getAsJsonArray("orders");
            if (jsonArrayOrders != null) {
                for (JsonElement element : jsonArrayOrders) {
                    orders.add(gson.fromJson(element, Order.class));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDiscount(Discount discount) throws InvalidDiscountCode{
        for (Discount discountItem:discounts){
            if (discountItem.code.equals(discount.code)){
                throw new InvalidDiscountCode("This discount code already exists");
            }

        }
        discounts.add(discount);

    }
    public void addUser(User user) throws Exception{
        if (user==null){
            throw new Exception("User can't be null");
        }
        for (User userItem:users){
            if (userItem.getId()==user.getId()){
                throw new Exception("User with this ID already exists");
            }
        }
        users.add(user);
    }
    public void addRestaurant(Restaurant restaurant) throws Exception{
        if (restaurant==null){
            return;
        }
        for (Restaurant restaurantItem:restaurants){
            if (restaurantItem.id.equals(restaurant.id)){
                throw new Exception("Restaurant already exists");
            }
        }
        restaurants.add(restaurant);
    }
    public Order createOrder(Integer id, Integer userId,
                             Integer restaurant_id, Order.ReceivingType type) throws Exception {
        Restaurant restaurantNewOrder = null;
        for (Restaurant restaurant : restaurants) {
            if (restaurant.id.equals(restaurant_id)) {
                restaurantNewOrder = restaurant;
                break;
            }
        }
        if (restaurantNewOrder == null) {
            throw new Exception("We don't have a restaurant with this ID");
        }

        User orderUser = null;
        for (User user : users) {
            if (user.getId()==userId) {
                orderUser = user;
                break;
            }
        }
        if (orderUser == null) {
            throw new Exception("We don't have a user with this ID");
        }

        // 3. Generate a unique order ID
        int newOrderId = 1;
        if (!orders.isEmpty()) {
            newOrderId = orders.stream()
                    .mapToInt(o -> o.id)
                    .max()
                    .getAsInt() + 1;
        }
        Order newOrder = new Order(newOrderId, userId, restaurantNewOrder, type);
        orders.add(newOrder);
        return newOrder;
    }
    public void setDate(LocalDate todayDate) {
        AdminPanel.todayDate=todayDate;
        for (Restaurant restaurant:restaurants){
            restaurant.setDate(todayDate);
        }

    }
    public Restaurant getBestRestaurant() throws Exception{
        if (restaurants.isEmpty()){
            throw new Exception("Restaurant list is empty.");
        }
        Restaurant bestRestaurant=restaurants.get(0);
        for (Restaurant restaurant:restaurants){
            if (restaurant.score>bestRestaurant.score){
                bestRestaurant=restaurant;
            }
        }
        return bestRestaurant;
    }
    public Restaurant getMostOrderedRestaurant() throws Exception{
        if (restaurants.isEmpty()){
            throw new Exception("Restaurant list is empty.");
        }
        Restaurant mostOrderedRestaurant=null;
        int maxOrders=0;
        for (Restaurant restaurant:restaurants){
            int currentOrderCount=0;
            for (Order order:orders){
                if (order.restaurant.id.equals(restaurant.id)){
                    currentOrderCount++;
                }
            }
            if (currentOrderCount>maxOrders){
                maxOrders=currentOrderCount;
                mostOrderedRestaurant=restaurant;
            }
        }
        return mostOrderedRestaurant;

    }
}
