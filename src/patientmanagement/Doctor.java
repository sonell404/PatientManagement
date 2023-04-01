package patientmanagement;

public class Doctor 
{
    private String name;
    private String department;
    private String specialty;
    
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }
    public String getDepartment()
    {
        return department;
    }

    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }
    public String getSpecialty()
    {
        return specialty;
    }

    public boolean isSurgeon()
    {
        return true;
    }

    public boolean onDuty()
    {
        return true;
    }
}
