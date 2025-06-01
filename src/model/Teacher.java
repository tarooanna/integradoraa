package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Teacher implements Identifiable, Serializable{

    private String idNumber;
    private IDType idType;
    private String name;
    private String eMail;
    private String uniqueID;
    private ArrayList<Course> courses;

    public Teacher(String idNumber, IDType idType, String name, String email){

        this.idNumber = idNumber;
        this.idType = idType;
        this.name = name;
        this.eMail = email;
        this.uniqueID = generateCode();
        this.courses = new ArrayList<Course>();
    }

    public String generateCode(){

        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);

    }

    @Override
    public String toString() {
        return "Teacher [idNumber=" + idNumber + ", idType=" + idType + ", name=" + name + ", eMail=" + eMail
                + ", uniqueID=" + uniqueID + ", courses=" + courses.size() + "]";
    }

    public String getIdNumber() {
        return idNumber;
    }

    public boolean addCourse(Course course){
        return courses.add(course);
    }

    public String getProfile(){
        return name+" "+uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public ArrayList<Course> getCourses(){
        return this.courses;
    }
}
    
    

