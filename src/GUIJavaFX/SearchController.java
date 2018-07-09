package GUIJavaFX;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.scene.layout.FlowPane;

public class SearchController implements Initializable {
	// Interface components
	@FXML
	private AnchorPane root;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;
	@FXML
	private JFXButton addKnowledgeBtn;

	public static AnchorPane rootP;

	@FXML
	private FlowPane main;

	@FXML
	private JFXTextField searchField;

	@FXML
	private JFXTreeTableView<Ontology> treeView;

	@FXML
	private JFXComboBox<String> languageCB;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<String> options =
			    FXCollections.observableArrayList(
			    		"EN",
						"FR",
						"DE"
			    );

		languageCB.getItems().addAll(options);


		rootP = root;

		try {
			VBox box = FXMLLoader.load(getClass().getResource("SidePanelContent.fxml"));
			drawer.setSidePane(box);
		} catch (IOException ex) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
		}

		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
		transition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer.isShown()) {
				drawer.close();
			} else
				drawer.open();
		});
	}

	/**
	 * Triggered when search button is clicked
	 *
	 * @param event
	 */
	public void searchBtnAction(ActionEvent event) {
		Utils utils = new Utils();
		//Selected language by the user
		System.out.println("Selected Language: " + languageCB.getValue());
		ObservableList<Ontology> ontologies = null;
		try {
			//Calling the search method from the Utils class
			ontologies = utils.Search(searchField.getText().replace(" ", ";"),languageCB.getValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Repeat this block of code if you have more than one column
		//This block is for only the "Ontology" column
		JFXTreeTableColumn<Ontology, String> ontologyText = new JFXTreeTableColumn<>("Ressources");
		ontologyText.setPrefWidth(400);
		ontologyText.setCellValueFactory(
				new Callback<TreeTableColumn.CellDataFeatures<Ontology, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Ontology, String> param) {
						// TODO Auto-generated method stub
						return param.getValue().getValue().ontologyText;
					}
				});

		final TreeItem<Ontology> root = new RecursiveTreeItem<Ontology>(ontologies, RecursiveTreeObject::getChildren);
		treeView.getColumns().setAll(ontologyText);
		treeView.setRoot(root);
		treeView.setShowRoot(false);
	}

}