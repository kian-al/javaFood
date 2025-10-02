package org.TaskManagerProgram.com.Exceptions;

public class PayException extends Throwable{
    public PayException(){
        System.out.println("Payment failed");
    }
}
