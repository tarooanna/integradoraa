package ui;
import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.InvalidAssignmentException;
import exceptions.InvalidCourseException;
import exceptions.InvalidProjectException;
import exceptions.InvalidResultIndexException;
import exceptions.InvalidSemesterException;
import exceptions.InvalidTeacherException;
import model.Controller;

public class Exe {

    private Scanner input;
    private Controller controller;

    public static void main(String[] args) {
        Exe exe = new Exe();
        exe.roleMenu();
    }

    public Exe(){
        input = new Scanner(System.in);
        controller = new Controller();
        System.out.println("¿Desea cargar su propio archivo?");
        System.out.println("1. si");
        System.out.println("2. no");
        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Ingrese la ruta de su archivo:");
                String path = input.nextLine();
                System.out.println(controller.loadData(path));
                break;
        
            default:
                System.out.println("Se usará el archivo demo");
                System.out.println(controller.loadData());
                break;
        }
    }

    public void roleMenu(){
        int choice = 0;
       do {
            System.out.println("Ingrese su rol: ");
            System.out.println("1. Administrador");
            System.out.println("2. Profesor");
            choice = input.nextInt();
            input.nextLine();

            if (choice != 1 && choice != 2) {
            System.out.println("Opción inválida. Intente nuevamente.");
            }
        }while (choice != 1 && choice != 2);

        switch (choice) {
            case 1:
                adminMenu();
                break;
        
            case 2:
                teacherMenu();
                break;

        }
        
    }

    public void teacherMenu(){
        int choice;
        do {
            System.out.println("BIENVENIDO, SELECCIONE LA ACCIÓN A REALIZAR: ");
            System.out.println("1. Registrar proyecto.");
            System.out.println("2. Registrar resultado");
            System.out.println("3. Registrar un entregable.");
            System.out.println("4. Buscar proyectos");
            System.out.println("5 Buscar proyecto");
            System.out.println("6. Borrar entregable");
            System.out.println("0. Guardar y salir.");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    registerProject();
                    break;
                
                case 2:
                    registerResult();
                    break;
                
                case 3:
                    registerAssignment();
                    break;

                case 4:
                    searchProject();
                    break;
                
                case 5:
                    modifyProject();
                    break;
                case 6:
                    deleteAssignment();
                    break;
                case 0:
                    saveData();
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (choice != 0);

    }

    public void adminMenu(){
        int choice;
        do {
            System.out.println("BIENVENIDO, SELECCIONE LA ACCIÓN A REALIZAR: ");
            System.out.println("1. Registrar curso.");
            System.out.println("2. Registrar profesor.");
            System.out.println("3. Enlazar un profesor con un curso.");
            System.out.println("0. Guardar y salir.");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    registerCourse();
                    break;
                
                case 2:
                    registerTeacher();
                    break;
                
                case 3:
                    linkCourseAndTeacher();
                    break;

                case 0:
                    saveData();
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        } while (choice != 0);
    }
    
    public void registerCourse(){
        System.out.println("Bienvenido a registrar curso: ");
        boolean flag = false;
        while (!flag) {
            try {
                System.out.println("Ingrese el código del curso: ");
                String code = input.nextLine();
                System.out.println("Ingrese el nombre del curso: ");
                String name = input.nextLine();
                System.out.println("Ingrese una corta descripción para el curso: ");
                String description = input.nextLine();
                System.out.println("Ingrese la cantidad de créditos ");
                int credits = input.nextInt();
                input.nextLine();
                System.out.println(controller.registerCourse(code, name, description, credits));
                flag = true;
            } catch (InputMismatchException e) {
                System.out.println("Tipo de dato inválido. Intente de nuevo.");
                input.nextLine();
            }
        }
    }

    public void registerTeacher(){
        System.out.println("Bienvenido a registar profesor: ");
        try {
            System.out.println("Ingrese el número de documento del profesor: ");
            String id = input.nextLine();
            System.out.println("Tipos de documento: ");
            System.out.println("1. Cédula de ciudadanía");
            System.out.println("2. Pasaporte");
            System.out.println("3. Cédula de extranjería");
            System.out.println("Ingrese el tipo de documento: ");
            int idType = input.nextInt();
            input.nextLine();
            System.out.println("Ingrese el nombre completo: ");
            String name = input.nextLine();
            System.out.println("Ingrese el email del profesor: ");
            String email = input.nextLine();
            System.out.println(controller.registerTeacher(id, idType, name, email));
            
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            input.nextLine();
        }
    }

    public void linkCourseAndTeacher(){
        System.out.println("Bienvenido a enlazar curso y profesor.");
        try {
            System.out.println("Profesores registrados: ");
            System.out.println(controller.listOfTeachers());
            System.out.println("Ingrese el número de documento del profesor a enlazar: ");
            String teacherID = input.nextLine();
            System.out.println("Cursos registrados: ");
            System.out.println(controller.listOfCourses());
            System.out.println("Ingrese el código del curso a enlazar: ");
            String courseCode = input.nextLine();
            System.out.println(controller.linkCourseToTeacher(teacherID, courseCode));
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
            input.nextLine();
        } catch (InvalidTeacherException o){
            System.out.println(o.getMessage());
        } catch (InvalidCourseException p){
            System.out.println(p.getMessage());
        }
        
    }

    public void registerProject(){
        System.out.println("Bienvenido a registrar proyecto. ");
        try {
            System.out.println("Estos son los cursos registrados: ");
            System.out.println(controller.listOfCourses());
            System.out.println("Ingrese el identificador del curso del proyecto: ");
            String courseId = input.nextLine();
            System.out.println("Ingrese el nombre del proyecto: ");
            String name = input.nextLine();
            System.out.println("Ingrese el enlace al enunciado: ");
            String link = input.nextLine();
            System.out.println("Ingrese el semestre del proyecto. (Debe seguir el formato YYYY-X)");
            String semester = input.nextLine();
            System.out.println("Estos son los tipos de proyecto: ");
            System.out.println("1. Tarea integradora");
            System.out.println("2. Proyecto de curso");
            System.out.println("3. Proyecto final");
            System.out.println("Ingrese una opción: ");
            int type = input.nextInt();
            input.nextLine();
            System.out.println("Ingrese una descripción: ");
            String description = input.nextLine();
            System.out.println("Ingrese las empresas beneficiarias. Sepárelas con una coma (,)");
            String business = input.nextLine();
            System.out.println("Ingrese las palabras clave del proyecto. Sepárelas con una coma (,)");
            String keywords = input.nextLine();
            int inherits = 0;
            do {
                System.out.println("¿El proyecto deriva de otro? Ingrese 1 para sí y 2 para no.");
                inherits = input.nextInt();
                input.nextLine();
            } while (inherits != 1 && inherits != 2);
            switch (inherits) {
                case 1:
                    System.out.println("Estos son todos los proyectos guardados por curso: ");
                    System.out.println(controller.showAllProjects());
                    System.out.println("Ingrese el código del proyecto padre: ");
                    String id = input.nextLine();
                    System.out.println(controller.registerProject(courseId, name, semester, link, type, description, business, keywords, id));
                    break;
            
                case 2:
                    System.out.println(controller.registerProject(courseId, name, semester, link, type, description, business, keywords));
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Tipo de dato inválido. Intente de nuevo");
            input.nextLine();
        } catch (InvalidCourseException x){
            System.out.println(x.getMessage());
        } catch (InvalidProjectException p){
            System.out.println(p.getMessage());
        }
    }

    public void registerResult(){
        System.out.println("Bienvenido a registrar resultado.");
        try {
            System.out.println("Estos son los cursos disponibles: ");
            System.out.println(controller.listOfCourses());
            System.out.println("Ingrese el id único del curso del proyecto del resultado: ");
            String course = input.nextLine();
            System.out.println("Estos son los proyectos dentro del curso: ");
            System.out.println(controller.showProjectsInACourse(course));
            System.out.println("Ingrese el id único del proyecto al que le va a añadir el resultado: ");
            String project = input.nextLine();
            System.out.println("Ingrese el año del resultado: ");
            String year = input.nextLine();
            System.out.println("Ingrese el mes del resultado:");
            String month = input.nextLine();
            System.out.println("Ingrese el día del resultado: ");
            String day = input.nextLine();
            System.out.println("Ingrese el grupo en el que matricularon los estudiantes: ");
            String group = input.nextLine();
            System.out.println(controller.registerResult(course, project, year+"-"+month+"-"+day, group));
            
        } catch (InputMismatchException e) {
            System.out.println("Tipo de dato incorrecto. Intente de nuevo.");
            input.nextLine();
        } catch (InvalidCourseException d){
            System.out.println(d.getMessage());
        }
    
    }

    public void registerAssignment(){
        System.out.println("Bienvenido a registrar entregable: ");
        try {
            System.out.println("Estos son los cursos disponibles: ");
            System.out.println(controller.listOfCourses());
            System.out.println("Ingrese el id único del curso: ");
            String course = input.nextLine();
            System.out.println("Estos son los proyectos dentro del curso: ");
            System.out.println(controller.showProjectsInACourse(course));
            System.out.println("Ingrese el id único del proyecto: ");
            String project = input.nextLine();
            System.out.println("Ingrese el índice del resultado al que se anexará el entregable: ");
            int resultIndex = input.nextInt();
            input.nextLine();
            System.out.println("Fases del ciclo de vida de desarrollo de Software: ");
            System.out.println("1. Análisis de Requerimientos.");
            System.out.println("2. Diseño");
            System.out.println("3. Construcción");
            System.out.println("4. Pruebas");
            System.out.println("5. Despliegue");
            int phase = 0;
            while (phase < 1 || phase>5) {
                System.out.println("Ingrese una fase válida: ");
                phase = input.nextInt();
                input.nextLine();
            }
            System.out.println("Tipos de entregable: ");
            System.out.println("1. Repositorio");
            System.out.println("2. Documento");
            System.out.println("3. Artefacto");
            int type = 0;
            while (type<1 || type>3) {
                System.out.println("Ingrese un tipo válido: ");
                type = input.nextInt();
            }
            switch (type) {
                case 1:
                    System.out.println("Ingrese el url del repositorio en github: ");
                    String link = input.nextLine();
                    System.out.println("Ingrese el número de archivos en el repositorio: ");
                    int amountOfArchives = input.nextInt();
                    input.nextLine();
                    System.out.println(controller.registerAssignment(course, project, resultIndex, phase, link, amountOfArchives));
                    break;

                case 2:
                    System.out.println("Ingrese el url del documento. Debe ser un enlace válido a ");
                    System.out.println("SharePoint, OneDrive, Google Drive o Dropbox.");
                    link = input.nextLine();
                    System.out.println(controller.registerAssignment(course, project, resultIndex, phase, link));
                    break;

                case 3:
                    System.out.println("Existen los siguientes tipos de artefacto: ");
                    System.out.println("1. Especificación de requerimientos");
                    System.out.println("2. Diagrama de clases");
                    System.out.println("3. Infografía");
                    System.out.println("4. Modelo de datos");
                    System.out.println("5. Plan de pruebas");
                    System.out.println("6. Diagrama de despliegue");
                    System.out.println("Ingrese uno: ");
                    int artifactType = input.nextInt();
                    while (artifactType<1 || artifactType>6) {
                        System.out.println("Ingrese un tipo válido: ");
                        artifactType = input.nextInt();
                        input.nextLine();
                    }
                    System.out.println(controller.registerAssignment(course, project, resultIndex, phase, artifactType));
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Ha ingresado un tipo de dato inválido.");
            input.nextLine();
        } catch (InvalidCourseException d){
            System.out.println(d.getMessage());
        } catch (InvalidProjectException q){
            System.out.println(q.getMessage());
        } catch (InvalidResultIndexException i){
            System.out.println( i.getMessage());
        }
        
    }

    public void searchProject(){
        System.out.println("Bienvenido a búsqueda de proyecto: ");
        try {
            System.out.println("1. Buscar por código");
            System.out.println("2. Buscar proyectos sin resultados");
            System.out.println("3. Buscar proyectos filtrando por semestre");
            int option = 0;
            while (option<1 || option>3) {
                System.out.println("Seleccione uno: ");
                option = input.nextInt();
                input.nextLine();
            }
            switch (option) {
                case 1:
                    System.out.println("Estos son todos los proyectos guardados por curso: ");
                    System.out.println(controller.showAllProjects());
                    System.out.println("Ingrese el código del proyecto que desea ver: ");
                    String id = input.nextLine();
                    System.out.println(controller.showProjectInfoByUniqueId(id));
                    break;

                case 2:
                    System.out.println("Estos son los proyectos sin resultados:");
                    System.out.println(controller.showProjectsWithNoResults());
                    break;
                
                case 3:
                    System.out.println("Ingrese el semestre del que desea ver el proyecto (En el formato YYYY-X): ");
                    String semester = input.nextLine();
                    System.out.println("Estos son los cursos con proyecto en el semestre ingresado: ");
                    System.out.println(controller.GetCoursesWithProjectsInSemester(semester));
                    System.out.println("Ingrese el código del curso ");
                    String course = input.nextLine();
                    System.out.println("El curso tiene los siguientes proyectos;");
                    System.out.println(controller.showProjectsInACourse(course, semester));
                    System.out.println("Ingrese el código del proyecto que quiere consultar");
                    String projectId = input.nextLine();
                    System.out.println(controller.showProjectInfoByUniqueId(projectId));
                    break;
            }
        } catch(InputMismatchException w){
            System.out.println("Tipo de dato inválido. Intente de nuevo");
            input.nextLine();
        }catch (InvalidSemesterException e) {
           System.out.println(e.getMessage());
        } catch (InvalidProjectException p) {
            System.out.println(p.getMessage());
        } catch (InvalidCourseException c) {
            System.out.println(c.getMessage());
        }
       
    }

    public void modifyProject(){
        try {
            System.out.println("Bienvenido a modificar proyecto. ");
            System.out.println("Estos son todos los proyectos guardados por curso: ");
            System.out.println(controller.showAllProjects());
            System.out.println("Ingrese el código del proyecto que desea ver: ");
            String id = input.nextLine();
            System.out.println(controller.showProjectInfoByUniqueId(id));
            System.out.println("Los atributos que puede cambiar son: ");
            System.out.println("1. Nombre");
            System.out.println("2. Semestre");
            System.out.println("3. Tipo");
            System.out.println("4. Link al enunciado");
            System.out.println("5. Descripción");
            System.out.println("6. Empresas beneficiarias");
            System.out.println("7. Plabras clave");
            int attribute = 0;
            while (attribute <1 || attribute > 7) {
                System.out.println("Escoja uno de los atributos: ");
                attribute = input.nextInt();
                input.nextLine();
            }
            switch (attribute) {
                case 1,2,4,5,6,7:
                    System.out.println("Ingrese el valor nuevo del atributo. Recuerde separar por (,) si el atributo a cambiar es una lista: ");
                    String newData = input.nextLine();
                    System.out.println(controller.modifyCommonAttributeProject(id, attribute, newData));

                    break;
            
                case 3:
                    System.out.println("Los tipos de proyecto son: ");
                    System.out.println("Estos son los tipos de proyecto: ");
                    System.out.println("1. Tarea integradora");
                    System.out.println("2. Proyecto de curso");
                    System.out.println("3. Proyecto final");
                    int type = 0;
                    while (type<1 || type>4) {
                        System.out.println("Ingrese una opción: ");
                        type = input.nextInt();
                    }
                    System.out.println(controller.modifyTypeProject(id, type));
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Tipo de dato inválido. Intente de nuevo");
            input.nextLine();
        } catch (InvalidCourseException w) {
            System.out.println(w.getMessage());
        } catch (InvalidProjectException p) {
            System.out.println(p.getMessage());
        }
        
    }

    public void deleteAssignment(){
        try {
            System.out.println("Bienvenido a borrar un entregable.");
            System.out.println("Estos son los cursos disponibles: ");
            System.out.println(controller.listOfCourses());
            System.out.println("Ingrese el id único del curso: ");
            String course = input.nextLine();
            System.out.println("Estos son los proyectos dentro del curso: ");
            System.out.println(controller.showProjectsInACourse(course));
            System.out.println("Ingrese el id único del proyecto: ");
            String project = input.nextLine();
            System.out.println("Ingrese el índice del resultado en el que está el entregable: ");
            int resultIndex = input.nextInt();
            input.nextLine();
            System.out.println("Ingrese el código único del entregable");
            System.out.println(controller.deleteAssignment(project, course, resultIndex, project));  
        } catch (InputMismatchException e) {
            System.out.println("Tipo de dato inválido. Intente de nuevo");
            input.nextLine();
        } catch (InvalidCourseException w) {
            System.out.println(w.getMessage());
        } catch (InvalidProjectException p) {
            System.out.println(p.getMessage());
        } catch (InvalidResultIndexException r) {
            System.out.println(r.getMessage());
        } catch (InvalidAssignmentException a) {
            System.out.println(a.getMessage());
        }
    }

    public void saveData(){
        System.out.println(controller.saveData());
    }
}
