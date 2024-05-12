import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Project {

    private String projectID;
    private List<Assignment> assignments;

    private String projectPath = "";

    public Project(String projectID) {
        this.projectID = projectID;
        this.assignments = new ArrayList<>();
        this.projectPath = " ";
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String path) {
        this.projectPath = path;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void createProjectWithExistingConfiguration(Configuration configuration,String language) {
        // Create a new Project with the given project ID
        Project newProject = new Project(projectID);


        if (language.toLowerCase().equals("java")){
            newProject.addAssignment(new Assignment("Assignment1", "java"));
        }
        else if (language.toLowerCase().equals("c")){
            newProject.addAssignment(new Assignment("Assignment1", "c"));
        }
        else if (language.toLowerCase().equals("python")){
            newProject.addAssignment(new Assignment("Assignment1", "python"));
        }
        else{
            System.out.println("Invalid Language...");
        }

        // Add the provided configuration to the new Project
        loadScene("java");


        // Optionally, you can add more assignments or configurations here
    }

    public void createProjectWithoutExistingConfiguration() {
        // Create a new Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter configuration details
        System.out.println("Enter configuration name:");
        String configName = scanner.nextLine();

        System.out.println("Enter compiler path:");
        String compilePath = scanner.nextLine();

        System.out.println("Enter compiler parameters (or leave empty if none):");
        String compileParameters = scanner.nextLine();

        System.out.println("Enter execution method:");
        String executionMethod = scanner.nextLine();

        // Create a new Configuration object with the provided details
        Configuration newConfig = new Configuration();
        if (compileParameters == null){
            newConfig.createConfigurationWithoutParameter(configName, compilePath, executionMethod);
        }
        else{
            newConfig.createConfigurationWithParameter(configName, compilePath, compileParameters, executionMethod);
        }


        // Create a new Project with the given project ID
        Project newProject = new Project(projectID);
        System.out.println("Which language do you want to use: ");
        String language = scanner.nextLine();

        // Add the newly created configuration to the new Project
        newProject.addAssignment(new Assignment("Assignment1", language));

        // Optionally, you can add more assignments or configurations here
        loadScene("java");

        // Close the scanner to prevent resource leak
        scanner.close();
    }

    private void loadScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load the screen");
            alert.setContentText("There was an error loading the screen: " + fxmlPath);
            alert.showAndWait();
        }
    }
}
