package com.foreverrafs.core;

import com.foreverrafs.core.exceptions.InvalidEquationException;
import com.foreverrafs.core.exceptions.InvalidIntervalException;
import com.foreverrafs.core.exceptions.NotABinaryException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

/**
 * Created by Aziz Rafsanjani on 11/2/2017.
 */


public final class Numericals {
    /***
     * Converts a whole number from Base 10 to Base 2. Note: Only Integers
     * @param dec A decimal number(n) where n >= 1
     * @return string a string representation of the binary equivalent of the supplied decimal numeral
     */
    public static String decimalIntToBinary(int dec) {
        int Nk = dec;
        StringBuilder binary = new StringBuilder();

        int bk = appendToResult(Nk, binary, BinaryOperationType.DecimalInteger);

        //magically manipulate and append result to the stringbuilder while Nk is greater than 0
        while (Nk != 0 && (Nk - bk != 0)) {
            Nk = (Nk - bk) / 2;
            bk = appendToResult(Nk, binary, BinaryOperationType.DecimalInteger);
        }

        return binary.reverse().toString();
    }

    /***
     * Converts a whole number from Base 10 to Base 2. Note: Only Fractions
     * @param dec A decimal number(n) where 0 < n < 1
     * @return string a string representation of the binary equivalent of the supplied decimal numeral
     */
    public static String decimalFractionToBinary(double dec) {
        double Nk = dec;
        StringBuilder binary = new StringBuilder();
        binary.append(".");

        Nk = 2 * Nk;

        int bk = appendToResult(Nk, binary, BinaryOperationType.DecimalFraction);

        //the magic recipe, (:)
        while (Nk != 0 && ((Nk - bk) != 0)) {
            Nk = (Nk - bk) * 2;
            bk = appendToResult(Nk, binary, BinaryOperationType.DecimalFraction);
        }

        return binary.toString();
    }

    /**
     * Converts a decimal integer to a binary numeral but if the decimal has a fractional part,
     * the number is separated into two parts using . as the delimiter, one being the whole part and the other being the fractional part.
     * the binary equivalent of these parts are individually computed and merged together to form a complete binary
     *
     * @param dec a decimal number of the form xx.xxxx
     * @return string a string representation of the binary equivalent of the supplied decimal numeral
     */
    public static String decimalToBinary(double dec) {

        String decStr = String.valueOf(dec);

        //differentiate decimal numeral into fractional and whole parts
        String wholeStr = decStr.substring(0, decStr.indexOf("."));
        String fractionalStr = "0." + decStr.substring(wholeStr.length() + 1);

        int whole = Integer.parseInt(wholeStr);
        double fractional = Double.parseDouble(fractionalStr);

        //Independently calculate the binary form of the individual parts i.e whole and fractional parts
        String integerResult = decimalIntToBinary(whole);
        String fractionalResult = decimalFractionToBinary(fractional);


        //join it all together

        return integerResult + fractionalResult;
    }

    /***
     * Appends the result of the ternary operation on bk to a stringbuilder object supplied as an argument
     * @param N the number to be analyzed
     * @param sb The stringbuilder object to which the result of the operation will be appended
     * @param op the operationType whether decimal integer or decimal fraction
     * @return int
     */
    private static int appendToResult(double N, StringBuilder sb, BinaryOperationType op) {
        int bk = 0; //assign something dummy to prevent compiler issues
        switch (op) {
            case DecimalInteger: //number is exclusively an integer (eg XXX.00000)
                bk = N % 2 == 0 ? 0 : 1;
                break;

            case DecimalFraction: //number is exclusively a fraction (eg 0.XXXXX)
                bk = (N >= 1 ? 1 : 0);
            default:
                //oops
                break;
        }
        //append outcome to our stringbuilder
        sb.append(bk);
        return bk;
    }


    /***
     * Computes the roots of an equation using the bisection method
     * @param expr  an expression of the form f(x) = 0
     * @param x1 The lower boundary of the interval
     * @param  x2 upper boundary of the interval
     * @param iterations The maximum number of times the interval must be bisected
     * @param tol The tolerance level of the answer produced
     * @return double
     */

