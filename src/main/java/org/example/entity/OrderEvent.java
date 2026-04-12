package org.example.entity;

public class OrderEvent {
    private String orderId;
    private double price;
    private int quantity;

    // Getters and setters (important: Disruptor reuses the event object)
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return "OrderEvent{orderId='" + orderId + "', price=" + price + ", quantity=" + quantity + "}";
    }
}
