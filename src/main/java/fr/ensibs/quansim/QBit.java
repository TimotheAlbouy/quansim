package fr.ensibs.quansim;

/**
 * A qbit, represented by its alpha and beta coordinates in the canonical base.
 */
public class QBit {

    /**
     * the alpha coordinate of the qbit
     */
    private Complex alpha;

    /**
     * the beta coordinate of the qbit.
     */
    private Complex beta;

    /**
     * Constructor.
     * @param alpha the alpha coordinate
     * @param beta the beta coordinate
     */
    public QBit(Complex alpha, Complex beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    /**
     * Draw randomly a value for the qbit according to its associated probabilities.
     * @return true if the drawn bit was 1, false otherwise
     */
    public boolean randomDraw() {
        return Math.random() > Math.pow(alpha.modulus(), 2);
    }

}
