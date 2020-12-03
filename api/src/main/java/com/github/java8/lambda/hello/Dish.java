package com.github.java8.lambda.hello;

/**
 * @author hangs.zhang
 * @date 2018/10/31
 * *****************
 * function:
 */
public class Dish {

    private String name;

    // 卡路里
    private Integer calories;

    private Type type;

    private Boolean vegetarian;

    public enum Type {
        META, FISH, OTHER
    }

    public Dish(String name, Integer calories, Type type, Boolean vegetarian) {
        this.name = name;
        this.calories = calories;
        this.type = type;
        this.vegetarian = vegetarian;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", calories=" + calories +
                ", type=" + type +
                ", vegetarian=" + vegetarian +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }
}
