/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

/**
 *
 * @author Steven
 */
public class Student {

    //variables
    private String name;
    private String lastname;
    private int age;
    private String town;
    private String identification;
    private float admissionGrade;

    //constructores
    public Student() {
        this.name = "";
        this.name = "";
        this.age = 0;
        this.town = "";
        this.identification = "";
        this.admissionGrade = 0;
    }

    public Student(String name, String lastName, int age, String town, String identification, float admissionGrade) {
        this.name = name;
        this.lastname = lastName;
        this.age = age;
        this.town = town;
        this.identification = identification;
        this.admissionGrade = admissionGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public float getAdmissionGrade() {
        return admissionGrade;
    }

    public void setAdmissionGrade(float admissionGrade) {
        this.admissionGrade = admissionGrade;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public String toString() {
        return "Student Id= " + identification + "\n" + "Name= " + name + "\n" + "Lastname= " + lastname + "\n" + "Age= " + age + "\n" + "Town= " + town + "\n" + "AdmissionGrade= " + admissionGrade;
    }

}
