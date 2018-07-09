package GUIJavaFX;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ontology extends RecursiveTreeObject<Ontology> {
	// Properties used as table columns
	StringProperty ontologyText;

	public Ontology(String ontologyText) {
		this.ontologyText = new SimpleStringProperty(ontologyText);
	}
}
