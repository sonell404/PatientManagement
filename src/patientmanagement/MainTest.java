package patientmanagement;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MainTest 
{
    
    // Before running this test, go to method in Main class and comment out call to determineSurgeryNeed()
    @Test
    public void testComplaintScanner()
    {
        // Print out method being tested to debug console
        System.out.println("complaintScanner");
        /*
         * If testing a valid input (e.g. "oncology"), change expected value to true
         * If testing an invalid input (e.g. "onccogy"), change expected value to false
         */
        assertEquals(true, Main.complaintScanner("oncology"));
    }
    
}
