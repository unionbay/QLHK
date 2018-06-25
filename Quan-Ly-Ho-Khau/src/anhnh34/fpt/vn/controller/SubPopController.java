package anhnh34.fpt.vn.controller;

import java.util.Date;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import anhnh34.com.vn.util.ComboBoxEditingCell;
import anhnh34.com.vn.util.DateEditingCell;
import anhnh34.com.vn.util.EditingCell;
import anhnh34.com.vn.util.comboboxSexEditingCell;
import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.entity.Sex;
import anhnh34.fpt.vn.model.PopulationModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SubPopController {
	
	private final Logger logger = Logger.getLogger(SubPopController.class);
	
	private static Stage subPopulationStage;
	public Stage getStage() {
		return stage;
	}


	public void setStage(Stage stage) {
		SubPopController.subPopulationStage = stage;
	}

	public SubPopController() {		
	}

	@FXML
	public void initialize() {
		btnAdd.visibleProperty().bind(updateMode);
		btnDelete.visibleProperty().bind(updateMode);
		btnDelete.visibleProperty().bind(updateMode);
		tbSubPop.editableProperty().bind(updateMode);
		
	}

	@FXML
	protected void onEditButtonClick() {
		if (updateMode.getValue() == true) {
			if (isInputValid() == false) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(dialogStage);
				alert.setTitle("Invalid Fields");
				alert.setHeaderText("Please correct invalid fields");
				alert.setContentText("Dữ liệu không hợp lệ");
				alert.showAndWait();
			} else {				
				updateMode.setValue(false);
				btnEdit.setText("Sửa");
			}
		} else {
			updateMode.setValue(true);
			btnEdit.setText("Hoàn thành");
		}
	}

	private boolean isInputValid() {
		boolean isValid = true;
		ArrayList<Population> populations = (ArrayList<Population>) SubPopController.populationModel.getSubPopulations().stream()
				.collect(Collectors.toList());
		for (Population pop : populations) {
			System.out.println(pop.getFullName() + " " + pop.getBirthday() + " " + pop.getPopulationType());
			if ("".equals(pop.getFullName()) || pop.getFullName() == null || pop.getSex() == null
					|| pop.getBirthday() == null || pop.getPopulationType() == null) {
				isValid = false;
				break;
			}
		}
		return isValid;
	}

	public void initSubPopData() {

		Callback<TableColumn<Population, LocalDate>, TableCell<Population, LocalDate>> dateCellFactory = (
				TableColumn<Population, LocalDate> param) -> new DateEditingCell();
		Callback<TableColumn<Population, String>, TableCell<Population, String>> cellFactory = (
				TableColumn<Population, String> param) -> new EditingCell();
		Callback<TableColumn<Population, PopCase>, TableCell<Population, PopCase>> comboboxCelLFactory = (
				TableColumn<Population, PopCase> param) -> new ComboBoxEditingCell();

		Callback<TableColumn<Population, Sex>, TableCell<Population, Sex>> comboboxSexCelLFactory = (
				TableColumn<Population, Sex> param) -> new comboboxSexEditingCell();

		colBirthday.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
		colBirthday.setCellFactory(dateCellFactory);
		colBirthday.setOnEditCommit((TableColumn.CellEditEvent<Population, LocalDate> t) -> {
			((Population) t.getTableView().getItems().get(t.getTablePosition().getRow())).setBirthday(t.getNewValue());
		});

		colName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
		colName.setCellFactory(cellFactory);
		colName.setOnEditCommit((TableColumn.CellEditEvent<Population, String> t) -> {
			((Population) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFullName(t.getNewValue());
		});

		colSex.setCellValueFactory(cellData -> cellData.getValue().SexPProperty());
		colSex.setCellFactory(comboboxSexCelLFactory);
		colSex.setOnEditCommit((TableColumn.CellEditEvent<Population, Sex> t)->{	
			logger.info("Commited value: " + t.getNewValue().getDesc());
			((Population)t.getTableView().getItems().get(t.getTablePosition().getRow())).setSexP(t.getNewValue());			
		});

		colPopCase.setCellValueFactory(cellData -> cellData.getValue().SelectedPopulationType());
		colPopCase.setCellFactory(comboboxCelLFactory);				
		tbSubPop.setItems(SubPopController.populationModel.getSubPopulations());
	}	

	@FXML
	protected void onActionAddRow() {
		Population pop = new Population();
		pop.setBirthday(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		SubPopController.populationModel.addPopulation(pop);
	}

	@FXML
	protected void onActionDeleteRow() {

	}
	
	public void setPopulationModel(PopulationModel model){
		SubPopController.populationModel = model;
	}
	public PopulationModel getPopulationModel() {
		return populationModel;
	}

	private Stage stage;
	private Stage dialogStage;
	private static PopulationModel populationModel;
	private BooleanProperty updateMode = new SimpleBooleanProperty(false);

	@FXML
	private TableView<Population> tbSubPop;

	@FXML
	private TableColumn<Population, String> colName;

	@FXML
	private TableColumn<Population, LocalDate> colBirthday;

	@FXML
	private TableColumn<Population, PopCase> colPopCase;

	@FXML
	private TableColumn<Population, Sex> colSex;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnDelete;

	@FXML
	private Button btnEdit;
}
