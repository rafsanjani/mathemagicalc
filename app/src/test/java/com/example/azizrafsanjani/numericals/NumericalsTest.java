package com.example.azizrafsanjani.numericals;

/**
 * Created by Aziz Rafsanjani on 10/21/2017.
 */


import android.support.v4.math.MathUtils;
import android.support.v4.widget.TextViewCompat;

import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;
import org.junit.Test;
import org.mariuszgromada.math.mxparser.mathcollection.SpecialFunctions;

import java.math.MathContext;

import static junit.framework.Assert.assertEquals;

public class NumericalsTest {
    @Test
    public void testDecimalIntToBinaryWith10() {
        assertEquals("1010", Numericals.DecimalIntToBinary(10));

    }

    @Test
    public void testBisectionShouldPass() {
        double y = Numericals.Bisect("x^5 + x^3 + 3", -2, -1, 4, 0.005);
        assertEquals(-1.0625, y);
    }

    @Test
    public void testNewtonRaphson() {
        String eqn = "x^2 - 3";
        double y = Numericals.NewtonRaphson(eqn, 1, 20);
        assertEquals(-1.0625, y);
    }

    @Test
    public void testGaussian() {
        double A[][] = {
                {100, 2555, 3},
                {4, 1, 2},
                {7, 333, 4}
        };


        double B[] = {2, 6, 3};

    }

    @Test
    public void testJacobi() {
        String system[] = {
                "(1/2)*(x2+1)",
                "(1/3)*(x1+x3+8)",
                "(1/2)*(x2-5)"};
        double initGuess[] = {0, 0 , 0};
       double sol[] = Numericals.Jacobi(system, initGuess,0.15);

    }

    @Test
    public void testGaussSeidel(){
        String system[] = {
                "(1/2)*(x2+1)",
                "(1/3)*(x1+x3+8)",
                "(1/2)*(x2-5)"};
        double initGuess[] = {0, 0 , 0};
        double sol[] = Numericals.GaussSeidel(system, initGuess,0.01);
    }

    @Test
    public void testGaussSeidelShouldPass(){
        String system[] = {
                "(1/4)*(x2+x3 + 3)",
                "(1/6)*(2*x1-x3+9)",
                "(1/7)*(x1-x2 - 6)"};
        double initGuess[] = {0, 0 , 0};
        double sol[] = Numericals.GaussSeidel(system, initGuess,0.00011);
    }

    @Test
    public void testGaussSeidelXOR(){
        String system[] = {
                "(2*x2+3*x3)",
                "(1/5)*(-3*x1-2*x3)",
                "(1+2*x1+3*x2)"};
        double initGuess[] = {0, 0 , 0};
        double omega = 1.5;
        double sol[] = Numericals.GaussSeidelWithSOR(system, initGuess,0.00011, omega);
    }

    @Test
    public void testRegulaFalsi() {
        String eqn = "f(x)=x^2 - 3";
        double y = Numericals.FalsePosition(eqn, -1, 2, 10, 0);
        System.out.println(y);
    }

    @Test
    public void testSecanteShouldPass() {
        String eqn = "x^3 + x^3 + 3";
        double y = Numericals.Secante(eqn, 1, -1, 1);
        System.out.println(y);
        assertEquals(-1.5, y);
    }

    @Test
    public void testBisectionShouldFail() {
        assertEquals(-1.1056875, Numericals.Bisect("f(x) = x^5 + x^3 + 3", -2, -1, 7, 0.00005));
    }
}
