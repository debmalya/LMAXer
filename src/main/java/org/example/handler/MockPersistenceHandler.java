package org.example.handler;

import com.lmax.disruptor.EventHandler;
import org.example.entity.OrderEvent;

public class MockPersistenceHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) {
        // Simulate DB insert
        System.out.printf("[DB] Saved %s\n" , event);
    }
}
