package RessourcesConvertModule;
import java.sql.*;
import java.util.*;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.CardinalityRestriction;


/*
 * Explanation the rules:
 *  For each table of the bdd, we create a concept with the same name.
 *  For each foreign key, we create a "foreignKey" property to link the two concepts"
 *  For each column which is not a primary key or a foreign key, we create a new concept and create a "has" property with the concept of the corresponding table
 */

public class OntoDB extends OntoResModel
{
    String url;
    String user;
    String passwd;

    public OntoDB(String namespace, String url, String user, String passwd)
    {
        super(namespace);
        this.url = url;
        this.user =  user;
        this.passwd = passwd;
        setFileURI(url);
    }

    public void convert()
    {

        // Connection to the database + retrieval of the metadata
        try
        {
            // Connection to mysql database
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver O.K.");

            Connection conn = DriverManager.getConnection(url, user, passwd);
            System.out.println("Effective connection !");
            DatabaseMetaData dmd = conn.getMetaData();

            // First we create the individual representing the database
            OntClass dbClass = ontologie.createClass(namespace + "database");
            ontologie.createIndividual(namespace + url, dbClass);

            ObjectProperty isInDB = ontologie.createObjectProperty(namespace + "isIn");
            CardinalityRestriction restriction = ontologie.createCardinalityRestriction(null, isInDB, 1);
            isInDB.setRange(dbClass);


            // Next we create the concepts related to tables
            ResultSet tables = dmd.getTables(conn.getCatalog(), null, "%", null);

            while(tables.next())
            {
                // For each table, we create a concept in the ontology
                String nameTable = tables.getString("TABLE_NAME");
                OntClass tableClass = ontologie.createClass(namespace + nameTable);

                tableClass.setSuperClass(restriction);
            }

            // We will store all key names in a list in order to deduce which column is not a key
            List<String> list_keys = new ArrayList<String>();

            // Next we can create other concepts and properties
            tables.first();
            tables.previous();

            while(tables.next())
            {
                String nameTable = tables.getString("TABLE_NAME");

                // Primary keys
                ResultSet pKeys = dmd.getPrimaryKeys(conn.getCatalog(), null, nameTable);

                while(pKeys.next())
                {
                    String nomPrimaryKey = pKeys.getString("COLUMN_NAME");
                    list_keys.add(nomPrimaryKey);
                }

                // Foreign keys
                ResultSet fKeys = dmd.getImportedKeys(conn.getCatalog(), null, nameTable);

                while(fKeys.next())
                {
                    String nomForeignKey = fKeys.getString("FKCOLUMN_NAME");
                    list_keys.add(nomForeignKey);

                    // For each foreign key, we create the "foreignKey" property
                    String nameForeignTable = fKeys.getString("PKTABLE_NAME");
                    OntClass foreign = ontologie.getOntClass(namespace + nameForeignTable);
                    String nameCurrentTable = fKeys.getString("FKTABLE_NAME");
                    OntClass current = ontologie.getOntClass(namespace + nameCurrentTable);

                    ObjectProperty fkProp = ontologie.createObjectProperty(namespace +
                                            nameCurrentTable + "Fk" + nameForeignTable);
                    fkProp.setDomain(current);
                    fkProp.setRange(foreign);
                }
            }

            // Finally, for each column which is not a key,  we use create a "has" property
            tables.first();
            tables.previous();

            while(tables.next())
            {
                String nameTable = tables.getString("TABLE_NAME");
                ResultSet columns = dmd.getColumns(conn.getCatalog(), null, nameTable, "%");

                while(columns.next())
                {
                    String columnName = columns.getString("COLUMN_NAME");

                    if(!list_keys.contains(columnName))
                    {
                        OntClass current = ontologie.getOntClass(namespace + nameTable);
                        OntClass col = ontologie.createClass(namespace + nameTable + "_" + columnName);
                        ObjectProperty colProp = ontologie.createObjectProperty(
                                                     namespace + nameTable + "Col" + columnName);
                        colProp.setDomain(current);
                        colProp.setRange(col);
                    }
                }
            }

            System.out.println("Ontology creation succeed !!!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
    public static void main(String[] args)
	{ 
    	OntoDB N= new OntoDB("http://www.ontologie.fr/mydatabase#","jdbc:mysql://localhost/krf_store","root","Admin2018");
    	N.convert();
    	N.persist("Ressources/Reseau/DB.owl");
	}
   
}





