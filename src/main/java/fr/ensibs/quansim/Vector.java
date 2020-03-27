package fr.ensibs.quansim;

/**
 * A vector of numbers, i.e. a 1-dimensional matrix.
 */
public class Vector<T extends Number<T>> extends Matrix<T> {

    /**
     * true if the vector is vertical, false if it is horizontal
     */
    private boolean isVertical;

    /**
     * Constructor.
     * @param length the length of the vector
     * @param isVertical true if the vector is vertical, false if it is horizontal
     */
    public Vector(int length, boolean isVertical) {
        super(isVertical ? 1 : length, isVertical ? length : 1);
        this.isVertical = isVertical;
    }

    /**
     * Constructor.
     * @param height the height of the vector
     */
    public Vector(int height) {
        this(height, true);
    }

    /**
     * Get the length of the vector.
     * @return the length of the vector
     */
    public int length() {
        return this.isVertical ? this.height() : this.width();
    }

    /**
     * Get a coordinate of the vector.
     * @param i the index of the coordinate
     * @return the coordinate value
     */
    public T getCoordinate(int i) {
        return this.isVertical ? this.getCell(0, i) : this.getCell(i, 0);
    }

    /**
     * Set a coordinate of the vector.
     * @param i the index of the coordinate
     * @param n the new coordinate value
     */
    public void setCoordinate(int i, T n) {
        if (this.isVertical)
            this.setCell(0, i, n);
        else this.setCell(i, 0, n);
    }

    @Override
    public Vector<T> plus(Matrix<T> m) {
        Matrix<T> r = super.plus(m);
        return this.isVertical ? r.getColumnVector(0) : r.getRowVector(0);
    }

    @Override
    public Vector<T> minus(Matrix<T> m) {
        Matrix<T> r = super.minus(m);
        return this.isVertical ? r.getColumnVector(0) : r.getRowVector(0);
    }

    @Override
    public Vector<T> times(double s) {
        Matrix<T> r = super.times(s);
        return this.isVertical ? r.getColumnVector(0) : r.getRowVector(0);
    }

    @Override
    public Vector<T> negative() {
        Matrix<T> r = super.negative();
        return this.isVertical ? r.getColumnVector(0) : r.getRowVector(0);
    }

    @Override
    public Vector<T> transpose() {
        Matrix<T> r = super.transpose();
        return this.isVertical ? r.getColumnVector(0) : r.getRowVector(0);
    }

}
