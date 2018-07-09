package SearchModule;

import java.io.File;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import GUIJavaFX.Ontology;
import OntoNetworkModule.OntologiesNetwork;
import RessourcesConvertModule.*;
import aml.AML;
import aml.ontology.Ontology2Match;

public class SearchOnto {

	String[] pert = new String[100];
	Integer[] pert2 = new Integer[100];
	Onto SO = new Onto("http://unige/owl/Requette#");
	int i, j;
	AML aml ,aml2;
	Set<Integer> T, S, SR, TR;
	String check,pathQuerry,pathAns,pathnet,Ont2;
	OntologiesNetwork ON;
	public JTable table;
	File repertoire,Profile2Onto,QuerryRep,QuerryAns;
	File[] files,Profile2OntoR,	FileQuerryRep, FileQuerryAns;

	SearchOnto()
	{i=0;
	j=0;
		table = new JTable();
	table.setModel(new DefaultTableModel(
			new Object[][] {

			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		));
	           repertoire = new File("Store//results//NetworkAlignements//");
			   Profile2Onto = new File("Ressources/Profile");
			   QuerryRep=new File("Store/Querry/RepQuerryCons/");
			   QuerryAns=new File("Store/Querry/QuerryAns/");

			    files = repertoire.listFiles();
				Profile2OntoR = Profile2Onto.listFiles();
				FileQuerryRep=QuerryRep.listFiles();
			    FileQuerryAns=QuerryAns.listFiles();

			pathQuerry="Store/Querry/RepQuerryCons/Querry"+FileQuerryRep.length+".owl";
			pathAns="Store/Querry/QuerryAns/AnsQuerry"+FileQuerryAns.length+".owl";
			table.getColumnModel().getColumn(1).setPreferredWidth(500);
	}


	public void FromKeystoCons (String Querry, String Path, String Lang)
	{StringTokenizer querry = new StringTokenizer(Querry,";");

		while (querry.hasMoreTokens()) {
			check = querry.nextToken();
			SO.CreateNewSClass(check);
			System.out.println(check);
		}
		SO.persistFile(Path);
	}

	public void AlignOnto (String Pathsrc, String Pathtarg, String Pathal)
	{
		try {
			aml.openOntologies(Pathsrc, Pathtarg);
			aml.matchAuto();
			aml.saveAlignmentRDF(Pathal,Pathsrc, Pathtarg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public JTable  Search (String Querry,String Lang) throws Exception {
		String A=null;
		FromKeystoCons(Querry,pathQuerry,Lang);
		AlignOnto(pathQuerry,"Ressources/Profile/GSEMOnto1.owl",pathAns);

			while (i != files.length) {
			aml2 = AML.getInstance();
			pathnet = files[i].getPath();
			ON = new OntologiesNetwork();
			Ont2 = ON.GetOnto2("AL", pathnet);

			if (Ont2 != null) {
				aml2.openOntologies("Ressources/Profile/onto1.owl", Ont2);
				aml2.openAlignment(pathnet);
				SR = aml2.getAlignment().getSources();
				TR = aml2.getAlignment().getTargets();
				System.out.println(SR.size() + " / " + TR.size());


				for (Integer s : TR) {
					if (aml.getAlignment().containsTarget(s))
					{
						if( !A.equals(aml2.getTarget().getURI()))
						{
							//ontologies.add(new Ontology(aml2.getTarget().getURI()));
						j++;
						A=aml2.getTarget().getURI();
						}
					}
					else
						System.out.println("Alignement avec : " + files[i].toString() + "NO");

				}
				aml.closeAlignment();
				aml.closeOntologies();
			}

			aml2.closeAlignment();
			aml2.closeOntologies();
			i++;
		}
			return table;
	}

}
