package org.example;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.example.entity.OrderEvent;
import org.example.entity.OrderEventFactory;
import org.example.handler.OrderEventHandler;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class DisruptorDemo {
    static void main(String[] args) throws InterruptedException {
        // 1. Create a Disruptor with ring buffer size (must be power of 2)
        int bufferSize = 1024;
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                new OrderEventFactory(),
                bufferSize,
                DaemonThreadFactory.INSTANCE,       // thread factory for worker threads
                com.lmax.disruptor.dsl.ProducerType.SINGLE,  // single producer
                new com.lmax.disruptor.BlockingWaitStrategy() // wait strategy
        );

        // 2. Connect the handler (consumer)
        disruptor.handleEventsWith(new OrderEventHandler());

        // 3. Start the Disruptor – this starts worker threads
        disruptor.start();

        // 4. Get the ring buffer reference
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        // 5. Publish events using the RingBuffer
        for (int i = 0; i < 10; i++) {
            long sequence = ringBuffer.next();   // claim a slot
            try {
                OrderEvent event = ringBuffer.get(sequence);
                event.setOrderId("Order-" + i);
                event.setPrice(100.0 + i);
                event.setQuantity(i * 10);
            } finally {
                ringBuffer.publish(sequence);    // make the event visible to consumers
            }
        }

        // Allow some time for processing
        Thread.sleep(1000);

        // 6. Shutdown gracefully
        disruptor.shutdown();
    }

}
