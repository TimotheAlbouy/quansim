package fr.ensibs.quansim;

/**
 * A matrix of complex numbers.
 */
public class ComplexMatrix {

    /**
     * the 2-dimensional list containing the matrix's cells.
     */
    private final Complex[][] cells;

    /**
     * Constructor.
     * @param width the width of the matrix
     * @param height the height of the matrix
     */
    public ComplexMatrix(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("The matrix's dimensions cannot be negative.");

        this.cells = new Complex[height][width];
    }

    /**
     * Constructor.
     * @param cells the 2-dimensional array containing the matrix values
     */
    public ComplexMatrix(Complex[][] cells) {
        if (cells == null)
            throw new NullPointerException("The initialization matrix cannot be null.");
        if (cells.length == 0)
            throw new IllegalArgumentException("The initialization matrix cannot be empty.");

        this.cells = cells;
    }

    /**
     * Get the width of the matrix.
     * @return the width of the matrix
     */
    public int width() {
        return this.cells[0].length;
    }

    /**
     * Get the height of the matrix.
     * @return the height of the matrix
     */
    public int height() {
        return this.cells.length;
    }

    /**
     * Get the complex number at the (x, y) coordinates.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the complex number of the cell
     */
    public Complex getCell(int x, int y) {
        if (x < 0 || x >= this.width())
            throw new IndexOutOfBoundsException("The x coordinate is out of bounds.");

        if (y < 0 || y >= this.height())
            throw new IndexOutOfBoundsException("The y coordinate is out of bounds.");

        return this.cells[y][x];
    }

    /**
     * Set the number of the cell having the x and y coordinates in the matrix.
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param c the complex number to put in the cell
     */
    public void setCell(int x, int y, Complex c) {
        if (x < 0 || x >= this.width())
            throw new IndexOutOfBoundsException("The x coordinate is out of bounds.");

        if (y < 0 || y >= this.height())
            throw new IndexOutOfBoundsException("The y coordinate is out of bounds.");

        this.cells[y][x] = c;
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
        for (int y = 0; y < this.height(); y++) {
            for (int x = 0; x < this.width(); x++) {
                Complex c = this.getCell(x, y).plus(m.getCell(x, y));
                ret.setCell(x, y, c);
            }
        }
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
                Complex c = this.getCell(0, y).times(m.getCell(x, 0));
                for (int i = 1; i < this.width(); i++)
                    c = c.plus(this.getCell(i, y).times(m.getCell(x, i)));
                ret.setCell(x, y, c);
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
        for (int x = 0; x < this.width(); x++)
            for (int y = 0; y < this.height(); y++)
                ret.setCell(x, y, this.getCell(x, y).times(s));
        return ret;
    }

    /**
     * Divide the matrix with a scalar.
     * @param s the scalar
     * @return a new matrix representing the result
     */
    public ComplexMatrix divide(double s) {
        return this.times(1 / s);
    }

    /**
     * Get the negative of the matrix.
     * @return a new matrix representing the result
     */
    public ComplexMatrix negative() {
        return this.times(-1);
    }

    /**
     * Get the transpose of the matrix.
     * @return a new matrix representing the result
     */
    public ComplexMatrix transpose() {
        ComplexMatrix ret = new ComplexMatrix(this.height(), this.width());
        for (int x = 0; x < this.width(); x++)
            for (int y = 0; y < this.height(); y++)
                ret.setCell(y, x, this.getCell(x, y));
        return ret;
    }

    /**
     * Get a column vector.
     * @param c the column to retrieve
     * @return the column vector
     */
    public ComplexVector getColumnVector(int c) {
        if (c < 0 || c >= this.width())
            throw new IndexOutOfBoundsException("The column number is out of bounds.");

        ComplexVector ret = new ComplexVector(this.height());
        for (int i = 0; i < this.height(); i++)
            ret.setCoordinate(i, this.getCell(c, i));
        return ret;
    }

    /**
     * Get a row vector.
     * @param r the row to retrieve
     * @return the row vector
     */
    public ComplexVector getRowVector(int r) {
        if (r < 0 || r >= this.height())
            throw new IndexOutOfBoundsException("The row number is out of bounds.");

        ComplexVector ret = new ComplexVector(this.width());
        for (int i = 0; i < this.width(); i++)
            ret.setCoordinate(i, this.getCell(i, r));
        return ret;
    }

    /**
     * Tell if the matrix is square.
     * @return true if the matrix is square, false otherwise
     */
    public boolean isSquare() {
        return this.width() == this.height();
    }

    /**
     * Create a deep copy of the matrix.
     * @return a new matrix representing the copy
     */
    public ComplexMatrix copy() {
        ComplexMatrix ret = new ComplexMatrix(this.width(), this.height());
        for (int x = 0; x < this.width(); x++)
            for (int y = 0; y < this.width(); y++)
                ret.setCell(x, y, this.getCell(x, y));
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexMatrix)) return false;
        ComplexMatrix m = (ComplexMatrix) o;
        if (this.width() != m.width() || this.height() != m.height())
            return false;
        for (int x = 0; x < this.width(); x++)
            for (int y = 0; y < this.height(); y++)
                if (!this.getCell(x, y).equals(m.getCell(x, y)))
                    return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < this.height(); y++) {
            if (y == 0)
                builder.append("┌\t");
            else if (y == this.height() - 1)
                builder.append("└\t");
            else builder.append("│\t");
            builder.append(this.getCell(0, y));
            for (int x = 1; x < this.width(); x++)
                builder.append("\t").append(this.getCell(x, y));
            if (y == 0)
                builder.append("\t┐\n");
            else if (y == this.height() - 1)
                builder.append("\t┘\n");
            else builder.append("\t│\n");
        }
        return builder.toString();
    }

}