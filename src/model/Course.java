package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import exceptions.InvalidProjectException;

public class Course implements Identifiable, Serializable{

    private String code;
    private String name;
    private String description;
    private int credits;
    private String uniqueID;
    private ArrayList<Teacher> teachers;
    private ArrayList<Project> projects;

    public Course(String code, String name, String description, int credits) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.uniqueID = generateCode();
        teachers = new ArrayList<Teacher>();
        projects = new ArrayList<Project>();
    }

    public String generateCode(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    @Override
    public String toString() {
        return "Course [code=" + code + ", name=" + name + ", description=" + description + ", credits=" + credits
                + ", uniqueID=" + uniqueID + ", teachers=" + teachers.size() + ", projects=" + projects.size() + "]";
    }

    public String getCode() {
        return code;
    }

    public boolean addTeacher(Teacher teacher){
        return teachers.add(teacher);
    }

    public String getProfile(){
        return name+" "+uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public boolean addProject(Project project){
        return projects.add(project);
    }

    public boolean hasProject(String projectId){
        return searchProject(projectId) != null;
    }

    public String showProjects(){
        String message = "";
        for (Project project : projects) {
            message += project.toString()+"\n";
        }
        if (message.equals("")) {
            message = "No hay proyectos guardados";
        }
        return message;
    }

    public String showProjects(String semester){
        String message = "";
        for (Project project : projects) {
            if (project.getSemester().equals(semester)) {
                message += project.getProfile()+"\n";
            }
        }
        if (message.equals("")) {
            message = "No hay proyectos guardados en el semestre indicado.";
        }
        return message;
    }

    public Project searchProject(String projectId){
        for (Project project : projects) {
            if (project.getUniqueId().equals(projectId)) {
                return project;
            }
        }
        return null;
    }

    public int amountOfResults(String projectId){
        Project project = searchProject(projectId);
        return project.amountOfResults();
    }

    public String addResult(String projectId, Result result){
        return searchProject(projectId).addResult(result);
    }

    public String addAssignment(String projectId, int resultIndex, Assignment assignment)throws InvalidProjectException{
       if (searchProject(projectId)== null) {
        throw new InvalidProjectException("El proyecto "+projectId+" no existe dentro del curso "+getProfile());
       }
       return searchProject(projectId).addAssignment(resultIndex, assignment);
    }

    public String showSpecificProject(String projectCode){
        return searchProject(projectCode).getCompleteInfo();
    }

    public String getProjectsWithoutResults(){
        String projectsInfo = "";
        for (Project project : projects) {
            if (project.amountOfResults() == 0) {
                projectsInfo += project.toString()+"\n";
            }
        }
        return projectsInfo;
    }

    public boolean hasProjectsInSemester(String semester){
        for (Project project : projects) {
            if (project.getSemester().equals(semester)) {
                return true;
            }
        }
        return false;
    }

    public String modifyProjectAttribute(String projectId, int option, String newValue){
        return searchProject(projectId).modifyAttribute(option, newValue);
    }

    public String modifyProjectType(String projectId, ProjectType type){
        searchProject(projectId).setType(type);
        return searchProject(projectId).toString();
    }

    public String deleteAssignmente(String projectId, int resultIndex, String AssignmentID) throws InvalidProjectException{
        if (searchProject(projectId)==null) {
            throw new InvalidProjectException("El proyecto con el id único "+projectId+" no existe en el curso "+getProfile());
        }
        return searchProject(projectId).deleteAssignment(resultIndex, AssignmentID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course other = (Course) obj;
        return uniqueID.equals(other.uniqueID);
    }
}
