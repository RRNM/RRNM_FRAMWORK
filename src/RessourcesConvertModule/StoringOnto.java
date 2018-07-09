package RessourcesConvertModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ModelMaker;
import org.apache.jena.tdb.TDBFactory;

public class StoringOnto {
	public OntModel maker;
	
	public StoringOnto()
	{maker=ModelFactory.createOntologyModel();}

	public OntModelSpec getModelSpec(ModelMaker maker) {
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);
		spec.setBaseModelMaker(maker);
		return spec;
	}
	public OntModel getMaker()
	{
		return maker;
	}
	public void setMaker(OntModel M)
	{
		maker=M;
	}

	public  OntModel createTDBModelFromFile(String name, String filePath) {
		InputStreamReader reader = null;
		FileInputStream inputStreamfile = null;
		File file = new File(filePath);
		org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
		maker = ModelFactory.createOntologyModel();
		try {
			inputStreamfile = new FileInputStream(file);
			reader = new InputStreamReader(inputStreamfile, "UTF-8");
			maker.read(reader, null);
			reader.close();
		} catch (IOException e) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		// store the owlfile in a triple store
		Dataset dataset = TDBFactory.createDataset(name);
		dataset.begin(ReadWrite.WRITE);
		dataset.addNamedModel("foo", maker);
		dataset.commit();
		dataset.end();
		return maker;
	}

	public static void main(String[] args) {

		//String filePath = "Ressources/ontologie.owl";
		//OntModel OM = createTDBModelFromFile("uploads/datasets", filePath);

	}
}