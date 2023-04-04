package patientmanagement;

import static org.junit.Assert.*;
import org.junit.Test;

public class DepartmentTest 
{
    // Test Department constructor
    @Test
    public void testDepartmentConstructor()
    {
        // Test object with 'oncology' string passed
        Department testDepartment = new Department("oncology");

        // Test object is not null
        assertNotNull(testDepartment);
        // Test variables that should be set are getting set and to the correct values
        assertEquals(true, testDepartment.getCanPrescribeMedication());
        assertEquals(true, testDepartment.getCanAdmitPatients());
        assertEquals(true, testDepartment.getCanDischargeInPatients());
        assertEquals(true, testDepartment.getCanTransferPatient());
        assertEquals(true, testDepartment.getCanHaveOperations());
    }
}
