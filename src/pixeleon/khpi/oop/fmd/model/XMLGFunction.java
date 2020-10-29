package pixeleon.khpi.oop.fmd.model;

import java.util.Collections;
import java.util.Comparator;

import pixeleon.khpi.oop.fmd.model.xml.EquationData;
import pixeleon.khpi.oop.fmd.model.xml.EquationData.GFunctionData.GFunctionPair;

public class XMLGFunction extends AbstractGFunction {
	private EquationData data;

	public XMLGFunction(EquationData eqData) {
		this.data = eqData;
	}
	
	private EquationData.GFunctionData gFunctionData(){
		if(data.getGFunctionData() == null) {
			data.setGFunctionData(new EquationData.GFunctionData());
		}
		return data.getGFunctionData();
		
	}
	
	@Override
	public int pairsCount() {
		return gFunctionData().getGFunctionPair().size();
	}

	@Override
	public double getFX(int i) {
		return gFunctionData().getGFunctionPair().get(i).getYofX();
	}

	@Override
	public double getFX(double x) {
		for (int i = 0; i <= gFunctionData().getGFunctionPair().size(); i++) {
			if (gFunctionData().getGFunctionPair().get(i).getX() == x)
				return gFunctionData().getGFunctionPair().get(i).getYofX();
		}
		throw new RuntimeException("Wrong data!");
	}

	@Override
	public double getX(int i) {
		return gFunctionData().getGFunctionPair().get(i).getX();
	}

	@Override
	public void setFX(int i, double fx) {
		gFunctionData().getGFunctionPair().get(i).setYofX(fx);
	}

	@Override
	public void setFX(double x, double fx) {
		for (int i = 0; i <= gFunctionData().getGFunctionPair().size(); i++) {
			if (gFunctionData().getGFunctionPair().get(i).getX() == x)
				gFunctionData().getGFunctionPair().get(i).setYofX(fx);
		}
	}

	@Override
	public void setX(int i, double x) {
		gFunctionData().getGFunctionPair().get(i).setX(x);
	}
	
	@Override
	public void setPair(int i, double x, double fx) {
		gFunctionData().getGFunctionPair().get(i).setX(x);
		gFunctionData().getGFunctionPair().get(i).setYofX(fx);
	}

	@Override
	public void addPair(double x, double fx) {
		GFunctionPair pair = new GFunctionPair();
		pair.setX(x);
		pair.setYofX(fx);
		gFunctionData().getGFunctionPair().add(pair);
	}

	@Override
	public void removePair(int i, double x, double fx) {
		gFunctionData().getGFunctionPair().remove(i);
	}

	@Override
	public double getMaxX() {
		return Collections.max(gFunctionData().getGFunctionPair(), new CompareByX()).getX();
	}

	@Override
	public double getMaxYofX() {
		return Collections.max(gFunctionData().getGFunctionPair(), new CompareByYofX()).getYofX();
	}
	
	@Override
	public double getMinX() {
		return Collections.min(gFunctionData().getGFunctionPair(), new CompareByX()).getX();
	}

	@Override
	public double getMinYofX() {
		return Collections.min(gFunctionData().getGFunctionPair(), new CompareByYofX()).getYofX();
	}
	
	class CompareByX implements Comparator<GFunctionPair>
	{
	    @Override
	    public int compare(GFunctionPair gfp1, GFunctionPair gfp2) {
	        return Double.compare(gfp1.getX(), gfp2.getX());
	    }
	}
	
	class CompareByYofX implements Comparator<GFunctionPair>
	{
	    @Override
	    public int compare(GFunctionPair gfp1, GFunctionPair gfp2) {
	        return Double.compare(gfp1.getYofX(), gfp2.getYofX());
	    }
	}
}
