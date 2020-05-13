package fr.ensibs.quansim;

/**
 * Class containing a variety of quantum gate matrices.
 */
public class QuantumGates {

    /**
     * X quantum game matrix
     */
    public static final ComplexMatrix X = new ComplexMatrix(new Complex[][]{
            {new Complex(0, 0), new Complex(1, 0)},
            {new Complex(1, 0), new Complex(0, 0)}
    });

    /**
     * Y quantum game matrix
     */
    public static final ComplexMatrix Y = new ComplexMatrix(new Complex[][]{
            {new Complex(0, 0), new Complex(0, -1)},
            {new Complex(0, 1), new Complex(0, 0)}
    });

    /**
     * Z quantum game matrix
     */
    public static final ComplexMatrix Z = new ComplexMatrix(new Complex[][]{
            {new Complex(1, 0), new Complex(0, 0)},
            {new Complex(0, 0), new Complex(-1, 0)}
    });

    /**
     * H quantum game matrix
     */
    public static final ComplexMatrix H = new ComplexMatrix(new Complex[][]{
            {new Complex(1 / Math.sqrt(2), 0), new Complex(1 / Math.sqrt(2), 0)},
            {new Complex(1 / Math.sqrt(2), 0), new Complex(-1 / Math.sqrt(2), 0)}
    });

    /**
     * CNOT quantum game matrix
     */
    public static final ComplexMatrix CNOT = new ComplexMatrix(new Complex[][]{
            {new Complex(1, 0), new Complex(0, 0), new Complex(0, 0), new Complex(0, 0)},
            {new Complex(0, 0), new Complex(1, 0), new Complex(0, 0), new Complex(0, 0)},
            {new Complex(0, 0), new Complex(0, 0), new Complex(0, 0), new Complex(1, 0)},
            {new Complex(0, 0), new Complex(0, 0), new Complex(1, 0), new Complex(0, 0)}
    });

    /**
     * Private constructor to assert noninstanciability.
     */
    private QuantumGates() {
        throw new AssertionError();
    }

}
