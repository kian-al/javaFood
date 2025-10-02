package org.TaskManagerProgram.com.Exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {

    @Test
    public void testFoodNotFound() {
        FoodNotFound ex = new FoodNotFound();
        assertEquals("Food not found", ex.getMessage());
    }

    @Test
    public void testFoodOutOfStock() {
        FoodOutOfStock ex = new FoodOutOfStock();
        assertEquals("Out of stock", ex.getMessage());
    }

    @Test
    public void testInvalidDiscountCode() {
        InvalidDiscountCode ex = new InvalidDiscountCode("Invalid code");
        assertEquals("Invalid code", ex.getMessage());
    }

    @Test
    public void testInvalidScore() {
        InvalidScore ex = new InvalidScore("Bad score");
        assertEquals("Bad score", ex.getMessage());
    }

    @Test
    public void testPayException() {
        PayException ex = new PayException();
        assertEquals("Payment failed", ex.getMessage());
    }

    @Test
    public void testRestaurantIsClose() {
        RestaurantIsClose ex = new RestaurantIsClose();
        assertEquals("Restaurant is closed", ex.getMessage());
    }
}
