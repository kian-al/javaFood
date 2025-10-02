package org.TaskManagerProgram.com.Models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void testFoodConstructorAssignsFields() {
        Food food = new Food(1, "Pizza", 120.0);

        assertEquals(Integer.valueOf(1), food.id);
        assertEquals("Pizza", food.name);
        assertEquals(Double.valueOf(120.0), food.price);
    }

    @Test
    void testFoodWithNulls() {
        Food food = new Food(null, null, null);
        assertNull(food.id);
        assertNull(food.name);
        assertNull(food.price);
    }
}
