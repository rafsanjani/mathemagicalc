package com.example.azizrafsanjani.numericals.model;

public class BisectionResult {
    double x1, x2, x3, tolerance;
    int iteration;

    public BisectionResult(double x1, double x2, double x3, int iteration,  double tolerance) {
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.iteration = iteration;
        this.tolerance = tolerance;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getX3() {
        return x3;
    }

    public double getTolerance(){
        return tolerance;
    }

    public int getIteration() {
        return iteration;
    }
}
