package anhnh34.fpt.vn.controller;

import java.io.IOException;
import java.sql.Date;
import org.apache.log4j.Logger;

import anhnh34.com.vn.util.Constans;
import anhnh34.fpt.vn.entity.Location;
import anhnh34.fpt.vn.entity.Report;
import anhnh34.fpt.vn.model.ExcelModel;
import anhnh34.fpt.vn.model.ReportModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldListCell;
import jxl.write.Number;

public class MainController {

	final static Logger logger = Logger.getLogger(MainController.class);

	public Stage getMainStage() {
		return mainStage;
	}

	public static void setMainStage(Stage mainStage) {
		MainController.mainStage = mainStage;
	}

	@FXML
	private MenuItem menuPopulation;

	@FXML
	public void initialize() {
		try {
			MainController.reportModel = new ReportModel();
			MainController.reportModel.initModel();
			this.initBinding();

			lvLocation.setItems(MainController.reportModel.getLocationList());
			lvLocation.setCellFactory(lv -> {
				TextFieldListCell<Location> cell = new TextFieldListCell<Location>();
				cell.setConverter(new StringConverter<Location>() {
					@Override
					public String toString(Location arg0) {
						return arg0.getDescription();
					}

					@Override
					public Location fromString(String arg0) {
						return reportModel.getLocationList().stream().filter(lc -> lc.getDescription().equals(arg0))
								.findFirst().orElse(null);
					}
				});
				return cell;
			});

			lvLocation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Location>() {
				@Override
				public void changed(ObservableValue<? extends Location> observable, Location oldValue,
						Location newValue) {
					getReportById(newValue);
					report.set(reportModel.getReport());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void onDetailButtonClick() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/anhnh34/fpt/vn/application/PopulationDetail.fxml"));
			Pane pane = loader.load();
			PopulationDetaiLController popDetailController = loader.<PopulationDetaiLController>getController();
			popDetailController.setMainController(this);
			if (populationDetailStage == null) {
				populationDetailStage = new Stage();
				populationDetailStage.initModality(Modality.APPLICATION_MODAL);
				populationDetailStage.initOwner(mainStage.getScene().getWindow());
				populationDetailStage.setResizable(false);
				PopulationDetaiLController.setPopDetailStage(populationDetailStage);
				PopulationDetaiLController.setReportModel(MainController.reportModel);
				popDetailController.initializeData();
				Scene scene = new Scene(pane);
				populationDetailStage.setScene(scene);
			}
			popDetailController.reload();
			populationDetailStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void onActionExport() {
		if (selectedLocation == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.initOwner(mainStage);
			alert.setTitle("Warning");
			alert.setHeaderText("Vui lòng chọn phường cần in báo cáo");
			alert.setAlertType(AlertType.WARNING);
			alert.show();
			return;
		}

		try {
			this.exportExcel();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	protected void onAddPopAction() {
		try {

			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/anhnh34/fpt/vn/application/PopulationForm.fxml"));
			Pane pane = loader.load();
			PopulationController popController = loader.<PopulationController>getController();
			if (populationStage == null) {
				populationStage = new Stage();
				populationStage.initModality(Modality.APPLICATION_MODAL);
				populationStage.initOwner(mainStage.getScene().getWindow());
				populationStage.setResizable(false);
				popController.setPopulationStage(populationStage);
				popController.setFormMode(Constans.ADD_FORM);
				popController.loadData();
				Scene scene = new Scene(pane);
				populationStage.setScene(scene);
			}
			popController.bindingObject();
			popController.getPopulationModel().reset();
			populationStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Report getReport() {
		return MainController.reportModel.getReport();
	}

	private void getReportById(Location location) {
		try {

			if (dpFrom.getValue() == null || dpTo.getValue() == null) {
				MainController.reportModel.getReport(location.getId(), null, null);
			} else if (dpFrom.getValue() != null && dpTo.getValue() != null) {
				Date dateFrom = Date.valueOf(dpFrom.getValue());
				Date dateTo = Date.valueOf(dpTo.getValue());
				MainController.reportModel.getReport(location.getId(), dateFrom, dateTo);
			} else {
				// Display popup require input all information of dpFrom and
				// dpTo.
			}

			this.selectedLocation = location;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateReport() {
		try {
			if (dpFrom.getValue() == null || dpTo.getValue() == null) {
				MainController.reportModel.getReport(selectedLocation.getId(), null, null);
			} else {
				Date dateFrom = Date.valueOf(dpFrom.getValue());
				Date dateTo = Date.valueOf(dpTo.getValue());
				MainController.reportModel.getReport(selectedLocation.getId(), dateFrom, dateTo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public IntegerProperty counterProperty() {
		return counter;
	}

	// this is also required
	public int getCounter() {
		return counter.get();
	}

	private void initBinding() {
		// Khai sinh
		txtRbTotalNum.textProperty().bindBidirectional(this.getReport().getRegisterOfBirth().TotalNumber(),
				new NumberStringConverter());
		txtRbWoman.textProperty().bindBidirectional(this.getReport().getRegisterOfBirth().TotalWomanNumber(),
				new NumberStringConverter());

		// Nhap them
		txtAmTotalNumber.textProperty().bindBidirectional(this.getReport().getAddMore().TotalNumber(),
				new NumberStringConverter());
		txtAmWomanNumber.textProperty().bindBidirectional(this.getReport().getAddMore().TotalWomanNumber(),
				new NumberStringConverter());
		txtAmAfNumber.textProperty().bindBidirectional(this.getReport().getAddMore().AboveFourteenYear(),
				new NumberStringConverter());
		txtAmWfNUmber.textProperty().bindBidirectional(this.getReport().getAddMore().WomanAboveFourteenYear(),
				new NumberStringConverter());

		// Nhap moi
		txtAddNewHo.textProperty().bindBidirectional(this.getReport().getAddNew().isAddNew(),
				new NumberStringConverter());
		txtAnTotalNumber.textProperty().bindBidirectional(this.getReport().getAddNew().TotalNumber(),
				new NumberStringConverter());
		txtAnWomanTotalNumber.textProperty().bindBidirectional(this.getReport().getAddNew().TotalWomanNumber(),
				new NumberStringConverter());
		txtAddAboveFTotalNumber.textProperty().bindBidirectional(this.getReport().getAddNew().AboveFourteenYear(),
				new NumberStringConverter());
		txtAddWomanAboveFTotalNumber.textProperty()
				.bindBidirectional(this.getReport().getAddNew().WomanAboveFourteenYear(), new NumberStringConverter());

		// Ngoai tinh
		txtHo.textProperty().bindBidirectional(this.getReport().getOutOfLc().AddNewProperty(),
				new NumberStringConverter());
		txtOsTotalNumber.textProperty().bindBidirectional(this.getReport().getOutOfLc().TotalNumber(),
				new NumberStringConverter());
		txtOsAfTotalNumber.textProperty().bindBidirectional(this.getReport().getOutOfLc().AboveFourteenYear(),
				new NumberStringConverter());
		txtOsWTotalNumber.textProperty().bindBidirectional(this.getReport().getOutOfLc().TotalWomanNumber(),
				new NumberStringConverter());
		txtOsWafTotalNumber.textProperty().bindBidirectional(this.getReport().getOutOfLc().WomanAboveFourteenYear(),
				new NumberStringConverter());

		// Khac
		txtToDeny.textProperty().bindBidirectional(this.getReport().ToDeny(), new NumberStringConverter());
		txtReallocate.textProperty().bindBidirectional(this.getReport().Reallocate(), new NumberStringConverter());
		txtDeathCertificate.textProperty().bindBidirectional(this.getReport().DeathCertificate(),
				new NumberStringConverter());
		txtChangePopulation.textProperty().bindBidirectional(this.getReport().ChangePopulation(),
				new NumberStringConverter());
		txtChangeDistrict.textProperty().bindBidirectional(this.getReport().ChangeDistrict(),
				new NumberStringConverter());
		txtChangePopDocument.textProperty().bindBidirectional(this.getReport().ChangePopDocument(),
				new NumberStringConverter());
		txtJoinPopDocDocument.textProperty().bindBidirectional(this.getReport().JoinPopDocDocument(),
				new NumberStringConverter());

	}

	public void exportExcel() throws IOException, WriteException {
		ExcelModel excelModel = new ExcelModel();		
		excelModel.exportExcel(MainController.reportModel.getReports());		
	}

	private void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 15);
		times10pt.setColour(Colour.WHITE);
		times10pt.setBoldStyle(WritableFont.BOLD);

		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);
		// times.setBackground(Colour.AQUA);
		times.setAlignment(Alignment.CENTRE);

		// create create a bold font with underlines
		WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 15, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);

		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);
		timesBoldUnderline.setAlignment(Alignment.CENTRE);
		timesBoldUnderline.setVerticalAlignment(VerticalAlignment.CENTRE);
		timesBoldUnderline.setBackground(Colour.BLUE);
		times10ptBoldUnderline.setColour(Colour.WHITE);

		// Write a few headers
		addCaption(sheet, 0, 1, "Phường");
		addCaption(sheet, 3, 1, "Khai Sinh");

	}

	private void addNumber(WritableSheet sheet, int column, int row, Integer integer)
			throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		int widthInChars = 30;
		int heightInPoints = 26 * 20;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.setRowView(row, heightInPoints);
		sheet.setColumnView(column, widthInChars);
		sheet.mergeCells(column, row, column + 2, row);
		sheet.addCell(label);

	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

	private final IntegerProperty counter = new SimpleIntegerProperty(100);
	private final SimpleObjectProperty<Report> report = new SimpleObjectProperty<>(new Report());

	private static Stage mainStage;
	private static Stage populationStage;
	protected static ReportModel reportModel;
	private Location selectedLocation;

	private static Stage populationDetailStage;
	@FXML
	private DatePicker dpFrom;
	@FXML
	private DatePicker dpTo;
	@FXML
	private TextField txtRbTotalNum;
	@FXML
	private TextField txtRbWoman;
	@FXML
	private TextField txtAmTotalNumber;
	@FXML
	private TextField txtAmWomanNumber;
	@FXML
	private TextField txtAmAfNumber;
	@FXML
	private TextField txtAmWfNUmber;
	@FXML
	private TextField txtOsTotalNumber;
	@FXML
	private TextField txtOsWTotalNumber;
	@FXML
	private TextField txtOsAfTotalNumber;
	@FXML
	private TextField txtOsWafTotalNumber;
	@FXML
	private TextField txtToDeny;
	@FXML
	private TextField txtReallocate;
	@FXML
	private TextField txtDeathCertificate;
	@FXML
	private TextField txtChangePopulation;
	@FXML
	private TextField txtChangeDistrict;
	@FXML
	private TextField txtChangePopDocument;
	@FXML
	private TextField txtJoinPopDocDocument;
	@FXML
	private TextField txtAnTotalNumber;
	@FXML
	private TextField txtAnWomanTotalNumber;

	@FXML
	private TextField txtAddAboveFTotalNumber;

	@FXML
	private TextField txtAddWomanAboveFTotalNumber;

	@FXML
	private TextField txtAddNewHo;

	@FXML
	private TextField txtHo;

	@FXML
	private ListView<Location> lvLocation;

	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	private String inputFile;
}