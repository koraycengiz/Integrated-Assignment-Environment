import java.util.HashMap;
import java.util.Map;

public class ReportGenerator {
    private Map<String, String> reports = new HashMap<>();

    private String resultID;
    private String assignmentID;
    private String studentID;
    private boolean success=false;
    private String errorMessage;

    public void setResultID(String resultID) {
        this.resultID = resultID;
    }
    public String getResultID() {return resultID;}

    public void setAssignmentID(String assignmentID) {
        this.assignmentID = assignmentID;
    }
    public String getAssignmentID() {return assignmentID;}

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentID() {return studentID;}

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public String getErrorMessage() {return errorMessage;}

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean isSuccess(boolean success){
        if (success==true){
            this.success=true;
        }
        return this.success;
    }

    public void displayResult(){
        if (isSuccess(this.success)){
            getResultID();
            getAssignmentID();
            getStudentID();
            generateReport(studentID,getReport(studentID));
        }else {
            getErrorMessage();
        }
    }
    public void generateReport(String studentId, String reportContent) {
        reports.put(studentId, reportContent);
    }

    public String getReport(String studentId) {
        return reports.getOrDefault(studentId, "No report available for this student");
    }
}
