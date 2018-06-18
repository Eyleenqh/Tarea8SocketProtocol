/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Steven
 */
public class XMLStudentManager {

    //variables
    private Document document;
    private Element root;
    private String path;
    private boolean successAddSon;
    
    public XMLStudentManager(String path) throws JDOMException, IOException {
        this.path = path;
        
        File fileStudent = new File(this.path);
        if (fileStudent.exists()) {
            //1. El archivo ya existe, entonces lo cargo en memoria
            //toma la estructura de datos y la carga en memoria
            SAXBuilder saxBuilder = new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true); //ignora espacios en blanco

            //cargar en memoria
            this.document = saxBuilder.build(this.path);
            this.root = this.document.getRootElement();
        } else {
            //2. No existe el documento, entonces lo creo y luego lo cargo en memoria
            //creamos el elemento raiz
            this.root = new Element("Student");
            this.root.setAttribute("studentId", "num");

            //creamos el documento
            this.document = new Document(this.root);

            //guardamos el documento
            storeXML();
        }
    }//end method

    //almacena en disco duro nuestro documento XML en la ruta especificada
    private void storeXML() throws FileNotFoundException, IOException {
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(this.document, new PrintWriter(this.path));
    }//end method

    //metodo para insertar una persona nueva
    public boolean insertPerson(Element e) throws IOException {
        Element eStudent = new Element("student");
        eStudent.setAttribute("studentId", e.getAttributeValue("studentId"));
        if (insertSon(e.getAttributeValue("studentId"))) {
            return false;
        } else {
            //crear el nombre
            Element eName = new Element("name");
            eName.addContent(e.getChildText("name"));
            
            Element eLastName = new Element("lastname");
            eLastName.addContent(e.getChildText("lastname"));
            
            Element eAge = new Element("age");
            eAge.addContent(e.getChildText("age"));
            
            Element eTown = new Element("town");
            eTown.addContent(e.getChildText("town"));
            
            Element admissionGrade = new Element("admissionGrade");
            admissionGrade.addContent(e.getChildText("admissionGrade"));
            
            eStudent.addContent(eName);
            eStudent.addContent(eLastName);
            eStudent.addContent(eAge);
            eStudent.addContent(eTown);
            eStudent.addContent(admissionGrade);

            //agregar a root
            root.addContent(eStudent);
            //guarde todo
            storeXML();
            return true;
        }
    }

    //agrega hijos
    public boolean insertSon(String identification) throws IOException {
        this.successAddSon = false;
        search(this.root, String.valueOf(identification));
        return this.successAddSon;
    }

    //busca el id del padre
    public void search(Element parent, String id) throws IOException {
        //se pregunta si es el id que busco
        String prueba = parent.getAttributeValue("studentId");
        if (((String) (parent.getAttributeValue("studentId"))).equals(id)) {
            //agrega el contenido al elemento
            this.successAddSon = true;
            
        } else {
            //si no lo encuentra, retorne cada uno de los hijos
            List childrenList = parent.getChildren("student");
            if (childrenList.size() > 0) {
                for (Object object : childrenList) {
                    Element child = (Element) object;
                    search(child, id);
                }
            }
        }
    }
    
    public String retrieve(String value) throws IOException {
        List listElementos = this.root.getChildren();
        int cont = 0;
        for (Object objectActual : listElementos) {
            Element elementoActual = (Element) objectActual;
            if (elementoActual.getAttributeValue("studentId").equals(value) || elementoActual.getChildText("name").equals(value)) {
                //se concatena la informcaion de un estudiante
                String sStudent = elementoActual.getAttributeValue("studentId") + "---" + elementoActual.getChildText("name")
                        + "---" + elementoActual.getChildText("lastname") + "---" + elementoActual.getChildText("age") + "---"
                        + elementoActual.getChildText("town") + "---" + elementoActual.getChildText("admissionGrade");
                return sStudent;
            }
        }
        return null;
    }
    
    public boolean delete(String identification) throws IOException {
        List listElementos = this.root.getChildren();
        int cont = 0;
        for (Object objectActual : listElementos) {
            Element elementoActual = (Element) objectActual;
            if (elementoActual.getAttributeValue("studentId").equals(identification)) {
                this.root.removeContent(cont);
                storeXML();
                return true;
            }
            cont++;
        }
        return false;
    }
    
    public boolean update(Element e) throws IOException {
        List listElementos = this.root.getChildren();
        int cont = 0;
        for (Object objectActual : listElementos) {
            Element elementoActual = (Element) objectActual;
            if (elementoActual.getAttributeValue("studentId").equals(e.getAttributeValue("studentId"))) {
                //valida si los nombres son diferentes y si lo son, le asigna el nuevo valor
                if (!e.getChildText("name").equals("-1")) {
                    Element eName = new Element("name");
                    eName.addContent(e.getChildText("name"));
                    elementoActual.removeChildren("name");
                    elementoActual.addContent(0, eName);
                }

                //valida si los apellidos son diferentes y si lo son, le asigna el nuevo valor
                if (!e.getChildText("lastname").equals("-1")) {
                    Element eLastName = new Element("lastname");
                    eLastName.addContent(e.getChildText("lastname"));
                    elementoActual.removeChildren("lastname");
                    elementoActual.addContent(1, eLastName);
                }

                //valida si las edades son diferentes y si lo son, le asigna el nuevo valor
                if (!e.getChildText("age").equals("-1")) {
                    Element eAge = new Element("age");
                    eAge.addContent(e.getChildText("age"));
                    elementoActual.removeChildren("age");
                    elementoActual.addContent(2, eAge);
                }

                //valida si las ciudades son diferentes y si lo son, le asigna el nuevo valor
                if (!e.getChildText("town").equals("-1")) {
                    Element eTown = new Element("town");
                    eTown.addContent(e.getChildText("town"));
                    elementoActual.removeChildren("town");
                    elementoActual.addContent(3, eTown);
                }

                //valida si las notas son diferentes y si lo son, le asigna el nuevo valor
                if (!e.getChildText("admissionGrade").equals("-1.0")) {
                    Element eAdmissionGrade = new Element("admissionGrade");
                    eAdmissionGrade.addContent(e.getChildText("admissionGrade"));
                    elementoActual.removeChildren("admissionGrade");
                    elementoActual.addContent(4, eAdmissionGrade);
                }
                storeXML();
                return true;
            }
            cont++;
        }
        return false;
    }
}
