package fr.ensibs.quansim;

import static fr.ensibs.quansim.QuantumGates.*;

/**
 * A qbit, represented by its alpha and beta coordinates in the canonical base.
 */
public class QBit {

    /**
     * the state of the qbit, represented by a complex vector
     */
    private Vector<Complex> state = new Vector<>(2);

    /**
     * Constructor.
     * @param alpha the alpha coordinate
     * @param beta the beta coordinate
     */
    public QBit(Complex alpha, Complex beta) {
        double p = Math.pow(alpha.modulus(), 2) + Math.pow(beta.modulus(), 2);
        if (p <= 0.999 || p >= 1.001)
            throw new IllegalArgumentException("The addition of the square moduli of alpha and beta must be 1.");

        this.state.setCoordinate(0, alpha);
        this.state.setCoordinate(1, beta);
    }

    /**
     * Get the alpha coordinate of the qbit.
     * @return the alpha coordinate
     */
    public Complex alpha() {
        return this.state.getCoordinate(0);
    }

    /**
     * Get the beta coordinate of the qbit.
     * @return the beta coordinate
     */
    public Complex beta() {
        return this.state.getCoordinate(1);
    }

    /**
     * Get the probability to draw a |0>.
     * @return the given probability
     */
    public double p0() {
        return Math.pow(this.alpha().modulus(), 2);
    }

    /**
     * Get the probability to draw a |1>.
     * @return the given probability
     */
    public double p1() {
        return Math.pow(this.beta().modulus(), 2);
    }

    /**
     * Draw a value randomly for the qbit following the probabilities and set the state according to the result.
     * @return true if the drawn bit was 1, false otherwise
     */
    public boolean randomDraw() {
        boolean bitIs1 = Math.random() > Math.pow(this.alpha().modulus(), 2);
        if (bitIs1) {
            this.state.setCoordinate(0, new Complex(0, 0));
            this.state.setCoordinate(1, new Complex(Math.sqrt(.5), Math.sqrt(.5)));
        } else {
            this.state.setCoordinate(0, new Complex(Math.sqrt(.5), Math.sqrt(.5)));
            this.state.setCoordinate(1, new Complex(0, 0));
        }
        return bitIs1;
    }

    /**
     * Get the state vector of the qbit.
     * @return the qbit state
     */
    public Vector<Complex> getState() {
        return this.state;
    }

    /**
     * Set the state vector of the qbit.
     * @param state the new qbit state
     */
    public void setState(Vector<Complex> state) {
        this.state = state;
    }

    /**
     * Apply the X quantum gate on the qbit.
     * @return the qbit after the quantum gate
     */
    public QBit X() {
        this.state = X.times(this.state).getColumnVector(0);
        return this;
    }

    /**
     * Apply the Y quantum gate on the qbit.
     * @return the qbit after the quantum gate
     */
    public QBit Y() {
        this.state = Y.times(this.state).getColumnVector(0);
        return this;
    }

    /**
     * Apply the Z quantum gate on the qbit.
     * @return the qbit after the quantum gate
     */
    public QBit Z() {
        this.state = Z.times(this.state).getColumnVector(0);
        return this;
    }

    /**
     * Apply the H quantum gate on the qbit.
     * @return the qbit after the quantum gate
     */
    public QBit H() {
        this.state = H.times(this.state).getColumnVector(0);
        return this;
    }

    /**
     * Create a copy of the qbit, even though it is physically impossible.
     * @return a copy of the qbit
     */
    public QBit copy() {
        return new QBit(this.alpha().copy(), this.beta().copy());
    }

    /**
     * Compare the qbit with another one, even though it is physically impossible.
     * @param o the object to compare with
     * @return true if the 2 qbits are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof QBit)) return false;
        QBit qBit = (QBit) o;
        return this.alpha().equals(qBit.alpha()) && this.beta().equals(qBit.beta());
    }

    @Override
    public String toString() {
        return state.toString();
    }

}
