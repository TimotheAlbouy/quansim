package fr.ensibs.quansim;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * A complex number, represented by it real and imaginary parts.
 */
public class Complex implements Number<Complex> {

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

    @Override
    public Complex plus(Complex c) {
        return new Complex(this.re + c.re, this.im + c.im);
    }

    @Override
    public Complex minus(Complex c) {
        return this.plus(c.negative());
    }

    @Override
    public Complex times(Complex c) {
        double real = this.re * c.re - this.im * c.im;
        double imaginary = this.re * c.im + this.im * c.re;
        return new Complex(real, imaginary);
    }

    @Override
    public Complex times(double s) {
        double real = this.re * s;
        double imaginary = this.im * s;
        return new Complex(real, imaginary);
    }

    @Override
    public Complex divide(Complex c) {
        Complex conjugate = c.conjugate();
        Complex numerator = this.times(conjugate);
        Complex denominator = c.times(conjugate);
        return new Complex(numerator.re / denominator.re, numerator.im / denominator.re);
    }

    @Override
    public Complex divide(double s) {
        double real = this.re / s;
        double imaginary = this.im / s;
        return new Complex(real, imaginary);
    }

    @Override
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

    @Override
    public Complex negative() {
        return this.times(-1);
    }

    @Override
    public Complex inverse() {
        double denominator = Math.pow(this.re, 2) + Math.pow(this.im, 2);
        double real = this.re / denominator;
        double imaginary = - this.im / denominator;
        return new Complex(real, imaginary);
    }

    /**
     * Get a copy of the complex number.
     * @return a copy of the complex
     */
    public Complex copy() {
        return new Complex(this.re, this.im);
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Complex)) return false;
        Complex c = (Complex) o;
        double threshold = .000000001;
        boolean reEquals = this.re - threshold < c.re && this.re + threshold > c.re;
        boolean imEquals = this.im - threshold < c.im && this.im + threshold > c.im;
        return reEquals && imEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(re, im);
    }

    @Override
    public String toString() {
        if (this.re == 0 && this.im == 0)
            return "0";

        StringBuilder builder = new StringBuilder();
        DecimalFormat format = new DecimalFormat("#0.##");
        if (this.re != 0) {
            builder.append(format.format(this.re));
            if (this.im != 0)
                builder.append(" + ");
        }
        if (this.im != 0) {
            if (this.im == -1)
                builder.append('-');
            else if (this.im != 1)
                builder.append(format.format(this.im));
            builder.append("i");
        }
        return builder.toString();
    }

}
