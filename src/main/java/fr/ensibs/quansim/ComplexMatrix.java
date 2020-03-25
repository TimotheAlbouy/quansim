package fr.ensibs.quansim;

/**
 * A matrix of complex numbers.
 */
public class ComplexMatrix {

    /**
     * the 2-dimensional array containing the matrix's values.
     */
    private Complex[][] matrix;

    /**
     * Constructor.
     * @param width the width of the matrix
     * @param height the height of the matrix
     */
    public ComplexMatrix(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("The matrix's dimensions cannot be negative.");

        this.matrix = new Complex[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                this.matrix[x][y] = new Complex(0, 0);
    }

    /**
     * Get the width of the matrix.
     * @return the width of the matrix
     */
    public int width() {
        return this.matrix.length;
    }

    /**
     * Get the height of the matrix.
     * @return the height of the matrix
     */
    public int height() {
        return this.matrix[0].length;
    }

    /**
     * Get the complex number of the cell having the x and y coordinates in the matrix.
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return the complex number contained in the cell
     */
    public Complex getCell(int x, int y) {
        if (x < 0 || x >= this.width())
            throw new IndexOutOfBoundsException("The x coordinate is out of bounds.");

        if (y < 0 || y >= this.height())
            throw new IndexOutOfBoundsException("The y coordinate is out of bounds.");

        return this.matrix[x][y];
    }

    /**
     * Set the complex number of the cell having the x and y coordinates in the matrix.
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param c the complex number to put in the cell
     */
    public void setCell(int x, int y, Complex c) {
        if (c == null)
            throw new NullPointerException("The new value cannot be null.");

        if (x < 0 || x >= this.width())
            throw new IndexOutOfBoundsException("The x coordinate is out of bounds.");

        if (y < 0 || y >= this.height())
            throw new IndexOutOfBoundsException("The y coordinate is out of bounds.");

        this.matrix[x][y] = c;
    }

    /**
     * Add the matrix with another one.
     * @param m the other matrix
     * @return a new matrix representing the result
     */
    public ComplexMatrix plus(ComplexMatrix m) {
        if (m == null)
            throw new NullPointerException("The other matrix cannot be null.");

        if (this.width() != m.width())
            throw new IllegalArgumentException("The matrices' widths must be equal.");

        if (this.height() != m.height())
            throw new IllegalArgumentException("The matrices' heights must be equal.");

        ComplexMatrix ret = new ComplexMatrix(this.width(), this.height());
        for (int y = 0; y < this.height(); y++)
            for (int x = 0; x < this.width(); x++)
                ret.matrix[x][y] = this.matrix[x][y].plus(m.matrix[x][y]);
        return ret;
    }

    /**
     * Subtract the matrix with another one.
     * @param m the other matrix
     * @return a new matrix representing the result
     */
    public ComplexMatrix minus(ComplexMatrix m) {
        if (m == null)
            throw new NullPointerException("The other matrix cannot be null.");

        return this.plus(m.negative());
    }

    /**
     * Multiply the matrix with another one.
     * @param m the other matrix
     * @return a new matrix representing the result
     */
    public ComplexMatrix times(ComplexMatrix m) {
        if (m == null)
            throw new NullPointerException("The other matrix cannot be null.");

        if (this.width() != m.height())
            throw new IllegalArgumentException("This matrix's width must be equal to the other matrix's height.");

        ComplexMatrix ret = new ComplexMatrix(m.width(), this.height());
        for (int y = 0; y < ret.height(); y++) {
            for (int x = 0; x < ret.width(); x++) {
                Complex c = new Complex(0, 0);
                for (int i = 0; i < this.width(); i++)
                    c = c.plus(this.matrix[i][y].times(m.matrix[x][i]));
                ret.matrix[x][y] = c;
            }
        }
        return ret;
    }

    /**
     * Multiply the matrix with a scalar.
     * @param s the scalar
     * @return a new matrix representing the result
     */
    public ComplexMatrix times(double s) {
        ComplexMatrix ret = new ComplexMatrix(this.width(), this.height());
        for (int y = 0; y < this.height(); y++)
            for (int x = 0; x < this.width(); x++)
                ret.matrix[x][y] = ret.matrix[x][y].times(s);
        return ret;
    }

    /**
     * Get the negative of the matrix.
     * @return a new matrix representing the result
     */
    public ComplexMatrix negative() {
        return this.times(-1);
    }

}