    public static List<LocationOfRootResult> bisect(String expr, double x1, double x2, int iterations, double tol) throws InvalidEquationException {
        List<LocationOfRootResult> roots = new ArrayList<>();

        while (iterations > 0) {
            double x3 = (x1 + x2) / 2;
            double stoppingCriteria = Math.abs(x1 - x2) / 2;

            LocationOfRootResult tempRes = new LocationOfRootResult(x1, x2, x3, iterations, stoppingCriteria);
            roots.add(tempRes);

            //a mathematical function of the form f(x) = 0
            Function fx;

            //is our approximated root less than or equal to the tolerance limit or are we out of moves?
            if (stoppingCriteria <= tol || iterations == 1)
                break;

            if (expr.contains("f(x)"))
                fx = new Function(expr);
            else
                fx = new Function(String.format("f(x) = %s", expr));

            //if any of these computations returns NaN, it is assumed that the equation was improperly formatted.
            double fx1 = fx.calculate(x1);
            double fx3 = fx.calculate(x3);

            if (Double.isNaN(fx1) || Double.isNaN(fx3)) {
                throw new InvalidEquationException("Invalid Equation: " + fx.getFunctionExpressionString());
            }
            //the root lies in the left part of the boundary
            if (fx1 * fx3 < 0)
                x2 = x3;
            else
                x1 = x3;

            --iterations;
        }

        return roots;
    }

    /***
     * Computes the root of an equation of the form f(x) = 0 using the Newton - Raphson Forumula
     * @param expr a function of the form f(x) = 0
     * @param x1 the initial guess of the root
     * @param maxIterations maximum number of times we are to iterate
     * @return double
     */

    public static List<LocationOfRootResult> newtonRaphsonAll(String expr, double x1, int maxIterations) {
        List<LocationOfRootResult> roots = new ArrayList<>();

        Argument x;
        Expression ex;
        Function fx;
        while (maxIterations > 0) {

            if (expr.contains("f(x)"))
                expr = expr.substring(expr.lastIndexOf("=") + 1).trim();


            x = new Argument(String.format("x = %s", x1));

            ex = new Expression("der(" + expr + ", x)", x);

            fx = new Function(String.format("f(x) = %s", expr));

            double fx1 = fx.calculate(x1);
            double derX1 = ex.calculate();

            if (Double.isNaN(fx1) || Double.isNaN(derX1)) {
                throw new InvalidEquationException("Invalid Equation: " + fx.getFunctionExpressionString());
            }

            double approxRoot = x1 - (fx1 / derX1);

            roots.add(new LocationOfRootResult(approxRoot, maxIterations, fx1, x1));

            //check if current root is equal to the previous root then break from the loop
            if (approxRoot == x1)
                break;

            //set x1 to the current root obtained and store the result. after that we loop again
            x1 = approxRoot;
            maxIterations--;
        }
        return roots;
    }

    /***
     * Computes the root of an equation of the form f(x) = 0 using the false position (regula falsi) method
     * @param expr an expression of the form f(x) = 0
     * @param x0 The lower boundary of the interval
     * @param x1 The upper boundary of the interval
     * @param maxIterations The maximum number of times the interval must be bisected
     * @param tol Specifies the tolerance value for which the solution answer must adhere to
     * @return double which is the final root value without any additional information
     * @deprecated use {@link #falsePositionAll(String, double, double, int, double)} instead and take the last value
     * @throws InvalidIntervalException When the interval doesn't bracket the root
     */
    public static Double falsePosition(String expr, double x0, double x1, int maxIterations, double tol) throws InvalidIntervalException {
        if (maxIterations < 1)
            return 0.00;

        //sanitize the equation
        if (expr.contains("="))
            expr = expr.substring(expr.lastIndexOf("=") + 1);


        Function fx = new Function(String.format("f(x) = %s", expr.toLowerCase()));

        double fx0 = fx.calculate(x0);
        double fx1 = fx.calculate(x1);
        double fx2;

        if (fx0 * fx1 > 0)
            throw new InvalidIntervalException(String.format("The function doesn't change sign between the specified intervals: [%.2f],[%.2f] ", x0, x1));

        double x2 = x1 - ((x1 - x0) / (fx1 - fx0)) * fx1;

        fx2 = fx.calculate(x2);

        double stoppingCriteria = (x2 - x1) / x1;

        if (maxIterations == 1 || stoppingCriteria <= tol)
            return x2;

        if ((fx0 * fx2) < 0)
            x1 = x2;
        else
            x0 = x2;

        return falsePosition(expr, x0, x1, maxIterations - 1, tol);
    }

