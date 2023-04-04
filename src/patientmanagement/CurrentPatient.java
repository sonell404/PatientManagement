package patientmanagement;

// SBA22075 - Sonel Ali

// Imports
import java.util.Arrays;

/*
 * According to the brief, there will be a Patient class used in program testing that implements an interface,
 * but currently we only have access to the interface and not the class. To ensure my program works
 * for who ever is testing next, I have created a test Patient class with bare functionality according to 
 * the interface and then another CurrentPatient class that extends the Patient class. This has allowed me to
 * implement the functionality provided in the interface and also some extra functionality without 
 * prohibiting the next person from running the program with the currently unavailable Patient class
 */
public class CurrentPatient extends Patient
{
    // Class variables
    private String name;
    private int patientId;
    private boolean isInpatient;
    private String[] complaints;
    private boolean needsMedication;
    private boolean needsSurgery;
    private String department;
    private String doctor;

    // Getters & Setters
    @Override
    public String getName()
    {
        return name;
    }
    public void setPatientID(int patientId)
    {
        this.patientId = patientId;
    }
    @Override
    public int getPatientId()
    {
        return patientId;
    }
    public void setComplaints(String[] complaints)
    {
        this.complaints = complaints;

        if (Arrays.asList(complaints).contains("medication"))
        {
            System.out.println("Patient needs medication");
            needsMedication = true;
        }
        if (Arrays.asList(complaints).contains("surgery"))
        {
            System.out.println("Patient needs surgery");
            needsSurgery = true;
        }
    }
    @Override
    public String[] getComplaints()
    {
        return complaints;
    }
    public void setDepartment(String department)
    {
        this.department = department;
    }
    @Override
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

    // Boolean check methods
    @Override
    public boolean isInpatient()
    {
        return isInpatient;
    }
    @Override
    public boolean needsMedication()
    {
        return needsMedication;
    }
    @Override
    public boolean needsSurgery()
    {
        return needsSurgery;
    }
    
    // Override parent method to transfer department
    @Override
    public void transferDepartment(String department)
    {
        if (Arrays.asList(complaints).contains("medication"))
        {
            System.out.println("Patient needs medication");
            needsMedication = true;
        }
        if (Arrays.asList(complaints).contains("surgery"))
        {
            System.out.println("Patient needs surgery");
            needsSurgery = true;
        }

        setDepartment(department);
    }
    // Override parent method to have surgery
    @Override
    public void haveSurgery(String speciality)
    {
        System.out.println("\nPatient received " + speciality + " surgery");
    }
    // Override method to prescribe medication
    @Override
    public void prescribeMedication(String speciality)
    {
        System.out.println("\nPatient received " + speciality + " medication");
    }
    // Override method to admit to inpatients
    @Override
    public void admitInpatient(String department)
    {
        this.department = department;
        isInpatient = true;
        System.out.println("\nPatient has succesfully been admitted to " + this.department);
    }
    // Override method to discharge from inpatients
    @Override
    public void dischargeInpatient()
    {
        isInpatient = false;
        System.out.println("\nPatient has successfully been discharged");
    }

    // Class constructor
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

    public String[] patientToArray()
    {
        String[] patientDetails = new String[8];

        patientDetails[0] = getName();
        patientDetails[1] = Boolean.toString(isInpatient);
        patientDetails[2] = Arrays.toString(complaints);
        patientDetails[3] = Boolean.toString(needsMedication);
        patientDetails[4] = Boolean.toString(needsSurgery);
        patientDetails[5] = department;
        patientDetails[6] = doctor;

        return patientDetails;
    }

    // Override Object toString method to display patient details
    @Override
    public String toString()
    {
        System.out.printf("%-25s%-3s", "PatientID:", patientId);
        System.out.printf("\n%-25s%-3s", "Name:", name);
        System.out.printf("\n%-25s%-3s", "Department:", department);
        System.out.printf("\n%-25s%-3s", "Doctor:", doctor);
        System.out.printf("\n%-25s%-3s", "Complaints:", Arrays.toString(complaints));
        System.out.printf("\n%-25s%-3s", "Surgery needed:", needsSurgery);
        System.out.printf("\n%-25s%-3s", "Medication needed:", needsMedication);
        System.out.println();
        return "";
    }
} // Class
