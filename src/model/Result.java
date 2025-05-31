package model;

import java.time.LocalDate;
import java.util.ArrayList;

import exceptions.InvalidAssignmentException;

public class Result implements Identifiable{

    private ArrayList<Assignment> assignments;
    private ArrayList<Assignment> deletedAssignments;
    private LocalDate date;
    private String studentsGroup;
    private String uniqueID;
    private int numberOfResult;
    private String projectId;

    public Result(LocalDate date, String group, int numberOfResult, String projectId){
        this.date = date;
        this.studentsGroup = group;
        this.numberOfResult = numberOfResult;
        this.projectId = projectId;
        this.uniqueID = generateCode();
    }

    public String generateCode(){
        return "Proyecto"+projectId+"-Resultado"+numberOfResult+"-Grupo"+studentsGroup;
    }

    @Override
    public String toString() {
        return "Result [assignments=" + assignments.size() + ", date=" + date + ", studentsGroup=" + studentsGroup
                + ", uniqueID=" + uniqueID + "]";
    }

    public String getUniqueID() {
        return uniqueID;
    }
    
    public String addAssignment(Assignment assignment){
        assignments.add(assignment);
        return "Se agregó con éxito el entregable\n"+assignment.toString();
    }

    public String getCompleteInfo(){
        String message= toString()+"\n";
        message += "Entregables: \n";
        for (Assignment assignment : assignments) {
            message += assignment.toString()+"\n";
        }
        return message;
    }

    public Assignment searchAssignment(String assignmentID){
        Assignment assignment = null;
        for (Assignment a : assignments) {
            if (a.getCode().equals(assignmentID)) {
                assignment = a;
            }
        }
        return assignment;
    }

    public String deleteAssignmente(String assignmentID) throws InvalidAssignmentException{
        if (searchAssignment(assignmentID) ==null) {
            throw new InvalidAssignmentException("No existe el entregable con el ID "+assignmentID+" en "+getUniqueID());
        }
        deletedAssignments.add(searchAssignment(assignmentID));
        assignments.remove(searchAssignment(assignmentID));
        return "Entregable borrado";
    }
}
