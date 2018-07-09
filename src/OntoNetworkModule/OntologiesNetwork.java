package OntoNetworkModule;
import org.xml.sax.*;
import org.xml.sax.helpers.LocatorImpl;
import aml.match.Alignment;
import aml.ontology.Ontology;
import java.io.*;
import java.util.Iterator;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;

public class OntologiesNetwork {

	 Ontology Onto1;
	 Ontology Onto2;
	 Alignment al;
	 String File;
	 Element racine;
	 Document document;
	public int K;


	void OntologiesNetwork()
	 {


	 }
	
	public Element createCouple(String O1, String U1, String O2, String U2,String S2, String AL)
	{
		Element couple = new Element("Couple");
		Element onto1 = new Element("Onto1");
	     Attribute path = new Attribute("Path",O1);
	     Attribute uri = new Attribute("uri",U1);
	     onto1.setAttribute(path);
	     onto1.setAttribute(uri);
	     Element onto2 = new Element("Onto2");
	     Attribute path2 = new Attribute("Path",O2);
	     Attribute uri2 = new Attribute("uri",U2);
	     Attribute SOU = new Attribute("source",S2);
	     onto2.setAttribute(path2);
	     onto2.setAttribute(uri2);
	     onto2.setAttribute(SOU);
	     Element A = new Element("AL");
	     Attribute pathal = new Attribute("Path",AL);
	    A.setAttribute(pathal);
	      couple.addContent(onto1);
	      couple.addContent(onto2);
	      couple.addContent(A);
	      return couple;
	}

	 boolean CreateOntoNetFile (Element C)
	 {
		 racine=new Element ("OntologiesNetwork");
		 document = new Document(racine);
		 racine.addContent(C);
	      affiche();
	      enregistre("store/results/exercice.xml");

		 return true;
	 }

	public void Ajouter (Element C) throws Exception
	 {
		 lireFichier("store/results/exercice.xml");
		 AjoueterElement(C);
		 enregistre("store/results/exercice.xml");

	 }

	 void affiche()
	 {
	    try
	    {
	       //On utilise ici un affichage classique avec getPrettyFormat()
	       XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	       sortie.output(document, System.out);
	    }
	    catch (java.io.IOException e){}
	 }
	 void enregistre(String fichier)
	 {
	    try
	    {
	       //On utilise ici un affichage classique avec getPrettyFormat()
	       XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	       //Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
	       //avec en argument le nom du fichier pour effectuer la sérialisation.
	       sortie.output(document, new FileOutputStream(fichier));
	    }
	    catch (java.io.IOException e){}
	 }
	  void lireFichier(String fichier) throws Exception
	   {
	      SAXBuilder sxb = new SAXBuilder();
	      document = sxb.build(new File(fichier));
	      racine = document.getRootElement();
	   }
	   String GetAl(String element1 , String att1 , String element2 , String att2)
	   {
	      //Dans un premier temps on liste tous les étudiants
	      List listEtudiant = racine.getChildren("Couple");
	      Iterator i = listEtudiant.iterator();
	      String A ="nop";
	      //On parcours la liste grâce à un iterator
	      while(i.hasNext())
	      {
	         Element courant = (Element)i.next();
	         //Si l’étudiant possède l'Element en question on applique
	         //les modifications.
	         if(courant.getChild(element1).getAttributeValue("Path") == att1 && courant.getChild(element2).getAttributeValue("Path") == att2 )

	        	A= courant.getChild("AL").getAttributeValue("Path");



	      }
	      return A;
	   }
	   String GetOnto1(String element1 , String att1)
	   {
		   try {
			lireFichier("store/results/exercice.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //Dans un premier temps on liste tous les couples
	      List listCouple = racine.getChildren("Couple");
	      Iterator i = listCouple.iterator();
	      String A ="";
	      //On parcours la liste grâce à un iterator
	      while(i.hasNext())
	      {
	         Element courant = (Element)i.next();
	         if(courant.getChild(element1).getAttributeValue("Path") == att1 )
	         A= courant.getChild("Onto1").getAttributeValue("Path");
	      }
	      return A;
	   }
	   
	   public String GetOnto2(String element1 , String att1)
	   { try {
			lireFichier("store/results/exercice.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //Dans un premier temps on liste tous les couples
	      List listCouple = racine.getChildren("Couple");
	      Iterator i = listCouple.iterator();
	      String A ="";
	      //On parcours la liste grâce à un iterator
	      K=0;
	      while(i.hasNext())
	      {
	    	  
	         Element courant = (Element)i.next();
	         //System.out.println(courant.getChild(element1).getAttributeValue("Path"));
	         if(courant.getChild(element1).getAttributeValue("Path").contentEquals(att1)  )
	         {
	         A= courant.getChild("Onto1").getAttributeValue("Path");
	         K++;
	         }
	      }
	     // System.out.println("A=  "+A);
	     // System.out.println("k=  "+K);
	      return A;
	   }
	   
	   
	   
	   public String GetOntoPath(String att1)
	   { try {
			lireFichier("store/results/exercice.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //Dans un premier temps on liste tous les étudiants
	      List listCouple = racine.getChildren("Couple");
	      Iterator i = listCouple.iterator();
	      String A ="";
	      //On parcours la liste grâce à un iterator
	      System.out.println("att1="+att1);
	      while(i.hasNext())
	      {
	    	  System.out.println("Dans la boucle");
	         Element courant = (Element)i.next();
	         if(courant.getChild("Onto2").getAttributeValue("uri").contentEquals(att1)  )
	         A= courant.getChild("Onto2").getAttributeValue("Path");
	      }
	      System.out.println("A="+A);
	      return A;
	   }
	   public String GetSourcePath(String att1)
	   { try {
			lireFichier("store/results/exercice.xml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      //Dans un premier temps on liste tous les étudiants
	      List listCouple = racine.getChildren("Couple");
	      Iterator i = listCouple.iterator();
	      String A ="";
	      //On parcours la liste grâce à un iterator
	      System.out.println("att1="+att1);
	      while(i.hasNext())
	      {
	    	  System.out.println("Dans la boucle");
	         Element courant = (Element)i.next();
	         if(courant.getChild("Onto2").getAttributeValue("uri").contentEquals(att1)  )
	         A= courant.getChild("Onto2").getAttributeValue("source");
	      }
	      System.out.println("A="+A);
	      return A;
	   }


	   void AjoueterElement(Element C)
	   {

		     racine.addContent(C);


	   }

	   String GetAlignmentfromSource ( String Path)
	   {

		   return Path;
	   }

	   String GetAlignmentfromTarget ( String Path)
	   {
		   return Path;
	   }


}
