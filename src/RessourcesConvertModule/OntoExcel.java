package RessourcesConvertModule;

import java.io.*;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.officeDocument.x2006.customProperties.CTProperty;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.POIXMLProperties;

public
class OntoExcel extends OntoResModel
{
	FileInputStream input_document = null;
	
	private XSSFWorkbook readMetadata;
	private static XSSFWorkbook workbook;

	public OntoExcel(String args, String namespace)
	{
		super(namespace);
		setFileURI(args);
		try {
			input_document = new FileInputStream(new File(args));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void MetadataExtractor() throws IOException
	{
		readMetadata           = new XSSFWorkbook(input_document);
		POIXMLProperties props = readMetadata.getProperties();
		POIXMLProperties.ExtendedProperties metadata= props.getExtendedProperties();
		POIXMLProperties.CoreProperties coreProp = props.getCoreProperties();
		/* Read and print core properties as SOP */
		setDocumentCreator(coreProp.getCreator());
        setCreationdate(coreProp.getCreated().toString());
        setLastModif     ( coreProp.getModified().toString());
        setLastModifC      ( coreProp.getLastModifiedByUser());
        setDescription     (coreProp.getDescription());
        setKeywords        (coreProp.getKeywords());
        setTitle           (coreProp.getTitle());
        setSubject         (coreProp.getSubject());
        setCategory        (coreProp.getCategory());
        /* Read and print extended properties */
		POIXMLProperties.ExtendedProperties extProp
			= props.getExtendedProperties();
        setCompany         (extProp.getUnderlyingProperties().getCompany());
    // setTemplate        = extProp.getUnderlyingProperties().getTemplate();;
        setManager         (extProp.getUnderlyingProperties().getManager());

		
		/* Finally, we can retrieve some custom Properies*/
		POIXMLProperties.CustomProperties custProp =
		props.getCustomProperties();
		List<CTProperty>
		my1=custProp.getUnderlyingProperties().getPropertyList();
		System.out.println("Size :" + my1.size());
		for (int i = 0; i < my1.size(); i++) {
		   CTProperty pItem = my1.get(i);
		   System.out.println(""+pItem.getPid());
		   System.out.println(""+pItem.getFmtid());
		   System.out.println(""+pItem.getName());
		   System.out.println(""+pItem.getLpwstr());
		}
		   
	}
	   protected void convert()
	    {
		   try {
			MetadataExtractor();
			ConvertMetaData();
			CellsRowsExtractor(getFileURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	
	    	
	    }

	public  void CellsRowsExtractor(String args) throws IOException
	{
		FileInputStream fis = new FileInputStream(new File(args));

		workbook              = new XSSFWorkbook(fis);
		
		
	
		XSSFSheet spreadsheet = workbook.getSheetAt(0);
		
		int lastcell = spreadsheet.getRow(0).getLastCellNum();
		int lastRow  = spreadsheet.getLastRowNum();
		// Non empty Last cell Number or index return
		for (int j = 0; j <= lastcell; j++)
		{
			
			try
			{
				CreateNewClass(spreadsheet.getRow(0)
						   .getCell(j)
						   .getRichStringCellValue()
						   .toString(), "has_Titles_Row0_Colum"+j, getTitle(),spreadsheet.getRow(0)
						   .getCell(j)
						   .getRichStringCellValue()
						   .toString());
				System.out.println(spreadsheet.getRow(0)
								   .getCell(j)
								   .getRichStringCellValue()
								   .toString());
			}
			catch(Exception e)
			{
			};
		}
		for (int j = 0; j <= lastRow; j++)
		{
			
			try
			{
				CreateNewClass(spreadsheet.getRow(j)
						   .getCell(1)
						   .getRichStringCellValue()
						   .toString(), "has_Titles_Row"+j+"_Colum1", getTitle(),spreadsheet.getRow(j)
						   .getCell(1)
						   .getRichStringCellValue()
						   .toString());
				System.out.println(spreadsheet.getRow(j)
								   .getCell(1)
								   .getRichStringCellValue()
								   .toString());
			}
			catch(Exception e)
			{
			};
		}
		
	    for(int i = 2; i <= lastcell; i++)
		for (int j = 1; j <= lastRow; j++)
		{
			
			try
			{CreateNewClass(spreadsheet.getRow(j)
					   .getCell(i)
					   .getRichStringCellValue()
					   .toString(), "has_Cells_Row"+j+"_Colum"+i, getTitle(),spreadsheet.getRow(j)
					   .getCell(i)
					   .getRichStringCellValue()
					   .toString());
				System.out.println(spreadsheet.getRow(j)
								   .getCell(i)
								   .getRichStringCellValue()
								   .toString());
			}
			catch(Exception e)
			{
			};
		}

		
		
		
		System.out.println("Footer "+spreadsheet.getFooter());
		System.out.println("Heasder "+spreadsheet.getHeader());
		System.out.println("Sname "+ spreadsheet.getSheetName());
		System.out.println("RepColums "+spreadsheet.getRepeatingColumns());
		System.out.println("j "+ spreadsheet.getRow(0).getLastCellNum());
		
		fis.close();
	}
	public static void main (String[] args)
	{
		OntoExcel OE= new OntoExcel("Ressources\\Documents\\PLANNING.xlsx","http://www.ontologie.fr/monexcelplan#");
		
		OE.convert();
		OE.persist("Ressources/Reseau/PLANNING.owl");
		
	}
}
