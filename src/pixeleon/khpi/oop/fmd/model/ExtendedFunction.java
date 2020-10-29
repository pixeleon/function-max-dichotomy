package pixeleon.khpi.oop.fmd.model;

import java.util.function.DoubleUnaryOperator;

public interface ExtendedFunction extends DoubleUnaryOperator {
	default double y(double x) {
		return applyAsDouble(x);
	}

	default String test(String argName, String funcName, double from, double to, double step) {
		StringBuilder sb = new StringBuilder("");
		for (double x = from; x <= to; x += step) {
			sb.append(String.format(argName + " = %9.4f     " + funcName + "(" + argName + ") = %9.4f%n", x, y(x)));
		}
		return sb.toString();
	}
}
