/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import Domain.Student;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Steven
 */
public class Client {

    //atributos
    private InetAddress address;
    private Socket socket;
    public PrintStream send;
    private char petitionType;

    //constructor
    public Client() {
        try {
            //this.address = InetAddress.getByName("192.168.43.8");
            this.address = InetAddress.getLocalHost();
            this.socket = new Socket(this.address, 5025);
            this.send = new PrintStream(socket.getOutputStream());
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //metodo para agregar estudiantes
    public String create(Student newStudent) throws IOException {
        Element eStudent = new Element("student");
        //agregamos un atributo
        eStudent.setAttribute("studentId", newStudent.getIdentification());

        //crear el nombre
        Element eName = new Element("name");
        eName.addContent(newStudent.getName());

        //crear el apellido
        Element eLastname = new Element("lastname");
        eLastname.addContent(newStudent.getLastname());

        //crear la edad
        Element eAge = new Element("age");
        eAge.addContent(String.valueOf(newStudent.getAge()));

        //crear la ciudad
        Element eTown = new Element("town");
        eTown.addContent(newStudent.getTown());

        //crear el elemento nota
        Element eAdmissionGrade = new Element("admissionGrade");
        eAdmissionGrade.addContent(String.valueOf(newStudent.getAdmissionGrade()));

        //agregar al elemento student
        eStudent.addContent(eName);
        eStudent.addContent(eLastname);
        eStudent.addContent(eAge);
        eStudent.addContent(eTown);
        eStudent.addContent(eAdmissionGrade);

        XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
        String s = this.petitionType + "---" + outputter.outputString(eStudent);

        this.send.println(s);
        
        //Instancio un lector
        BufferedReader messageFromServer = new BufferedReader(
                new InputStreamReader(
                        this.socket.getInputStream()
                )
        );
        //leo el mensaje
        String infoResponse = messageFromServer.readLine();
        return infoResponse;
    }

    /////////////////////ESTOS 2 DEBEN DEVOLVER UN ESTUDIANTE///////////////////////////
    //metodo para buscar estudiantes por Id
    public Student idRetrieve(String id) throws IOException {
        String s = this.petitionType + "---" + id;
        this.send.println(s);

        //Instancio un lector
        BufferedReader messageFromServer = new BufferedReader(
                new InputStreamReader(
                        this.socket.getInputStream()
                )
        );
        //leo el mensaje
        String infoResponse = messageFromServer.readLine();
        //obtengo cada atributo del estudiante
        String studentInfo[] = infoResponse.split("---");
        Student searchedStudent = new Student(studentInfo[1], studentInfo[2], Integer.parseInt(studentInfo[3]), studentInfo[4], studentInfo[0], Float.parseFloat(studentInfo[5]));
        return searchedStudent;
    }

    //metodo para buscar estudiantes por nombre
    public Student nameRetrieve(String name) throws IOException {
        String s = this.petitionType + "---" + name;
        this.send.println(s);
        //Instancio un lector
        BufferedReader messageFromServer = new BufferedReader(
                new InputStreamReader(
                        this.socket.getInputStream()
                )
        );
        //leo el mensaje
        String infoResponse = messageFromServer.readLine();
        String studentInfo[] = infoResponse.split("---");
        //obtengo cada atributo del estudiante
        Student searchedStudent = new Student(studentInfo[1], studentInfo[2], Integer.parseInt(studentInfo[3]), studentInfo[4], studentInfo[0], Float.parseFloat(studentInfo[5]));
        return searchedStudent;
    }

    //metodo para actualizar la informacion de un estudiante
    public String update(Student targetStudent) throws IOException {
        Element eStudent = new Element("student");
        //agregamos un atributo
        eStudent.setAttribute("studentId", targetStudent.getIdentification());

        //crear el nombre
        Element eName = new Element("name");
        eName.addContent(targetStudent.getName());

        //crear el apellido
        Element eLastname = new Element("lastname");
        eLastname.addContent(targetStudent.getLastname());

        //crear la edad
        Element eAge = new Element("age");
        eAge.addContent(String.valueOf(targetStudent.getAge()));

        //crear la ciudad
        Element eTown = new Element("town");
        eTown.addContent(targetStudent.getTown());

        //crear el elemento nota
        Element eAdmissionGrade = new Element("admissionGrade");
        eAdmissionGrade.addContent(String.valueOf(targetStudent.getAdmissionGrade()));

        //agregar al elemento student
        eStudent.addContent(eName);
        eStudent.addContent(eLastname);
        eStudent.addContent(eAge);
        eStudent.addContent(eTown);
        eStudent.addContent(eAdmissionGrade);

        XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
        String s = this.petitionType + "---" + outputter.outputString(eStudent);

        this.send.println(s);
        //Instancio un lector
        BufferedReader messageFromServer = new BufferedReader(
                new InputStreamReader(
                        this.socket.getInputStream()
                )
        );
        //leo el mensaje
        String infoResponse = messageFromServer.readLine();
        return infoResponse;
    }

    //metodo que envia finalize al servidor para cerrar la conexion
    public void finalize(String command) {
        this.send.println(command);
    }

    //metodo para eliminar la informacion de un estudiantea
    public String delete(String targetId) throws IOException {
        this.send.println(this.petitionType + "---" + targetId);
        //Instancio un lector
        BufferedReader messageFromServer = new BufferedReader(
                new InputStreamReader(
                        this.socket.getInputStream()
                )
        );
        //leo el mensaje
        String infoResponse = messageFromServer.readLine();
        return infoResponse;
    }

    //metodos accesores
    public char getPetitionType() {
        return petitionType;
    }

    public void setPetitionType(char petitionType) {
        this.petitionType = petitionType;
    }
}
