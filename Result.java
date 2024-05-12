import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Result {
    private HashMap<String, String> reports;
    private String resultID;
    private String assignmentID;
    private String studentID;
    private boolean success;
    private String errorMessage;

    public Result() {
        this.reports = new HashMap<>();
    }

    public Result(String resultID, String assignmentID, String studentID, boolean success, String errorMessage) {
        this.reports = new HashMap<>();
        this.resultID = resultID;
        this.assignmentID = assignmentID;
        this.studentID = studentID;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    public void addReport(String studentID, String status) {
        reports.put(studentID, status);
    }

    public void displayReports() {
        System.out.println("StudentID\tSuccess");
        System.out.println();
        for (String studentID : reports.keySet()) {
            String success = reports.get(studentID);
            System.out.println(studentID + "\t" + success);
        }
    }

    public HashMap<String, String> getReports() {
        return reports;
    }

    public void setReports(HashMap<String, String> reports) {
        this.reports = reports;
    }

    public String getResultID() {
        return resultID;
    }

    public void setResultID(String resultID) {
        this.resultID = resultID;
    }

    public String getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(String assignmentID) {
        this.assignmentID = assignmentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
