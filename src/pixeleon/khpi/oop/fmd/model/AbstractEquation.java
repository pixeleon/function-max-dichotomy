package pixeleon.khpi.oop.fmd.model;

import java.text.DecimalFormat;

import pixeleon.khpi.oop.fmd.model.ExtendedFunction;

public class AbstractEquation {
	
	DecimalFormat df = new DecimalFormat("#,####"); 
	
	private ExtendedFunction f;
	private ExtendedFunction g;
	private Double xMax = null;

	public AbstractEquation clearRoots() {
		xMax = null;
		return this;
	}

	public ExtendedFunction getF() {
		return f;
	}

	public AbstractEquation setF(ExtendedFunction f) {
		this.f = f;
		return clearRoots();
	}

	public ExtendedFunction getG() {
		return g;
	}

	public AbstractEquation setG(ExtendedFunction g) {
		this.g = g;
		return clearRoots();
	}

	public AbstractEquation setFG(ExtendedFunction f, ExtendedFunction g) {
		this.f = f;
		this.g = g;
		return clearRoots();
	}

	public AbstractEquation maxDich(double a, double b, double eps) {
		// wrong interval
		if (b < a) {
			xMax = null;
			return this;
		}

		// zero interval
		if (a == b) {
			xMax = f.applyAsDouble(a) - g.applyAsDouble(a);
			return this;
		}

		// checking if functions are coinciding/parallel
		boolean feqg = true;
		double inity = (f.applyAsDouble(a) - g.applyAsDouble(a));
		for (double ix = a + 0.1; ix <= b; ix += 0.1) {
			//System.out.println(df.format((f.applyAsDouble(ix) - g.applyAsDouble(ix))));
			double iy = Double.valueOf(df.format((f.applyAsDouble(ix) - g.applyAsDouble(ix))));
			if (iy != inity) {
				feqg = false;
				break;
			}
		}

		// functions are coinciding/parallel -> finish
		if (feqg) {
			xMax = null;
			return this;
		}

		// dichotomy method for finding MAX in [a, b]
		xMax = (f.applyAsDouble(a) - g.applyAsDouble(a)) > (f.applyAsDouble(b) - g.applyAsDouble(b)) ? a : b;
		do {
			double x = (a + b) / 2;
			double f1 = f.applyAsDouble(x - eps) - g.applyAsDouble(x - eps);
			double f2 = f.applyAsDouble(x + eps) - g.applyAsDouble(x + eps);
			if (f1 < f2)
				a = x;
			else
				b = x;
		} while (Math.abs(b - a) > eps);
		if (((a + b) / 2) > xMax ) {
			xMax = (a + b) / 2;
		}
		return this;
	}

	public Double getXMax() {
		return xMax == null ? null : xMax;
	}

	public Double getYMax() {
		return xMax == null ? null : f.applyAsDouble(xMax) - g.applyAsDouble(xMax);
	}

	@Override
	public String toString() {
		if (getXMax() != null) {
			return "x.max = " + getXMax().floatValue() + ", \ny(x.max) = " + getYMax().floatValue();
		} else {
			return "Equation was not solved...";
		}

	}

	public static void main(String[] args) {
		AbstractEquation abstreq = new AbstractEquation();
		System.out.println(abstreq);
		System.out.println(abstreq.setFG(x -> 3, x -> Math.pow(Math.E, x)-2*x*x).maxDich(-1, 5, 0.001));
	}

}
