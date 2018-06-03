package com.example.azizrafsanjani.numericals.model;

public class OdeResult {
    private double x, y, n;

    public OdeResult(double y, double x, double n) {
        this.x = x;
        this.y = y;
        this.n = n;
    }


    public double getX() {
        return x;
    }

    public double getN() {
        return n;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}
