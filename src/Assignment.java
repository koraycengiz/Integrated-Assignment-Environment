import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Assignment {
    private String assignmentId;
    private String language;
    private List<Configuration> configurations;
    private List<StudentSubmission> studentSubmissions;
    private List<Result> results;

    public Assignment(String assignmentId, String language) {
        this.assignmentId = assignmentId;
        this.language = language;
        this.configurations = new ArrayList<>();
        this.studentSubmissions = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public void evaluateStudentSubmissions() {
        for (StudentSubmission submission : studentSubmissions) {
            boolean compilationSuccess = compileSourceCode(submission.getSubmissionPath());
            if (!compilationSuccess) {
                System.out.println("Compilation failed for student ID: " + submission.getStudentId());
                continue;
            }

            boolean executionSuccess = runExecutableWithParameters(submission.getSubmissionPath());
            if (!executionSuccess) {
                System.out.println("Execution failed for student ID: " + submission.getStudentId());
                continue;
            }

            boolean outputCorrect = compareOutputWithExpected();
            if (outputCorrect) {
                System.out.println("Submission passed for student ID: " + submission.getSubmissionId());
            } else {
                System.out.println("Submission failed for student ID: " + submission.getSubmissionId());
            }
        }
    }

    private boolean compileSourceCode(String submissionPath) {

        String compilerPath = "javac"; // Java compiler command
        String compilerParameters = "";

        try {
            String compileCommand = compilerPath + " " + submissionPath + " " + compilerParameters;
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

    private boolean runExecutableWithParameters(String submissionPath) {
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

            // Prepare the command to run the Java program
            String command = "java " + submissionPath;

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


    private boolean compareOutputWithExpected() {

        String outputPath = "C:\\Users\\Koray\\Desktop\\Test\\output.txt"; // Example path to actual output file
        String expectedPath = "C:\\Users\\Koray\\Desktop\\Test\\answerkey.txt"; // Example path to expected output file

        try (BufferedReader outputReader = new BufferedReader(new FileReader(outputPath));
             BufferedReader expectedReader = new BufferedReader(new FileReader(expectedPath))) {

            String outputLine;
            String expectedLine = null;

            while ((outputLine = outputReader.readLine()) != null && (expectedLine = expectedReader.readLine()) != null) {
                if (!outputLine.equals(expectedLine)) {
                    System.out.println("Output does not match expected output.");
                    return false;
                }
            }
            outputLine = outputReader.readLine();
            expectedLine = expectedReader.readLine();

            if (outputLine != null || expectedLine != null) {
                System.out.println("Output does not match expected output.");
                return false;
            }

            System.out.println("Output matches expected output.");
            return true;

        } catch (IOException e) {
            System.out.println("Error comparing output with expected: " + e.getMessage());
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
        return configurations;
    }

    public void addConfiguration(Configuration configuration) {
        configurations.add(configuration);
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
}
