package fr.ensibs.quansim;

public interface Number<T extends Number<T>> {

    /**
     * Add the number with another one.
     * @param n the other number
     * @return a new number representing the result
     */
    T plus(T n);

    /**
     * Subtract the number with another one.
     * @param n the other number
     * @return a new number representing the result
     */
    T minus(T n);

    /**
     * Multiply the number with another one.
     * @param n the other number
     * @return a new number representing the result
     */
    T times(T n);

    /**
     * Multiply the number with a scalar.
     * @param s the scalar
     * @return a new number representing the result
     */
    T times(double s);

    /**
     * Divide the number with another one.
     * @param n the other number
     * @return a new number representing the result
     */
    T divide(T n);

    /**
     * Divide the number with a scalar.
     * @param s the scalar
     * @return a new number representing the result
     */
    T divide(double s);

    /**
     * Elevate the number with a scalar.
     * @param p the power
     * @return a new number representing the result
     */
    T power(int p);

    /**
     * Get the negative of the number.
     * @return a new number representing the result
     */
    T negative();

    /**
     * Get the inverse of the number.
     * @return a new number representing the result
     */
    T inverse();

    /**
     * Get a copy of the number.
     * @return a new number representing the copy
     */
    T copy();

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

}
