package fr.ensibs.quansim;

import java.util.Arrays;

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
     * Give the probability to draw the given basic state.
     * @param basicState the decimal representation of the basic state (e.g. 3 = b011 in a 3 qbits register)
     * @return the corresponding probability
     */
    public double proba(int basicState) {
        return Math.pow(this.state.getCoordinate(basicState).modulus(), 2);
    }

    /**
     * Apply a 2x2 quantic gate on a qbit of the register.
     * @param qg the quantic gate matrix
     * @param qbitIdx the index of the qbit in the register
     * @return the qbit register after the quantic gate
     */
    public QBitRegister apply(ComplexMatrix qg, int qbitIdx) {
        if (qg == null)
            throw new NullPointerException("The quantum gate matrix cannot be null.");

        if (qg.width() != 2 && qg.height() != 2)
            throw new IllegalArgumentException("The quantum gate matrix must be 2x2.");

        if (qbitIdx < 0 || qbitIdx >= this.size())
            throw new IndexOutOfBoundsException("The qbit index is out of bounds.");

        int offset = (int) Math.pow(2, qbitIdx);
        int startIdx = 0;
        int ctr = 0;
        while (startIdx < this.state.length()) {
            Complex c1 = this.state.getCoordinate(startIdx);
            Complex c2 = this.state.getCoordinate(startIdx + offset);
            Complex[] coordinates = new Complex[]{c1, c2};
            ComplexVector cv = new ComplexVector(coordinates);
            cv = qg.times(cv).getColumnVector(0);
            this.state.setCoordinate(startIdx, cv.getCoordinate(0));
            this.state.setCoordinate(startIdx + offset, cv.getCoordinate(1));
            startIdx++;
            ctr++;
            if (ctr >= offset) {
                startIdx += offset;
                ctr = 0;
            }
        }
        return this;
    }

    /**
     * Apply a nxn quantic gate on one or several qbits of the register.
     * @param qg the quantic gate matrix
     * @param qbitsIdx the indexes of the qbits in the register
     * @return the qbit register after the quantic gate
     */
    public QBitRegister apply(ComplexMatrix qg, int... qbitsIdx) {
        if (qg == null)
            throw new NullPointerException("The quantum gate matrix cannot be null.");

        if (qg.width() <= 1 || !isPowerOfTwo(qg.width()))
            throw new IllegalArgumentException("The quantum gate matrix must have dimensions that are a power of 2.");

        if (qg.width() != qg.height())
            throw new IllegalArgumentException("The quantum gate matrix must be square.");

        if (qg.width() > this.state.length())
            throw new IllegalArgumentException("The quantum gate matrix cannot be longer than the state vector.");

        if (qbitsIdx == null)
            throw new NullPointerException("The list of qbits indexes cannot be null.");

        if (qbitsIdx.length == 0)
            throw new IllegalArgumentException("There must be at least 1 selected qubit.");

        if (Math.pow(2, qbitsIdx.length) !=  qg.width())
            throw new IllegalArgumentException("The given quantic gate is incompatible with the number of qubits.");

        Arrays.sort(qbitsIdx);
        for (int i = 0; i < qbitsIdx.length; i++)
            if (qbitsIdx[i] < 0 || qbitsIdx[i] >= this.size())
                throw new IndexOutOfBoundsException("One of the qbit indexes is out of bounds.");
            else if (i > 0 && qbitsIdx[i - 1] == qbitsIdx[i])
                throw new IllegalArgumentException("The list of qbit indexes cannot contain duplicates.");

        // length of the sub-vectors on which we will apply the quantic gate
        int subvectorLen = qg.width();
        // number of sub-vectors, i.e. number of times we apply the quantic gate on the state vector
        int subvectorNb = this.state.length() / subvectorLen;

        boolean[][] substatesBin = new boolean[this.state.length()][qbitsIdx.length];
        for (int i = 0; i < substatesBin.length; i++) {
            boolean[] stateBin = toBinary(i, this.size());
            for (int j = 0; j < qbitsIdx.length; j++)
                substatesBin[i][qbitsIdx.length - 1 - j] = stateBin[this.size() - 1 - qbitsIdx[j]];
        }

        int[] offsets = new int[subvectorLen];
        for (int i = 0; i < subvectorLen; i++) {
            int idx = 0;
            while (!Arrays.equals(toBinary(i, qbitsIdx.length), substatesBin[idx])) idx++;
            offsets[i] = idx;
        }

        int startIdx = 0;
        for (int i = 0; i < subvectorNb; i++) {
            while (!Arrays.equals(new boolean[qbitsIdx.length], substatesBin[startIdx])) startIdx++;
            Complex[] coordinates = new Complex[subvectorLen];
            for (int j = 0; j < subvectorLen; j++)
                coordinates[j] = this.state.getCoordinate(startIdx + offsets[j]);
            ComplexVector cv = new ComplexVector(coordinates);
            cv = qg.times(cv).getColumnVector(0);
            for (int j = 0; j < subvectorLen; j++)
                this.state.setCoordinate(startIdx + offsets[j], cv.getCoordinate(j));
            startIdx++;
        }

        return this;
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
        for (int i = 0; i < this.state.length(); i++) {
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
        return toBinary(drawnState, this.size());
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

    /**
     * Convert a number to its binary representation.
     * @param number the number to convert
     * @param length the number of bits in output
     * @return an array of booleans representing the binary number
     */
    private static boolean[] toBinary(int number, int length) {
        final boolean[] ret = new boolean[length];
        for (int i = 0; i < length; i++)
            ret[length - 1 - i] = (1 << i & number) != 0;
        return ret;
    }

    /**
     * Tell if the given number is a power of 2.
     * @param number the number to check
     * @return true if and only if the number is a power of 2
     */
    private static boolean isPowerOfTwo(int number) {
        return number > 0 && ((number & (number - 1)) == 0);
    }

}
