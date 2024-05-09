package com.example.demoo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.security.auth.callback.NameCallback;
import java.io.ObjectInputFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration extends Application {

    private Map<String, Configuration> configurations = new HashMap<>();
    private TextField nameField, compilerPathField, compilerParametersField, executionMethodField;
    private String name;
    private String compilerPath;
    private String compilerParameters;
    private String executionMethod;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Configuration Manager");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40, 40, 40, 40));
        grid.setVgap(20);
        grid.setHgap(20);

        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        nameField = new TextField();
        GridPane.setConstraints(nameField, 1, 0);

        Label compilerPathLabel = new Label("Compiler Path:");
        GridPane.setConstraints(compilerPathLabel, 0, 1);
        compilerPathField = new TextField();
        GridPane.setConstraints(compilerPathField, 1, 1);

        Label compilerParametersLabel = new Label("Compiler Parameters:");
        GridPane.setConstraints(compilerParametersLabel, 0, 2);
        compilerParametersField = new TextField();
        GridPane.setConstraints(compilerParametersField, 1, 2);

        Label executionMethodLabel = new Label("Execution Method:");
        GridPane.setConstraints(executionMethodLabel, 0, 3);
        executionMethodField = new TextField();
        GridPane.setConstraints(executionMethodField, 1, 3);

        Button addButton = new Button("Add");
        GridPane.setConstraints(addButton, 1, 4);
        addButton.setOnAction(e -> addConfiguration());

        Button editButton = new Button("Edit");
        GridPane.setConstraints(editButton, 2, 4);
        editButton.setOnAction(e -> editConfiguration());

        Button infoButton = new Button("Information");
        GridPane.setConstraints(infoButton, 3, 4);
        infoButton.setOnAction(e -> showConfigurationInfo());

        Button deleteButton = new Button("Delete");
        GridPane.setConstraints(deleteButton, 4, 4);
        deleteButton.setOnAction(e -> deleteConfiguration());

        grid.getChildren().addAll(nameLabel, nameField, compilerPathLabel, compilerPathField,
                compilerParametersLabel, compilerParametersField, executionMethodLabel,
                executionMethodField, addButton, editButton, deleteButton, infoButton);

        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setCompilerPath(String compilerPath) {
        this.compilerPath = compilerPath;
    }
    public String getCompilerPath() {
        return compilerPath;
    }

    public void setCompilerParameters(String compilerParameters) {
        this.compilerParameters = compilerParameters.toLowerCase();
    }
    public String getCompilerParameters() {
        return compilerParameters;
    }
    public void setExecutionMethod(String executionMethod) {
        this.executionMethod = executionMethod;
    }
    public String getExecutionMethod() {
        return executionMethod;
    }

    private void addConfiguration() {
        String name = nameField.getText().toLowerCase();
        String compilerPath = compilerPathField.getText();
        String compilerParameters = compilerParametersField.getText();
        String executionMethod = executionMethodField.getText();

        if (configurations.containsKey(name)) {
            showAlert("Error", null, "Configuration with name '" + name + "' already exists.");
        } else {
            Configuration config = new Configuration();
            config.setName(name);
            config.setCompilerPath(compilerPath);
            config.setCompilerParameters(compilerParameters);
            config.setExecutionMethod(executionMethod);
            configurations.put(name, config);

            showAlert("Success", null, "Configuration added successfully!");

            // Clear fields after adding
            clearFields();
        }
    }

    private void editConfiguration() {
        String name = nameField.getText().toLowerCase();
        Configuration config = configurations.get(name);

        if (config != null) {
            List<String> choices = Arrays.asList("Name", "Compiler Path", "Compiler Parameters", "Execution Method");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Name", choices);
            dialog.setTitle("Edit Configuration");
            dialog.setHeaderText(null);
            dialog.setContentText("Select property to edit:");

            String selectedProperty = dialog.showAndWait().orElse(null);

            if (selectedProperty != null) {
                TextInputDialog inputDialog = new TextInputDialog();
                inputDialog.setTitle("Edit Configuration");
                inputDialog.setHeaderText(null);
                inputDialog.setContentText("Enter new value:");

                String newValue = inputDialog.showAndWait().orElse(null);

                if (newValue != null && !newValue.isEmpty()) {
                    switch (selectedProperty) {
                        case "Name":
                            String newName = newValue.toLowerCase();
                            if (!newName.equals(name)) {
                                configurations.remove(name);
                                configurations.put(newName, config);
                                config.setName(newValue);
                            }
                            break;
                        case "Compiler Path":
                            config.setCompilerPath(newValue);
                            break;
                        case "Compiler Parameters":
                            config.setCompilerParameters(newValue);
                            break;
                        case "Execution Method":
                            config.setExecutionMethod(newValue);
                            break;
                    }
                    showAlert("Success", null, "Configuration edited successfully!");
                } else {
                    showAlert("Error", null, "Invalid input!");
                }
            }
        } else {
            showAlert("Error", null, "Configuration not found!");
        }
    }

    private void showConfigurationInfo() {
        String configName = nameField.getText().toLowerCase();
        Configuration config = configurations.get(configName);

        if (config != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Configuration Info");
            alert.setHeaderText(null);
            alert.setContentText("Name: " + config.getName() + "\n" +
                    "Compiler Path: " + config.getCompilerPath() + "\n" +
                    "Compiler Parameters: " + config.getCompilerParameters() + "\n" +
                    "Execution Method: " + config.getExecutionMethod());
            alert.showAndWait();
        } else {
            showAlert("Error", null, "Configuration with name '" + configName + "' not found.");
        }
    }


    private void deleteConfiguration() {
        String name = nameField.getText().toLowerCase();
        Configuration removedConfig = configurations.remove(name);

        if (removedConfig != null) {
            showAlert("Success", null, "Configuration removed successfully!");
            clearFields();
        } else {
            showAlert("Error", null, "Configuration not found!");
        }
    }

    private void clearFields() {
        nameField.clear();
        compilerPathField.clear();
        compilerParametersField.clear();
        executionMethodField.clear();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
