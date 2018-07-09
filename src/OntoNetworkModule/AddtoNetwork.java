package OntoNetworkModule;
import java.io.File;
import java.io.FileWriter;

import aml.AML;


public class AddtoNetwork {

	public static String sourcePath = "Ressources/Profile/GSEMOnto.owl";
	public static String targetPath;
	public static OntologiesNetwork N,N2;
	public static String referencePath = "";
	public static String outputPath = "";
	public static FileWriter F ;
	public static File repertoire = new File("Ressources/Reseau");
	public static File[] files=repertoire.listFiles();
	public static File repertoireProfile = new File("Store/results/ProfileAlignments/");
	public static File[] filesprofile=repertoireProfile.listFiles();
	public static File repertoirereseau = new File("Store/results/NetworkAlignements/");
	public static File[] filesreseau=repertoirereseau.listFiles();
	public static AML aml ,aml2;

	public void AlignwithProfile (String Target, String PathTarget) throws Exception
	{
		 aml = AML.getInstance();
		 N= new OntologiesNetwork();
		System.out.println("Mapping with Profile");
		targetPath=Target;

		aml.openOntologies(sourcePath, Target);
		aml.matchAuto();
		

		/*Enregistrer l'alignement dans un fichier */
		 outputPath= "Store\\results\\ProfileAlignments\\"+filesprofile.length+".rdf";
		 F = new FileWriter(new File(outputPath));
		 if(!outputPath.equals(""))
			aml.saveAlignmentRDF(outputPath,sourcePath, Target);

		N.Ajouter(N.createCouple(sourcePath, aml.getSource().getURI(), Target, aml.getTarget().getURI(), PathTarget, outputPath));
		aml.closeAlignment();
		aml.closeOntologies();
	}

	public static void AlignwithNetwork () throws Exception
	{String Target,PathTarget;
		 N2= new OntologiesNetwork();
		 aml2 = AML.getInstance();
		System.out.println("Alignement avec le reseau");

		    String S="Store\\results\\NetworkAlignments\\";
			File file = new File(S);
			if (file.mkdir())
                System.out.println("Ajout du dossier : " + file.getAbsolutePath());


		int  k=0,Jk=0;
		int t=filesreseau.length;
		boolean appendrdf=true;
		
		int size=0;
		for (int i=0; i<files.length;i++)
		
			for (int j=0; j<files.length && j!=i;j++)
			
			{
					Target=files[j].toString();
					PathTarget=files[j].toString();
			
		aml2.openOntologies(files[i].toString(),Target);
		aml2.matchAuto();
        t++;
		outputPath= S+ t+ ".rdf";
		F = new FileWriter(new File(outputPath));

		if (aml2.getAlignment().size()!=0)
			{
			aml2.saveAlignmentRDF(outputPath,files[i].toString(),Target);
		    N2.Ajouter(N2.createCouple(files[i].toString(), aml2.getSource().getURI(),Target, aml2.getTarget().getURI(), PathTarget, outputPath));
			System.out.println("Alignement SAVED");
			
			System.out.println("Evaluation    "+aml2.getAlignment().size()+"   Output: "+outputPath );
			size=size+aml2.getAlignment().size();
			Jk++;
			}
		else
			System.out.println("SourceOK"+aml2.getSource().getURI());
		
				
				k++;
				aml2.closeAlignment();
				appendrdf=false;
				aml2.closeOntologies();
				System.out.println("i   "+i );
			}
		
		  
		
		
		System.out.println(" Nb Concept aliniée  "+ size + " Nb Alignement  "+k + "Nb Ressource Alignée"+ Jk);
		
	F.close();
	}
	
	public static void main (String args[])
	{
	
	
	


	
	
	
		try {
			AlignwithNetwork();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	

}
}
