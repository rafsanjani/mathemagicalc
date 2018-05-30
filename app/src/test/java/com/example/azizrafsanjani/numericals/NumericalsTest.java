package com.example.azizrafsanjani.numericals;

/**
 * Created by Aziz Rafsanjani on 10/21/2017.
 */


import com.example.azizrafsanjani.numericals.model.LocationOfRootResult;
import com.example.azizrafsanjani.numericals.utils.Numericals;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class NumericalsTest {
    private static void printMatrix(double[][] matrix) {
        for (double[] aMatrix : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(aMatrix[j] + " ");
            }
            System.out.println();
        }
    }

    private static void printMatrix(double A[][], double B[]) {
        for (int rowIndex = 0; rowIndex < A.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < A.length; columnIndex++) {
                System.out.print(A[rowIndex][columnIndex] + " ");
            }
            System.out.print(B[rowIndex]);
            System.out.println();
        }
    }

    @Test
    public void testIterationsWithtolerance() {
        double x1 = -2;
        double x2 = -1;
        double tol = 0.0039;

        int iterations = Numericals.getBisectionIterations(tol, x1, x2);
        System.out.println(iterations);

        assertEquals(iterations, 8);
    }

    @Test
    public void testToleranceWithIterations() {
        double x1 = -2;
        double x2 = -1;
        int iterations = 8;

        double tolerance = Numericals.getBisectionTolerance(iterations, x1, x2);

        assertEquals(tolerance, 0.00390625);

    }

    private static void printMatrix(double system[]) {
        for (double aSystem : system) {
            System.out.print(aSystem + " ");
        }
    }

    @Test
    public void testDecimalIntToBinaryWith10() {
        assertEquals("1010", Numericals.DecimalIntToBinary(10));

    }

    @Test
    public void testGenerateTexEquation() {
        String tex = Numericals.generateTexEquation("f(x) = 3*x^2 - 2");
        System.out.println(tex);
    }

    @Test
    public void testBisectionShouldPass() {
        double y = Numericals.Bisect("x^5 + x^3 + 3", -2, -1, 4, 0.005);
        assertEquals(-1.0625, y);
    }

    @Test
    public void testBisectionAllShouldPass() {
        List<LocationOfRootResult> longBisection = Numericals.BisectAll("x^5 + x^3 + 3", -2, -1, 4, 0.005);
        assertEquals(-1.0625, longBisection.get(3).getX3());
    }

    @Test
    public void testNewtonRaphson() {
        String eqn = "x^5 + x^3+3";
        double y = Numericals.NewtonRaphson(eqn, -1, 20);
        assertEquals(-1.105298546, y);
    }

    @Test
    public void testNewtonRaphsonAll() {
        String eqn = "x^5 + x^3+3";
        List<LocationOfRootResult> results = Numericals.NewtonRaphsonAll(eqn, -1, 20);
        for (LocationOfRootResult x : results) {
            System.out.println(x.getX1());
        }
    }

    @Test
    public void testGaussianComplete() {
        double[][] A = {
                {1, -2, -3},
                {3, 5, 2},
                {2, 3, -1}
        };

        double B[] = {0, 0, -1};

        //Note: final answer to this system is {-0.5, 0.5, 0.5}

        double sol[] = Numericals.GaussianWithCompletePivoting(A, B);


        printMatrix(sol);
    }

    @Test
    public void testGaussianWithPartialPivoting() {
        double[][] A = {
                {1, -2, -3},
                {3, 5, 2},
                {2, 3, -1}
        };

        double B[] = {0, 0, -1};

        //Note: final answer to this system is {0.5, -0.5, 0.5}

        double sol[] = Numericals.GaussianWithPartialPivoting(A, B);

    }

    @Test
    public void testKillRowsBeneath() {
        double[][] A = {
                {1, -2, -3},
                {3, 5, 2},
                {2, 3, -1}
        };

        double B[] = {0, 0, -1};


        printMatrix(A);
    }

    @Test
    public void testGaussianPartial4x4Matrix() {
        double[][] A = {
                {8, 3, 4, 5},
                {14, 4, 33, 23},
                {15, 4, 23, 7},
                {4, 11, 17, 16}
        };


       // double[] solution = {-14.48, 19.56, 34.12, -5.68};

        double B[] = {31, 17, 22, 51};


        double iSolution[] = Numericals.GaussianWithCompletePivoting(A, B);
        //note solution to the above big matrix is {59.5, -67.5, 87,-55, -20.5}
        printMatrix(A);

    }

    @Test
    public void testGaussianComplete4x4Matrix() {
        double[][] A = {
                {1, -4, 4, 7},
                {0, 2, -1, 0},
                {2, 1, 1, 4},
                {2, -3, 2, -5}
        };


        double[] solution = {-14.48, 19.56, 34.12, -5.68};

        double B[] = {4, 5, 2, 9};

        //double sol[] = Numericals.lsolve(A, B);

        //ANOTHER EXAMPLE
        double[][] AA = {
                {1, 2, 4, 3, 5},
                {3, 5, 3, 1, 2},
                {1, 4, 4, 2, 1},
                {4, 1, 2, 5, 3},
                {5, 2, 1, 4, 1}
        };

        double BB[] = {5, 6, 7, 8, 9};

        double iSolution[] = Numericals.GaussianWithCompletePivoting(AA, BB);
        //note solution to the above big matrix is {59.5, -67.5, 87,-55, -20.5}
        printMatrix(iSolution);

    }

    @Test
    //This test should pass
    public void testMultiplicationOfMatrixPass() {
        double A[][] = {
                {4, 3, 5},
                {4, 1, 2},
                {5, 6, 8}
        };

        double[] B = {2, 3, 5};

        double[] solution = Numericals.multiplyMatrix(A, B);
        double[] expected = {42, 21, 68};

        int valid = 0;
        for (int a = 0; a < solution.length; a++) {
            if (solution[a] == expected[a]) {
                valid++;
            }
        }
        assertEquals(valid, solution.length);
    }

    @Test
    public void testMultiplicationofMatrix() {
        double A[][] = {
                {0, 1, 0},
                {1, 0, 0},
                {0, 0, 1}
        };

        double B[][] = {
                {-0.5, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        double C[] = {-0.5, 0.5, 0.5};

        double D[] = Numericals.multiplyMatrix(A, C);
        printMatrix(D);

    }

    @Test
    public void testJacobi() {
        String system[] = {
                "(1/2)*(x2+1)",
                "(1/3)*(x1+x3+8)",
                "(1/2)*(x2-5)"};
        double initGuess[] = {0, 0, 0};
        double sol[] = Numericals.Jacobi(system, initGuess, 0.15);

    }

    @Test
    public void testGaussSeidel() {
        String system[] = {
                "(1/2)*(x2+1)",
                "(1/3)*(x1+x3+8)",
                "(1/2)*(x2-5)"};
        double initGuess[] = {0, 0, 0};
        double sol[] = Numericals.GaussSeidel(system, initGuess, 0.01);
    }

    @Test
    public void testGaussSeidelShouldPass() {
        String system[] = {
                "(1/4)*(x2+x3 + 3)",
                "(1/6)*(2*x1-x3+9)",
                "(1/7)*(x1-x2 - 6)"};
        double initGuess[] = {0, 0, 0};
        double sol[] = Numericals.GaussSeidel(system, initGuess, 0.000001);
    }

    @Test
    public void testGaussSeidelXOR() {
        String system[] = {
                "(-1+x2-x3)/3",
                "(7+x1+x3)/3",
                "(-7-x1+x2)/3"
        };
        double initGuess[] = {0, 0, 0};
        double omega = 1.25;
        double sol[] = Numericals.GaussSeidelWithSOR(system, initGuess, 0.00011, omega);
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
        double y = Numericals.Secante(eqn, 1, -1, 100);
        System.out.println(y);
        assertEquals(-1.5, y);
    }

    @Test
    public void testSecanteAllShouldPass() {
        String eqn = "x^3 + x^3 + 3";
        List<LocationOfRootResult> result = Numericals.SecanteAll(eqn, 1.0, -1.0, 800);
        System.out.println("X1      X2        X3      DIFFERENCE");

        for (LocationOfRootResult y : result) {
            System.out.println(y.getIteration() + ":::::" + y.getX1() + " " + y.getX2() + " " + y.getX3() + " " + y.getDifference());
        }
        assertEquals(-1.5, result.get(0).getX3());
    }


    @Test
    public void testBisectionShouldFail() {
        assertEquals(-1.1056875, Numericals.Bisect("f(x) = x^5 + x^3 + 3", -2, -1, 7, 0.00005));
    }

    @Test
    public void testDecimalToHexadecimal() {
        String decimal = "123994839";
        String hex = Numericals.DecimalToHexadecimal(decimal);

        assertEquals(hex, "f");

    }
}
