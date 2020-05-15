package fr.ensibs.quansim;

import java.util.Arrays;

import static fr.ensibs.quansim.QuantumGates.*;

/**
 * Test class for the Quansim library.
 */
public class QuansimTest {

    /**
     * threshold of acceptance to define the margin of error
     */
    private static final double THRESHOLD = 0.05;

    /**
     * number of iterations for each identity
     */
    private static final int ITERATIONS = 1000;

    /**
     * Entry point of the application.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        new QuansimTest();
    }

    /**
     * Constructor.
     */
    public QuansimTest() {
        //this.testIdentitiesTheoric();
        System.out.println();
        //this.testIdentitiesEmpiric();
        System.out.println();
        //this.testRegister();
        System.out.println();
        this.testEntanglement();
    }

    /**
     * Test the quantum gates' identities by comparing the qbits' states.
     */
    private void testIdentitiesTheoric() {
        System.out.println("THEORIC TEST OF QUANTUM GATE IDENTITIES");
        QBit model, qb1, qb2, qb3, qb4;
        int ctr;

        System.out.print("1) HXH = Z: ");
        ctr = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            model = randomQBit();
            qb1 = model.copy().H().X().H();
            qb2 = model.copy().Z();
            if (qb1.equals(qb2)) ctr++;
        }
        if (ctr == ITERATIONS)
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("2) HZH = X: ");
        ctr = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            model = randomQBit();
            qb1 = model.copy().H().Z().H();
            qb2 = model.copy().X();
            if (qb1.equals(qb2)) ctr++;
        }
        if (ctr == ITERATIONS)
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("3) HYH = -Y: ");
        ctr = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            model = randomQBit();
            qb1 = model.copy().H().Y().H();
            qb2 = model.copy().apply(Y.negative());
            if (qb1.equals(qb2)) ctr++;
        }
        if (ctr == ITERATIONS)
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("4) X² = Y² = Z² = I: ");
        ctr = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            model = randomQBit();
            qb1 = model.copy().X().X();
            qb2 = model.copy().Y().Y();
            qb3 = model.copy().Z().Z();
            qb4 = model.copy();
            if (qb1.equals(qb2) && qb1.equals(qb3) && qb1.equals(qb4)) ctr++;
        }
        if (ctr == ITERATIONS)
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("5) H = (X+Z)/√2: ");
        ctr = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            model = randomQBit();
            qb1 = model.copy().H();
            qb2 = model.copy().apply(X.plus(Z).divide(Math.sqrt(2)));
            if (qb1.equals(qb2)) ctr++;
        }
        if (ctr == ITERATIONS)
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("6) H² = I: ");
        ctr = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            model = randomQBit();
            qb1 = model.copy().H().H();
            qb2 = model.copy();
            if (qb1.equals(qb2)) ctr++;
        }
        if (ctr == ITERATIONS)
            System.out.println("success");
        else System.out.println("failure");
    }

    /**
     * Test the quantum gates' identities by comparing the number of 1 randomly drawn.
     */
    private void testIdentitiesEmpiric() {
        System.out.println("EMPIRIC TEST OF QUANTUM GATE IDENTITIES");
        QBit model, qb1, qb2, qb3, qb4;
        int ctr1, ctr2, ctr3, ctr4;

        System.out.print("1) HXH = Z: ");
        ctr1 = 0;
        ctr2 = 0;
        model = randomQBit();
        for (int i = 0; i < ITERATIONS; i++) {
            qb1 = model.copy().H().X().H();
            qb2 = model.copy().Z();
            if (qb1.randomDraw()) ctr1++;
            if (qb2.randomDraw()) ctr2++;
        }
        if (roughlyEqual(ctr1, ctr2))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("2) HZH = X: ");
        ctr1 = 0;
        ctr2 = 0;
        model = randomQBit();
        for (int i = 0; i < ITERATIONS; i++) {
            qb1 = model.copy().H().Z().H();
            qb2 = model.copy().X();
            if (qb1.randomDraw()) ctr1++;
            if (qb2.randomDraw()) ctr2++;
        }
        if (roughlyEqual(ctr1, ctr2))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("3) HYH = -Y: ");
        ctr1 = 0;
        ctr2 = 0;
        model = randomQBit();
        for (int i = 0; i < ITERATIONS; i++) {
            qb1 = model.copy().H().Y().H();
            qb2 = model.copy().apply(Y.negative());
            if (qb1.randomDraw()) ctr1++;
            if (qb2.randomDraw()) ctr2++;
        }
        if (roughlyEqual(ctr1, ctr2))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("4) X² = Y² = Z² = I: ");
        ctr1 = 0;
        ctr2 = 0;
        ctr3 = 0;
        ctr4 = 0;
        model = randomQBit();
        for (int i = 0; i < ITERATIONS; i++) {
            qb1 = model.copy().X().X();
            qb2 = model.copy().Y().Y();
            qb3 = model.copy().Z().Z();
            qb4 = model.copy();
            if (qb1.randomDraw()) ctr1++;
            if (qb2.randomDraw()) ctr2++;
            if (qb3.randomDraw()) ctr3++;
            if (qb4.randomDraw()) ctr4++;
        }
        if (roughlyEqual(ctr1, ctr2) && roughlyEqual(ctr1, ctr3) && roughlyEqual(ctr1, ctr4))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("5) H = (X+Z)/√2: ");
        ctr1 = 0;
        ctr2 = 0;
        model = randomQBit();
        for (int i = 0; i < ITERATIONS; i++) {
            qb1 = model.copy().H();
            qb2 = model.copy().apply(X.plus(Z).divide(Math.sqrt(2)));
            if (qb1.randomDraw()) ctr1++;
            if (qb2.randomDraw()) ctr2++;
        }
        if (roughlyEqual(ctr1, ctr2))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("6) H² = I: ");
        ctr1 = 0;
        ctr2 = 0;
        model = randomQBit();
        for (int i = 0; i < ITERATIONS; i++) {
            qb1 = model.copy().H().H();
            qb2 = model.copy();
            if (qb1.randomDraw()) ctr1++;
            if (qb2.randomDraw()) ctr2++;
        }
        if (roughlyEqual(ctr1, ctr2))
            System.out.println("success");
        else System.out.println("failure");
    }

    /**
     * Test the behaviour of qbit registers.
     */
    private void testRegister() {
        System.out.println("TEST OF 2 QBITS REGISTERS");
        QBitRegister model, qr1, qr2;
        int ctr1, ctr2;

        System.out.print("1) X on LSB: ");
        ctr1 = 0;
        ctr2 = 0;
        model = randomQBitRegister(2);
        for (int i = 0; i < ITERATIONS; i++) {
            qr1 = model.copy();
            qr2 = model.copy().apply(X, 0);
            if (qr1.randomDraw()[1]) ctr1++;
            if (!qr2.randomDraw()[1]) ctr2++;
        }
        if (roughlyEqual(ctr1, ctr2))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("2) X on MSB: ");
        ctr1 = 0;
        ctr2 = 0;
        model = randomQBitRegister(2);
        for (int i = 0; i < ITERATIONS; i++) {
            qr1 = model.copy();
            qr2 = model.copy().apply(X, 1);
            if (qr1.randomDraw()[0]) ctr1++;
            if (!qr2.randomDraw()[0]) ctr2++;
        }
        if (roughlyEqual(ctr1, ctr2))
            System.out.println("success");
        else System.out.println("failure");
    }

    /**
     * Test of entanglement of 2 qubits.
     */
    private void testEntanglement() {
        System.out.println("TEST OF ENTANGLEMENT");
        QBitRegister model = new QBitRegister(2);
        System.out.println(model);
        boolean failure = false;
        int i = 0;
        while (i < ITERATIONS && !failure) {
            QBitRegister qr = model.copy().apply(H, 1).apply(CNOT, 0, 1);
            boolean[] draws = qr.randomDraw();
            if (draws[0] != draws[1]) failure = true;
            i++;
        }
        if (!failure)
            System.out.println("success");
        else System.out.println("failure");
    }

    /**
     * Generate a qbit with a random state.
     * @return a random qbit
     */
    private QBit randomQBit() {
        // we start from the formula: |alpha|² + |beta|² = 1
        // <=> sqrt(alphaRe² + alphaIm²)² + sqrt(betaRe² + betaIm²)² = 1
        // <=> alphaRe² + alphaIm² + betaRe² + betaIm² = 1
        // (the radicands cannot be negative because they are the sum of the squares of real numbers)

        // we separate the [0,1] interval into 4 sections which lengths are
        // the squares of the real and imaginary components of alpha and beta
        double[] bounds = new double[3];
        for (int i = 0; i < 3; i++)
            bounds[i] = Math.random();
        Arrays.sort(bounds);

        double alphaRe = Math.sqrt(bounds[0]) * (this.randomBoolean() ? -1 : 1);
        double alphaIm = Math.sqrt(bounds[1] - bounds[0]) * (this.randomBoolean() ? -1 : 1);
        double betaRe = Math.sqrt(bounds[2] - bounds[1]) * (this.randomBoolean() ? -1 : 1);
        double betaIm = Math.sqrt(1 - bounds[2]) * (this.randomBoolean() ? -1 : 1);

        Complex alpha = new Complex(alphaRe, alphaIm);
        Complex beta = new Complex(betaRe, betaIm);
        return new QBit(alpha, beta);
    }

    /**
     * Generate a qbit register with a random state.
     * @param n the number of qbits
     * @return a random qbit
     */
    private QBitRegister randomQBitRegister(int n) {
        // generalization of randomQBit()
        if (n <= 0)
            throw new IllegalArgumentException("The number of qbits in the register must be positive.");

        int complexNb = (int) Math.pow(2, n);
        int boundsNb = 2 * complexNb + 1;
        double[] bounds = new double[boundsNb];
        bounds[0] = 0;
        bounds[boundsNb - 1] = 1;
        for (int i = 1; i < boundsNb - 1; i++)
            bounds[i] = Math.random();
        Arrays.sort(bounds);

        Complex[] coordinates = new Complex[complexNb];
        for (int i = 0; i < complexNb; i++) {
            int startIndex = 2 * i;
            double re = Math.sqrt(bounds[startIndex + 1] - bounds[startIndex]);
            double im = Math.sqrt(bounds[startIndex + 2] - bounds[startIndex + 1]);
            if (this.randomBoolean()) re *= -1;
            if (this.randomBoolean()) im *= -1;
            coordinates[i] = new Complex(re, im);
        }

        return new QBitRegister(coordinates);
    }

    /**
     * Draw a random boolean.
     * @return true half of the time, false the other half of the time
     */
    private boolean randomBoolean() {
        return Math.random() < .5;
    }

    /**
     * Tell if the distance between the two counters is inferior to the margin of error.
     * @param ctr1 the first counter
     * @param ctr2 the second counter
     * @return true if and only if the 2 counters are roughly equal
     */
    private boolean roughlyEqual(int ctr1, int ctr2) {
        int marginOfError = (int) (ITERATIONS * THRESHOLD);
        return ctr1 + marginOfError >= ctr2 && ctr1 - marginOfError <= ctr2;
    }

}
