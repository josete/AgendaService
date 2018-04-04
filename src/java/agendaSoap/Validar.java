/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendaSoap;

import clases.Agenda;
import clases.Persona;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Validator;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import metodos.ImportarExportar;
import org.xml.sax.SAXException;

/**
 *
 * @author Portatil
 */
@WebService(serviceName = "Agenda")
public class Validar {

    @WebMethod(operationName = "validarAgenda")
    public boolean validarAgenda(File a) {
        File schemaFile = new File("validador.xsd");
        /*ImportarExportar i = new ImportarExportar("agenda.xml");
        File f = i.guardar(a);*/
        Source xmlFile = new StreamSource(a);
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(schemaFile);
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
            return true;
        } catch (SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @WebMethod(operationName = "devolverAgenda")
    public Agenda devolverAgenda(){
        ImportarExportar i = new ImportarExportar("agenda.xml");
        Agenda a = i.cargar();
        return a;
    }
    
    @WebMethod(operationName = "devolverPersona")
    public Persona devolverPersona(String nombre){
        ImportarExportar i = new ImportarExportar("agenda.xml");
        Agenda a = i.cargar();
        Persona p = null;
        for(Persona pe : a.getPersonas()){
            if(pe.getNombre().equals(nombre)){
                p = pe;
                break;
            }
        }
        return p;
    }
    
    @WebMethod(operationName = "insertarPersona")
    public void insertarPersona(Persona persona){
        ImportarExportar i = new ImportarExportar("agenda.xml");
        Agenda a = i.cargar();
        a.anadirPersona(persona);
        i.guardar(a);
    }
}
