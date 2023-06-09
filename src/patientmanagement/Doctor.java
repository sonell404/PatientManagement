package patientmanagement;

// SBA22075 - Sonel Ali

// Imports
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * Instead of implementing the PatientInterface and being obliged to using all of its methods,
 * I chose to work with a CurrentPatient object that extends the Patient class that 
 * implements the interface instead and use it's methods that way. This reduces unnecessary 
 * code in this class.
 */
// Doctor class
public class Doctor 
{  
    // CSV Variables - to be set according to provided csv file
    private String name;
    private String department;
    private String specialty;
    private boolean surgeon;

    // Ability Variables - to be set by Department object
    private boolean canPrescribeMedication;
    private boolean canAdmitPatients;
    private boolean canDischargeInPatients;
    private boolean canTransferPatient;
    private boolean canHaveOperations;

    // Current Patient
    private CurrentPatient currentPatient;
    
    // Getters & Setters
    // CSV 
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
        return surgeon;
    }
    public void setCurrentPatient(CurrentPatient currentPatient)
    {
        this.currentPatient = currentPatient;
    }
    public CurrentPatient getCurrentPatient()
    {
        return currentPatient;
    }

    // Abilities
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

    // Patient methods
    public void transferDepartment(String department)
    {
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            currentPatient.transferDepartment(department);
        }
    }
    public void haveSurgery()
    {
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else if (!currentPatient.needsSurgery())
        {
            System.out.println("\nPatient does not need surgery");
        }
        else
        {
            currentPatient.haveSurgery(this.specialty);
        }
    }
    public void prescribeMedication()
    {
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else if (!currentPatient.needsMedication())
        {
            System.out.println("\nPatient does not need medication");
        }
        else
        {
            currentPatient.prescribeMedication(this.specialty);
        }
    }
    public void admitInpatient()
    {
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            currentPatient.admitInpatient(this.department);
        }
    }
    public void dischargeInpatient()
    {
        if (canDischargeInPatients)
        {
            
        }
        else if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            currentPatient.dischargeInpatient();
            currentPatient = null;
        }
    }

    // Constructor
    public Doctor(Department department, String speciality, boolean isSurgeon)
    {
        // Assign department abilities to doctor abilities
        canPrescribeMedication = department.getCanPrescribeMedication();
        canAdmitPatients = department.getCanAdmitPatients();
        canDischargeInPatients = department.getCanDischargeInPatients();
        canTransferPatient = department.getCanTransferPatient();
        canHaveOperations = department.getCanHaveOperations();
        // Set department name
        setDepartment(department.getName());
        // Set speciality variable
        setSpecialty(speciality);
        // Set surgeon variable
        surgeon = isSurgeon;

        // Try with resources for csv file reader
        try (BufferedReader br = new BufferedReader(new FileReader("src/patientmanagement/Sláintecare Doctor Info.csv")))
        {
            // Initialise line variable that will hold line of values from file
            String line = "";
            // Skip the first header row
            br.readLine();

            // Loop through file
            while ((line = br.readLine()) != null) 
            {
                // Assign values of the current line to an array without the comma
                String[] values = line.split(",");

                // If department is emergency and surgery is needed, search for appropriate doctor
                if ((department.getName().equalsIgnoreCase("emergency")) 
                    && isSurgeon()
                    && values[1].equalsIgnoreCase("emergency")
                    && values[2].equalsIgnoreCase(getSpecialty())
                    && values[3].equalsIgnoreCase("yes")
                    && values[4].equalsIgnoreCase("yes"))
                {
                    // Set name with appropriate doctors name and break from loop
                    setName(values[0]);
                    break;
                }
                // If department is emergency and surgery is not needed, search for appropriate doctor
                else if ((getDepartment().equalsIgnoreCase("emergency")) 
                        && !isSurgeon()
                        && values[1].equalsIgnoreCase("emergency")
                        && values[2].equalsIgnoreCase(getSpecialty())
                        && values[4].equalsIgnoreCase("yes"))
                {
                    // Set name with appropriate doctors name and break from loop
                    setName(values[0]);
                    break;
                }
                // If department is not emergency and surgery is needed, search for appropriate doctor
                else if (!(getDepartment().equalsIgnoreCase("emergency")) 
                        && isSurgeon()
                        && values[1].equalsIgnoreCase(getSpecialty())
                        && values[3].equalsIgnoreCase("yes")
                        && values[4].equalsIgnoreCase("yes"))
                {
                    // Set name with appropriate doctors name and break from loop
                    setName(values[0]);
                    break;
                }
                // If department is not emergency and surgery is not needed, search for appropriate doctor
                else if (!(getDepartment().equalsIgnoreCase("emergency")) 
                        && !isSurgeon()
                        && values[1].equalsIgnoreCase(getSpecialty())
                        && values[4].equalsIgnoreCase("yes"))
                {
                    // Set name with appropriate doctors name and break from loop
                    setName(values[0]);
                    break;
                }
            }

            // If no appropriate doctor was found, set variables as unavailable, so user can see
            if (getName() == null)
            {
                setName("DOCTOR UNAVAILABLE");
                setDepartment("DOCTOR UNAVAILABLE");
                setSpecialty("DOCTOR UNAVAILABLE");
            }
        }
        // Catch NullPointerException
        catch (NullPointerException e)
        {
            // Inform user
            System.out.println("BLEH");
            e.printStackTrace();
        }
        // Catch FileNotFoundException
        catch (FileNotFoundException e)
        {
            // Inform user
            System.out.println("Error with file");
            // Print exception stack trace for further detail
            e.printStackTrace();
        }
        // Catch IOException
        catch (IOException e)
        {
            // Inform user
            System.out.println("Error closing reader");
            // Print exception stack trace for further detail
            e.printStackTrace();
        } // try/catch
    } // Constructor
} // Class
