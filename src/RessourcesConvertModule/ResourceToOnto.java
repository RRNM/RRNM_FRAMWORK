package RessourcesConvertModule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import java.util.Scanner;

import org.jdom2.JDOMException;



public class ResourceToOnto
{
    /* type = resource type
     * path = resource path */
	int nextonto=0;
	File repertoire = new File("Ressources/Reseau");
	File[] files=repertoire.listFiles();
	String namespace,pathonto;
	 public OntoResModel onto = null;

	public int getnextonto()
	{

		return files.length+1;
	}

	public String getonto()
	{return pathonto;}


    public  OntoResModel RessourceToOntoExtract(String type, String path)
    {
   
    
    	
        if(type.equals("xlsx")||type.equals("csv"))
        {
             onto = new OntoExcel(namespace,
            		path);
           
           
        }
        
        else if(type.equals("Databases"))
        {
            try
            {
                Scanner scanner = new Scanner(new File(path));
                String url = "jdbc:mysql://" + scanner.nextLine();
                String userName = scanner.nextLine();
                String password = scanner.nextLine();
                scanner.close();

                onto = new OntoDB(namespace,
                                         url,
                                         userName,
                                         password);
                
              
               

            }
            catch(FileNotFoundException e)
            {
                e.printStackTrace();
          
               
            }
           
        }
        else if(type.equals("html"))
        {
            onto = new OntoHTML(namespace,
                                         path);
          
            
      
            return onto;
        }
        else if(type.equals("WEBSERVICE"))
        {
             onto = new OntoWebService(namespace,
                    path);
         
           
            
        }
        else if (type.equals("XML"))
        		{
        	 onto =new OntoXML(namespace,
                    path);
        	
            
          
        		}
        else
        {
        	System.out.print("USAGE :\nResourceToOnto TYPE FILE_PATH\n\tTYPE : --excel|--db|--html|--service|--XML");
        	onto=null;
        	
        }
        
        
		return onto;
		
    }
    
    public void RessourceToOntoConvert(String type, String path, int j)
    {
    	int next= getnextonto()+j;
    	namespace ="http://www.ontologie.fr/monOntologie"+next+"#";
        pathonto= "Ressources\\Reseau\\monOntologie"+next+".owl";
    	if (onto!=null)
        {onto.convert();
        onto.persist(pathonto);
        }
    }
    }









