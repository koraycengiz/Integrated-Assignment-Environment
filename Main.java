import java.util.Scanner;
import javafx.*;

public class Main {
    public static void main(String[] args) {
        Result result = new Result();

        Stage stage=new Stage();

        // Create main configurations for Java, C, and Python
        Configuration mainConfiguration = new Configuration();
        Configuration mainConfiguration_c = new Configuration();
        Configuration mainConfiguration_python = new Configuration();

        mainConfiguration.createConfigurationWithoutParameter("Java", "javac", "java");
        mainConfiguration_c.createConfigurationWithParameter("c","gcc","-o ",".exe");
        mainConfiguration_python.createConfigurationWithoutParameter("Python","python",".py");

        // Create a new Project
        Project myProject = new Project("MyProject");

        // Create assignments and add them to the Project
        Assignment assignment1 = new Assignment("A2", "Java");
        assignment1.addConfiguration(mainConfiguration.getConfigurations().get("java"));
        myProject.addAssignment(assignment1);

        Assignment assignment2 = new Assignment("A3", "C");
        assignment2.addConfiguration(mainConfiguration_c.getConfigurations().get("c"));
        myProject.addAssignment(assignment2);

        Assignment assignment3 = new Assignment("A4", "Python");
        assignment3.addConfiguration(mainConfiguration_python.getConfigurations().get("python"));
        myProject.addAssignment(assignment3);

        // Now you can perform operations on the Project, such as evaluating assignments
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter '1' to create project with existing configuration or '2' to create project without existing configuration:");
        int choice = scanner.nextInt();
        String status = "unknown";

        if (choice == 1) {
            // Create a new Project with an existing configuration
            System.out.println("Which language would you like to use: (Java-C-Python): ");
            scanner.nextLine();
            String language = scanner.nextLine();
            switch (language.toLowerCase()) {
                case "java" -> {
                    myProject.createProjectWithExistingConfiguration(mainConfiguration.getConfigurations().get("java"), "java");
                    String randomStudentId = "20200602015";
                    String randomSubmissionPath = "test.java";

                    System.out.println("Please enter the expected file's absolute path: ");
                    String projectPath = scanner.nextLine();
                    myProject.setProjectPath(projectPath);
                    myProject.getAssignments().get(0).setProjectManagerPath(projectPath);


                    myProject.getAssignments().get(0).addStudentSubmission(new StudentSubmission(randomStudentId, randomSubmissionPath));

                    myProject.getAssignments().get(0).evaluateStudentSubmissions();
                    System.out.println("--------------------------------------------------------------");
                    if (myProject.getAssignments().get(0).isSuccess()){
                        status = "Successed";
                    }
                    else{
                        status = "Failed";
                    }
                    result.addReport(randomStudentId,status);
                    result.displayReports(stage);
                    break;
                }
                case "c" -> {
                    myProject.createProjectWithExistingConfiguration(mainConfiguration.getConfigurations().get("c"), "c");
                    String randomStudentId = "20200602041";
                    String randomSubmissionPath = "test.c"; // Replace with actual path later

                    System.out.println("Please enter the expected file's absolute path: ");
                    String projectPath = scanner.nextLine();
                    myProject.setProjectPath(projectPath);
                    myProject.getAssignments().get(1).setProjectManagerPath(projectPath);

                    myProject.getAssignments().get(1).addStudentSubmission(new StudentSubmission(randomStudentId, randomSubmissionPath));



                    myProject.getAssignments().get(1).evaluateStudentSubmissions();
                    System.out.println("--------------------------------------------------------------");
                    if (myProject.getAssignments().get(1).isSuccess()){
                        status = "Successed";
                    }
                    else{
                        status = "Failed";
                    }
                    result.addReport(randomStudentId,status);
                    result.displayReports(stage);
                    break;
                }
                case "python" -> {
                    myProject.createProjectWithExistingConfiguration(mainConfiguration.getConfigurations().get("python"), "python");
                    String randomStudentId = "20200602052";
                    String randomSubmissionPath = "main.py"; // Replace with actual path later

                    System.out.println("Please enter the expected file's absolute path: ");
                    String projectPath = scanner.nextLine();
                    myProject.setProjectPath(projectPath);
                    myProject.getAssignments().get(2).setProjectManagerPath(projectPath);

                    myProject.getAssignments().get(2).addStudentSubmission(new StudentSubmission(randomStudentId, randomSubmissionPath));

                    myProject.getAssignments().get(2).evaluateStudentSubmissions();

                    System.out.println("--------------------------------------------------------------");
                    if (myProject.getAssignments().get(2).isSuccess()){
                        status = "Successed";
                    }
                    else{
                        status = "Failed";
                    }
                    result.addReport(randomStudentId,status);
                    result.displayReports(stage);
                    break;
                }
                default -> System.out.println("Invalid Language...");
            }
        } else if (choice == 2) {
            // Create a new Project without an existing configuration
            Scanner input = new Scanner(System.in);

            // Prompt the user to enter configuration details
            System.out.println("Enter configuration name:");
            String configName = input.nextLine();

            System.out.println("Enter compiler path:");
            String compilePath = input.nextLine();

            System.out.println("Enter compiler parameters (or leave empty if none):");
            String compileParameters = input.nextLine();

            System.out.println("Enter execution method:");
            String executionMethod = input.nextLine();

            // Create a new Configuration object with the provided details
            Configuration newConfig = new Configuration();
            if (compileParameters == null){
                newConfig.createConfigurationWithoutParameter(configName, compilePath, executionMethod);
                newConfig.setName(configName);
                newConfig.setCompilerPath(compilePath);
                newConfig.setCompilerParameters(compileParameters);
                newConfig.setExecutionMethod(executionMethod);
            }
            else{
                newConfig.createConfigurationWithParameter(configName, compilePath, compileParameters, executionMethod);
                newConfig.setName(configName);
                newConfig.setCompilerPath(compilePath);
                newConfig.setCompilerParameters(compileParameters);
                newConfig.setExecutionMethod(executionMethod);
            }

            Assignment assignment4 = new Assignment("A5", configName);
            assignment4.addConfiguration(newConfig.getConfigurations().get(configName));
            myProject.addAssignment(assignment4);


            System.out.println("Please enter the student ID: ");
            String randomStudentId = input.nextLine();
            System.out.println("Please enter the file path: ");
            String randomSubmissionPath = input.nextLine();

            System.out.println("Please enter the expected file's absolute path: ");
            String projectPath = scanner.nextLine();
            myProject.setProjectPath(projectPath);
            myProject.getAssignments().get(3).setProjectManagerPath(projectPath);

            myProject.getAssignments().get(3).addStudentSubmission(new StudentSubmission(randomStudentId, randomSubmissionPath));

            myProject.getAssignments().get(3).evaluateRandom();

            System.out.println("--------------------------------------------------------------");
            if (myProject.getAssignments().get(3).isSuccess()){
                status = "Successed";
            }
            else{
                status = "Failed";
            }
            result.addReport(randomStudentId,status);
            result.displayReports(stage);
        } else {
            System.out.println("Invalid choice.");
        }

        System.out.println("--------------------------------------------------------------");

        // Close the scanner
        scanner.close();
    }
}
