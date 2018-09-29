package com.foreverrafs.numericals.model;

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

    public void setX(double x) {
        this.x = x;
    }

    public double getN() {
        return n;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
