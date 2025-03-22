package com.spring.entity;



public class DashboardMetrics {

    private long totalOrders;
    private long totalDelivered;
    private long totalCanceled;
    private double totalRevenue;

    public DashboardMetrics(long totalOrders, long totalDelivered, long totalCanceled, double totalRevenue) {
        this.totalOrders = totalOrders;
        this.totalDelivered = totalDelivered;
        this.totalCanceled = totalCanceled;
        this.totalRevenue = totalRevenue;
    }

    // Getters and setters
    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public long getTotalDelivered() {
        return totalDelivered;
    }

    public void setTotalDelivered(long totalDelivered) {
        this.totalDelivered = totalDelivered;
    }

    public long getTotalCanceled() {
        return totalCanceled;
    }

    public void setTotalCanceled(long totalCanceled) {
        this.totalCanceled = totalCanceled;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
