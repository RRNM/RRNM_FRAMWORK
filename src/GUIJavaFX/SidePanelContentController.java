package GUIJavaFX;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class SidePanelContentController implements Initializable {

	@FXML
	private JFXButton b1;
	@FXML
	private JFXButton b2;
	@FXML
	private JFXButton b3;
	@FXML
	private JFXButton exit;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	@FXML
	private void changeColor(ActionEvent event) {
		JFXButton btn = (JFXButton) event.getSource();
		System.out.println(btn.getText());
		switch (btn.getText()) {
		case "Add Knowledge":
			HomeController.rootP.setStyle("-fx-background-color:#00FF00");
			Parent window1 = null;
			try {
				window1 = FXMLLoader.load(getClass().getResource("AddKnowledge.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Stage mainStage;
			mainStage = MainUI.parentWindow;
			mainStage.getScene().setRoot(window1);
			break;
		case "Search":
			HomeController.rootP.setStyle("-fx-background-color:#0000FF");
			HomeController.rootP.setStyle("-fx-background-color:#00FF00");
			Parent window2 = null;
			try {
				window2 = FXMLLoader.load(getClass().getResource("Search.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Scene newSceneWindow1 = new Scene(window1);

			Stage mainStage1;
			// mainStage = (Stage)
			// ((Node)event.getSource()).getScene().getWindow();
			mainStage1 = MainUI.parentWindow;
			mainStage1.getScene().setRoot(window2);
			break;

		case "Home":
			Parent window3 = null;
			try {
				window3 = FXMLLoader.load(getClass().getResource("Home.fxml"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mainStage = MainUI.parentWindow;
			mainStage.getScene().setRoot(window3);
			break;
		}
	}

	@FXML
	private void exit(ActionEvent event) {
		System.exit(0);
	}

}