    public static List<LocationOfRootResult> falsePositionAll(String expr, double x0, double x1, int maxIterations, double tol) {
        List<LocationOfRootResult> results = new ArrayList<>();
        double stoppingCriteria;
        //sanitize the equation
        if (expr.contains("="))
            expr = expr.substring(expr.lastIndexOf("=") + 1);


        Function fx = new Function(String.format("f(x) = %s", expr.toLowerCase()));

        do {
            double fx0 = fx.calculate(x0);
            double fx1 = fx.calculate(x1);
            double fx2;

            //these are just to test whether the function changes sign or not but will not be used in the computations.
            //only fx2 is used in the actual computation

            double x2 = x1 - ((x1 - x0) / (fx1 - fx0)) * fx1;
            fx2 = fx.calculate(x2);

            stoppingCriteria = (Math.abs(x2 - x1) / x1);

            results.add(new LocationOfRootResult(maxIterations, x0, x1, x2, fx0, fx1, fx2, stoppingCriteria));


            if ((fx0 * fx2) < 0)
                x1 = x2;
            else
                x0 = x2;
            maxIterations--;

        } while (maxIterations > 0 && stoppingCriteria <= tol);

        return results;
    }

    /***
     * Computes the root of an equation using the secante method.
     * @param expr an equation of the form f(x) = 0
     * @param x0 the lower limit
     * @param x1 the upper limit
     * @param maxIterations The maximum number of iterations to be conducted
     * @return double the final root value without any further or additional information.
     * @deprecated use #{@link #secanteAll(String, double, double, int)} instead and take the last item of the returned collection
     */
    public static Double secante(String expr, double x0, double x1, int maxIterations) {
        if (maxIterations < 1)
            return 0.00;

        if (expr.contains("=")) {
            expr = expr.substring(expr.lastIndexOf("=") + 1);
        }

        Function fx = new Function("f(x) = " + expr);
        double fx0 = fx.calculate(x0);
        double fx1 = fx.calculate(x1);

        double x2 = x1 - ((x1 - x0) / (fx1 - fx0));

        boolean stoppingCriteria = (Math.abs(x2 - x1) == x1);

        if (maxIterations == 1 || stoppingCriteria)
            return x2;

        return secante(expr, x2, x1, maxIterations - 1);
    }


    /***
     * Computes the root of an equation using the secante method.
     * @param expr an equation of the form f(x) = 0
     * @param x0 the lower limit
     * @param x1 the upper limit
     * @param maxIterations The maximum number of iterations to be conducted
     * @return a collection containing the information about the root at every iterative step
     */
    public static List<LocationOfRootResult> secanteAll(String expr, double x0, double x1, int maxIterations) {
        List<LocationOfRootResult> result = new ArrayList<>();


        while (maxIterations > 0) {
            if (expr.contains("=")) {
                expr = expr.substring(expr.lastIndexOf("=") + 1);
            }

            Function fx = new Function("f(x) = " + expr);
            double fx0 = fx.calculate(x0);
            double fx1 = fx.calculate(x1);

            double x2 = x1 - ((x1 - x0) / (fx1 - fx0));

            double difference = Math.abs(x2 - x0);

            //we are not using maxiterations in the output because we would need to reverse it. instead
            //the sequence of the list gives us an idea of the iteration number. lets use that instead
            result.add(new LocationOfRootResult(maxIterations, x0, x1, x2, difference));
            x0 = x2;

            if (difference == 0)
                break;
            maxIterations--;
        }
        return result;
    }


