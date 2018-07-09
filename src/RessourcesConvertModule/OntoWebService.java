package RessourcesConvertModule;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

import org.apache.jena.iri.impl.Main;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.CardinalityRestriction;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.mem.GraphTripleStore;
import org.apache.jena.graph.Triple;
import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.query.ReadWrite;

public class OntoWebService extends OntoResModel {

    String wsdlFileName;

    public OntoWebService(String namespace, String wsdlFileName) {
        super(namespace);
        this.wsdlFileName = wsdlFileName;
    }

    public void convert() {
        try {
            File wsdlFile = new File(wsdlFileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(wsdlFile);

            doc.getDocumentElement().normalize();

            // Creation of classes and properties

            // Name of the service
            OntClass wsClassName = ontologie.createClass(namespace + "webServiceName");

            // Parameters of the service
            OntClass inputParameter = ontologie.createClass(namespace + "inputParameter");
            OntClass outputParameter = ontologie.createClass(namespace + "outputParameter");
            ObjectProperty isInputParameterFrom = ontologie.createObjectProperty(namespace + "isInputParameterFrom");
            CardinalityRestriction restrictionInput = ontologie.createCardinalityRestriction(null, isInputParameterFrom, 1);
            isInputParameterFrom.setRange(wsClassName);
            ObjectProperty isOutputParameterFrom = ontologie.createObjectProperty(namespace + "isOutputParameterFrom");
            CardinalityRestriction restrictionOutput = ontologie.createCardinalityRestriction(null, isOutputParameterFrom, 1);
            isOutputParameterFrom.setRange(wsClassName);

            // Operations provided by the service
            OntClass operation = ontologie.createClass(namespace + "operation");
            ObjectProperty isOperationFrom = ontologie.createObjectProperty(namespace + "isOperationFrom");
            CardinalityRestriction restrictionOperation = ontologie.createCardinalityRestriction(null, isOperationFrom, 1);
            isOperationFrom.setRange(wsClassName);




            // First we get the name of the web service
            String wsName = doc.getDocumentElement().getAttribute("name");
            ontologie.createIndividual(namespace + wsName, wsClassName);

            Node root = doc.getDocumentElement();
            if(root.hasChildNodes()) {
                NodeList nList = root.getChildNodes();
                boolean isInput = true;
                for(int count = 0; count < nList.getLength(); count++) {
                    Node tempNode = nList.item(count);
                    if(tempNode.getNodeType() == Node.ELEMENT_NODE) {

                        // We get parameters
                        if(tempNode.getNodeName().equals("message")) {
                            NodeList nList2 = tempNode.getChildNodes();
                            if(isInput) {
                                for(int count2 = 0; count2 < nList2.getLength(); count2++) {
                                    if(nList2.item(count2).getNodeType() == Node.ELEMENT_NODE) {
                                        Element tempNode2 = (Element) nList2.item(count2);
                                        ontologie.createIndividual(namespace + tempNode2.getAttribute("name"), inputParameter);
                                    }
                                }
                            }
                            else {
                                for(int count2 = 0; count2 < nList2.getLength(); count2++) {
                                    if(nList2.item(count2).getNodeType() == Node.ELEMENT_NODE) {
                                        Element tempNode2 = (Element) nList2.item(count2);
                                        ontologie.createIndividual(namespace + tempNode2.getAttribute("name"), outputParameter);
                                    }
                                }
                            }
                            isInput = false;
                        }
                        if(tempNode.getNodeName().equals("portType")) {
                            NodeList nList2 = tempNode.getChildNodes();
                            for(int count2 = 0; count2 < nList2.getLength(); count2++) {
                                if(nList2.item(count2).getNodeType() == Node.ELEMENT_NODE) {
                                    Element tempNode2 = (Element) nList2.item(count2);
                                    ontologie.createIndividual(namespace + tempNode2.getAttribute("name"), operation);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("Ontologie creation succeed !!!");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}


