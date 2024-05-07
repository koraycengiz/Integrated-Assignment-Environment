public class Main {
    public static void main(String[] args) {

        Assignment assignment = new Assignment("A2", "Java");

        String randomStudentId = "20200602015";
        String randomSubmissionPath = "C:\\Users\\Koray\\Desktop\\Test\\src\\test.java"; // Replace with actual path later
        assignment.addStudentSubmission(new StudentSubmission(randomStudentId, randomSubmissionPath));

        assignment.evaluateStudentSubmissions();
    }
}
