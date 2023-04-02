package patientmanagement;

public class Patient implements PatientInterface
{
    private String name;
    private int patientID;
    private String department;
    private boolean isInpatient;
    private String[] complaints;
    private boolean needsMedication;
    private boolean needsSurgery;

    public String getName()
    {
        return name;
    }
    public int getPatientId()
    {
        return patientID;
    }
    public String getDepartment()
    {
        return department;
    }
    public boolean isInpatient()
    {
        return isInpatient;
    }
    public String[] getComplaints()
    {
        return complaints;
    }
    public boolean needsMedication()
    {
        return needsMedication;
    }
    public boolean needsSurgery()
    {
        return needsSurgery;
    }
    
    public void transferDepartment(String department)
    {

    }
    public void haveSurgery(String speciality)
    {

    }
    public void prescribeMedication(String speciality)
    {

    }
    public void admitInpatient(String department)
    {

    }
    public void dischargeInpatient()
    {

    }
}
