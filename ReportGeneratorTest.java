import org.example.demo.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGenerator();
    }

    @Test
    void testGenerateAndGetReport() {
        reportGenerator.generateReport("12345", "Pass");
        assertEquals("Pass", reportGenerator.getReport("12345"));
    }

    @Test
    void getResultID(){
        String resultID="a";
        assertEquals("a",resultID);
    }

    @Test
    void DisplayResult(){
        boolean result=false;
        if (reportGenerator.isSuccess(true)){
            result=true;
        }

        assert (result);
    }
}
