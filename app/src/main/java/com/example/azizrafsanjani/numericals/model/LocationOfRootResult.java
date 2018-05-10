package com.example.azizrafsanjani.numericals.model;

public class LocationOfRootResult {
    private double x1, x2, x3, tolerance;  //for bisection and secante
    private double fx1, derX1;             //for newton raphson
    private double difference; //for secante;
    private int iteration;

    //Bisection Method Signature
    public LocationOfRootResult(double x1, double x2, double x3, int iteration, double tolerance) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.iteration = iteration;
        this.tolerance = tolerance;
    }

    //Newton Raphson Signature
    public LocationOfRootResult(double x1, int iteration, double fx1, double derX1) {
        this.x1 = x1;
        this.iteration = iteration;
        this.fx1 = fx1;
        this.derX1 = derX1;
    }

    //Secante Signature
    public LocationOfRootResult(int iteration, double x1, double x2, double x3, double difference) {
        this.iteration = iteration;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.difference = difference;
    }

    public double getX1() {
        return x1;
    }

    public double getDerX1() {
        return derX1;
    }

    public double getX2() {
        return x2;
    }

    public double getFx1() {
        return fx1;
    }

    public double getDifference(){return difference;}


    public double getX3() {
        return x3;
    }

    public double getTolerance() {
        return tolerance;
    }

    public int getIteration() {
        return iteration;
    }
}

