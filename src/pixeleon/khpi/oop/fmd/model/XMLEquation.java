package pixeleon.khpi.oop.fmd.model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.*;

import pixeleon.khpi.oop.fmd.model.xml.EquationData;;

public class XMLEquation extends AbstractEquation {

	@SuppressWarnings("serial")
	public static class FileException extends Exception {
		private String fileName;

		public FileException(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}
	}

	@SuppressWarnings("serial")
	public static class FileReadException extends FileException {
		public FileReadException(String fileName) {
			super(fileName);
		}
	}

	@SuppressWarnings("serial")
	public static class FileWriteException extends FileException {
		public FileWriteException(String fileName) {
			super(fileName);
		}
	}

	private EquationData eqData;

	public XMLEquation() {
		clearEquation();
	}

	public XMLEquation(String fileName) {
		try {
			readFromFile(fileName);
		} catch (FileReadException e) {
			throw new RuntimeException("Error reading: " + fileName);
		}
	}

	public XMLEquation clearEquation() {
		eqData = new EquationData();
		setF(new XMLFFunction(eqData));
		setG(new XMLGFunction(eqData));
		return this;
	}

	public EquationData getData() {
		return eqData;
	}

	public AbstractFFunction getFFunction() {
		return (AbstractFFunction) getF();
	}

	public AbstractGFunction getGFunction() {
		return (AbstractGFunction) getG();
	}

	public XMLEquation readFromFile(String fileName) throws FileReadException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("pixeleon.khpi.oop.fmd.model.xml");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			eqData = (EquationData) unmarshaller.unmarshal(new FileInputStream(fileName));
			setF(new XMLFFunction(eqData));
			setG(new XMLGFunction(eqData));
			return this;
		} catch (FileNotFoundException | JAXBException e) {
			throw new FileReadException(fileName);
		}
	}

	public XMLEquation writeToFile(String fileName) throws FileWriteException {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("pixeleon.khpi.oop.fmd.model.xml");
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(eqData, new FileWriter(fileName));
			return this;
		} catch (JAXBException | IOException e) {
			throw new FileWriteException(fileName);
		}
	}
	
	public XMLEquation saveReport(String fileName, String imageName, double a, double b, double eps) throws FileWriteException {
        try (PrintWriter out = new PrintWriter(
          new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            out.printf("<html>%n");
            out.printf("<head>%n");
            out.printf("<meta http-equiv='Content-Type' content='text/html; " + 
                       "charset=UTF-8'>%n");
            out.printf("</head>%n");
            out.printf("<body>%n");
            out.printf("<h2>Report</h2>%n");
            out.printf("<p>Solving equation f(x) - g(x) -> max with input data:</p>%n");
            out.printf("<h4>Function: <span style='font-family:Times, Serif;'>" + 
                       "<em>f(x)</em></span></h4>%n");
            out.printf("<table border = '1' cellpadding=4 cellspacing=0>%n");
            out.printf("<tr>%n");
            out.printf("<th>degree</th>%n");
            out.printf("<th>coefficient</th>%n");
            out.printf("</tr>%n");
            out.printf("<td>%n");
            for (int i = 0; i < getFFunction().getDegree(); i++) {
                out.printf("<tr>%n");
                out.printf("<td>%d</td>", i);
                out.printf("<td>%8.3f</td>%n", getFFunction().getA(i));
                out.printf("</tr>%n");
            }
            out.printf("</table>%n");
            out.printf("<h4>Function: <span style='font-family:Times, Serif;'>" +
                       "<em>g(x)</em></span></h4>%n");
            out.printf("<table border = '1' cellpadding=4 cellspacing=0>%n");
            out.printf("<tr>%n");
            out.printf("<th>index</th>%n");
            out.printf("<th>x</th>%n");
            out.printf("<th>g(x)</th>%n");
            out.printf("</tr>%n");
            out.printf("<td>%n");
            for (int i = 0; i < getGFunction().pairsCount(); i++) {
                out.printf("<tr>%n");
                out.printf("<td>%d</td>", i);
                out.printf("<td>%8.3f</td>%n", getGFunction().getX(i));
                out.printf("<td>%8.3f</td>%n", getGFunction().getFX(i));
                out.printf("</tr>%n");
            }
            out.printf("</table>%n");
            maxDich(a, b , 0.001);
            if (getXMax() != null) {
            	out.printf ("<p>there was obtained such max value for it (x.max : y(x.max)): %s : %s</p>%n", getXMax(), getYMax());
    		} else {
    			out.printf("<p>the equation was not solved</p>%n");
    		}
            if (imageName != null) {
                out.printf("<img src = \"" + imageName + "\"/>");
            }
            out.printf("</body>%n");
            out.printf("</html>%n");
            return this;
        }
        catch (IOException e) {
            throw new FileWriteException(fileName);
        }
    }
}
