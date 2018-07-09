package RessourcesConvertModule;

import java.util.Set;
import java.util.StringTokenizer;

import org.apache.jena.ontology.ObjectProperty;

public class OntoResModel extends Onto{
	 public OntoResModel(String namespace) {
		super(namespace);
		// TODO Auto-generated constructor stub
	}
	String DocumentCreator;
     String Creationdate;
     String LastModif;
     String LastModifC;
     String Description;
     String Keywords;
     String Title;
     String Subject;
     String Category;
     String Company;
     String Manager;
     String Template;
     String ReturnMessage;
     String Language;
     String FileURI;
     Set<RessourceEntities> RE;
     Set <LinkEntities> LE;
     
     public String getFileURI() {
 		return  FileURI;
 	}
 	public void setFileURI(String FileUri) {
 		 FileURI = FileUri;
 	}
     
     
     public String getDocumentCreator() {
		return DocumentCreator;
	}
     public String getLanguage() {
 		return Language;
 	}
	public void setDocumentCreator(String documentCreator) {
		DocumentCreator = documentCreator;
	}
	public String getCreationdate() {
		return Creationdate;
	}
	public void setCreationdate(String creationdate) {
		Creationdate = creationdate;
	}
	public String getLastModif() {
		return LastModif;
	}
	public void setLastModif(String lastModif) {
		LastModif = lastModif;
	}
	public String getLastModifC() {
		return LastModifC;
	}
	public void setLastModifC(String lastModifC) {
		LastModifC = lastModifC;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getKeywords() {
		return Keywords;
	}
	public void setKeywords(String keywords) {
		Keywords = keywords;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getManager() {
		return Manager;
	}
	public void setManager(String manager) {
		Manager = manager;
	}
	
	public String getReturnMessage() {
		return ReturnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		ReturnMessage = returnMessage;
	}
	public String getTemplate() {
		return Template;
	}
	public void setTemplate(String template) {
		Template = template;
	}
	public void setLanguage(String Language) {
		Language = Language;
	}
	public String convertUri()
    {
    	StringTokenizer NewFileURI = new StringTokenizer(FileURI,"\\");
    	String next="";
    	CreateNewSClass("Document");
		while (	NewFileURI.hasMoreTokens())
			next = NewFileURI.nextToken();

			
			System.out.println(next);
       return next;

		}
	public void ConvertMetaData()
	{
		String uriclass=convertUri().replaceAll("[-,/&()יא #.'%;!?]", "_");

		
		if (Title!=null)
    	{   Title=Title.replaceAll("[-,/&()יא #.'%;!?]", "_");
    	CreateNewClass(Title, "is_Title", Title,"Document");
    		System.out.println("DocumentCreator "+ Title);
    		CreateNewClass(uriclass, "is_Path", Title,"Document");	
    	
    	ObjectProperty colProp1 = ontologie.createObjectProperty(
                 namespace +  "belongsto");
    	ObjectProperty colProp2 = ontologie.createObjectProperty(
                namespace +  "createdBy");
    	ObjectProperty colProp3 = ontologie.createObjectProperty(
                namespace +  "has_category");
    	ObjectProperty colProp4 = ontologie.createObjectProperty(
                namespace +  "hasdescription");
    	ObjectProperty colProp5 = ontologie.createObjectProperty(
                namespace +  "haskeywords");
    	ObjectProperty colProp6 = ontologie.createObjectProperty(
                namespace +  "hastitle");
    	ObjectProperty colProp7 = ontologie.createObjectProperty(
                namespace +  "is_a");
    	ObjectProperty colProp8 = ontologie.createObjectProperty(
                namespace +  "hassubject");
    	ObjectProperty colProp9 = ontologie.createObjectProperty(
                namespace +  "managedby");
    	colProp1.setDomain(ontologie.getOntClass(namespace + Title));

    	colProp2.setDomain(ontologie.getOntClass(namespace + Title));
    	colProp3.setDomain(ontologie.getOntClass(namespace + Title));
    	colProp4.setDomain(ontologie.getOntClass(namespace + Title));
    	colProp5.setDomain(ontologie.getOntClass(namespace + Title));
    	colProp6.setDomain(ontologie.getOntClass(namespace + Title));
    	colProp7.setDomain(ontologie.getOntClass(namespace + Title));
    	colProp8.setDomain(ontologie.getOntClass(namespace + Title));
    	colProp9.setDomain(ontologie.getOntClass(namespace + Title));


      if (DocumentCreator!=null)
    	{DocumentCreator=DocumentCreator.replaceAll("[-,/&()יא #.'%;!?]", "_");
    	
        	CreateNewSClass(DocumentCreator);
        	colProp2.setRange(ontologie.getOntClass(namespace + DocumentCreator));
        	System.out.println("DocumentCreator "+ DocumentCreator);

    	}
    	
    	if (Description!=null)
    	{
    		Description=Description.replaceAll("[-,/&()יא #.'%;!?]", "_");
    		CreateNewSClass(Description);
    		colProp4.setRange(ontologie.getOntClass(namespace + Description));
    	}
    	if (Subject!=null)
    	{Subject=Subject.replaceAll("[-,/&()יא #.'%;!?]", "_");
    		CreateNewSClass(Subject);
    		colProp8.setRange(ontologie.getOntClass(namespace +Subject));
    	}
    	if (Category!=null)
    	{Category=Category.replaceAll("[-,/&()יא #.'%;!?]", "_");
    		CreateNewSClass(Category);
    		colProp3.setRange(ontologie.getOntClass(namespace + Category));
    	}
    	if (Manager!=null)
    	{Manager=Manager.replaceAll("[-,/&()יא #.'%;!?]", "_");
    		CreateNewSClass(Manager);
    		colProp9.setRange(ontologie.getOntClass(namespace + Manager));
    	}
        if (Company!=null && !Company.isEmpty())
        {Company=Company.replaceAll("[-,/&()יא #.'%;!?]", "_");
        	CreateNewSClass(Company);
        	colProp1.setRange(ontologie.getOntClass(namespace + Company));
        }

if (Keywords!=null)
{
        StringTokenizer KeyWords = new StringTokenizer(Keywords," ");
        String check;

		while (KeyWords.hasMoreTokens()) {
			check = KeyWords.nextToken();
			CreateNewSClass(check);
			colProp5.setRange(ontologie.getOntClass(namespace + check));
			System.out.println(check);
		}
	}
    	}


    }
	}

