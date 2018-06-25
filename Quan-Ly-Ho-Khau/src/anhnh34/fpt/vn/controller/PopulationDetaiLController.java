package anhnh34.fpt.vn.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import org.apache.log4j.Logger;

import anhnh34.com.vn.util.Constans;
import anhnh34.fpt.vn.entity.Population;
import anhnh34.fpt.vn.model.PopulationModel;
import anhnh34.fpt.vn.model.ReportModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopulationDetaiLController {
	private final Logger logger = Logger.getLogger(PopulationDetaiLController.class);
	public static Stage getPopDetailStage() {
		return popDetailStage;
	}

	public static void setPopDetailStage(Stage popDetailStage) {
		PopulationDetaiLController.popDetailStage = popDetailStage;
	}
	
	public void reload(){
		tbvPopulation.refresh();
	}

	@FXML
	public void initialize() {
		tbvPopulation.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						try {
							Population selectedPop = tbvPopulation.getSelectionModel().getSelectedItem();
							if(selectedPop == null) return;							
							if (selectedPop.getParentId() == null || selectedPop.getParentId().isEmpty()) {		
								//logger.info(selectedPop.toString());
								updatePopulation(selectedPop);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		});
	}

	private void updatePopulation(Population pop) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(Constans.RS_POPULATION_FORM));
		Pane pane = loader.load();
		popController = loader.<PopulationController>getController();
		popController.setPopulationStage(popFormStage);
		popController.setFormMode(Constans.UPDATE_FORM);
		popController.setPopulationStage(popDetailStage);		
		popController.setPopulation(pop);				
		popController.setPopDetailController(this);
		if (popFormStage == null) {
			popFormStage = new Stage();
			popFormStage.initModality(Modality.APPLICATION_MODAL);
			popFormStage.initOwner(popDetailStage.getScene().getWindow());
			popFormStage.setResizable(false);
			Scene scene = new Scene(pane);
			popFormStage.setScene(scene);
			popController.bindingObject();
		}
		popController.loadData();
		popFormStage.show();
	}

	public void updateReport() {
		this.mainController.updateReport();
		this.tbvPopulation.refresh();
	}

	public static void setReportModel(ReportModel reportModel) {
		PopulationDetaiLController.reportModel = reportModel;
	}

	@FXML
	protected void onDeleteClick() {		
		Population selectedPopulation = tbvPopulation.getSelectionModel().getSelectedItem();
		if (selectedPopulation != null) {
			PopulationModel popModel;
			try {
				popModel = new PopulationModel();				
				Alert alert = new  Alert(AlertType.CONFIRMATION);				
				alert.initOwner(dialogStage);
				alert.setTitle("Xóa hộ khẩu");
				alert.setHeaderText("Bạn có chắc chắn muốn xóa hộ khẩu này");
				alert.showAndWait().ifPresent(response->{
					if(response == ButtonType.OK){
						try {
							if(popModel.deletePopulation(selectedPopulation) == 1){
								reportModel.removeItem(selectedPopulation);
								Alert alert2 = new Alert(AlertType.CONFIRMATION);
								alert2.initOwner(dialogStage);
								alert2.setTitle("Xóa");
								alert2.setHeaderText("Xóa thông tin hộ khẩu thành công");
								alert2.showAndWait();
							}
						} catch (SQLException e) {							
							e.printStackTrace();
						}
					}
				});								
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void initializeData() {	
		clChuHo.setCellValueFactory(cellData -> cellData.getValue().houseHolderName());
		clTenDayDu.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
		clDiaChi.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
		clHo.setCellValueFactory(cellData -> cellData.getValue().AddNew());
		clNgoaiTinh.setCellValueFactory(cellData -> cellData.getValue().OutSide());
		clSex.setCellValueFactory(cellData -> cellData.getValue().getSexP().DescProperty());
		clDistrict.setCellValueFactory(cellData -> cellData.getValue().getLocation().DescriptionProperty());
		clBirthday.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
		clTruongHopNhap.setCellValueFactory(cellData->cellData.getValue().SelectedPopulationType().getValue().ValueProperty());
		clNoiDung.setCellValueFactory(cellData -> cellData.getValue().Reason());
		
		tbvPopulation.itemsProperty().bind(MainController.reportModel.PopList());
		
	}

	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	private static ReportModel reportModel;
	private static Stage popDetailStage;
	private static Stage popFormStage;
	private MainController mainController;	

	private PopulationController popController;
	@FXML
	private TableColumn<Population, String> clChuHo;
	@FXML
	private TableColumn<Population, String> clDiaChi;
	@FXML
	private TableColumn<Population, String> clHo;
	@FXML
	private TableColumn<Population, String> clNgoaiTinh;
	@FXML
	private TableColumn<Population, String> clTruongHopNhap;
	@FXML
	private TableColumn<Population, String> clNoiDung;
	@FXML
	private TableColumn<Population, String> clTenDayDu;
	@FXML
	private TableColumn<Population, String> clSex;
	@FXML
	private TableColumn<Population, String> clDistrict;
	@FXML
	private TableColumn<Population, LocalDate> clBirthday;
	@FXML
	private TableView<Population> tbvPopulation;
	private Stage dialogStage;
}
