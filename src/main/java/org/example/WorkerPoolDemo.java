package org.example;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;
import org.example.entity.OrderEvent;
import org.example.entity.OrderEventFactory;
import org.example.handler.OrderWorker;

import java.util.concurrent.Executors;

public class WorkerPoolDemo {
     static void main(String[] args) throws InterruptedException {
        RingBuffer<OrderEvent> ringBuffer = RingBuffer.create(
                ProducerType.SINGLE,
                new OrderEventFactory(),
                1024,
                new BlockingWaitStrategy()
        );

        // Create 3 workers
        OrderWorker[] workers = new OrderWorker[] {
                new OrderWorker(1),
                new OrderWorker(2),
                new OrderWorker(3)
        };

        WorkerPool<OrderEvent> workerPool = new WorkerPool<>(
                ringBuffer,
                ringBuffer.newBarrier(),  // sequence barrier (can be null if no dependencies)
                new IgnoreExceptionHandler(),
                workers
        );

        // Start the worker pool – this starts worker threads
        workerPool.start(Executors.newFixedThreadPool(3));

        // Publish events using the RingBuffer
        for (int i = 0; i < 10; i++) {
            long seq = ringBuffer.next();
            try {
                OrderEvent event = ringBuffer.get(seq);
                event.setOrderId("Order-" + i);
                event.setPrice(100.0 + i);
                event.setQuantity(i * 10);
            } finally {
                ringBuffer.publish(seq);
            }
        }

        Thread.sleep(2000);
        workerPool.drainAndHalt(); // stop after all events processed
    }
}
