package pixeleon.khpi.oop.fmd.model;

import pixeleon.khpi.oop.fmd.model.xml.EquationData;
import pixeleon.khpi.oop.fmd.model.xml.EquationData.FFunctionData.FFunctionTerm;;

public class XMLFFunction extends AbstractFFunction {
	private EquationData data;

	public XMLFFunction(EquationData eqData) {
		this.data = eqData;
	}
	
	 private EquationData.FFunctionData fFunctionData() {
	        if (data.getFFunctionData() == null) {
	        	data.setFFunctionData(new EquationData.FFunctionData());
	        }
	        return data.getFFunctionData();
	    }
	
	@Override
	public int getDegree() {
		return fFunctionData().getFFunctionTerm().size() - 1;
	}

	@Override
	public double getA(int i) {
		return fFunctionData().getFFunctionTerm().get(i).getCoeff();
	}

	@Override
	public void setA(int i, double a) {
		fFunctionData().getFFunctionTerm().get(i).setCoeff(a);
	}

	@Override
	public void addA(int i, double a) {
		FFunctionTerm fft = new FFunctionTerm(); 
		fft.setDegree(i);
		fft.setCoeff(a);
		fFunctionData().getFFunctionTerm().add(fft);
	}

}
