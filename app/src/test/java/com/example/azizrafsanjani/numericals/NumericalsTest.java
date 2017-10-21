package com.example.azizrafsanjani.numericals;

/**
 * Created by Aziz Rafsanjani on 10/21/2017.
 */

import com.example.azizrafsanjani.numericals.NumericalMethods.Numericals;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NumericalsTest {
    @Test
    public void testDecimalIntToBinaryWith10(){
        assertEquals("1010", Numericals.DecimalIntToBinary(10));

    }

    @Test
    public void testBisectionShouldPass(){
        double y =  Numericals.Bisect("f(x) = x^5 + x^3 + 3",-2, -1, 2, 0.005);
        assertEquals(-1.125, y);
    }

    @Test
    public void testBisectionShouldFail(){
        assertEquals(-1.1056875, Numericals.Bisect("f(x) = x^5 + x^3 + 3",-2, -1, 7, 0.00005));
    }
}
