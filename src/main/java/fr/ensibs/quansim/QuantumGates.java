package fr.ensibs.quansim;

/**
 * Class containing a variety of quantum gate matrices.
 */
public class QuantumGates {

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

    /**
     * CNOT quantum game matrix
     */
    public static final Matrix<Complex> CNOT = new Matrix<>(4, 4);

    static {
        initializeX();
        initializeY();
        initializeZ();
        initializeH();
        initializeCNOT();
    }

    /**
     * Private constructor to assert noninstanciability.
     */
    private QuantumGates() {
        throw new AssertionError();
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

    /**
     * Initialize the CNOT quantum gate matrix.
     */
    private static void initializeCNOT() {
        CNOT.setCell(0, 0, new Complex(1, 0));
        CNOT.setCell(1, 0, new Complex(0, 0));
        CNOT.setCell(2, 0, new Complex(0, 0));
        CNOT.setCell(3, 0, new Complex(0, 0));
        CNOT.setCell(0, 1, new Complex(0, 0));
        CNOT.setCell(1, 1, new Complex(1, 0));
        CNOT.setCell(2, 1, new Complex(0, 0));
        CNOT.setCell(3, 1, new Complex(0, 0));
        CNOT.setCell(0, 2, new Complex(0, 0));
        CNOT.setCell(1, 2, new Complex(0, 0));
        CNOT.setCell(2, 2, new Complex(0, 0));
        CNOT.setCell(3, 2, new Complex(1, 0));
        CNOT.setCell(0, 3, new Complex(0, 0));
        CNOT.setCell(1, 3, new Complex(0, 0));
        CNOT.setCell(2, 3, new Complex(1, 0));
        CNOT.setCell(3, 3, new Complex(0, 0));
    }

}
