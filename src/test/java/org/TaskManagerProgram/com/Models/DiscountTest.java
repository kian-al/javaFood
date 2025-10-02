package org.TaskManagerProgram.com.Models;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountTest {

    @Test
    public void testDiscountCreation() {
        Discount discount = new Discount(1, "OFF20", 20, LocalDate.of(2025, 12, 31));

        assertEquals(1, discount.id);
        assertEquals("OFF20", discount.code);
        assertEquals(20, discount.percentage);
        assertEquals(LocalDate.of(2025, 12, 31), discount.expireDate);
    }

    @Test
    public void testDiscountSetters() {
        Discount discount = new Discount(2, "OFF10", 10, LocalDate.now());

        assertEquals(2, discount.id);
        assertEquals("OFF10", discount.code);
        assertEquals(10, discount.percentage);
        assertEquals(LocalDate.now(), discount.expireDate);
    }
}
