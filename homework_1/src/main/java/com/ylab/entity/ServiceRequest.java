package com.ylab.entity;

/**
 * Класс представляет заявку на обслуживание автомобиля.
 */
public class ServiceRequest {
    private User customer;
    private Car car;
    private String description;
    private String status;

    /**
     * Конструктор для создания новой заявки на обслуживание.
     *
     * @param customer Клиент, создавший заявку.
     * @param car Автомобиль, на который создана заявка.
     * @param description Описание заявки.
     * @param status Статус заявки.
     */
    public ServiceRequest(User customer, Car car, String description, String status) {
        this.customer = customer;
        this.car = car;
        this.description = description;
        this.status = status;
    }

    public User getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "customer=" + customer.getUsername() +
                ", car=" + car +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