    public static double[] gaussianWithCompletePivoting(double[][] A, double[] B) {

        int N = B.length;
        final double[][] IDENTITY = generateIdentityMatrix(N);

        List<double[][]> Qn = new ArrayList<>();


        for (int k = 0; k < N; k++) {
            double[][] iMatrix = generateIdentityMatrix(N);

            //get pivot column
            int maxColumn = getPivotColumn(A, k);

            //swap pivot column
            swapColumns(A, maxColumn, k);
            swapColumns(iMatrix, maxColumn, k);

            //has any swapping occurred at the column matrix
            if (!Arrays.deepEquals(iMatrix, IDENTITY)) {
                Qn.add(iMatrix);
            }

            //get the pivot row
            int maxRow = getPivotRow(A, k);

            //swap the pivot row with the first row in matrix A
            swapRows(A, maxRow, k);

            //swap corresponding values of the pivot row in the solution matrix
            double temp = B[k];
            B[k] = B[maxRow];
            B[maxRow] = temp;

            //reduce all elements beneath the pivot row
            killRowsBeneath(A, B, k);
        }
        //get the product of all items in Qn

        //multiply the product with the result obtained after solving by back subtitution
        //x = Q.x* where x* is the result obtained after solving by back substitution

        //reordering should take place after that

        //solve by backsubstitution
        return getSolutionByBackSubstitution(A, B, N);
    }

    /***
     *
     * @param dimension the dimension of the matrix for which an identity is to be generated
     * @return a square matrix identity matrix where dimension  = dimension
     */
    private static double[][] generateIdentityMatrix(int dimension) {
        double[][] matrix = null;
        switch (dimension) {
            case 3:

                matrix = new double[][]{
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                };
                break;
            case 4:
                matrix = new double[][]{
                        {1, 0, 0, 0},
                        {0, 1, 0, 0},
                        {0, 0, 1, 0},
                        {0, 0, 0, 1}
                };
                break;

            case 5:
                matrix = new double[][]{
                        {1, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1}
                };
                break;
        }
        return matrix;
    }

    public static String decimalToHexadecimal(String decimal) {
        StringBuilder stringBuilder = new StringBuilder();
        double decimalDouble = Double.parseDouble(decimal);

        //obtain just the integer part and convert to hex first
        int wholePart = (int) decimalDouble;

        stringBuilder.append(Integer.toHexString(wholePart));
        decimalDouble = decimalDouble - wholePart;

        if (decimalDouble == 0) {
            return stringBuilder.toString().toUpperCase();
        }

        stringBuilder.append(".");

        for (int i = 0; i < 16; i++) {
            decimalDouble *= 16;
            int digit = (int) decimalDouble;
            stringBuilder.append(Integer.toHexString(digit));
            decimalDouble = decimalDouble - digit;

            if (decimalDouble == 0)
                break;
        }

        return stringBuilder.toString().toUpperCase();
    }

    public static String decimalToOctal(String decimal) {
        StringBuilder stringBuilder = new StringBuilder();
        double decimalDouble = Double.parseDouble(decimal);

        //obtain just the integer part and convert to hex first
        int wholePart = (int) decimalDouble;

        stringBuilder.append(Integer.toOctalString(wholePart));
        decimalDouble = decimalDouble - wholePart;

        if (decimalDouble == 0) {
            return stringBuilder.toString().toUpperCase();
        }

        stringBuilder.append(".");

        for (int i = 0; i < 8; i++) {
            decimalDouble *= 8;
            int digit = (int) decimalDouble;
            stringBuilder.append(Integer.toOctalString(digit));
            decimalDouble = decimalDouble - digit;
            if (decimalDouble == 0)
                break;
        }

        return stringBuilder.toString().toUpperCase();
    }

    public static double[] multiplyMatrix(double[][] iSolution, double[] x) {
        RealMatrix lhs = MatrixUtils.createRealMatrix(iSolution);
        RealMatrix rhs = MatrixUtils.createColumnRealMatrix(x);

        return lhs.multiply(rhs).getColumn(0);
    }

    public static double[] gaussianWithPartialPivoting(double[][] A, double[] B) {
        int N = B.length;
        for (int k = 0; k < N; k++) {
            //get the pivot row
            int maxRow = getPivotRow(A, k);

            //swap the pivot row with the first row in matrix A
            swapRows(A, maxRow, k);

            //swap corresponding values of the pivot row in the solution matrix
            double temp = B[k];
            B[k] = B[maxRow];
            B[maxRow] = temp;

            //reduce all elements beneath the pivot row
            killRowsBeneath(A, B, k);
        }

        //solve by backsubstitution
        return getSolutionByBackSubstitution(A, B, N);
    }

