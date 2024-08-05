package com.ylab.entity;

/**
 * Класс представляет автомобиль в автосалоне.
 */
public class Car {

    private String brand;
    private String model;
    private int year;
    private double price;
    private String condition;

    /**
     * Конструктор для создания нового автомобиля.
     *
     * @param brand Марка автомобиля.
     * @param model Модель автомобиля.
     * @param year Год выпуска автомобиля.
     * @param price Цена автомобиля.
     * @param condition Состояние автомобиля.
     */
    public Car(String brand, String model, int year, double price, String condition) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", condition='" + condition + '\'' +
                '}';
    }
}
