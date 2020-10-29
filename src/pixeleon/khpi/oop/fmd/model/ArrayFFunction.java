package pixeleon.khpi.oop.fmd.model;

public class ArrayFFunction extends AbstractFFunction {

	private double[] arr;

	public ArrayFFunction(double... arr) {
		this.arr = arr;
		for (int i = 0; i <= arr.length - 1; i++) {
			this.arr[i] = arr[i];
		}
	}

	@Override
	public int getDegree() {
		return arr.length - 1;
	}

	@Override
	public double getA(int i) {
		return arr[i];
	}

	@Override
	public void setA(int i, double a) {
		arr[i] = a;
	}

	public static void main(String[] args) {
		System.out.println(new ArrayFFunction(-3, 0, 2).test("x", "f", -3, 2, 0.5));
	}

	@Override
	public void addA(int i, double a) {
		throw new UnsupportedOperationException();
	}

}