    private static double[] getSolutionByBackSubstitution(double[][] A, double[] B, int N) {
        double[] solution = new double[N];
        try {
            for (int i = N - 1; i >= 0; i--) {
                double sum = 0.0;

                for (int j = i + 1; j < N; j++)
                    sum += A[i][j] * solution[j];

                solution[i] = (B[i] - sum) / A[i][i];
            }
            //round all array contents to 2dp
            roundTo2Dp(A, B);
            roundTo2Dp(solution);
        } catch (ArrayIndexOutOfBoundsException ay) {
            Timber.d("getSolutionByBackSubstitution: %s", ay.getMessage());
        }


        return solution;
    }

    private static void killRowsBeneath(double[][] A, double[] B, int k) {
        int N = A.length;
        for (int i = k + 1; i < N; i++) {
            double factor = A[i][k] / A[k][k];
            B[i] -= factor * B[k];
            try {
                for (int j = k; j < N; j++)
                    A[i][j] -= factor * A[k][j];
            } catch (ArrayIndexOutOfBoundsException ay) {
                Timber.e("killRowsBeneath: %s", ay.getMessage());
            }
        }
    }

    private static void roundTo2Dp(double[][] A, double[] B) {
        for (int i = 0; i < A.length; i++) {
            B[i] = Double.parseDouble(String.format(Locale.US, "%.2f", B[i]));
            for (int j = 0; j < A.length; j++) {
                A[i][j] = Double.parseDouble(String.format(Locale.US, "%.2f", A[i][j]));
            }
        }
    }

    private static void roundTo2Dp(double[] B) {
        for (int i = 0; i < B.length; i++) {
            B[i] = Double.parseDouble(String.format(Locale.US, "%.2f", B[i]));
        }
    }

    private static int getPivotRow(double[][] system, int k) {
        int maxRowIndex = k;

        for (int i = k + 1; i < system.length; i++) {
            if (Math.abs(system[i][k]) > Math.abs(system[maxRowIndex][k]))
                maxRowIndex = i;
        }

        return maxRowIndex;
    }

    private static void swapRows(double[][] system, int maxRow, int rowIndex) {
        double[] tmpRow = system[maxRow];
        system[maxRow] = system[rowIndex];
        system[rowIndex] = tmpRow;
    }

    private static void swapColumns(double[][] system, int maxCol, int colIndex) {
        for (double[] aSystem : system) {
            ArrayUtils.swap(aSystem, maxCol, colIndex);
        }
    }

    private static int getPivotColumn(double[][] system, int k) {
        int N = system.length;

        int maxColIndex = k;
        int maxRowIndex;

        double maxNumber = -1;

        for (int i = k; i < N; i++) {
            for (int j = k; j < N; j++) {
                if (system[i][j] > maxNumber) {
                    maxRowIndex = i;
                    maxColIndex = j;
                    maxNumber = system[maxRowIndex][maxColIndex];
                }
            }
        }

        return maxColIndex;
    }


    /***
     *
     * @param function a differential equation whose initial conditions will be provided
     * @param h the step size of the interval. for eg. a height of 0.2 for [0,1] will yield 5 results because 1/0.2 = 5
     * @param interval the interval to be considered during the computation. a 1d array of 2 elements
     * @return list of OdeResults
     */
    public static List<OdeResult> solveOdeByEulersMethod(String function, double h, double[] interval, double initY) throws InvalidEquationException {
        if (interval.length == 0)
            throw new InvalidIntervalException("No Interval Provided");

        if (function.trim().contains("f(x)")) {
            function = function.substring(function.indexOf("=") + 1);
        }

        List<OdeResult> results = new ArrayList<>();

        //set the first x value to the lower interval
        double initX = interval[0];

        int steps = (int) (interval[1] / h);

        //intialize an array of size (steps + 2) to hold each iterative output and initialize it's firt element
        //allow additional space for initial conditions
        double[] yn = new double[steps + 2];
        double[] xn = new double[steps + 2];

        //the initial value will be given and stored in the additional space provided during array declaration
        yn[0] = initY;

        //start counting the distance from zero the first given value
        xn[0] = initX;

        for (int i = 0; i < steps + 1; i++) {
            Argument x = new Argument("x", xn[i]);
            Argument y = new Argument("y", yn[i]);


            Expression expression = new Expression(function, x, y);


            double fxResult = expression.calculate();

            //if the answer is infinity or NaN, then we can conclude that the syntax of the equation was wrong
            if (Double.isNaN(fxResult) || Double.isInfinite(fxResult)) {
                throw new InvalidEquationException("Invalid Equation: " + expression.getExpressionString());
            }

            yn[i + 1] = yn[i] + (h * fxResult);
            xn[i + 1] = xn[i] + h;


            results.add(new OdeResult(yn[i], xn[i], i));
        }
        return results;
    }

