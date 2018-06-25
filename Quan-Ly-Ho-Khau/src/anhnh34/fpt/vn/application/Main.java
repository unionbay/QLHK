package anhnh34.fpt.vn.application;
	
import anhnh34.fpt.vn.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class Main extends Application {
	private static Stage loginStage;
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
			Pane pane = loader.load();			
			LoginController loginController = loader.<LoginController>getController();
			loginStage = new Stage();
			LoginController.setLoginStage(loginStage);
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Đăng nhập");
			primaryStage.setMinWidth(250);
			primaryStage.setMinHeight(200);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
