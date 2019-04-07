package com.lnsoft.test.fastJson.other;

/**
 * Created By Chr on 2019/4/6/0006.
 */
public class Apple {

    private Integer id;

    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Apple() {
    }

    public Apple(Integer id, String color) {
        this.id = id;
        this.color = color;
    }
}
