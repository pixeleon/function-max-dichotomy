package pixeleon.khpi.oop.fmd.model;

import pixeleon.khpi.oop.fmd.model.XMLEquation.FileException;

public class ConsoleApp {

	public static void main(String[] args) throws FileException {
		XMLEquation eq = new XMLEquation("TestFunction1.xml");
		System.out.println(eq);
		System.out.println(eq.maxDich(-5, 5, 0.001));
		eq.saveReport("testRep.xml", null, -5, 5, 0.001);
		System.out.println(eq.readFromFile("Parabola.xml").maxDich(-5, 5, 0.001));
		System.out.println(eq.readFromFile("Coinciding.xml").maxDich(-5, 5, 0.001));
		System.out.println(eq.readFromFile("Parallel.xml").maxDich(-5, 5, 0.001));

	}

}