    /***
     * Solves a system of linear equations using jacobi's method
     * @param system the system of linear equation to be computed
     * @return an array which is the final system computed after the last jacobi iterate
     */
    public static double[] jacobi(String[] system, double[] initGuess, double epsilon) {
        double[] iSolution = new double[3];

        for (int i = 0; i < system.length; i++) {
            Function fx = new Function("f(x)= " + system[i]);
            fx.addArguments(new Argument("x1 = " + initGuess[0]));
            fx.addArguments(new Argument("x2 = " + initGuess[1]));
            fx.addArguments(new Argument("x3 = " + initGuess[2]));

            iSolution[i] = fx.calculate();

            //prevent NaN and infinite solutions when user inputs something undesirable
            if (Double.isNaN(iSolution[i]) || Double.isInfinite(iSolution[i]))
                throw new IllegalArgumentException("Syntax Error, Please check expression");
        }

        double[] difference = new double[3];
        for (int i = 0; i < iSolution.length; i++) {
            difference[i] = iSolution[i] - initGuess[i];
        }

        //infinite norm of the difference of kth and (k - 1)th iterate
        double iNorm = getMaxElement(difference);

        //stopping criteria
        if (iNorm < epsilon) {
            return iSolution;
        } else {
            return jacobi(system, iSolution, epsilon);
        }
    }

    private static double getMaxElement(double[] array) {
        double max = 0;
        for (double item : array) {
            if (Math.abs(item) > Math.abs(max))
                max = item;
        }

        return Math.abs(max);
    }

    /***
     * Solves a system of linear equations using Gauss-Seidel's method
     * @param system the system generated after computing all steps
     * @return the system generated after the last gauss seidel iterate
     */
    public static double[] gaussSeidel(String[] system, double[] initGuess, double epsilon) {
        double[] iSolution = new double[3];
        double[] initGuessTemp = initGuess.clone();


        for (int i = 0; i < system.length; i++) {
            Function fx = new Function("f(x)= " + system[i]);
            fx.addArguments(new Argument("x1 = " + initGuessTemp[0]));
            fx.addArguments(new Argument("x2 = " + initGuessTemp[1]));
            fx.addArguments(new Argument("x3 = " + initGuessTemp[2]));

            iSolution[i] = fx.calculate();
            initGuessTemp[i] = iSolution[i];

            //prevent NaN and infinite solutions when user inputs something undesirable
            if (Double.isNaN(iSolution[i]) || Double.isInfinite(iSolution[i]))
                throw new IllegalArgumentException("Syntax Error, Please check expression");
        }

        double[] difference = new double[3];
        for (int i = 0; i < iSolution.length; i++) {
            difference[i] = iSolution[i] - initGuess[i];
        }

        //infinite norm of the difference of kth and (k - 1)th iterate
        double iNorm = getMaxElement(difference);
        //stopping criteria
        if (iNorm < epsilon) {
            return iSolution;
        } else {
            return gaussSeidel(system, iSolution, epsilon);
        }
    }

