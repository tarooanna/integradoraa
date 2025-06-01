package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import exceptions.InvalidResultIndexException;

public class Project implements Identifiable, Serializable{
    String name;
    String semester;
    String uniqueId;
    ProjectType type;
    String link;
    String description;
    String[] business;
    String[] keywords;
    ArrayList<Result> results;
    Project father;

    public Project(String[] business, String semester, String name, String[] keywords, String link, String desciption, ProjectType type){
        this.business = business;
        this.semester = semester;
        this.name = name;
        this.keywords = keywords;
        this.link = link;
        this.description = desciption;
        this.type = type;
        this.uniqueId = generateCode();
        results = new ArrayList<Result>();
        father = null;
    }

    public Project(String[] business, String semester, String name, String[] keywords, String link, String desciption, ProjectType type, Project father){
        this.business = business;
        this.semester = semester;
        this.name = name;
        this.keywords = keywords;
        this.link = link;
        this.description = desciption;
        this.type = type;
        this.uniqueId = generateCode();
        results = new ArrayList<Result>();
        this.father = father;
    }

    public String generateCode(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    @Override
    public String toString() {

        String resultsProfile = "";
        for (Result result : results) {
            resultsProfile += result.getUniqueID()+" / ";
        }
        if (resultsProfile.equals("")) {
            resultsProfile = "empty";
        }
        return "Project [name=" + name + ", semester=" + semester + ", uniqueId=" + uniqueId + ", type=" + type
                + ", link=" + link + ", description=" + description + ", business=" + Arrays.toString(business)
                + ", keywords=" + Arrays.toString(keywords) + ", Has father=" + !(father == null) + ", results= "+resultsProfile+"]\n";
    }
    
    public String getProfile(){
        return uniqueId+" "+name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public int amountOfResults(){
        return results.size();
    }

    public String addResult(Result result){
        if (results.size() == 3) {
            return "Capacidad de resultados superada";
        }
        return "Resultado guardado\n"+result.toString() ;
    }
    
    public String addAssignment(int indexResult, Assignment assignment)throws InvalidResultIndexException{
        if (indexResult<1||indexResult>results.size() || results.size() == 0) {
            throw new InvalidResultIndexException("Indice inválido para el proyecto "+getProfile());
        }
        return results.get(indexResult-1).addAssignment(assignment);
    }  

    public String getCompleteInfo(){
        String info = toString();
        info += "Resultados: \n";
        for (Result result : results) {
            info += result.getCompleteInfo();
        }
        return info;
    }

    public String getSemester() {
        return semester;
    }

    public String modifyAttribute(int option, String newValue){
        switch (option) {
            case 1:
                name = newValue;
                break;
            case 2:
                semester = newValue;
                break;
            case 4:
                link = newValue;
                break;
            case 5: 
                description = newValue;
                break;
            case 6:
                business = newValue.split(",");
                break;
            case 7:
                keywords = newValue.split(",");
                break;
        }
        return toString();
    }

    public void setType(ProjectType type) {
        this.type = type;
    }
    
    public String deleteAssignment(int index, String Assignmentid){
        if (index<1||index>results.size() || results.size() == 0) {
            throw new InvalidResultIndexException("Indice inválido para el proyecto "+getProfile());
        }
        return results.get(index-1).deleteAssignmente(Assignmentid);
    }
    
}
