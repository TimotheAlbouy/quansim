package fr.ensibs.quansim;

import java.util.Arrays;

import static fr.ensibs.quansim.QuantumGates.*;

/**
 * Test class for the Quansim library.
 */
public class QuansimTest {

    private static final double THRESHOLD = 0.05;

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
        this.testIdentities();

        /**
        System.out.println("Threshold of acceptance: " + THRESHOLD);

        QBit qBit;
        int outliers = 0;
        for (int i = 0; i < ITERATIONS; i++) {
            qBit = randomQBit();//new QBit(new Complex(.5, .5), new Complex(.5, .5));
            boolean drawBefore = qBit.;
            qBit.X();
            qBit.randomDraw();
        }*/
    }

    public void testIdentities() {
        System.out.println("TEST OF GATE IDENTITIES");

        System.out.print("1) HXH = Z: ");
        if (H.times(X).times(H).equals(Z))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("2) HZH = X: ");
        if (H.times(Z).times(H).equals(X))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("3) HYH = -Y: ");
        if (H.times(Y).times(H).equals(Y.negative()))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("4) X² = Y² = Z² = I: ");
        if (X.times(X).equals(I2) && Y.times(Y).equals(I2) && Z.times(Z).equals(I2))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("5) (X+Z)/√2 = H: ");
        if (X.plus(Z).divide(Math.sqrt(2)).equals(H))
            System.out.println("success");
        else System.out.println("failure");

        System.out.print("5) H² = I: ");
        if (H.times(H).equals(I2))
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
     * Draw a random boolean.
     * @return true half of the time, false the other half of the time
     */
    private boolean randomBoolean() {
        return Math.random() < .5;
    }

}
