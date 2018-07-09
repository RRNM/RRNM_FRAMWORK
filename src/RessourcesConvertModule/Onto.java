package RessourcesConvertModule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;




//Converts an excel file to ontology

public  class Onto
{

  public  String namespace;
  public String ClassC;
  public String Prop;
  public String Dom;
  public String Ranj;
  
  public  OntModel ontologie;
    public Onto(String namespace)
    {
        ontologie = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
        ontologie.createOntology(namespace);
        ontologie.createProperty("language","en");
		this.namespace = namespace;
    }
    
   


    //actual method which does the convertion rsc->onto

    public void CreateNewSClass(String ClassName)
{System.out.println("testonto" +ClassName);
    	
    	ClassC=ClassName.replaceAll("[-,/&()יא #.'%;!?]", "_");
    	ontologie.createClass(namespace + ClassC);

}
    public String GetUri()
    {
    return namespace;

    }
    protected void CreateNewClassDataProp(String ClassName, String Prop, String Value)
{
ClassC=ClassName.replaceAll("[-,/&()יא #.'%!?;]", "_");


if ( !ClassC.isEmpty())
{

	OntClass  Cname=ontologie.createClass(namespace + ClassC);


DatatypeProperty prop= ontologie.createDatatypeProperty(namespace + Prop);
System.out.println(" Value"+Value);
Cname.addProperty (prop, Value,"xsd:string");
prop.setRange(Cname);
}
}

    protected void CreateNewClass(String ClassName, String Prop, String Dom,
                                  String Ran)
    {
    	ClassC=ClassName.replaceAll("[-,/&()יא #.'%!?;]", "_");
    	this.Dom=Dom.replaceAll("[-,/&()יא #.'%;!?]", "_");
    	this.Ranj=Ran.replaceAll("[-,/&()יא #.'%;!?]", "_");
    	this.Prop=Prop.replaceAll("[-,/&()יא #.'%;!?]", "_");
    
    	
    	
    	if ( !ClassC.isEmpty())
    	
        ontologie.createClass(namespace + ClassC);
        
        
    	if ( !this.Prop.isEmpty())
        {ObjectProperty newProp = ontologie.createObjectProperty(namespace + this.Prop);
    	
    	if( !this.Dom.isEmpty())
{
       OntClass Domain = ontologie.getOntClass(namespace + this.Dom);
   
        // set Domain and Rang
       newProp.setDomain(Domain);

        if(Ranj != "no" && Ranj!=" " )
        {
            OntClass Rang = ontologie.getOntClass(namespace + this.Ranj);
            newProp.setRange(Rang);
        }}
    }}

    protected void CreateNewIndiv(String ClassName, String Indiv)
    {
        //FIXME alignment is bugged because of individuals
        OntClass Cname = ontologie.getOntClass(namespace + ClassName);
        ontologie.createIndividual(namespace + Indiv, Cname);
    }
    protected void CreateDataTypeProp(String PropName, String ClassNameN, String Value)
    {
      
    	OntClass Cname = ontologie.getOntClass(namespace + ClassC);
    	DatatypeProperty prop= ontologie.createDatatypeProperty(namespace + PropName);
    	System.out.println(" Value"+Value);
    	Cname.addProperty(prop, Value,"xsd:string");
    	prop.setRange(Cname);
       
    }
    

    //left out comments are legacy ways of doing, soon to be deleted
    public void persist(String fileName)
    {
        FileOutputStream fichierSortie = null;

        try
        {
            fichierSortie = new FileOutputStream(new File(fileName));
        }
        catch(FileNotFoundException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


        ontologie.write(fichierSortie);
        try {
			fichierSortie.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        /*GraphTripleStore graph = new GraphTripleStore(null);

        Dataset dataset = TDBFactory.createDataset("uploads/dataset");
        dataset.begin(ReadWrite.WRITE);
        dataset.addNamedModel("foo", ontologie);
        dataset.commit();
        dataset.end();
        try
        {
            for(Statement s : ontologie.listStatements(null, null, (RDFNode)null).toList())
            {
            	fichierSortie.write(s.toString().getBytes());
            	fichierSortie.write("\n".getBytes());
                Triple T = new Triple(s.getObject().asNode(), s.getPredicate().asNode(), s.getResource().asNode());

            	graph.add(T);
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }*/

    }
    public void persistFile(String fileName)
    {
    	
        FileOutputStream fichierSortie = null;

        try
        {
            fichierSortie = new FileOutputStream(new File(fileName));
        }
        catch(FileNotFoundException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


        ontologie.write(fichierSortie);
        try {
			fichierSortie.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }


	protected void convert() {
		// TODO Auto-generated method stub

	}

}
