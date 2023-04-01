package patientmanagement;

public class Patient implements PatientInterface
{
    private String firstName;
    private String lastName;
    private int patientId;
    private String department;
    private String[] complaints;

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public String getName()
    {
        return firstName + lastName;
    }

    public void setPatientID(int patientID)
    {
        this.patientId = patientID;
    }
    public int getPatientId()
    {
        return patientID;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }
    public String getDepartment()
    {
        return department;
    }

    public String[] getComplaints()
    {
        return complaints;
    }

    public boolean isInpatient()
    {
        
    }

    public Patient(String firstName, String lastName, String[] complaints)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.complaints = complaints;
    }

    public boolean needsMedication()
    {
        return false;
    }

    public boolean needsSurgery()
    {
        return false;
    }
}
