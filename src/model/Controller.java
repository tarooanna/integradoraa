package model;

import java.util.ArrayList;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import exceptions.InvalidCourseException;
import exceptions.InvalidProjectException;
import exceptions.InvalidSemesterException;
import exceptions.InvalidTeacherException;

public class Controller {

    private ArrayList<Course> courses;
    private ArrayList<Teacher> teachers;


    public Controller(){
        courses = new ArrayList<Course>();
        teachers = new ArrayList<Teacher>();
    }

    public Controller(String path){

    }

    public String loadData(){
        File dataBase = new File("data\\demodata.dat");

        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(dataBase))) {
            boolean flag = true;
            while (flag) {
                try {
                    Teacher teacher = (Teacher) reader.readObject();
                    teachers.add(teacher);
                    for (Course course : teacher.getCourses()) {
                        if (!courses.contains(course)) {
                            courses.add(course);
                        }
                    }
                } catch (EOFException r) {
                    flag = false;
                }
            }
            return "Carga exitosa";
        } catch (FileNotFoundException e) {
            return "Archivo no encontrado";
        } catch (ClassNotFoundException | IOException p) {
            return "Error en la carga: " + p.getMessage();
        }

    }

    public String saveData(){
        File dataBase = new File("data\\demodata.dat");

        try {
            dataBase.createNewFile();
            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(dataBase));

            for (Teacher teacher : teachers) {
                writer.writeObject(teacher);
            }

            writer.flush();
            writer.close();
            return "";
        } catch (FileNotFoundException e) {
            // TODO: handle exception
            return "Archivo no encontrado";
        } catch (IOException e){
            return "Error al cargar";
        }
    }

    public String registerTeacher(String idNumber, int idType, String name, String email){
        String message = "";
        IDType type = turnIDType(idType);
        if (searchTeacher(idNumber) != null) {
            return "El profesor con el número de documento "+ idNumber +" ya existe.";
        }
        if (!isValidEmail(email)) {
            return "El correo "+email+" no es válido. Intente de nuevo";
        }
        if (type == null) {
            return idType+" no es una opción válida de tipo de documento. Intente de nuevo.";
        }
        Teacher teacher = new Teacher(idNumber, type, name, email);
        teachers.add(teacher);
        message += "El profesor "+name+" fue agregado exitosamente. \n";
        message += teacher.toString();
        return message;
    }

    public boolean isValidEmail(String email){
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public Teacher searchTeacher(String id){
        Teacher teacher = null;
        for (Teacher i : teachers) {
            if (i.getIdNumber().equals(id)) {
                teacher = i;
            }
        }
        return teacher;
    }

    public Teacher searchTeacherByUniqueID(String id){
        Teacher teacher = null;
        for (Teacher i : teachers) {
            if (i.getUniqueID().equals(id)) {
                teacher = i;
            }
        }
        return teacher;
    }

    public IDType turnIDType(int option){
        IDType[] types = IDType.values();
        if (option<1 || option > types.length) {
            return null;
        }else{
            return types[option-1];
        }
         
    }

    public String registerCourse(String code, String name, String description, int credits){
        String message = "";
        if (searchCourseByCode(code) != null) {
            return "El curso con el código "+code+" ya existe.";
        }
        Course course = new Course(code, name, description, credits);
        courses.add(course);
        message += "El curso "+name+" fue creado exitosamente.\n";
        message += course.toString();
        return message;
    }

    public  Course searchCourseByCode(String code){
        Course course = null;
        for (Course g : courses) {
            if (g.getCode().equals(code)) {
                course = g;
            }
        }
        return course;
    }

    public Course searchCourseByUniqueID(String id){
        Course course = null;
        for (Course p : courses) {
            if (p.getUniqueID().equals(id)) {
                course = p;
            }
        }
        return course;
    }

    public String linkCourseToTeacher(String teacherIdNumber, String courseCode) throws InvalidCourseException, InvalidTeacherException{
        String message = "";
        Course course = searchCourseByUniqueID(courseCode);
        Teacher teacher = searchTeacherByUniqueID(teacherIdNumber);
        if (course == null) {
            throw new InvalidCourseException("El curso con el código "+courseCode+" no existe");
        }
        if (teacher == null) {
            throw new InvalidTeacherException("El profesor con el documento "+teacherIdNumber+" no existe");
        }
        teacher.addCourse(course);
        course.addTeacher(teacher);
        message += "El profesor "+teacher.getProfile()+" fue enlazado al curso "+course.getProfile();
        return message;
    }

    public String listOfTeachers(){
        String message = "";
        for (Teacher teacher : teachers) {
            message += teacher.getProfile()+"\n";
        }
        return message;
    }

    public String listOfCourses(){
        String message = "";
        for (Course course : courses) {
            message += course.getProfile()+"\n";
        }
        return message;
    }

    public String registerProject(String courseId, String name, String semester,String link, int type, String description, String business, String keywords) throws InvalidCourseException{
        Course course = searchCourseByUniqueID(courseId);
        ProjectType projectType = turnProjectType(type);
        String[] businessArray = business.split(",");
        String[] keyWordsArray = keywords.split(",");
        if (course == null) {
            throw new InvalidCourseException( "El curso con el ID único "+courseId+" no existe.");
        }
        if (projectType == null) {
            return type+" no es una opción de tipo de proyecto. Intente de nuevo.";
        }
        if (!validSemester(semester)) {
            return "El semestre "+semester+" no es válido.";
        }
        Project project = new Project(businessArray, semester, name, keyWordsArray, link, description, projectType);
        if (course.addProject(project)) {
            return "El proyecto fue agregado correctamente en el curso "+course.getProfile()+"\n "+project.toString();
        }
        return "";
    }

    public String registerProject(String courseId, String name, String semester,String link, int type, String description, String business, String keywords, String fatherProjectID) throws InvalidCourseException{
        Course course = searchCourseByUniqueID(courseId);
        ProjectType projectType = turnProjectType(type);
        String[] businessArray = business.split(",");
        String[] keyWordsArray = keywords.split(",");
        if (course == null) {
            throw new InvalidCourseException( "El curso con el ID único "+courseId+" no existe.");
        }
        if (projectType == null) {
            return type+" no es una opción de tipo de proyecto. Intente de nuevo.";
        }
        if (!validSemester(semester)) {
            return "El semestre "+semester+" no es válido.";
        }
        Project father = null; 
        boolean found = false;
        for (Course c : courses) {
            if (c.hasProject(fatherProjectID)) {
                found = true;
                father = c.searchProject(fatherProjectID);
            }
        }
        if (!found) {
            throw new InvalidProjectException("El proyecto con el id único "+fatherProjectID+" no existe. Proyecto padre inválido.");
        }
        Project project = new Project(businessArray, semester, name, keyWordsArray, link, description, projectType, father);
        if (course.addProject(project)) {
            return "El proyecto fue agregado correctamente en el curso "+course.getProfile()+"\n "+project.toString();
        }
        return "";
    }

    public ProjectType turnProjectType(int choice){
        ProjectType[] types = ProjectType.values();
        if (choice<1 || choice > types.length) {
            return null;
        }
        return types[choice-1];
    }

    public boolean validSemester(String semester){
        return semester.matches("^\\d{4}-(1|2)$");
    }

    public String showProjectsInACourse(String courseId) throws InvalidCourseException{
        Course course = searchCourseByUniqueID(courseId);
        if (course == null) {
            throw new InvalidCourseException("No existe en curso con el código "+courseId);
        }
        return course.showProjects();
    }

    public String showProjectsInACourse(String courseId, String semester) throws InvalidCourseException{
        Course course = searchCourseByUniqueID(courseId);
        if (course == null) {
            throw new InvalidCourseException("No existe en curso con el código "+courseId);
        }
        return course.showProjects(semester);
    }

    public String registerResult(String courseId, String projectId, String dateString, String group) throws InvalidCourseException, InvalidProjectException{
        Course course = searchCourseByUniqueID(courseId);
        if (course == null) {
            throw new InvalidCourseException("El curso ingresado no es válido");
        }
        if (!course.hasProject(projectId)){
            throw new InvalidProjectException("No existe el proyecto "+projectId+" en el curso "+courseId);
        }
        if (course.amountOfResults(projectId) == 3) {
            return "El proyecto "+projectId+" ya alcanzó un máximo de 3 resultados.";
        }
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            return "La fecha "+dateString+" no es válida";
        }
        Result result = new Result(date, group, course.amountOfResults(projectId)+1, projectId);
        return course.addResult(projectId, result);
    }

    public String registerAssignment(String courseId, String projectId, int resultIndex, int DevelopmentPhase, String link, int archives){
        if (!validGitHub(link)) {
            return "Link de GitHub inválido";
        }

        Repository assignment = new Repository(turnPhase(DevelopmentPhase), link, archives);
        return searchCourseByUniqueID(courseId).addAssignment(projectId, resultIndex, assignment);
    }

    public String registerAssignment(String courseId, String projectId, int resultIndex, int DevelopmentPhase, String link){
        if (!validCloudStorageLink(link)) {
            return "URL inválido";
        }
        Document document = new Document(turnPhase(DevelopmentPhase), link);
        return searchCourseByUniqueID(courseId).addAssignment(projectId, resultIndex, document);
    }

    public String registerAssignment(String courseId, String projectId, int resultIndex, int DevelopmentPhase, int type){
        Artifact artifact = new Artifact(turnArtifactType(type), turnPhase(DevelopmentPhase));
        return searchCourseByUniqueID(courseId).addAssignment(projectId, resultIndex, artifact);
    }

    public boolean validGitHub(String link){
        return link.matches("^https:\\/\\/(www\\.)?github\\.com\\/([\\w-]+)\\/([\\w.-]+)(\\.git)?$");
    }
    
    public DevelopmentPhase turnPhase(int option){
        DevelopmentPhase[] phases = DevelopmentPhase.values();
        return phases[option-1];
    }

    public  boolean validCloudStorageLink(String url) {
        if (url == null) return false;

        String regex = "^(https:\\/\\/)" +
            "(" +
                "drive\\.google\\.com\\/[^\\s]+|" +
                "www\\.dropbox\\.com\\/[^\\s]+|" +
                "dropbox\\.com\\/[^\\s]+|" +
                "onedrive\\.live\\.com\\/[^\\s]+|" +
                "[\\w.-]+\\.sharepoint\\.com\\/[^\\s]+" +
            ")$";

        return url.matches(regex);
    }

    public ArtifactType turnArtifactType(int type){
        ArtifactType[] types = ArtifactType.values();
        return types[type-1];
    }

    public String showAllProjects(){
        String projects = "";
        for (Course course : courses) {
            projects += course.getProfile()+":\n";
            projects += course.showProjects();
        }
        return projects;
    }

    public String showProjectInfoByUniqueId(String projectId) throws InvalidProjectException{
        String message = "";
        boolean found = false;
        for (Course course : courses) {
            if (course.hasProject(projectId)) {
                found = true;
                message = course.showSpecificProject(projectId);
            }
        }
        if (!found) {
            throw new InvalidProjectException("El proyecto con el id único "+projectId+" no existe");
        }
        return message;
    }

    public String showProjectsWithNoResults(){
        String info = "";
        for (Course course : courses) {
            info+= course.getProjectsWithoutResults()+"\n";
        }
        if (info.equals("")) {
            info = "No hay proyectos sin resultados.";
        }
        return info;
    }

    public String GetCoursesWithProjectsInSemester(String semester) throws InvalidSemesterException{
        if (!validSemester(semester)) {
            throw new InvalidSemesterException("Semestre inválido. Debe seguir el formato YYYY-X donde X es 1 o 2. Intente de nuevo");
        }
        String coursesInfo = "";
        for (Course course : courses) {
            if (course.hasProjectsInSemester(semester)) {
                coursesInfo += course.getProfile()+"\n";
            }
        }
        return coursesInfo;
    }

    public String modifyCommonAttributeProject(String projectId, int option, String newValue) throws InvalidProjectException{
        if (option == 2 && !validSemester(newValue)) {
            return "Semestre inválido. Intente de nuevo";
        }
        for (Course course : courses) {
            if (course.hasProject(projectId)) {
                return course.modifyProjectAttribute(projectId, option, newValue);
            }
        }
        throw new InvalidProjectException("El proyecto con el id "+projectId+" no existe");
    }  
    
    public String modifyTypeProject(String projectID, int type){
        ProjectType newType = turnProjectType(type);
        for (Course course : courses) {
            if (course.hasProject(projectID)) {
               return course.modifyProjectType(projectID, newType);
            }
        }
        throw new InvalidProjectException("El proyecto con el id "+projectID+" no existe");
    }

    public String deleteAssignment(String projectId, String courseID, int resultIndex, String assignmentID){
        return searchCourseByUniqueID(courseID).deleteAssignmente(projectId, resultIndex, assignmentID);
    }

}