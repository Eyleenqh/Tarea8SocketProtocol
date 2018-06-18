/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea8socketprotocoltestserver;

import Data.XMLStudentManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author Eyleen
 */
public class Tarea8SocketProtocolTestServer {

    /**
     * @param args the command line arguments
     */
    private static String serverName;
    private static XMLStudentManager xml;
    private static String[] msj;

    public static void main(String[] args) throws JDOMException, IOException {
        xml = new XMLStudentManager("./student.xml");
        try {
            //nombre del servidor
            serverName = "Superman";//es el nombre del servidor

            //instancia de este Servidor 
            ServerSocket serverSocket;

            //instancia de un Socket Cliente: se conectará en algún momento
            Socket client;

            //definir instancia y cuál será el puerto en el que se va a escuchar
            serverSocket = new ServerSocket(5025);
            //instancia

            do {
                //aquí hasta que un cliente se conecte.
                client = serverSocket.accept();
                System.out.println("Client wants a service");
                System.out.println("Sever accepts");

                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String msjFromClient = br.readLine();
                msj = msjFromClient.split("---");
                PrintStream send = null;

                //valida si se esta finalizando la conexion
                if (msjFromClient.equalsIgnoreCase("finalize")) {
                    System.out.println("Sever wait for another string but client send finalize");
                    break;
                }
                    switch (msj[0]) {
                        //inserta un nuevo estudiante
                        case "C":
                            System.out.println("Client send a string to insert");
                            send = new PrintStream(client.getOutputStream());
                            int infoC = 0;
                            //Preparo el mensaje que deseo enviar
                            if (optionC()) {
                                infoC = 1;
                            }
                            //Envío solo ese mensaje
                            send.println(String.valueOf(infoC));

                            //finalizamos la comunicacion con ese cliente
                            client.close();
                            break;

                        //obtiene un element deacuerdo a un id o name
                        case "R":
                            System.out.println("Client want a Student");
                            //Clase para enviar datos a traves del socket.
                            send = new PrintStream(client.getOutputStream());

                            //Preparo el mensaje que deseo enviar
                            String infoR = xml.retrieve(msj[1]);
                            if (infoR != null) {
                                System.out.println("Student find");
                            } else {
                                System.out.println("Student don't find");
                            }
                            //Envío solo ese mensaje
                            send.println(infoR);
                            //finalizamos la comunicacion con ese cliente
                            client.close();
                            break;

                        case "U":
                            System.out.println("Client send a information to update");
                            send = new PrintStream(client.getOutputStream());
                            int infoU = 0;
                            //Preparo el mensaje que deseo enviar
                            if (optionU()) {
                                infoU = 1;
                            }
                            //Envío solo ese mensaje
                            send.println(String.valueOf(String.valueOf(infoU)));
                            //finalizamos la comunicacion con ese cliente
                            client.close();
                            break;

                        case "D":
                            System.out.println("Client want delete a Student");
                            send = new PrintStream(client.getOutputStream());
                            int infoD = 0;
                            //Preparo el mensaje que deseo enviar
                            if (optionD(msj)) {
                                infoD = 1;
                            }
                            //Envío solo ese mensaje
                            send.println(String.valueOf(String.valueOf(infoD)));
                            //finalizamos la comunicacion con ese cliente
                            client.close();
                            break;
                    }
          
            } while (true);
            System.out.println("Sever accepts the finalize. Closing");
            client.close();
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Tarea8SocketProtocolTestServer.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("1000 -> busy port!");
        }
    }

    public static boolean optionC() throws IOException, JDOMException {
        Boolean result = false;
        String recibido = msj[1];
        SAXBuilder builder = new SAXBuilder();
        StringReader stringR = new StringReader(recibido);
        Document doc = builder.build(stringR);
        Element e = doc.getRootElement();

        //crear el student
        System.out.println("sever verifies if exits");
        if (!xml.insertPerson(e)) {
            System.out.println("Already exists");
        } else {
            result = true;
            System.out.println("The student has been insert");
        }
        return result;
    }

    public static boolean optionU() throws JDOMException, IOException {
        Boolean result = false;
        String recibido2 = msj[1];
        SAXBuilder builder2 = new SAXBuilder();
        StringReader stringR2 = new StringReader(recibido2);
        Document doc2 = builder2.build(stringR2);
        Element e2 = doc2.getRootElement();
        //actualiza el student
        if (xml.update(e2)) {
            result = true;
            System.out.println("Student update");
        }else{
            System.out.println("The student has not been update");
        }
        return result;
    }

    public static boolean optionD(String[] msj) throws IOException {
        Boolean result = xml.delete(msj[1]);
        if (result) {
            System.out.println("Student deleted");
        } else {
            System.out.println("The student has not been deleted");
        }
        return result;
    }
}
