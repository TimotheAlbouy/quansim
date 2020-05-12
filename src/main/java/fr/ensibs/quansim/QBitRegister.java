package fr.ensibs.quansim;

/**
 * A qbit register, containing one or more qbits.
 */
public class QBitRegister {

    /**
     * the state of the qbit register
     */
    private final Vector<Complex> state;

    /**
     * Constructor.
     * @param n the number of qbits
     */
    public QBitRegister(int n) {
        int length = (int) Math.pow(2, n);
        this.state = new Vector<>(length, true);
    }

    /**
     * Get the number of qbits in the register.
     * @return the size of the register
     */
    public int size() {
        return (int) Math.sqrt(this.state.length());
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
            // we separate into several sections the values of the vector
            // that are part of alpha and those that are part of beta
            int sectionWidth = (int) (this.state.length() / Math.pow(2, i + 1));
            // we determine in which section the current value is in
            int sectionIndex = (j - (j % sectionWidth)) / sectionWidth;
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
        double drawnValue = Math.random();
        int drawnState = -1;
        for (int i = 0; i < this.size(); i++) {
            if (drawnState == -1) {
                accumulator += Math.pow(this.state.getCoordinate(i).modulus(), 2);
                if (drawnValue <= accumulator) {
                    drawnState = i;
                    this.state.setCoordinate(i, new Complex(1, 0));
                } else this.state.setCoordinate(i, new Complex(0, 0));
            } else this.state.setCoordinate(i, new Complex(0, 0));
        }
        return this.toBinary(drawnState);
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

    private boolean[] toBinary(int number) {
        // number of bits = ceil(log2(number))
        int length = (int) Math.ceil(Math.log10(number) / Math.log(2));
        final boolean[] ret = new boolean[length];
        for (int i = 0; i < length; i++) {
            ret[length - 1 - i] = (1 << i & number) != 0;
        }
        return ret;
    }

}
