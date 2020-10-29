package pixeleon.khpi.oop.fmd.model;

public abstract class AbstractGFunction implements ExtendedFunction {

	public abstract int pairsCount();
	public abstract double getFX(int i);
	public abstract double getFX(double x);
	public abstract double getX(int i);
	public abstract void setFX(int i, double fx);
	public abstract void setFX(double x, double fx);
	public abstract void setX(int i, double x);
	public abstract double getMaxX();
	public abstract double getMaxYofX();
	public abstract double getMinX();
	public abstract double getMinYofX();
	public abstract void setPair(int i, double x, double fx);
	public abstract void addPair(double x, double fx);
	public abstract void removePair(int i, double x, double fx);

	@Override
	public double applyAsDouble(double x) {
		for (int i = 0; i < pairsCount(); i++) {
			if (getX(i) == x) {
				return getFX(i);
			}
		}
		double sum = 0;
		for (int i = 0; i < pairsCount(); i++) {
			double psi1 = 1;
			double psi2 = 1;
			double xi = getX(i);
			for (int j = 0; j < pairsCount(); j++) {
				if (j != i) {
					psi1 *= (x - getX(j));
				}
			}
			for (int k = 0; k < pairsCount(); k++) {
				if (k != i) {
					psi2 *= xi - getX(k);
				}
			}
			sum += getFX(xi) * psi1 / psi2;
		}
		return sum;
	}
}
