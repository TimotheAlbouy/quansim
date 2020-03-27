package fr.ensibs.quansim;

/**
 * A qbit register, containing one or more qbits.
 */
public class QBitRegister extends Vector<Complex> {

    /**
     * Constructor.
     * @param n the number of qbits
     */
    public QBitRegister(int n) {
        super((int) Math.pow(2, n));
    }

    /**
     * Get the number of qbits in the register.
     * @return the size of the register
     */
    public int registerSize() {
        return (int) Math.sqrt(this.length());
    }

    /**
     * Get a qbit from the register.
     * @param i the index of the qbit to retrieve
     * @return the qbit identified by its index
     */
    public QBit getQBit(int i) {
        if (i < 0 || i >= this.length() / 2)
            throw new IndexOutOfBoundsException("The index of the qbit is out of bounds.");

        Complex alpha = new Complex(0, 0);
        Complex beta = new Complex(0, 0);
        // for each value of the vector representing the qbit register's state
        for (int j = 0; j < this.length(); j++) {
            // we separate into several sections the values of the vector
            // that are part of alpha and those that are part of beta
            int sectionWidth = (int) (this.width() / Math.pow(2, i + 1));
            // we determine in which section the current value is in
            int sectionIndex = (j - (j % sectionWidth)) / sectionWidth;
            boolean addAlpha = sectionIndex % 2 == 0 ;
            // if the value is in an even section, add it to alpha
            if (addAlpha)
                alpha = alpha.plus(this.getCoordinate(j));
            // if the value is in an odd section, add it to beta
            else beta = beta.plus(this.getCoordinate(j));
        }

        return new QBit(alpha, beta);
    }

    /**
     * Draw values randomly for the qbits of the register according to their associated probabilities.
     * @return an array of booleans, which are equal to true if the drawn bit was 1, and false otherwise
     */
    public boolean[] randomDrawRegister() {
        boolean[] ret = new boolean[this.registerSize()];
        for (int i = 0; i < this.registerSize(); i++)
            ret[i] = this.getQBit(i).randomDraw();
        return ret;
    }

}
