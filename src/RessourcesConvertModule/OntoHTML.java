package RessourcesConvertModule;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.html.HtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.apache.tika.parser.ParseContext;




public class OntoHTML extends OntoResModel
{
	


    public OntoHTML(String namespace, String FileHTML)
    {
        super(namespace);
        
      
        setFileURI(FileHTML);
       
 
            }

 
   

    @Override
    protected void convert()
    {
    	
    	Document doc;
 
    	String href="";
    	Elements Titles= new Elements();
    	Elements Hrefs= new Elements();
    	String Hello="";
    	List<String> Text = new ArrayList <String>();
   
	try {
			/*doc = Jsoup.connect(getFileURI())
				  .data("query", "Java")
				  .userAgent("Mozilla")
				  .cookie("auth", "token")
				  .timeout(6000)
				  .post();*/
			
			File input = new File(getFileURI());
		 doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

			
		  
		 setTitle(doc.title()) ;
		      setFileURI(doc.baseUri())  ;
		     setDocumentCreator(doc.ownerDocument().ownText());
		     
		   /*  Element link = doc.select("div").first();

		     String text = doc.body().text(); // "An example link"
		     String linkHref = link.attr("href"); // "http://example.com/"
		     String linkText = link.text(); // "example""

		     String linkOuterH = link.outerHtml(); 
		         // "<a href="http://example.com"><b>example</b></a>"
		     String linkInnerH = link.html(); // "<b>example</b 
		     System.out.println("href ="+link.attr("href"));
		       System.out.println("title ="+link.text());
		       System.out.println("linkOuterH ="+linkOuterH);
		       System.out.println("linkInnerH ="+linkInnerH);
		     //  System.out.println("text ="+text);
		     Elements content = doc.getElementsByAttribute("div");
		     for (Element cont:content)
				{
		     Elements links = cont.getElementsByTag("li");
		     for (Element link : links) {
		       String linkHref = link.attr("href");
		       String linkText = link.text();
		       System.out.println("href ="+link.attr("href"));
		       System.out.println("title ="+link.text());
		     }
		      */
		     
	
	
	
		
	
	
		    CreateNewSClass( getTitle());
		   

			Elements content = doc.getAllElements();
			int i=0;
			
			for (Element cont:content)
			{
			Elements links1 = cont.getElementsByTag("div");
			for (Element link : links1) {
				Elements Titels = link.getElementsByTag("a");
			       for(Element Title : Titels) {
			    	
			    	
			    	   if(!Title.text().isEmpty()&& !Titles.contains(Title.attr("title"))&&!Title.attr("title").contains("#"))
				    	{Hello= Title.text().replaceAll("[-,/&()יא #.'%!?;=]", "_");
				    	System.out.println("title ="+Title.text());
				    	CreateNewSClass(Hello);
				    	if(!Title.attr("href").isEmpty()&& !Hrefs.contains(Title.attr("href")))
				    	{String Hello2= Title.attr("href").replaceAll("[-,/&()יא #.'%!?;=]", "_");
				    	CreateDataTypeProp("href",Hello,Hello2);
				    	Hrefs.add(Title.getElementById("href"));
						System.out.println("href ="+Hello2);
				    	}
				    	
				  
				    	
				    	}
			           
			    	   
			    	
			    	{
			    		
					
					
				
					
			    	}
			    	
			    	
			    /*	if(!Title.text().isEmpty()&& !Title.text().contains("#"))
			    	{Hello= Title.text();
			    	 CreateDataTypeProp(Hello,"Has_link",Hello2);
			    	Text.add(Title.text());
			    	System.out.println("Text ="+Title.text());
			    	CreateNewClass(Hello, "has_Texts"+i, getTitle(),Hello);
			    	
			    	}*/
			       			
				  
				    i++; 
			       }
			}}}
		
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
		}
		
ConvertMetaData();
    	    
		
    }


    public static void main(String[] args)
{
    	
    	OntoHTML OH = new OntoHTML ("http://www.ontologie.fr/programmecours#","Ressources/Documents/Prog cours.htm");
    	OH.convert();
    	OH.persistFile("Ressources\\Reseau\\Programmecours.owl");
	}

  



    }


