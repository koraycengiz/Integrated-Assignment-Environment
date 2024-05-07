import java.util.List;
import java.util.zip.ZipEntry;

public class StudentSubmission {

    private String submissionId;
    private String assignmentId;
    private String studentId;
    private List<ZipProcessor> submittedFiles;

    private String submissionPath;

    public StudentSubmission(String submissionId, String submissionPath) {
        this.submissionId = submissionId;
        this.submissionPath = submissionPath;
    }

    public void processSubmission(){
        //TO-DO write the method
    }

    public void unzip(){
        //TO-DO write the method
    }

    public void compileOrRun(){
        //TO-DO write the method
    }

    public void executeCommand(){
        //TO-DO write the method
    }

    public String getSubmissionPath() {
        return submissionPath;
    }

    public void setSubmissionPath(String submissionPath) {
        this.submissionPath = submissionPath;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<ZipProcessor> getSubmittedFiles() {
        return submittedFiles;
    }

    public void setSubmittedFiles(List<ZipProcessor> submittedFiles) {
        this.submittedFiles = submittedFiles;
    }
}
