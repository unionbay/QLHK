package anhnh34.fpt.vn.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

import anhnh34.com.vn.util.Constans;
import anhnh34.fpt.vn.entity.Location;
import anhnh34.fpt.vn.entity.PopCase;
import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.entity.Sex;
import anhnh34.fpt.vn.model.LocationModel;
import anhnh34.fpt.vn.model.PopulationModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class PopulationController {

	private static final Logger logger = Logger.getLogger(PopulationController.class);

	public PopulationController() {
		//this.fullNameProperty = new SimpleStringProperty();
	}

	public void setPopulationStage(Stage stage) {
		PopulationController.populationStage = stage;
	}

	public Stage getPopulationStage() {
		return populationStage;
	}

	public Stage getSubStage() {
		return subPopulationStage;
	}

	public SubPopController getSubPopController() {
		return subPopController;
	}

	public PopulationModel getPopulationModel() {
		return populationModel;
	}

	@FXML
	public void initialize() {
		try {
			if (PopulationController.populationModel == null) {
				PopulationController.populationModel = new PopulationModel();
				// this.bindingObject();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dateCreateDatePicker.setValue(LocalDate.now());
		locationModel = new LocationModel();
		togSexGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
			}
		});

		if (radioButton != null) {
			radioButton.setUserData("Nam");
			radioButton.setToggleGroup(togSexGroup);
		}

		if (radioButton2 != null) {
			radioButton2.setUserData("Nữ");
			radioButton2.setToggleGroup(togSexGroup);
		}
	}

	@FXML
	protected void onHandleAttachAction() {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/anhnh34/fpt/vn/application/SubPopulationForm.fxml"));
			Pane pane = loader.load();
			this.subPopController = loader.<SubPopController>getController();
			this.subPopController.setPopulationModel(PopulationController.populationModel);
			this.subPopController.initSubPopData();
			if (subPopulationStage == null) {
				subPopulationStage = new Stage();
				subPopController.setStage(subPopulationStage);
				Scene scene = new Scene(pane);
				subPopulationStage.initModality(Modality.WINDOW_MODAL);
				subPopulationStage.initOwner(populationStage);
				subPopulationStage.setMinWidth(900);
				subPopulationStage.setMinHeight(500);
				subPopulationStage.setScene(scene);
			}
			subPopulationStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void onActionAddPopulation() {
		if (this.isInputValid()) {
			try {
				if (this.formMode == Constans.ADD_FORM) {

					populationModel.addNewPopulation();
					populationModel.addSubPopulation();

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(dialogStage);
					alert.setTitle("Tạo mới");
					alert.setHeaderText("Tạo mới khẩu thành công");
					alert.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							this.reset();
							populationModel.reset();
							this.getPopulationStage().hide();
						}
					});
				}

				if (formMode == Constans.UPDATE_FORM) {
					PopulationController.populationModel.updateForm();
					this.getPopDetailController().updateReport();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(dialogStage);
					alert.setTitle("Cập nhật");
					alert.setHeaderText("Cập nhật thông tin hộ khẩu thành công");
					alert.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							this.getPopulationStage().hide();
						}
					});
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	protected void onActionCancel() {
		this.reset();
		populationModel.reset();
		this.getPopulationStage().hide();
	}

	@FXML
	protected void loadData() {
		try {
			if (this.formMode == Constans.UPDATE_FORM) {
				btnAddPopulation.setText("Cập nhật");
				PopulationController.populationModel.loadSubpopulations();
			}

			List<Location> locationList = locationModel.getDistricts();
			cbDistrict.setItems(FXCollections.observableArrayList(locationList));
			cbDistrict.setConverter(new StringConverter<Location>() {
				@Override
				public Location fromString(String string) {
					return cbDistrict.getItems().stream().filter(lc -> lc.getDescription().equals(string)).findFirst()
							.orElse(null);
				}

				@Override
				public String toString(Location object) {
					if(object == null) return "";
					return object.getDescription();
				}
			});
			List<PopCase> popCaseList = populationModel.getPopulationCase();
			logger.info("Population types size: "  + popCaseList.size());			
			checkPopTypeCombobox.getItems().setAll(popCaseList);
			checkPopTypeCombobox.setConverter(new StringConverter<PopCase>() {

				@Override
				public String toString(PopCase object) {
					if(object == null) return "";
					return object.getValue();
				}

				@Override
				public PopCase fromString(String string) {
					return checkPopTypeCombobox.getItems().stream().filter(pc -> pc.getValue().equals(string)).findFirst()
							.orElse(null);					
				}
			});			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isInputValid() {
		String errorMessage = "";
		if (txtFullName.getText() == null || txtFullName.getText().length() == 0) {
			errorMessage += "Vui lòng nhập tên chủ hộ \n";
		}

		if (txtAddress.getText() == null || txtAddress.getText().length() == 0) {
			errorMessage += "Vui lòng nhập địa chỉ \n";
		}

		if (txtReason.getText() == null || txtReason.getText().length() == 0) {
			errorMessage += "Vui lòng nhập nội dung cần giải quyết\n";
		}

		if (cbDistrict.getValue() == null) {
			errorMessage += "Vui lòng chọn Phường\n";
		}

//		if (popTypeCombobox.getValue() == null) {
//			errorMessage += "Vui lòng chọn trường hợp nhập\n";
//		}

		if (dateCreateDatePicker.getValue() == null) {
			errorMessage += "Vui lòng chọn ngày tạo";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}
	}

	private void handleAddForm() {
		population = new Population();
		population.setId("");
		population.setParentId("");
		population.setHouseHolderName(txtFullName.getText());
		population.setLocationId(cbDistrict.getValue().getId());
		population.setAddress(txtAddress.getText());
		//Add check types.
		System.out.println("handle add form reason value: " + txtReason.getText());
		population.setReason(txtReason.getText());
		if (birthdayDatePicker.getValue() != null) {
			population.setBirthday(birthdayDatePicker.getValue());
		}

		if (dateCreateDatePicker.getValue() != null) {
			population.setDateCreated(
					Date.from(dateCreateDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		}

		if (radioButtonAddNew.isSelected()) {
			population.setSex("X");
		} else {
			population.setSex("");
		}

		if (outSideRadioButton.isSelected()) {
			population.setOutSide("X");
		} else {
			population.setOutSide("");
		}
		population.setSex(togSexGroup.getSelectedToggle().getUserData().toString());
		population.setDocumentNumber(txtDocumentNum.getText());
		if (caseNum.getText() != null && !caseNum.getText().trim().isEmpty()) {
			population.setCaseNumber(Integer.valueOf(caseNum.getText()));
		}
		population.setPopStatus(Constans.ACTIVE);
		populationModel.setPopulation(population);
	}

	private void reset() {
		txtFullName.setText("");
		cbDistrict.setValue(null);
		txtAddress.setText("");		
		txtReason.setText("");
		birthdayDatePicker.setValue(null);
		togSexGroup.setUserData("Nữ");
		txtDocumentNum.setText("");
		caseNum.setText("");
	}

	public int getFormMode() {
		return formMode;
	}

	public void setPopulation(Population pop) {
		if (PopulationController.populationModel.getPopulation() == null) {
			PopulationController.populationModel.setPopulation(pop);
		} else {
			PopulationController.populationModel.getPopulation().setId(pop.getId());
			PopulationController.populationModel.getPopulation().setParentId(pop.getParentId());
			PopulationController.populationModel.getPopulation().setHouseHolderName(pop.getHouseHolderName());
			PopulationController.populationModel.getPopulation().setFullName(pop.getFullName());
			PopulationController.populationModel.getPopulation().setAddress(pop.getAddress());
			PopulationController.populationModel.getPopulation().setLocationId(pop.getLocationId());
			PopulationController.populationModel.getPopulation().setPopulationType(pop.getPopulationType());
			PopulationController.populationModel.getPopulation().setAddNew(pop.getAddNew());
			PopulationController.populationModel.getPopulation().setOutSide(pop.getOutSide());
			PopulationController.populationModel.getPopulation().setReason(pop.getReason());
			PopulationController.populationModel.getPopulation().setBirthday(pop.birthdayProperty().get());
			PopulationController.populationModel.getPopulation().setSex(pop.getSex());
			PopulationController.populationModel.getPopulation().setDocumentNumber(pop.getDocumentNumber());
			PopulationController.populationModel.getPopulation().setCaseNumber(pop.getCaseNumber());
			PopulationController.populationModel.getPopulation().setDateCreated(pop.getDateCreated());
			PopulationController.populationModel.getPopulation().setPopStatus(pop.getPopStatus());
			PopulationController.populationModel.getPopulation().setExternalFee(pop.getExternalFee());
			PopulationController.populationModel.getPopulation().setIsOutSide(pop.getIsOutSide());
			PopulationController.populationModel.getPopulation().setLocation(pop.getLocation());
			PopulationController.populationModel.getPopulation()
					.setSelectedPopulationType(pop.getSelectedPopulationType());
			PopulationController.populationModel.getPopulation().setIsMan(pop.getIsMan());
			PopulationController.populationModel.getPopulation().setIsAddNew(pop.getIsAddNew());
			PopulationController.populationModel.getPopulation().setSexP(pop.getSexP());
			PopulationController.populationModel.setOriginPop(pop);
		}

	}

	public void setFormMode(int formMode) {
		this.formMode = formMode;
	}

	private Population getPopulation() {
		return PopulationController.populationModel.getPopulation();
	}

	public void showValue() {
		// System.out.println("Full name: " + this.fullNameProperty.getValue());
	}

	public void bindingObject() {
		txtFullName.textProperty().bindBidirectional(this.getPopulation().houseHolderName());
		txtAddress.textProperty().bindBidirectional(this.getPopulation().addressProperty());
		cbDistrict.valueProperty().bindBidirectional(this.getPopulation().SelectedLocation());		
		checkPopTypeCombobox.getCheckModel().getCheckedItems().addListener(new ListChangeListener<PopCase>() {

			@Override
			public void onChanged(Change<? extends PopCase> c) {			
				populationModel.setSelectedTypes(checkPopTypeCombobox.getCheckModel().getCheckedItems());			
			}
		});
		this.getPopulation().SelectedLocation().addListener(new ChangeListener<Location>() {
			@Override
			public void changed(ObservableValue<? extends Location> observable, Location oldValue, Location newValue) {
				if (oldValue == null || newValue == null)
					return;
				if (oldValue.getId().compareTo(newValue.getId()) != 0) {
					for (int i = 0; i < PopulationController.populationModel.getSubPopulations().size(); i++) {
						Population pop = PopulationController.populationModel.getSubPopulations().get(i);
						pop.setLocation(newValue);
					}
				}
			}
		});
		outSideRadioButton.selectedProperty().bindBidirectional(this.getPopulation().IsOutSide());
		this.getPopulation().IsOutSide().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					getPopulation().setOutSide(Constans.OUTSIDE_LC);
				} else {
					getPopulation().setOutSide("");
				}
			}
		});

		radioButtonAddNew.selectedProperty().bindBidirectional(this.getPopulation().IsAddNew());
		this.getPopulation().IsAddNew().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					getPopulation().setAddNew(Constans.HO_MOI);
				} else {
					getPopulation().setAddNew("");
				}
			}
		});

		radioButton.selectedProperty().bindBidirectional(this.getPopulation().IsMan());
		this.getPopulation().IsMan().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue == true) {
					radioButton2.setSelected(false);
					Sex sexMan = new Sex(Sex.MAN_ID, Sex.MAN_DESC);
					getPopulation().setSexP(sexMan);
				} else {
					radioButton2.setSelected(true);
					Sex sexWoman = new Sex(Sex.WOMAN_ID, Sex.WOMAN_DESC);
					getPopulation().setSexP(sexWoman);
				}
			}
		});

		txtReason.textProperty().bindBidirectional(this.getPopulation().Reason());
		txtDocumentNum.textProperty().bindBidirectional(this.getPopulation().DocumentNumber());
		caseNum.textProperty().bindBidirectional(this.getPopulation().CaseNumber(), new NumberStringConverter());
		dateCreateDatePicker.valueProperty().bindBidirectional(this.getPopulation().DateCreated());
		birthdayDatePicker.valueProperty().bindBidirectional(this.getPopulation().birthdayProperty());
	}

	public void unbindingObject() {
		// logger.info(this.getPopulation().getHouseHolderName());
		// txtFullName.textProperty().unbind();
		// txtFullName.textProperty().unbindBidirectional(this.getPopulation().houseHolderName());
		// txtFullName.textProperty().unbind();
	}

	public void setPopDetailController(PopulationDetaiLController popDetailController) {
		this.popDetailController = popDetailController;
	}

	public PopulationDetaiLController getPopDetailController() {
		return popDetailController;
	}

	private static Stage populationStage;
	private static Stage subPopulationStage;
	private static PopulationModel populationModel;
	private LocationModel locationModel;
	private final ToggleGroup togSexGroup = new ToggleGroup();
	private Stage dialogStage;
	private Population population;
	private SubPopController subPopController;
	private PopulationDetaiLController popDetailController;
	private int formMode;	
	//private final String ACTIVE_STATUS = "STA01";
	//private final SimpleStringProperty fullNameProperty;

	@FXML
	private TextArea txtAddress;
	@FXML
	private TextArea txtReason;
	@FXML
	private TextField txtFullName;
	@FXML
	private Button btnAttachment;
	@FXML
	private DatePicker birthdayDatePicker;
	@FXML
	private TextField txtDocumentNum;
	@FXML
	private TextField caseNum;	
	@FXML
	private CheckComboBox<PopCase> checkPopTypeCombobox;
	@FXML
	private RadioButton outSideRadioButton;
	@FXML
	private DatePicker dateCreateDatePicker;
	@FXML
	private ComboBox<Location> cbDistrict;
	@FXML
	private RadioButton radioButton;
	@FXML
	private RadioButton radioButton2;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnAddPopulation;
	@FXML
	private RadioButton radioButtonAddNew;

}
