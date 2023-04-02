package patientmanagement;

import java.util.ArrayList;
import java.util.List;

public class Department 
{
    // Department variables
    private String name;
    // Department Abilities
    private boolean canPrescribeMedication;
    private boolean canAdmitPatients;
    private boolean canDischargeInPatients;
    private boolean canTransferPatient;
    private boolean canHaveOperations;
    // Patient List
    private List<CurrentPatient> patientList = new ArrayList<>();
    // Current Patient
    private CurrentPatient currentPatient;

    public String getName()
    {
        return name;
    }

    public boolean getCanPrescribeMedication()
    {
        return canPrescribeMedication;
    }
    public boolean getCanAdmitPatients()
    {
        return canAdmitPatients;
    }
    public boolean getCanDischargeInPatients()
    {
        return canDischargeInPatients;
    }
    public boolean getCanTransferPatient()
    {
        return canTransferPatient;
    }
    public boolean getCanHaveOperations()
    {
        return canHaveOperations;
    }
    public void addPatient(CurrentPatient patient)
    {
        patientList.add(patient);
    }
    public void removePatient(CurrentPatient patient)
    {
        patientList.remove(patient);
    }
    public void setCurrentPatient(CurrentPatient currentPatient)
    {
        this.currentPatient = currentPatient;
    }
    public CurrentPatient getCurrentPatient()
    {
        return currentPatient;
    }

    public Department(String name)
    {
        switch (name.toLowerCase())
        {
            case "cardiology" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = true;
                canDischargeInPatients = true;
                canTransferPatient = true;
                canHaveOperations = true;
                break;
            }
            case "rheumatology" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = true;
                canDischargeInPatients = true;
                canTransferPatient = false;
                canHaveOperations = false;
                break;
            }
            case "ent" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = true;
                canDischargeInPatients = true;
                canTransferPatient = false;
                canHaveOperations = true;
                break;
            }
            case "ophthalmology" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = true;
                canDischargeInPatients = true;
                canTransferPatient = true;
                canHaveOperations = true;
                break;
            }
            case "occupational therapy" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = false;
                canDischargeInPatients = true;
                canTransferPatient = false;
                canHaveOperations = false;
                break;
            }
            case "radiology" :
            {
                canPrescribeMedication = false;
                canAdmitPatients = false;
                canDischargeInPatients = false;
                canTransferPatient = true;
                canHaveOperations = false;
                break;
            }
            case "oncology" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = true;
                canDischargeInPatients = true;
                canTransferPatient = true;
                canHaveOperations = true;
                break;
            }
            case "obgyn" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = true;
                canDischargeInPatients = true;
                canTransferPatient = true;
                canHaveOperations = true;
                break;
            }
            case "emergency" :
            {
                canPrescribeMedication = true;
                canAdmitPatients = true;
                canDischargeInPatients = true;
                canTransferPatient = true;
                canHaveOperations = true;
                break;
            }
            default : 
            {
                canPrescribeMedication = false;
                canAdmitPatients = false;
                canDischargeInPatients = false;
                canTransferPatient = false;
                canHaveOperations = false;
                break;
            }
        }

        this.name = name;
    }
}
