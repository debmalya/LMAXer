package org.example.handler;

import com.lmax.disruptor.EventHandler;
import org.example.entity.OrderEvent;

public class ValidationHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        if (event.getPrice() <= 0) {
            throw new IllegalArgumentException("Invalid price");
        }
        System.out.printf("[VALID] %s\n" , event);
    }
}
