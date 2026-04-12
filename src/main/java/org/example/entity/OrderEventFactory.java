package org.example.entity;

import com.lmax.disruptor.EventFactory;

public class OrderEventFactory implements EventFactory<OrderEvent> {

    @Override
    public OrderEvent newInstance() {
        return new OrderEvent(); // called once per ring buffer slot
    }
}
