package pixeleon.khpi.oop.fmd.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayGFunction extends AbstractGFunction {

	private double[] xarr;
	private double[] fxarr;

	public ArrayGFunction(double[] xarr, double[] fxarr) {
		if (xarr.length != fxarr.length) {
			throw new RuntimeException("Wrong data!");
		}
		this.xarr = xarr;
		this.fxarr = fxarr;
	}

	@Override
	public int pairsCount() {
		return xarr.length;
	}

	@Override
	public double getFX(int i) {
		return fxarr[i];
	}

	@Override
	public double getFX(double x) {
		for (int i = 0; i < xarr.length; i++) {
			if (xarr[i] == x) {
				return fxarr[i];
			}
		}
		throw new RuntimeException("Wrong data!");
	}

	@Override
	public double getX(int i) {
		return xarr[i];
	}

	@Override
	public void setFX(int i, double fx) {
		fxarr[i] = fx;
	}

	@Override
	public void setFX(double x, double fx) {
		for (int i = 0; i < xarr.length; i++) {
			if (xarr[i] == x) {
				fxarr[i] = fx;
			}
		}
	}

	@Override
	public void setX(int i, double x) {
		xarr[i] = x;
	}

	@Override
	public void setPair(int i, double x, double fx) {
		xarr[i] = x;
		fxarr[i] = fx;
	}
	
	@Override
	public void addPair(double x, double fx) {
		throw new UnsupportedOperationException();	
	}
	
	@Override
	public void removePair(int i, double x, double fx) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public double getMaxX() {
		List<Double> l = new ArrayList(Arrays.asList(xarr));
		return Collections.max(l);
	}

	@Override
	public double getMaxYofX() {
		List<Double> l = new ArrayList(Arrays.asList(fxarr));
		return Collections.max(l);
	}
	
	@Override
	public double getMinX() {
		List<Double> l = new ArrayList(Arrays.asList(xarr));
		return Collections.min(l);
	}

	@Override
	public double getMinYofX() {
		List<Double> l = new ArrayList(Arrays.asList(fxarr));
		return Collections.min(l);
	}
	
	public static void main(String[] args) {
		System.out.println(new ArrayGFunction(new double[] { -6, -2, 0, 2, 6 }, new double[] { 5, 5, 5, 5, 5 })
				.test("x", "f", -3, 3, 0.5));
	}

	
}
