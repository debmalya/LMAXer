# How to use LMAX disruptor
## com.lmax.disruptor.EventFactory
## com.lmax.disruptor.EventHandler
## com.lmax.disruptor.RingBuffer
## com.lmax.disruptor.dsl.Disruptor
### Key points
* No locks – The RingBuffer.next() and publish() use CAS internally.
* Event reuse – The same OrderEvent instances are reused across the ring buffer. You must copy data if you need to store it beyond the handler’s lifetime.
* Single producer – We used ProducerType.SINGLE. For multiple producers, change to ProducerType.MULTI.
* Wait strategies – BlockingWaitStrategy is simple but slower. For extreme performance, try BusySpinWaitStrategy (only if you know your CPU cores will be dedicated).

### How to execute
* Run [Disruptor](./src/main/java/org/example/DisruptorDemo.java)
* Run [WorkPoolDemo](./src/main/java/org/example/WorkerPoolDemo.java)