package org.example.handler;

import com.lmax.disruptor.EventHandler;
import org.example.entity.OrderEvent;

public class OrderEventHandler implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.printf("%s processing %s\n",Thread.currentThread().getName() , event);
    }
}
