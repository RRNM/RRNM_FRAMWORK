package GUIJavaFX;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.lang.model.util.Elements;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import OntoNetworkModule.OntologiesNetwork;
import RessourcesConvertModule.*;
import aml.AML;
import aml.match.Alignment;
import aml.ontology.Ontology2Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Utils {

	String[] pert = new String[100];
	Integer[] pert2 = new Integer[100];
	Onto SO = new Onto("http://unige/owl/Requette#");
	int i, j;
	AML aml, aml2, aml3;
	Set<Integer> T, S, SR, TR, TP;
	String check, pathQuerry, pathAns, pathnet, Ont2, profilePath, Ont3;
	OntologiesNetwork ON;
	public JTable table;
	File repertoireProfile, Profile2Onto, QuerryRep, QuerryAns, repertoireReseau;
	File[] filesProfile, Profile2OntoR, FileQuerryRep, FileQuerryAns, filesReseau;
	ObservableList<Ontology> ontologies = FXCollections.observableArrayList();
	Ontology2Match O = null ;

	/**
	 * Search for ontologies - Here instead of returning a JTable, an observable
	 * list of ontology objects is returned. It's used later in building the
	 * table of the Search interface.
	 * 
	 * @param query
	 * @return ObservableList<Ontology> object
	 * @throws Exception
	 */
	Utils() {
		i = 0;
		j = 0;
		repertoireProfile = new File("Store/results/ProfileAlignments/");
		Profile2Onto = new File("Ressources/Profile");
		QuerryRep = new File("Store/Querry/RepQuerryCons/");
		QuerryAns = new File("Store/Querry/QuerryAns/");
		repertoireReseau = new File("Store/results/NetworkAlignements/");

		filesProfile = repertoireProfile.listFiles();
		filesReseau = repertoireReseau.listFiles();
		Profile2OntoR = Profile2Onto.listFiles();
		FileQuerryRep = QuerryRep.listFiles();
		FileQuerryAns = QuerryAns.listFiles();

		pathQuerry = "store/Querry/RepQuerryCons/Querry" + FileQuerryRep.length + ".owl";
		pathAns = "store/Querry/QuerryAns/AnsQuerry" + FileQuerryAns.length + ".rdf";
		System.out.println(pathQuerry);
		System.out.println(pathAns);
		profilePath = "Ressources/Profile/GSEMOnto.owl";
		

	}

	public void FromKeystoCons(String Querry, String Path, String Lang) {
		StringTokenizer querry = new StringTokenizer(Querry, ";");

		while (querry.hasMoreTokens()) {
			check = querry.nextToken();
			SO.CreateNewSClass(check);
			System.out.println(check);
		}
		SO.persistFile(Path);
		AlignOnto(profilePath, Path, pathAns);
	}

	public void AlignOnto(String Pathsrc, String Pathtarg, String Pathal) {
		System.out.println("pathsource=" + Pathsrc);
		System.out.println("pathtarg=" + Pathtarg);
		System.out.println("pathal=" + Pathal);
		aml = AML.getInstance();
		try {
			aml.openOntologies(Pathsrc, Pathtarg);
			aml.defaultConfig();
			aml.matchManual();
			aml.saveAlignmentRDF(Pathal, Pathsrc, Pathtarg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ObservableList<Ontology> Search(String Querry, String Lang) throws Exception {

		FromKeystoCons(Querry, pathQuerry, Lang);
		System.out.println("file lenth" + filesProfile.length);
		i = 0;
		j = 0;
		ON = new OntologiesNetwork();
		List<String> listTemp = new ArrayList<String>();
		List<String> listTemp2 = new ArrayList<String>();
		List<String> listTemp3 = new ArrayList<String>();
		Set<String> al;
		Set<Integer> b = null;
		List<String> Instance2 =  new ArrayList<String>();
		List<String> Instance3 =  new ArrayList<String>();
		List<String> Instance =  new ArrayList<String>();
		

		if (aml.getAlignment().isEmpty()) {
			ontologies.add(new Ontology("No match found"));
			System.out.println("no match found");

		} else {
			TP = aml.getAlignment().getSources();
			System.out.println(" / size " + TP.size());
			
			for (Integer p : TP) {
				Instance.add(aml.getInstance().getSource().getName((p)));
				System.out.println(" Instance1 source      " + aml.getInstance().getSource().getName((p)));
				System.out.println(" Instance1 Target     " + aml.getInstance().getTarget().getName((AML.getInstance().getAlignment().getBestSourceMatch(p))) );
			}
			
		

			// Step1: find all the resources linked directly to the profile
			// ontology
	while (i != filesProfile.length) {
				System.out.println(" Step1    :---------");
				aml2 = AML.getInstance();
				pathnet = filesProfile[i].toString();

				System.out.println("pathnet --------------: " + filesProfile[i].toString());
				Ont2 = ON.GetOnto2("AL", filesProfile[i].toString());

				aml2.openOntologies("Ressources/Profile/GSEMOnto.owl", Ont2);
				aml2.openAlignment(filesProfile[i].toString());

				TR = aml2.getAlignment().getSources();
				System.out.println(" / size " + TR.size());

				for (Integer s : TR) {
					if (aml2.getAlignment() != null && Instance.contains(aml2.getInstance().getSource().getName((s)))) {
						String A = ON.GetSourcePath(aml2.getTarget().getURI());
						System.out.println("contain: -----------------------"+ A+ "      Instance Source 1" + aml2.getInstance().getSource().getName((s)));

						if (!listTemp.contains(A) && !A.equals("")) {
							ontologies.add(new Ontology(A));
							 O = new Ontology2Match(A);
							 for (Integer l : O.getObjectProperties())
								 for (Integer m : O.getObjectProperty(l).getRange())
									 if ( O.getName(m).equals(aml2.getInstance().getSource().getName((s))))
										 System.out.println("prop:--"+O.getName(l));

							 //System.out.println("Name: --"+	O.getName(O.getIndex(aml2.getInstance().getSource().getName(s))));
							Instance2.add( aml2.getInstance().getTarget().getName((AML.getInstance().getAlignment().getBestSourceMatch(s)))) ;
							System.out.println("contain: -------- Instance Source 2 "+ aml2.getInstance().getTarget().getName((AML.getInstance().getAlignment().getBestSourceMatch(s))));
							listTemp.add(A);
							listTemp2.add(aml2.getInstance().getTarget().getName((AML.getInstance().getAlignment().getBestSourceMatch(s))));
							listTemp3.add(aml2.getInstance().getSource().getName((s)));
							

						}
					}
				
						System.out.println("2end step .......................................... ");
						// step2: Find the resources linked to the ones
						// resulting from step 1
						while (j != filesReseau.length) {
							System.out.println(" Step2    :---------");
							aml3 = AML.getInstance();
							System.out.println("pathnet2= " + filesReseau[j].toString());
							Ont3 = ON.GetOnto2("AL", filesReseau[j].toString());
							System.out.println("Onto3 = " + Ont3);
							System.out.println("On = " + ON.K);
							if (ON.K > 0) {
								aml3.openOntologies(Ont2, Ont3);
								aml3.openAlignment(filesReseau[j].toString());
								TR = aml3.getAlignment().getSources();
							

								for (Integer k : TR)
								{
									if (aml3.getAlignment() != null && Instance2.contains(aml3.getInstance().getSource().getName((k)))) {
										String A = ON.GetSourcePath(aml3.getTarget().getURI());
										System.out.println("Step 2 contain: -------------------------------" + A + "  Instance2   " + aml3.getInstance().getSource().getName((k)));

										if (!listTemp.contains(A) && !A.equals("")) {
											ontologies.add(new Ontology(A));
											O = new Ontology2Match(A);
											 for (Integer l : O.getObjectProperties())
												 for (Integer m : O.getObjectProperty(l).getRange())
													 if ( O.getName(m).equals(aml2.getInstance().getSource().getName((s))))
														 System.out.println("prop:--"+O.getName(l));
											listTemp.add(A);
											listTemp2.add(aml2.getInstance().getTarget().getName((AML.getInstance().getAlignment().getBestSourceMatch(k))));
											listTemp3.add(aml3.getInstance().getSource().getName((k)));

									}
							}
								}
							}
							j++;
							aml3.closeAlignment();
							aml3.closeOntologies();
						
								}
				

				}
				

			
				i++;
				aml2.closeAlignment();
				aml2.closeOntologies();

		}
			
	

		/*	System.out.println("Document      " + listTemp.get(i)); 
		
		
		for (int j = 0; j < listTemp2.size(); j++)
			System.out.println(listTemp2.get(j) + "   Class Source  ");
		for (int j = 0; j < listTemp3.size(); j++)
			System.out.println(listTemp3.get(j) + "   Class Target ");*/
		}
		//
		//for (int i = 0; i < listTemp.size(); i++)
			

		return ontologies;
	}

}
