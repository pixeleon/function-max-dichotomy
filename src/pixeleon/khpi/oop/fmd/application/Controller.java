package pixeleon.khpi.oop.fmd.application;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pixeleon.khpi.oop.fmd.model.XMLEquation;
import pixeleon.khpi.oop.fmd.model.XMLEquation.FileReadException;
import pixeleon.khpi.oop.fmd.model.XMLEquation.FileWriteException;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Controller implements Initializable {
	
	private XMLEquation equation = new XMLEquation();
	List<TextField> xTextFields;
	List<TextField> yofxTextFields;
	int number = 0;
	
	public static void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.show();
    }
	
	public static void showError(String message) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(message);
	        alert.showAndWait();
	}
	
	public static void showWindow(String title, String txt) {
		Stage stage = new Stage();
		try {
			String	st = new String(Files.readAllBytes(Paths.get(txt))); 
			Text text = new Text(0, 20, st);
			text.setFont(new Font(14));
			Scene scene = new Scene(new Group(text));
			stage.setTitle(title);
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e) {
			showError("Help files are absent!");
			//e.printStackTrace();
		}
	}
	 
	public static FileChooser getFileChooser(String title) {
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setInitialDirectory(new File("."));
	        fileChooser.getExtensionFilters().add(
	                new FileChooser.ExtensionFilter("XML-files (*.xml)", "*.xml"));
	        fileChooser.getExtensionFilters().add(
	                new FileChooser.ExtensionFilter("All files (*.*)", "*.*"));
	        fileChooser.setTitle(title);
	        return fileChooser;
	    }
	 
	@FXML TextField textFieldFFunction;
	@FXML Button buttonSolve;
	@FXML Button buttonClear;
	@FXML Button buttonReport;
	@FXML LineChart <Number,Number>lineChartMain;
	@FXML TextArea textFieldMax;
	@FXML TextField textFieldYofX1;
	@FXML TextField textFieldX4;
	@FXML TextField textFieldX2;
	@FXML TextField textFieldYofX2;
	@FXML TextField textFieldX3;
	@FXML TextField textFieldX5;
	@FXML TextField textFieldYofX3;
	@FXML TextField textFieldYofX4;
	@FXML TextField textFieldX1;
	@FXML TextField textFieldYofX5;
	@FXML BorderPane borderPaneMain;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		xTextFields = new ArrayList<>(Arrays.asList(textFieldX1, textFieldX2, textFieldX3, textFieldX4, textFieldX5));
		yofxTextFields = new ArrayList<>(Arrays.asList(textFieldYofX1, textFieldYofX2, textFieldYofX3, textFieldYofX4, textFieldYofX5));
		buttonSolve.setMaxWidth(Double.MAX_VALUE);
		buttonReport.setMaxWidth(Double.MAX_VALUE);
		
	}
	
	@FXML public void doClear() {
		  equation = new XMLEquation();
	      textFieldFFunction.setText("");
	      clearGTbale();
	      textFieldMax.setText("");
	      lineChartMain.getData().clear();
	      buttonReport.setDisable(true);
	}
	
	@FXML public void doClearFX(ActionEvent event) {
		  equation.clearEquation();
	      textFieldFFunction.setText("");
	      textFieldMax.setText("");
	      lineChartMain.getData().clear();
	}
	
	@FXML public void doClearGX(ActionEvent event) {
		  equation.clearEquation();
		  clearGTbale();
	      textFieldMax.setText("");
	      lineChartMain.getData().clear();
	}
	
	@FXML
    private void doOpen(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Open XML-file");
        File file;
        if ((file = fileChooser.showOpenDialog(null)) != null) {
            try {
                equation.readFromFile(file.getCanonicalPath());
                textFieldFFunction.setText(equation.getFFunction().toString());
                if (equation.getGFunction().pairsCount() > 5) {
    				showMessage("Too many arguments for g(x). \nLast odd arguments were skipped.");
    			}
                clearGTbale();
                for(int i = 0; i < equation.getGFunction().pairsCount() & i < 5; i++) {
                	xTextFields.get(i).setText(equation.getGFunction().getX(i)+"");
                	yofxTextFields.get(i).setText(equation.getGFunction().getFX(i)+"");
                }
                textFieldMax.setText("");
                lineChartMain.getData().clear();
                buttonReport.setDisable(true);
            }
            catch (IOException e) {
            	//e.printStackTrace();
            	showError("File not found");
            }
            catch (FileReadException e) {
            	showError("Error reading from file");
            	//e.printStackTrace();
			}
        }
    }
	
	@FXML
    private void doSave(ActionEvent event) {
        FileChooser fileChooser = getFileChooser("Save to XML-file");
        File file;
        if ((file = fileChooser.showSaveDialog(null)) != null) {
            try {
                	updateSourceData();
                	equation.writeToFile(file.getCanonicalPath());
                	showMessage("Succefully saved");
            }
            catch (Exception e) {
            	//e.printStackTrace();
            	showError("Error writing to file");
            }
        }
    }
	
	@FXML
    private void doExit(ActionEvent event) {
        Platform.exit();
    }
	
    @FXML
    private void doAbout(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About program");
        alert.setHeaderText("Function Max Dichotomy, v. 1.3 (12.2019)");
        alert.setContentText("Semester Project - OOP - Year 2 - Semester 1\nMade by Vadym Sheveliev, group KH-218г.е");
        alert.showAndWait();
    }
    
    @FXML public void doInputHelp(ActionEvent event) {
    	showWindow("How to input data", "inputhelp.txt");
    }

	@FXML public void doSolveHelp(ActionEvent event) {
		showWindow("How to solve equation", "solvehelp.txt");
	}

	@FXML public void doSaveHelp(ActionEvent event) {
		showWindow("How to save data", "savehelp.txt");
	}
    
	@FXML public void doReport(ActionEvent event) {
		try {
			WritableImage image = lineChartMain.snapshot(new SnapshotParameters(), null);
            File path = new File("Report.png");
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", path);
			equation.saveReport("Report.html", path.getName(), equation.getGFunction().getMinX(),
					equation.getGFunction().getMaxX(), 0.001);
		} catch (FileWriteException e) {
			showError("Error saving report");
			//e.printStackTrace();
		} catch (IOException e) {
			showError("File not found");
			//e.printStackTrace();
		}
	}
	
	@FXML 
	public void doSolve(ActionEvent event) {
		try {
			updateSourceData();
			if (equation.getFFunction().getDegree() < 0) {
				throw new Exception("No terms for f(x) or input is invalid");
			}
			if (equation.getGFunction().pairsCount() < 2) {
				throw new Exception("Not enough arguments for g(x). Input at least two points.");
			}
			double a = equation.getGFunction().getMinX();
			double b = equation.getGFunction().getMaxX();
			equation.maxDich(a, b, 0.001);
			textFieldMax.setText(equation.toString());
			Double xMax = null;
			Double yMax = null;
			if (equation.getXMax() != null) {
				xMax = equation.getXMax();
				yMax = equation.getYMax();
			}
			updateGraph(a, b, xMax, yMax);
			buttonReport.setDisable(false);
		} catch (NumberFormatException e) {
			//e.printStackTrace();
			showError("Invalid input data");
		} catch (Exception e) {
			showError(e.getMessage());
			//e.printStackTrace();
		}
	}
	
	@FXML public void doDraw(ActionEvent event) {
		updateSourceData();
		try {
			double a = equation.getGFunction().getMinX();
			double b = equation.getGFunction().getMaxX();
			updateGraph(a, b, null, null);
		}
		catch(NoSuchElementException e) {
			showError("Invalid input data");
		}
	}
	
	 public  void clearGTbale() {
		   for(TextField tf : xTextFields) {
	        	tf.setText("");
	        }
	        for(TextField tf : yofxTextFields) {
	        	tf.setText("");
	        }
	 }
	
	@SuppressWarnings("resource")
	private void updateSourceData() {
		equation.clearEquation();
		System.out.println(textFieldFFunction.getText());
		Scanner fFunc = new Scanner(textFieldFFunction.getText()).useLocale(Locale.US);
        int c = 0;
		while(fFunc.hasNext()) {
        	if (fFunc.hasNextDouble()) {
        		equation.getFFunction().addA(c++, fFunc.nextDouble());
        	}
        	else {
        		fFunc.next();
        	}
        }
		fFunc.close();
        for(int i = 0; i <= 4; i++) {
        	String x = xTextFields.get(i).getText();
        	String gx = yofxTextFields.get(i).getText();
        	if (!x.equals("") & !gx.equals("")) {
        		equation.getGFunction().addPair(Double.parseDouble(x), Double.parseDouble(gx));
        	}
        }
	}


	private void updateGraph(double a, double b, Double xMax, Double yMax) {
		lineChartMain.getData().clear();
		double h = (b - a) / 100;
		XYChart.Series<Number, Number> fxSeries = new XYChart.Series<>();
		fxSeries.setName("f(x)");
		XYChart.Series<Number, Number> gxSeries = new XYChart.Series<>();
		gxSeries.setName("g(x)");
		XYChart.Series<Number, Number> mainSeries = new XYChart.Series<>();
		mainSeries.setName("f(x)-g(x)");
		for (double x = a; x <= b; x += h) {
	        try {
				fxSeries.getData().add(new XYChart.Data<>(x, equation.getF().applyAsDouble(x)));
				gxSeries.getData().add(new XYChart.Data<>(x, equation.getG().applyAsDouble(x)));
				mainSeries.getData().add(new XYChart.Data<>(x, equation.getF().applyAsDouble(x) - equation.getG().applyAsDouble(x)));
			} catch (IllegalArgumentException e) {
				showError("Functions error");
			}
	    }
		if(xMax != null) {
			XYChart.Series<Number, Number> maxPoint = new XYChart.Series<>();
			maxPoint.setName("max");
			maxPoint.getData().add(new XYChart.Data<Number, Number>(xMax, yMax));
			lineChartMain.getData().addAll(fxSeries, gxSeries, mainSeries, maxPoint);
		}
		else {
			lineChartMain.getData().addAll(fxSeries, gxSeries, mainSeries);
		}
	}
}

	

	

