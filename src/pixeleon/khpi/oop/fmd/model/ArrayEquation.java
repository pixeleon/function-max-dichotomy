package pixeleon.khpi.oop.fmd.model;

public class ArrayEquation extends AbstractEquation {

	public ArrayEquation(ArrayFFunction aff, ArrayGFunction agf) {
		setF(aff);
		setG(agf);
	}

	public static void main(String[] args) {
		ArrayFFunction arrF = new ArrayFFunction(-2, 0, 2);
		ArrayGFunction arrG = new ArrayGFunction(new double[] { -6, -2, 0, 2, 6 },
				new double[] { -216, -8, 0, 8, 216 });
		ArrayEquation arreq = new ArrayEquation(arrF, arrG);
		arreq.clearRoots();
		System.out.println(arreq.maxDich(-2, 2, 0.001));
		System.out.println(arreq.setFG(x -> 4 - Math.exp(x), x -> 2 * x * x).maxDich(-2, 2, 0.001));

	}

}
