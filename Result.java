import java.util.HashMap;
import java.util.Stack;

import javafx.*;

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

    public void displayReports(Stage stage) {
        Text text=new Text();
        text.setText("StudentID\tSuccess");
        StackPane root=new StackPane();
        Scene scene=new Scene(root, 300, 400);
        root.getChildren().add(text);
        Text text2=new Text();
        text2.setText();
        root.getChildren().add(text2);
        for (String studentID : reports.keySet()) {
            Text text3=new Text();
            String success = reports.get(studentID);
            text3.setText(studentID + "\t" + success);
            root.getChildren().add(text3);
        }
        stage.setScene(scene);
        stage.setTitle("Result");
        stage.show();
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
