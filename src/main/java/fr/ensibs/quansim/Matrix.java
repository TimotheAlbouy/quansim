package fr.ensibs.quansim;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A matrix of numbers.
 */
public class Matrix<T extends Number<T>> {

    /**
     * the 2-dimensional list containing the matrix's cells.
     */
    private final List<List<T>> cells = new ArrayList<>();

    /**
     * Constructor.
     * @param width the width of the matrix
     * @param height the height of the matrix
     */
    public Matrix(int width, int height) {
        if (width <= 0 || height <= 0)
            throw new IllegalArgumentException("The matrix's dimensions cannot be negative.");

        for (int x = 0; x < width; x++) {
            this.cells.add(new ArrayList<>());
            for (int y = 0; y < height; y++)
                this.cells.get(x).add(null);
        }
    }

    /**
     * Get the width of the matrix.
     * @return the width of the matrix
     */
    public int width() {
        return this.cells.size();
    }

    /**
     * Get the height of the matrix.
     * @return the height of the matrix
     */
    public int height() {
        return this.cells.get(0).size();
    }

    /**
     * Get the number of the cell having the x and y coordinates in the matrix.
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @return the number contained in the cell
     */
    public T getCell(int x, int y) {
        if (x < 0 || x >= this.width())
            throw new IndexOutOfBoundsException("The x coordinate is out of bounds.");

        if (y < 0 || y >= this.height())
            throw new IndexOutOfBoundsException("The y coordinate is out of bounds.");

        return this.cells.get(x).get(y);
    }

    /**
     * Set the number of the cell having the x and y coordinates in the matrix.
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param n the number to put in the cell
     */
    public void setCell(int x, int y, T n) {
        if (x < 0 || x >= this.width())
            throw new IndexOutOfBoundsException("The x coordinate is out of bounds.");

        if (y < 0 || y >= this.height())
            throw new IndexOutOfBoundsException("The y coordinate is out of bounds.");

        this.cells.get(x).set(y, n);
    }

    /**
     * Add the matrix with another one.
     * @param m the other matrix
     * @return a new matrix representing the result
     */
    public Matrix<T> plus(Matrix<T> m) {
        if (m == null)
            throw new NullPointerException("The other matrix cannot be null.");

        if (this.width() != m.width())
            throw new IllegalArgumentException("The matrices' widths must be equal.");

        if (this.height() != m.height())
            throw new IllegalArgumentException("The matrices' heights must be equal.");

        Matrix<T> ret = new Matrix<>(this.width(), this.height());
        for (int y = 0; y < this.height(); y++) {
            for (int x = 0; x < this.width(); x++) {
                T v = this.getCell(x, y).plus(m.getCell(x, y));
                ret.setCell(x, y, v);
            }
        }
        return ret;
    }

    /**
     * Subtract the matrix with another one.
     * @param m the other matrix
     * @return a new matrix representing the result
     */
    public Matrix<T> minus(Matrix<T> m) {
        if (m == null)
            throw new NullPointerException("The other matrix cannot be null.");

        return this.plus(m.negative());
    }

    /**
     * Multiply the matrix with another one.
     * @param m the other matrix
     * @return a new matrix representing the result
     */
    public Matrix<T> times(Matrix<T> m) {
        if (m == null)
            throw new NullPointerException("The other matrix cannot be null.");

        if (this.width() != m.height())
            throw new IllegalArgumentException("This matrix's width must be equal to the other matrix's height.");

        Matrix<T> ret = new Matrix<>(m.width(), this.height());
        for (int y = 0; y < ret.height(); y++) {
            for (int x = 0; x < ret.width(); x++) {
                T n = this.getCell(0, y).times(m.getCell(x, 0));
                for (int i = 1; i < this.width(); i++)
                    n = n.plus(this.getCell(i, y).times(m.getCell(x, i)));
                ret.setCell(x, y, n);
            }
        }
        return ret;
    }

    /**
     * Multiply the matrix with a scalar.
     * @param s the scalar
     * @return a new matrix representing the result
     */
    public Matrix<T> times(double s) {
        Matrix<T> ret = new Matrix<>(this.width(), this.height());
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
    public Matrix<T> divide(double s) {
        return this.times(1 / s);
    }

    /**
     * Get the negative of the matrix.
     * @return a new matrix representing the result
     */
    public Matrix<T> negative() {
        return this.times(-1);
    }

    /**
     * Get the transpose of the matrix.
     * @return a new matrix representing the result
     */
    public Matrix<T> transpose() {
        Matrix<T> ret = new Matrix<>(this.height(), this.width());
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
    public Vector<T> getColumnVector(int c) {
        if (c < 0 || c >= this.width())
            throw new IndexOutOfBoundsException("The column number is out of bounds.");

        Vector<T> ret = new Vector<>(this.height(), true);
        for (int i = 0; i < this.height(); i++)
            ret.setCoordinate(i, this.getCell(c, i));
        return ret;
    }

    /**
     * Get a row vector.
     * @param r the row to retrieve
     * @return the row vector
     */
    public Vector<T> getRowVector(int r) {
        if (r < 0 || r >= this.height())
            throw new IndexOutOfBoundsException("The row number is out of bounds.");

        Vector<T> ret = new Vector<>(this.width(), false);
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
     * Get a copy of the matrix.
     * @return a new matrix representing the copy
     */
    public Matrix<T> copy() {
        Matrix<T> ret = new Matrix<>(this.width(), this.height());
        for (int x = 0; x < this.width(); x++)
            for (int y = 0; y < this.width(); y++)
                ret.setCell(x, y, this.getCell(x, y));
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix)) return false;
        Matrix<?> m = (Matrix<?>) o;
        if (this.width() != m.width() || this.height() != m.height())
            return false;
        for (int x = 0; x < this.width(); x++)
            for (int y = 0; y < this.height(); y++)
                if (!this.getCell(x, y).equals(m.getCell(x, y)))
                    return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
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