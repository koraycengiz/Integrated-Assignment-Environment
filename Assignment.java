import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Assignment {
    private String assignmentId;
    private String language;
    private List<Configuration> assingnmentConfigurations;
    private List<StudentSubmission> studentSubmissions;
    private List<Result> results;

    private Project projectManager;

    private String projectManagerPath;

    private Configuration config;
    private boolean success;

    public Assignment(String assignmentId, String language) {
        this.assignmentId = assignmentId;
        this.language = language;
        this.assingnmentConfigurations = new ArrayList<>();
        this.studentSubmissions = new ArrayList<>();
        this.results = new ArrayList<>();
        this.projectManager = new Project("1");
    }

    public void evaluateStudentSubmissions() {
        for (StudentSubmission submission : studentSubmissions) {
            if (language.toLowerCase().equals("c")){
                boolean compilationSuccess = c_compileSourceCode(submission.getSubmissionPath());
                if (!compilationSuccess) {
                    System.out.println("Compilation failed for student ID: " + submission.getStudentId());
                    continue;
                }

                boolean executionSuccess = cRunExecutableWithParameters(submission.getSubmissionPath());
                if (!executionSuccess) {
                    System.out.println("Execution failed for student ID: " + submission.getStudentId());
                    continue;
                }

                boolean outputCorrect = cCompareOutputWithExpected();
                if (outputCorrect) {
                    System.out.println("Submission passed for student ID: " + submission.getSubmissionId());
                    success = true;
                } else {
                    System.out.println("Submission failed for student ID: " + submission.getSubmissionId());
                    success = false;
                }
            }
            else if (language.toLowerCase().equals("java")){
                boolean compilationSuccess = javaCompileSourceCode(submission.getSubmissionPath());
                if (!compilationSuccess) {
                    System.out.println("Compilation failed for student ID: " + submission.getStudentId());
                    continue;
                }

                boolean executionSuccess = javaRunExecutableWithParameters(submission.getSubmissionPath());
                if (!executionSuccess) {
                    System.out.println("Execution failed for student ID: " + submission.getStudentId());
                    continue;
                }

                boolean outputCorrect = javaCompareOutputWithExpected();
                if (outputCorrect) {
                    System.out.println("Submission passed for student ID: " + submission.getSubmissionId());
                    success = true;
                } else {
                    System.out.println("Submission failed for student ID: " + submission.getSubmissionId());
                    success = false;
                }
            }
            else if (language.toLowerCase().equals("python")){
                /*
                boolean compilationSuccess = pythonCompileSourceCode(submission.getSubmissionPath());
                if (!compilationSuccess) {
                    System.out.println("Compilation failed for student ID: " + submission.getStudentId());
                    continue;
                }

                 */

                boolean executionSuccess = pythonRunExecutableWithParameters(submission.getSubmissionPath());
                if (!executionSuccess) {
                    System.out.println("Execution failed for student ID: " + submission.getStudentId());
                    continue;
                }

                boolean outputCorrect = pythonCompareOutputWithExpected();
                if (outputCorrect) {
                    System.out.println("Submission passed for student ID: " + submission.getSubmissionId());
                    success = true;
                } else {
                    System.out.println("Submission failed for student ID: " + submission.getSubmissionId());
                    success = false;
                }


            }
            else{
                System.out.println("Invalid language is given.Please restart the program and enter a valid language.");
            }

        }

    }

    private boolean c_compileSourceCode(String submissionPath) {
        try {
            // Get the first configuration (assuming there's only one)
            Configuration config = assingnmentConfigurations.get(0);

            String compilerPath = config.getCompilerPath(); // Compiler path from configuration
            String compilerParameters = config.getCompilerParameters(); // Compiler parameters from configuration
            String compiledName = "compiled.exe";
            String compileCommand;

            if (config.getCompilerParameters() == null){
                compileCommand = compilerPath + " " + submissionPath;
            }
            else{
                compileCommand = compilerPath + " " + submissionPath + " " + compilerParameters + compiledName;
            }

            Process compileProcess = Runtime.getRuntime().exec(compileCommand);

            BufferedReader reader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print compiler output (e.g., errors)
            }

            int compileExitValue = compileProcess.waitFor();

            if (compileExitValue == 0) {
                System.out.println("Compilation successful.");
                return true;
            } else {
                System.out.println("Compilation failed.");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error during compilation: " + e.getMessage());
            return false;
        }
    }

    private boolean cRunExecutableWithParameters(String submissionPath) {
        try {

            Configuration config = assingnmentConfigurations.get(0);
            String compiledName = "compiled";

            // Prepare the command to run the Java program
            String command = compiledName + config.getExecutionMethod();

            // Execute the command in the command prompt
            Process executionProcess = Runtime.getRuntime().exec("cmd /c " + command);

            // Capture and print program output
            BufferedReader reader = new BufferedReader(new InputStreamReader(executionProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print program output (e.g., results)
            }

            // Wait for the program execution to complete
            int executionExitValue = executionProcess.waitFor();

            // Check the execution result
            if (executionExitValue == 0) {
                System.out.println("Execution successful.");
                return true;
            } else {
                System.out.println("Execution failed.");
                return false;
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error during execution: " + e.getMessage());
            return false;
        }
    }

    private boolean cCompareOutputWithExpected() {

        String outputPath = "c_output_test.txt"; // Example path to actual output file
        String expectedPath = projectManagerPath; // Example path to expected output file

        try (BufferedReader outputReader = new BufferedReader(new FileReader(outputPath));
             BufferedReader expectedReader = new BufferedReader(new FileReader(expectedPath))) {

            String outputLine;
            String expectedLine = null;

            while ((outputLine = outputReader.readLine()) != null && (expectedLine = expectedReader.readLine()) != null) {
                if (!outputLine.equals(expectedLine)) {
                    System.out.println("Output does not match expected output.");
                    success = false;
                    return false;
                }
            }
            outputLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

            if (outputLine != null || expectedLine != null) {
                System.out.println("Output does not match expected output.");
                success = false;
                return false;
            }

            System.out.println("Output matches expected output.");
            success = true;
            return true;

        } catch (IOException e) {
            System.out.println("Error comparing output with expected: " + e.getMessage());
            success = false;
            return false;
        }
    }

    private boolean javaCompileSourceCode(String submissionPath) {
        try {
            // Get the first configuration (assuming there's only one)
            Configuration config = assingnmentConfigurations.get(0);

            String compilerPath = config.getCompilerPath(); // Compiler path from configuration
            String compilerParameters = config.getCompilerParameters(); // Compiler parameters from configuration
            String compileCommand;

            if (config.getCompilerParameters() == null){
                compileCommand = compilerPath + " " + submissionPath;
            }
            else{
                compileCommand = compilerPath + " " + submissionPath + " " + compilerParameters;
            }

            Process compileProcess = Runtime.getRuntime().exec(compileCommand);

            BufferedReader reader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print compiler output (e.g., errors)
            }

            int compileExitValue = compileProcess.waitFor();

            if (compileExitValue == 0) {
                System.out.println("Compilation successful.");
                return true;
            } else {
                System.out.println("Compilation failed.");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error during compilation: " + e.getMessage());
            return false;
        }
    }




    private boolean javaRunExecutableWithParameters(String submissionPath) {
        try {

            // Get the directory path from the submission path
            int lastSeparatorIndex = submissionPath.lastIndexOf(File.separator);
            if (lastSeparatorIndex == -1) {
                System.out.println("Invalid submission path: " + submissionPath);
                return false;
            }
            String directoryPath = submissionPath.substring(0, lastSeparatorIndex);

            // Prepare the command to change the directory
            String changeDirectoryCommand = "cmd /c cd " + directoryPath;

            // Execute the change directory command
            Process changeDirProcess = Runtime.getRuntime().exec(changeDirectoryCommand);
            changeDirProcess.waitFor(); // Wait for the directory change to complete

            Configuration config = assingnmentConfigurations.get(0);

            // Prepare the command to run the Java program
            String command = config.getExecutionMethod()+ " " + submissionPath;

            // Execute the command in the command prompt
            Process executionProcess = Runtime.getRuntime().exec("cmd /c " + command);

            // Capture and print program output
            BufferedReader reader = new BufferedReader(new InputStreamReader(executionProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print program output (e.g., results)
            }

            // Wait for the program execution to complete
            int executionExitValue = executionProcess.waitFor();

            // Check the execution result
            if (executionExitValue == 0) {
                System.out.println("Execution successful.");
                return true;
            } else {
                System.out.println("Execution failed.");
                return false;
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error during execution: " + e.getMessage());
            return false;
        }
    }

    private boolean javaCompareOutputWithExpected() {

        String outputPath = "java_output.txt"; // Example path to actual output file
        String expectedPath = projectManagerPath; // Example path to expected output file

        try (BufferedReader outputReader = new BufferedReader(new FileReader(outputPath));
             BufferedReader expectedReader = new BufferedReader(new FileReader(expectedPath))) {

            String outputLine;
            String expectedLine = null;

            while ((outputLine = outputReader.readLine()) != null && (expectedLine = expectedReader.readLine()) != null) {
                if (!outputLine.equals(expectedLine)) {
                    System.out.println("Output does not match expected output.");
                    success = false;
                    return false;
                }
            }
            outputLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

            if (outputLine != null || expectedLine != null) {
                System.out.println("Output does not match expected output.");
                success = false;
                return false;
            }

            System.out.println("Output matches expected output.");
            success = true;
            return true;

        } catch (IOException e) {
            System.out.println("Error comparing output with expected: " + e.getMessage());
            success = false;
            return false;
        }
    }

    private boolean pythonCompileSourceCode(String submissionPath) {
        try {
            // Get the first configuration (assuming there's only one)
            Configuration config = assingnmentConfigurations.get(0);

            String compilerPath = config.getCompilerPath(); // Compiler path from configuration
            String compilerParameters = config.getCompilerParameters(); // Compiler parameters from configuration
            String compileCommand;

            if (config.getCompilerParameters() == null){
                compileCommand = compilerPath + " " + submissionPath;
            }
            else{
                compileCommand = compilerPath + " " + submissionPath + " " + compilerParameters;
            }

            Process compileProcess = Runtime.getRuntime().exec(compileCommand);

            BufferedReader reader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print compiler output (e.g., errors)
            }

            int compileExitValue = compileProcess.waitFor();

            if (compileExitValue == 0) {
                System.out.println("Compilation successful.");
                return true;
            } else {
                System.out.println("Compilation failed.");
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error during compilation: " + e.getMessage());
            return false;
        }
    }

    private boolean pythonRunExecutableWithParameters(String submissionPath) {
        try {

            Configuration config = assingnmentConfigurations.get(0);

            // Prepare the command to run the Java program
            String command = config.getCompilerPath()+ " " + submissionPath;

            // Execute the command in the command prompt
            Process executionProcess = Runtime.getRuntime().exec("cmd /c " + command);

            // Capture and print program output
            BufferedReader reader = new BufferedReader(new InputStreamReader(executionProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print program output (e.g., results)
            }

            // Wait for the program execution to complete
            int executionExitValue = executionProcess.waitFor();

            // Check the execution result
            if (executionExitValue == 0) {
                System.out.println("Execution successful.");
                return true;
            } else {
                System.out.println("Execution failed.");
                return false;
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error during execution: " + e.getMessage());
            return false;
        }
    }

    private boolean pythonCompareOutputWithExpected() {

        String outputPath = "python_output.txt"; // Example path to actual output file
        String expectedPath = projectManagerPath; // Example path to expected output file

        try (BufferedReader outputReader = new BufferedReader(new FileReader(outputPath));
             BufferedReader expectedReader = new BufferedReader(new FileReader(expectedPath))) {

            String outputLine;
            String expectedLine = null;

            while ((outputLine = outputReader.readLine()) != null && (expectedLine = expectedReader.readLine()) != null) {
                if (!outputLine.equals(expectedLine)) {
                    System.out.println("Output does not match expected output.");
                    success = false;
                    return false;
                }
            }
            outputLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

            if (outputLine != null || expectedLine != null) {
                System.out.println("Output does not match expected output.");
                success = false;
                return false;
            }

            System.out.println("Output matches expected output.");
            success = true;
            return true;

        } catch (IOException e) {
            System.out.println("Error comparing output with expected: " + e.getMessage());
            success = false;
            return false;
        }
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getLanguage() {
        return language;
    }

    public List<Configuration> getConfigurations() {
        return assingnmentConfigurations;
    }

    public void addConfiguration(Configuration configuration) {
        assingnmentConfigurations.add(configuration);
    }

    public List<StudentSubmission> getStudentSubmissions() {
        return studentSubmissions;
    }

    public void addStudentSubmission(StudentSubmission submission) {
        studentSubmissions.add(submission);
    }

    public List<Result> getResults() {
        return results;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getProjectManagerPath() {
        return projectManagerPath;
    }

    public void setProjectManagerPath(String projectManagerPath) {
        this.projectManagerPath = projectManagerPath;
    }
}
