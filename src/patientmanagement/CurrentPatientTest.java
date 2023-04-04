package patientmanagement;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CurrentPatientTest 
{
    
    @Test
    public void testGetName()
    {
        // Print out method being tested to debug console
        System.out.println("getName");

        // Create instance of CurrentPatient
        CurrentPatient instance = new CurrentPatient();

        // Test string, set name
        String test = "John";
        instance.setName(test);

        // Call method being tested and assign value to variable for testing
        String result = instance.getName();

        // Test result variable
        assertEquals(test, result); 
    }
}
