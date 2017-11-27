package com.example.azizrafsanjani.numericals;

/**
 * Created by Aziz Rafsanjani on 10/21/2017.
 */


import com.example.azizrafsanjani.numericals.utils.Numericals;
import com.example.azizrafsanjani.numericals.utils.Utilities;;

import org.junit.Test;

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
                {3, 4, 3},
                {1, 5, -1},
                {-6, 3, 2}
        };
        double B[] = {1, 7, 9};
        double solution[] = Numericals.GaussianWithPartialPivoting(A, B);

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                System.out.print(A[i][j] + " ");
            }
            System.out.print(solution[i]);
            System.out.println();
        }

        System.out.println("the solution matrix is given as: ");
        for(int i = 0; i < solution.length; i++){
            System.out.println(solution[i]);
        }
    }

   /* @Test
   public void testGaussianWithPartialPivotingShouldPass(){
        double A = {
                {},
                {},
                {}
        }
    }*/

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
