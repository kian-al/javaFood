package org.TaskManagerProgram.com.Exceptions;

public class FoodOutOfStock extends Throwable{
    public FoodOutOfStock(){
        System.out.println("Out of stock");
    }
}
