package com.example.drools_app.model;

public enum CustomerType {
    LOYAL, NEW, DISSATISFIED;
 
    public String getValue() {
        return this.toString();
    }
}