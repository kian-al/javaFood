package org.TaskManagerProgram.com.Users;

import org.TaskManagerProgram.com.Models.Restaurant;

import java.util.ArrayList;

public class RestaurantOwner extends User{
    public ArrayList<Restaurant> restaurants;

    public RestaurantOwner(int id, String name) {
        super(id, name);
    }
}
