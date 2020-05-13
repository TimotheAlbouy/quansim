package fr.ensibs.quansim;

/**
 * A qbit register, containing one or more qbits.
 */
public class QBitRegister {

    /**
     * the state of the qbit register
     */
    private final ComplexVector state;

    /**
     * Constructor.
     * @param n the number of qbits
     */
    public QBitRegister(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("The number of qbits in the register must be positive.");

        int length = (int) Math.pow(2, n);
        this.state = new ComplexVector(length);
        this.state.setCoordinate(0, new Complex(1, 0));
        for (int i = 1; i < length; i++)
            this.state.setCoordinate(i, new Complex(0, 0));
    }

    /**
     * Constructor.
     * @param coordinates the coordinates of qbit register
     */
    public QBitRegister(Complex[] coordinates) {
        if (coordinates == null)
            throw new NullPointerException("The initialization coordinates cannot be null.");

        if (!isPowerOfTwo(coordinates.length))
            throw new IllegalArgumentException("The length of the coordinates must be a power of 2.");

        double sum = 0;
        for (Complex c : coordinates)
            sum += Math.pow(c.modulus(), 2);
        if (sum <= 0.999 || sum >= 1.001)
            throw new IllegalArgumentException("The sum of the square moduli of the coordinates must be 1.");

        this.state = new ComplexVector(coordinates);
    }

    /**
     * Get the number of qbits in the register.
     * @return the size of the register
     */
    public int size() {
        return (int) (Math.log10(this.state.length()) / Math.log10(2));
    }

    /**
     * Apply a quantic gate on a qbit of the register.
     * @param qg the quantic gate matrix
     * @param qbitIndex the index of the qbit in the register
     * @return the qbit register after the quantic gate
     */
    public QBitRegister apply(ComplexMatrix qg, int qbitIndex) {
        if (qg == null)
            throw new NullPointerException("The operation matrix cannot be null.");

        if (qg.width() != 2 && qg.height() != 2)
            throw new IllegalArgumentException("The quantum gate matrix must be 2x2.");

        if (qbitIndex < 0 || qbitIndex >= this.size())
            throw new IndexOutOfBoundsException("The qbit index is out of bounds.");

        int offset = (int) Math.pow(2, qbitIndex);
        int alphaCoord = 0;
        int betaCoord = offset;
        int ctr = 0;
        while (alphaCoord < this.state.length()) {
            Complex alpha = this.state.getCoordinate(alphaCoord);
            Complex beta = this.state.getCoordinate(betaCoord);
            Complex[] coordinates = new Complex[]{alpha, beta};
            ComplexVector cv = new ComplexVector(coordinates);
            cv = qg.times(cv).getColumnVector(0);
            this.state.setCoordinate(alphaCoord, cv.getCoordinate(0));
            this.state.setCoordinate(betaCoord, cv.getCoordinate(1));
            alphaCoord++;
            ctr++;
            if (ctr >= offset) {
                alphaCoord += offset;
                ctr = 0;
            }
            betaCoord = alphaCoord + offset;
        }
        return this;
    }

    /**
     * Get a qbit from the register.
     * @param i the index of the qbit to retrieve
     * @return the qbit identified by its index
     */
    public QBit getQBit(int i) {
        if (i < 0 || i >= this.state.length() / 2)
            throw new IndexOutOfBoundsException("The index of the qbit is out of bounds.");

        Complex alpha = new Complex(0, 0);
        Complex beta = new Complex(0, 0);
        // for each value of the vector representing the qbit register's state
        for (int j = 0; j < this.state.length(); j++) {
            // we separate the values of the vector into several sections
            // that are part of alpha and those that are part of beta
            int sectionLength = (int) (this.state.length() / Math.pow(2, i + 1));
            // we determine in which section the current value is in
            int sectionIndex = (j - (j % sectionLength)) / sectionLength;
            boolean addAlpha = sectionIndex % 2 == 0 ;
            // if the value is in an even section, add it to alpha
            if (addAlpha)
                alpha = alpha.plus(this.state.getCoordinate(j));
            // if the value is in an odd section, add it to beta
            else beta = beta.plus(this.state.getCoordinate(j));
        }

        return new QBit(alpha, beta);
    }

    /**
     * Draw values randomly for the qbits of the register according to their associated probabilities.
     * @return an array of booleans, which are equal to true if the drawn bit was 1, and false otherwise
     */
    public boolean[] randomDraw() {
        double accumulator = 0;
        // we draw a value in [0, 1]
        double drawnValue = Math.random();
        int drawnState = -1;
        // for all the complexes of the register's state
        for (int i = 0; i < this.size(); i++) {
            // if the drawn state has not been determined yet
            if (drawnState == -1) {
                // we accumulate the square moduli of the previous complexes
                accumulator += Math.pow(this.state.getCoordinate(i).modulus(), 2);
                // if the drawn value becomes inferior to the accumulator
                if (drawnValue <= accumulator) {
                    // we have determined the drawn state
                    drawnState = i;
                    // the new complex at this coordinate is 1
                    this.state.setCoordinate(i, new Complex(1, 0));
                }
                // else, the new complex at this coordinate is 0
                else this.state.setCoordinate(i, new Complex(0, 0));
            }
            // else, the new complex at this coordinate is 0
            else this.state.setCoordinate(i, new Complex(0, 0));
        }
        // return the binary representation of the drawn state
        return toBinary(drawnState);
    }

    /**
     * Create a deep copy of the qbit register, even though it is physically impossible.
     * @return a copy of the qbit register
     */
    public QBitRegister copy() {
        Complex[] coordinates = new Complex[this.state.length()];
        for (int i = 0; i < this.state.length(); i++)
            coordinates[i] = this.state.getCoordinate(i);
        return new QBitRegister(coordinates);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QBitRegister)) return false;
        QBitRegister register = (QBitRegister) o;
        return this.state.equals(register.state);
    }

    @Override
    public String toString() {
        return state.toString();
    }

    private static boolean[] toBinary(int number) {
        // number of bits = upper(log2(number))
        int length = (int) Math.ceil(Math.log10(number) / Math.log(2));
        final boolean[] ret = new boolean[length];
        for (int i = 0; i < length; i++)
            ret[length - 1 - i] = (1 << i & number) != 0;
        return ret;
    }

    private static boolean isPowerOfTwo(int number) {
        return number > 0 && ((number & (number - 1)) == 0);
    }

}
