package fr.ensibs.quansim;

/**
 * A complex number, represented by it real and imaginary parts.
 */
public class Complex {

    /**
     * the real part of the complex number
     */
    private double re;

    /**
     * the imaginary part of the complex number
     */
    private double im;

    /**
     * Constructor.
     * @param re the real part of the complex number
     * @param im the imaginary part of the complex number
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Add the complex with another one.
     * @param c the other complex
     * @return a new complex representing the result
     */
    public Complex plus(Complex c) {
        return new Complex(this.re + c.re, this.im + c.im);
    }

    /**
     * Subtract the complex with another one.
     * @param c the other complex
     * @return a new complex representing the result
     */
    public Complex minus(Complex c) {
        return this.plus(c.negative());
    }

    /**
     * Multiply the complex with another one.
     * @param c the other complex
     * @return a new complex representing the result
     */
    public Complex times(Complex c) {
        double real = this.re * c.re - this.im * c.im;
        double imaginary = this.re * c.im + this.im * c.re;
        return new Complex(real, imaginary);
    }

    /**
     * Multiply the complex with a scalar.
     * @param s the scalar
     * @return a new complex representing the result
     */
    public Complex times(double s) {
        double real = this.re * s;
        double imaginary = this.im * s;
        return new Complex(real, imaginary);
    }

    /**
     * Divide the complex with another one.
     * @param c the other complex
     * @return a new complex representing the result
     */
    public Complex divide(Complex c) {
        Complex conjugate = c.conjugate();
        Complex numerator = this.times(conjugate);
        Complex denominator = c.times(conjugate);
        return new Complex(numerator.re / denominator.re, numerator.im / denominator.re);
    }

    /**
     * Divide the complex with a scalar.
     * @param s the scalar
     * @return a new complex representing the result
     */
    public Complex divide(double s) {
        double real = this.re / s;
        double imaginary = this.im / s;
        return new Complex(real, imaginary);
    }

    /**
     * Elevate the complex with a scalar.
     * @param p the power
     * @return a new complex representing the result
     */
    public Complex power(int p) {
        if (p == 0)
            return new Complex(1, 0);

        Complex ret = this;
        if (p < 0) {
            for (int i = 0; i > p; i--)
                ret = ret.times(this);
            ret = ret.inverse();
        } else {
            for (int i = 0; i < p; i++)
                ret = ret.times(this);
        }
        return ret;
    }

    /**
     * Get the real part of the complex.
     * @return the real part of the complex
     */
    public double getRe() {
        return this.re;
    }

    /**
     * Get the imaginary part of the complex.
     * @return the imaginary part of the complex
     */
    public double getIm() {
        return this.im;
    }

    /**
     * Get the modulus of the complex.
     * @return the modulus of the complex
     */
    public double modulus() {
        return Math.sqrt(this.re * this.re + this.im * this.im);
    }

    /**
     * Get the conjugate of the complex.
     * @return a new complex representing the result
     */
    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }

    /**
     * Get the normalization of the complex.
     * @return a new complex representing the result
     */
    public Complex normalize() {
        return this.divide(this.modulus());
    }

    /**
     * Get the negative of the complex.
     * @return a new complex representing the result
     */
    public Complex negative() {
        return this.times(-1);
    }

    /**
     * Get the inverse of the complex.
     * @return a new complex representing the result
     */
    public Complex inverse() {
        double denominator = Math.pow(this.re, 2) + Math.pow(this.im, 2);
        double real = this.re / denominator;
        double imaginary = - this.im / denominator;
        return new Complex(real, imaginary);
    }

    @Override
    public String toString() {
        return this.re + " + " + this.im + "i";
    }

}
