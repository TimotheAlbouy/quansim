package fr.ensibs.quansim;

/**
 * A vector of complex numbers, i.e. a 1-dimensional matrix.
 */
public class ComplexVector extends ComplexMatrix {

    /**
     * Constructor.
     * @param length the length of the vector
     */
    public ComplexVector(int length) {
        super(1, length);
    }

    public ComplexVector(Complex[] coordinates) {
        super(createCells(coordinates));
    }

    /**
     * Get the length of the vector.
     * @return the length of the vector
     */
    public int length() {
        return this.height();
    }

    /**
     * Get a coordinate value of the vector.
     * @param i the index of the coordinate
     * @return the coordinate value
     */
    public Complex getCoordinate(int i) {
        return this.getCell(0, i);
    }

    /**
     * Set a coordinate of the vector.
     * @param i the index of the coordinate
     * @param c the new coordinate value
     */
    public void setCoordinate(int i, Complex c) {
        this.setCell(0, i, c);
    }

    /**
     * Create the complex matrix to pass to the parent constructor.
     * @param coordinates the list of complex numbers of the vector
     * @return the equivalent matrix of complex numbers
     */
    private static Complex[][] createCells(Complex[] coordinates) {
        Complex[][] cells = new Complex[coordinates.length][1];
        for (int i = 0; i < coordinates.length; i++)
            cells[i][0] = coordinates[i];
        return cells;
    }

}
