package anhnh34.fpt.vn.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
	private static final Logger logger = Logger.getLogger(LoginController.class);

	public static void setLoginStage(Stage loginStage) {
		LoginController.loginStage = loginStage;
	}

	public static Stage getLoginStage() {
		return loginStage;
	}

	public static void setMainStage(Stage mainStage) {
		LoginController.mainStage = mainStage;
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@FXML
	protected void onLoginAction() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/anhnh34/fpt/vn/application/Main.fxml"));
			Pane pane = loader.load();
			// MainController mainController =
			// loader.<MainController>getController();

			Scene scene = new Scene(pane);
			if (mainStage == null) {
				mainStage = new Stage();
				mainStage.setMinWidth(900);
				mainStage.setMinHeight(500);
				mainStage.setScene(scene);
				MainController.setMainStage(mainStage);
			}
			((Stage) btnLogin.getScene().getWindow()).hide();
			mainStage.show();
		} catch (IOException e) {
			logger.log(Priority.FATAL, "Exceptions happen!", e);
		}
	}

	@FXML
	private Button btnLogin;
	private static Stage loginStage;
	private static Stage mainStage;
}
