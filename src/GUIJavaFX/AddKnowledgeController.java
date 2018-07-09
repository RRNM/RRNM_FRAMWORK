package GUIJavaFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import OntoNetworkModule.AddtoNetwork;
import RessourcesConvertModule.OntoResModel;
import RessourcesConvertModule.ResourceToOnto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXTextArea;

public class AddKnowledgeController implements Initializable {
	// Interface components
	@FXML
	private AnchorPane root;

	@FXML
	private JFXDrawer drawer;

	@FXML
	private JFXHamburger hamburger;

	@FXML
	private JFXButton selectFileBtn;

	@FXML
	private JFXButton addKnowledgeBtn;

	@FXML
	private JFXTextArea textArea;

	@FXML
	private JFXTextField textField;


	private static File selectedFile;

	public static AnchorPane rootP;
	public ResourceToOnto RTO;

	@FXML
    private JFXTextField title;

    @FXML
    private JFXTextField description;

    @FXML
    private JFXTextField keywords;

    @FXML
    private JFXTextField subject;

    @FXML
    private JFXTextField category;

    @FXML
    private JFXTextField company;

    @FXML
    private JFXTextField template;

    @FXML
    private JFXTextField manager;

    @FXML
    private JFXTextField documentCreator;

    @FXML
    private JFXTextField lastModification;

    @FXML
    private JFXTextField creationDate;
    @FXML
	private JFXComboBox<String> languageCB;

    String fileExtension;
    
    int j;

	

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
		 RTO= new ResourceToOnto();
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
		j=0;
	}

	/**
	 * Triggered when the selectFile button is clicked
	 *
	 * @param event
	 */
	public void selectFileBtnAction(ActionEvent event) {

		FileChooser fc = new FileChooser();
		String fileName ;
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("knowledge", "*.txt", "*.xml",
				"*.xlsx", "*.html", "*.pdf", "*.csv");
		fc.getExtensionFilters().add(extensionFilter);
		selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			fileName=selectedFile.getName();
			textField.setText(fileName);
			fileExtension = fileName.substring(fileName.indexOf(".") + 1, selectedFile.getName().length());
			OntoResModel output = RTO.RessourceToOntoExtract((fileExtension), (selectedFile.getPath().toString()));

			title.setText(output.getTitle());
			description.setText(output.getDescription());
			keywords.setText(output.getKeywords());
			subject.setText(output.getSubject());
			category.setText(output.getCategory());
			company.setText(output.getCompany());
			template.setText(output.getTemplate());
			manager.setText(output.getManager());
			documentCreator.setText(output.getDocumentCreator());
			creationDate.setText(output.getCreationdate());
			lastModification.setText(output.getLastModif());
			languageCB.setAccessibleText("EN");
			

			System.out.print(output);
		} else {
			textField.setText("File not valid!");
		}
	}

	public void addKnowledgeBtnAction(ActionEvent event) {
		j++;
		AddtoNetwork ATN=new AddtoNetwork();
		
		RTO.onto.setTitle(title.getText());
		RTO.onto.setDescription(description.getText());
		RTO.onto.setKeywords(keywords.getText());
		RTO.onto.setSubject(subject.getText());
		RTO.onto.setCategory(category.getText());
		RTO.onto.setCompany(company.getText());
		RTO.onto.setTemplate(template.getText());
		RTO.onto.setManager(manager.getText());
		RTO.onto.setDocumentCreator(documentCreator.getText());
		RTO.onto.setCreationdate(creationDate.getText());
		RTO.onto.setLastModif(lastModification.getText());
		RTO.onto.setLanguage(languageCB.getValue());
		System.out.println("format:"+RTO.getonto());
				RTO.RessourceToOntoConvert((fileExtension), (selectedFile.getPath().toString()),j);
		
		try {
			ATN.AlignwithProfile(RTO.getonto(),selectedFile.getPath().toString());
			//ATN.AlignwithNetwork(RTO.getonto(),selectedFile.getPath().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}