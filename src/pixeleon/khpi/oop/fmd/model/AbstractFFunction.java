package pixeleon.khpi.oop.fmd.model;

public abstract class AbstractFFunction implements ExtendedFunction {

	public abstract int getDegree();
	public abstract double getA(int i);
	public abstract void setA(int i, double a);
	public abstract void addA(int i, double a);

	@Override
	public double applyAsDouble(double x) {
		double sum = getA(0);
		for (int i = 1; i <= getDegree(); i++) {
			sum += getA(i) * Math.pow(x, i);
		}
		return sum;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getA(0)+"");
		for(int i = 1; i <= getDegree(); i++) {
			double a = getA(i);
				if (a > 0) {
					sb.append(" +" + a + " ");
				}
				else {
					sb.append(" " + a + " ");
				}
			for(int j = 1; j <= i; j++) {
				sb.append("x");
			}
		}
		return new String(sb);
	}
}
