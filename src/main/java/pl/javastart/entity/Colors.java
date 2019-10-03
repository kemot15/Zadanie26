package pl.javastart.entity;

import org.hibernate.property.access.internal.PropertyAccessStrategyIndexBackRefImpl;

public enum Colors {
    BLACK ((char) 27 + "[30m"),
    RED ((char) 27 + "[31m"),
    GREEN ((char) 27 + "[32m"),
    CYAN ((char) 27 + "[36m");

    private String color;

    Colors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
