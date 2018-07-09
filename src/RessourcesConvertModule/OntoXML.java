package RessourcesConvertModule;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class OntoXML extends OntoResModel{


	org.jdom2.Document document;
    Element racine ;
    
    public OntoXML(String namespace, String path)
    {
        super(namespace);
         setFileURI(path);
         try {
			afficherElement();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
 
            }

    public void lireFichier() throws JDOMException, IOException{
        SAXBuilder sxb=new SAXBuilder();
        document=sxb.build(new File(getFileURI()));
        racine=document.getRootElement();
        System.out.println(racine);

    }
    public void afficherElement() throws JDOMException, IOException{
        lireFichier();
       

        List listFilsDirect =racine.getChildren();
        Iterator i= listFilsDirect.iterator();
         String str = "";

        while(i.hasNext()){
            Element courant = (Element) i.next();
            while(!str.equals(courant.toString())){
                str=courant.toString();
            System.out.println("..........ici on affichera les tourist Data..........");
            System.out.println("le fils direct est:"+courant);
            List listChildren=courant.getChildren();
            if( listChildren!=null){
                Iterator j = listChildren.iterator();
                        while(j.hasNext()){
                            Element filsCourant=(Element)j.next();
                            System.out.println("le fils du fils DataTourist : "+filsCourant);

                              CreateNewSClass(namespace+filsCourant.getName());
                              List listChildrenOfChildren =filsCourant.getChildren();
                         
                              if(listChildrenOfChildren !=null){
                                  Iterator k=listChildrenOfChildren.iterator();
                                   while(k.hasNext()){
                                       Element FilsFilsCourant=(Element)k.next();
                                       
                                   CreateNewClass(namespace+FilsFilsCourant.getName(),namespace+FilsFilsCourant.getName()+"_Attribute",namespace+FilsFilsCourant.getName(),namespace+filsCourant.getName());
                                       
    
                                  
                                       System.out.println(" le petit fils est : "+ FilsFilsCourant.getName());
                                       List listChildrenOfChildrenc =FilsFilsCourant.getChildren();
                                       if(listChildrenOfChildrenc !=null){
                                    	   Iterator T=listChildrenOfChildrenc.iterator();
                                           while(T.hasNext()){
                                        	   Element FilsFilsFilsCourant=(Element)T.next();
                          
                                            System.out.println("le sous fils du fils direct posséde un attribut "+FilsFilsFilsCourant.getName()+"_Attribute");
                                            CreateNewClass(namespace+FilsFilsFilsCourant.getName(),namespace+FilsFilsFilsCourant.getName()+"Attribute",namespace+FilsFilsFilsCourant.getName(),namespace+FilsFilsCourant.getName());
                                        
                                        }
                                   }

                              }



                        }
            }
            }

        }
        }
      
    }


    public static void main(String[] args) throws JDOMException, IOException {
       OntoXML OX= new OntoXML("http://www.ontologie.fr/monxmlfaculties#","Ressources/Documents/output.xml");
       OX.afficherElement();
       OX.persistFile("Ressources/Reseau/Faculties.owl");

    }

}


