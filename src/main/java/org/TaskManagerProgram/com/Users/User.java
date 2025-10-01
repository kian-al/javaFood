package org.TaskManagerProgram.com.Users;

import org.TaskManagerProgram.com.Models.Order;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private ArrayList<Order> orders;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.orders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        if (order != null) {
            orders.add(order);
        }
    }
}
