/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea8socketprotocoltestclient;

import Domain.Student;
import GUI.ClientInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Steven
 */
public class Tarea8SocketProtocolTestClient {

    //public static PrintStream send;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*try {
            InetAddress address;
            Socket socket;

            address = InetAddress.getByName("192.168.43.8");
            socket = new Socket(address, 5025);
            send = new PrintStream(socket.getOutputStream());

            Student newStudent = new Student("Andres", "B5718", 800);

            Element eStudent = new Element("student");
            //agregamos un atributo
            eStudent.setAttribute("identification", newStudent.getIdentification());

            //crear el nombre
            Element eName = new Element("name");
            eName.addContent(newStudent.getName());

            //crear el elemento nota
            Element eAdmissionGrade = new Element("admissionGrade");
            eAdmissionGrade.addContent(String.valueOf(newStudent.getAdmissionGrade()));

            //agregar al elemento student
            eStudent.addContent(eName);
            eStudent.addContent(eAdmissionGrade);

            XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
            String s = outputter.outputString(eStudent);

            send.println(s);

            socket.close();
        } catch (Exception e) {
            Logger.getLogger(Tarea8SocketProtocolTestClient.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("1000 - an exception has occurred, review socket connection");
        }*/
        ClientInterface ci = new ClientInterface();
        //ci.setVisible(true);
    }
}
