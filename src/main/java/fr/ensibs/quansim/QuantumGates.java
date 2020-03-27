package fr.ensibs.quansim;

/**
 * Class containing a variety of quantum gate matrices.
 */
public class QuantumGates {

    /**
     * I2 identity matrix
     */
    public static final Matrix<Complex> I2 = new Matrix<>(2, 2);

    /**
     * X quantum game matrix
     */
    public static final Matrix<Complex> X = new Matrix<>(2, 2);

    /**
     * Y quantum game matrix
     */
    public static final Matrix<Complex> Y = new Matrix<>(2, 2);

    /**
     * Z quantum game matrix
     */
    public static final Matrix<Complex> Z = new Matrix<>(2, 2);

    /**
     * H quantum game matrix
     */
    public static final Matrix<Complex> H = new Matrix<>(2, 2);

    static {
        initializeI2();
        initializeX();
        initializeY();
        initializeZ();
        initializeH();
    }

    /**
     * Private constructor to assert noninstanciability.
     */
    private QuantumGates() {
        throw new AssertionError();
    }

    /**
     * Initialize the I2 identity matrix.
     */
    private static void initializeI2() {
        I2.setCell(0, 0, new Complex(1, 0));
        I2.setCell(1, 0, new Complex(0, 0));
        I2.setCell(0, 1, new Complex(0, 0));
        I2.setCell(1, 1, new Complex(1, 0));
    }

    /**
     * Initialize the X quantum gate matrix.
     */
    private static void initializeX() {
        X.setCell(0, 0, new Complex(0, 0));
        X.setCell(1, 0, new Complex(1, 0));
        X.setCell(0, 1, new Complex(1, 0));
        X.setCell(1, 1, new Complex(0, 0));
    }

    /**
     * Initialize the Y quantum gate matrix.
     */
    private static void initializeY() {
        Y.setCell(0, 0, new Complex(0, 0));
        Y.setCell(1, 0, new Complex(0, -1));
        Y.setCell(0, 1, new Complex(0, 1));
        Y.setCell(1, 1, new Complex(0, 0));
    }

    /**
     * Initialize the Z quantum gate matrix.
     */
    private static void initializeZ() {
        Z.setCell(0, 0, new Complex(1, 0));
        Z.setCell(1, 0, new Complex(0, 0));
        Z.setCell(0, 1, new Complex(0, 0));
        Z.setCell(1, 1, new Complex(-1, 0));
    }

    /**
     * Initialize the H quantum gate matrix.
     */
    private static void initializeH() {
        H.setCell(0, 0, new Complex(1 / Math.sqrt(2), 0));
        H.setCell(1, 0, new Complex(1 / Math.sqrt(2), 0));
        H.setCell(0, 1, new Complex(1 / Math.sqrt(2), 0));
        H.setCell(1, 1, new Complex(-1 / Math.sqrt(2), 0));
    }

}
