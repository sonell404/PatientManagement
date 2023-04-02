package patientmanagement;

public class CurrentPatient extends Patient
{
    private String name;
    private int patientId;
    private boolean isInpatient;
    private String[] complaints;
    private boolean needsMedication;
    private boolean needsSurgery;
    private String department;
    private String doctor;

    public String getName()
    {
        return name;
    }
    public void setPatientID(int patientId)
    {
        this.patientId = patientId;
    }
    public int getPatientId()
    {
        return patientId;
    }
    public void setComplaints(String[] complaints)
    {
        this.complaints = complaints;
    }
    public String[] getComplaints()
    {
        return complaints;
    }
    public boolean isInpatient()
    {
        return isInpatient;
    }
    public boolean needsMedication()
    {
        return needsMedication;
    }
    public boolean needsSurgery()
    {
        return needsSurgery;
    }
    public void setDepartment(String department)
    {
        this.department = department;
    }
    public String getDepartment()
    {
        return department;
    }
    public void setDoctor(String doctor)
    {
        this.doctor = doctor;
    }
    public String getDoctor()
    {
        return doctor;
    }
    
    public void transferDepartment(String department)
    {
        if (complaints[complaints.length - 1].contains("medication"))
        {
            needsMedication = true;
        }
        if (complaints[complaints.length - 1].contains("surgery"))
        {
            needsSurgery = true;
        }

        if (needsMedication)
        {
            System.out.println("Patient needs medication");
        }
        if (needsSurgery)
        {
            System.out.println("Patient needs surgery");
        }
        setDepartment(department);
    }
    public void haveSurgery(String speciality)
    {
        System.out.println("\nPatient received " + speciality + " surgery");
    }
    public void prescribeMedication(String speciality)
    {
        System.out.println("\nPatient received " + speciality + " medication");
    }
    public void admitInpatient(String department)
    {
        this.department = department;
        isInpatient = true;
        System.out.println("\nPatient has succesfully been admitted to " + this.department);
    }
    public void dischargeInpatient()
    {
        isInpatient = false;
        System.out.println("\nPatient has successfully been discharged");
    }

    public CurrentPatient
            (String name, 
            boolean needsSurgery,
            boolean needsMedication,
            String[] complaints, 
            int patientId,
            String department,
            String doctor)
    {
        this.name = name;
        this.needsSurgery = needsSurgery;
        this.needsMedication = needsMedication;
        this.complaints = complaints;
        this.patientId = patientId;
        this.department = department;
        this.doctor = doctor;
    }
}
