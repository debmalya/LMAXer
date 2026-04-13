package org.example.handler;

import com.lmax.disruptor.WorkHandler;
import org.example.entity.OrderEvent;

public class OrderWorker implements WorkHandler<OrderEvent> {
    private final int workerId;

    public OrderWorker(int workerId) {
        this.workerId = workerId;
    }

    @Override
    public void onEvent(OrderEvent event) throws Exception {
        // No sequence or endOfBatch parameters
        System.out.printf("[Worker] %d processing: %s\n", workerId , event);
        // Simulate processing time
        Thread.sleep(10);
    }

}