    /***
     * Solves a system of linear equations using Gauss-Seidel's method with Succesive Over Relaxation (SOR)
     * @param system the system generated after computing all steps
     * @return the system generated after the last gauss seidel iterate
     */
    public static double[] gaussSeidelWithSOR(String[] system, double[] initGuess, double epsilon, double omega) {
        double[] iSolution = new double[3];
        double[] initGuessTemp = initGuess.clone();


        for (int i = 0; i < system.length; i++) {
            Function fx = new Function("f(x)= " + system[i]);
            fx.addArguments(new Argument("x1 = " + initGuessTemp[0]));
            fx.addArguments(new Argument("x2 = " + initGuessTemp[1]));
            fx.addArguments(new Argument("x3 = " + initGuessTemp[2]));

            iSolution[i] = (fx.calculate() * omega) + (((1 - omega) * initGuess[i]));
            initGuessTemp[i] = iSolution[i];

            //prevent NaN and infinite solutions when user inputs something undesirable
            if (Double.isNaN(iSolution[i]) || Double.isInfinite(iSolution[i]))
                throw new IllegalArgumentException("Syntax Error, Please check expression");
        }

        double[] difference = new double[3];
        for (int i = 0; i < iSolution.length; i++) {
            difference[i] = iSolution[i] - initGuess[i];
        }

        //infinite norm of the difference of kth and (k - 1)th iterate
        double iNorm = getMaxElement(difference);

        //stopping criteria
        if (iNorm < epsilon) {
            return iSolution;
        } else {
            return gaussSeidelWithSOR(system, iSolution, epsilon, omega);
        }

    }

    public static double binaryToDecimal(String bin) throws NotABinaryException {
        //if string is not a correct binary, then return
        if (!isBinary(bin)) {
            throw new NotABinaryException("Input is not a binary: " + bin);
        }

        int len = bin.length();
        // Fetch the radix point
        int point = bin.indexOf('.');

        char[] binary = bin.toCharArray();
        // Update point if not found
        if (point == -1)
            point = len;


        double intDecimal = 0, fracDecimal = 0, twos = 1;

        // Convert integral part of binary to decimal
        // equivalent
        for (int i = point - 1; i >= 0; --i) {
            // Subtract '0' to convert character
            // into integer
            intDecimal += (binary[i] - '0') * twos;
            twos *= 2;
        }

        // Convert fractional part of binary to
        // decimal equivalent
        twos = 2;
        for (int i = point + 1; i < len; ++i) {
            fracDecimal += (binary[i] - '0') / twos;
            twos *= 2.0;
        }

        // Add both integral and fractional part
        return intDecimal + fracDecimal;
    }

    /**
     * Determine whether a value is a binary or not. for example 11010 is binary whereas 11021 is not
     *
     * @param input a supposedly binary input
     * @return a boolean indicating whether input is a binary numeral or not
     */
    public static boolean isBinary(String input) {
        return input.matches("[01]+") || input.matches("[01.]+");
    }

    //get the number of iterations required using the tolerance given
    public static int getBisectionIterations(double tolerance, double x1, double x2) {
        double iterations = (Math.log(x2 - x1) - Math.log(tolerance)) / Math.log(2);

        return Math.round((float) iterations);
    }

    //get the tolerance level required using the number of iterations given
    public static double getBisectionTolerance(int iterations, double x1, double x2) {
        return (x2 - x1) / Math.pow(2, iterations);
    }


    public static String generateTexEquation(String equation) {
        if (equation.isEmpty()) {
            return "";
        }

        StringBuilder str = new StringBuilder(equation.replace("*", "").trim());
        str.insert(0, "$$");
        str.append("$$");
        return str.toString().toLowerCase();
    }

    /**
     * Performs a numerical interpolation using the Lagrange Polynomials.
     *
     * @param x      the x values for which there are known y values
     * @param y      the known values of y
     * @param knownX the known value x for which the corresponding unknown y value must be estimated
     * @return a floating value representing the y value for the knownX value which can be rounded to an arbitrary number of decimal places as desired
     */
    public static double interpolate(double[] x, double[] y, double knownX) {
        //number of terms
        int n = x.length;

        //the unknown y value which we must compute for
        double unknownY = 0;

        double numerator, denominator;
        for (int i = 0; i < n; i++) {
            numerator = 1;
            denominator = 1;

            for (int j = 0; j < n; j++) {
                if (j != i) {
                    numerator = numerator * (knownX - x[j]);
                    denominator = denominator * (x[i] - x[j]);
                }
            }
            try {
                unknownY = unknownY + (numerator / denominator) * y[i];
            } catch (ArrayIndexOutOfBoundsException exception) {
                Timber.i("interpolate: Array out of bounds!");
            }
        }
        return unknownY;
    }

    public enum BinaryOperationType {
        DecimalInteger,
        DecimalFraction
    }
}
