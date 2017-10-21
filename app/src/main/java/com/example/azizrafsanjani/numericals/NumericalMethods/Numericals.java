package com.example.azizrafsanjani.numericals.NumericalMethods;


import android.os.Debug;
import android.util.Log;

import org.mariuszgromada.math.mxparser.Function;

import java.util.Collections;

public final class Numericals {
    public enum BinaryOperationType {
        DecimalInteger,
        DecimalFraction,
        Mixed
    }

    /// <summary>
    /// Converts a whole number from Base 10 to Base 2.
    /// Note: Only integers (whole numbers) are supported for now
    /// </summary>
    /// <param name="dec"></param>
    /// <returns>a string representation of the binary equivalent of the supplied decimal numeral</returns>
    public static String DecimalIntToBinary(int dec) {
        int Nk = dec;
        StringBuilder binary = new StringBuilder();

        int bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalInteger);

        //magically manipulate and append result to the stringbuilder whilst Nk is greater than 0
        while (Nk != 0 && (Nk - bk != 0)) {
            Nk = (Nk - bk) / 2;
            bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalInteger);
        }

        //placeholder for our final binary result
        String result = binary.reverse().toString();

        //reverse the sequence of the string to portray true binary
        //result = ReverseString(result);


        //print the resulting binary to the user
        return result;
    }

    /// <summary>
    /// Converts the fractional part of a decimal numeral to a binary numeral
    /// </summary>
    /// <param name="dec">a double precision number in base 10</param>
    ///<returns>a string representation of the binary equivalent of the supplied decimal numeral</returns>
    public static String DecimalFractionToBinary(double dec) {
        double Nk = dec;
        StringBuilder binary = new StringBuilder();
        binary.append(".");

        Nk = 2 * Nk;


        int bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalFraction);


        //the magic recipe, (:)
        while (Nk != 0 && ((Nk - bk) != 0)) {
            Nk = (Nk - bk) * 2;
            bk = AppendToResult(Nk, binary, BinaryOperationType.DecimalFraction);
        }

        String result = binary.toString();
        return result;
    }

    /// <summary>
    /// Converts a decimal integer to a binary numeral but if the decimal has a fractional part,
    /// the number is separated into two parts, one being the whole part and the other being the fractional part.
    /// the binary equivalent of these parts are individually computed and merged together to form a complete binary
    /// </summary>
    /// <param name="dec">Either a </param>
    ///<returns>a string representation of the binary equivalent of the supplied decimal numeral</returns>
    public static String DecimalToBinary(double dec) {

        String decStr = String.valueOf(dec);

        //differentiate decimal numeral into fractional and whole parts
        String wholeStr = decStr.substring(0, decStr.indexOf("."));
        String fractionalStr = "0." + decStr.substring(wholeStr.length() + 1);

        int whole = Integer.parseInt(wholeStr);
        double fractional = Double.parseDouble(fractionalStr);

        //Independently calculate the binary form of the individual parts i.e whole and fractional parts
        String integerResult = DecimalIntToBinary(whole);
        String fractionalResult = DecimalFractionToBinary(fractional);


        //join it all together
        String binary = integerResult + fractionalResult;

        return binary;
    }

    /// <summary>
    /// Appends the result of the ternary operation on bk to a stringbuilder object supplied as an argument
    /// </summary>
    /// <param name="N"></param>
    /// <param name="sb">The stringbuilder object to which the result of the operation will be appended</param>
    /// <returns></returns>
    private static int AppendToResult(double N, StringBuilder sb, BinaryOperationType op) {
        int bk = 0000; //assign something dummy to prevent compiler issues
        switch (op) {
            case DecimalInteger: //number is exclusively an integer (eg XXX.00000)
                //bk = N % 2 == 0 ? 0 : 1;
                bk = IsEven(N) ? 0 : 1;
                break;

            case DecimalFraction: //number is exclusively a fraction (eg 0.XXXXX)
                if (N >= 1)
                    bk = 1;
                else
                    bk = 0;
                break;
            default:
                //oops
                break;
        }
        //append outcome to our stringbuilder
        sb.append(bk);
        return bk;
    }

    /// <summary>
    /// Computes the roots of an equation using the bisection method
    /// </summary>
    /// <param name="expr">an expression of the form f(x) = 0</param>
    /// <param name="x1">The lower boundary of the interval</param>
    /// <param name="x2">The upper boundary of the interval</param>
    /// <param name="iterations">The maximum number of times the interval must be bisected</param>
    /// <param name="tol">The tolerance level of the answer produced</param>
    /// <returns>a string representation of the root of the equation</returns>
    public static double Bisect(String expr, double x1, double x2, int iterations, double tol)
    {
        double x3 = (x1 + x2) / 2;

        double tolValue = Math.abs(x1 - x2) / 2;

        //a mathematical function of the form f(x) = 0
        Function fx;

        //is our approximated root less than or equal to the tolerance limit or are we out of moves?
        if (tolValue <= tol || iterations  == 1)
            return x3;

        fx = new Function(expr);

        double fx1 = fx.calculate(x1);
        double fx3 = fx.calculate(x3);

        //the root lies in the left part of the boundary
        if (fx1 * fx3 < 0)
            Bisect(expr, x1, x3, --iterations,tol);
        else
            //the root lies in the right part of the boundary
            Bisect(expr, x3, x2, --iterations, tol);

        return x3;
    }



    //helper method to check if an integer is an even number or an odd number
    private static boolean IsEven(double n) {
        return n % 2 == 0;
    }
}
