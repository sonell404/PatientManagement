package patientmanagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Doctor 
{  
    // CSV Variables
    private String name;
    private String department;
    private String specialty;
    private boolean surgeon;

    // Ability Variables
    private boolean canPrescribeMedication;
    private boolean canAdmitPatients;
    private boolean canDischargeInPatients;
    private boolean canTransferPatient;
    private boolean canHaveOperations;
    
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
                // Assign values of line to an array without the comma
                String[] values = line.split(",");

                // If department is emergency and surgery is needed, search for appropriate doctor
                if ((getDepartment().equalsIgnoreCase("emergency")) && isSurgeon())
                {
                    // If the current doctor meets the criteria needed and is on duty, set the name with current doctor name
                    if (values[1].equalsIgnoreCase("emergency")
                        && values[2].equalsIgnoreCase(getSpecialty())
                        && values[3].equalsIgnoreCase("yes")
                        && values[4].equalsIgnoreCase("yes"))
                    {
                        setName(values[0]);
                        break;
                    }
                }
                // If department is emergency and surgery is not needed, search for appropriate doctor
                else if ((getDepartment().equalsIgnoreCase("emergency")) && !isSurgeon())
                {
                    // If the current doctor meets the criteria needed and is on duty, set the name with current doctor name
                    if (values[1].equalsIgnoreCase("emergency")
                        && values[2].equalsIgnoreCase(getSpecialty())
                        && values[3].equalsIgnoreCase("no")
                        && values[4].equalsIgnoreCase("yes"))
                    {
                        setName(values[0]);
                        break;
                    }
                }
                // If department is not emergency and surgery is needed, search for appropriate doctor
                else if (!(getDepartment().equalsIgnoreCase("emergency")) && isSurgeon())
                {
                    // If the current doctor meets the criteria needed and is on duty, set the name with current doctor name
                    if (values[1].equalsIgnoreCase(getSpecialty())
                        && values[3].equalsIgnoreCase("yes")
                        && values[4].equalsIgnoreCase("yes"))
                    {
                        setName(values[0]);
                        break;
                    }
                }
                // If department is not emergency and surgery is not needed, search for appropriate doctor
                else if (!(getDepartment().equalsIgnoreCase("emergency")) && isSurgeon())
                {
                    // If the current doctor meets the criteria needed and is on duty, set the name with current doctor name
                    if (values[1].equalsIgnoreCase(getSpecialty())
                        && values[3].equalsIgnoreCase("no")
                        && values[4].equalsIgnoreCase("yes"))
                    {
                        setName(values[0]);
                        break;
                    }
                }
            }
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
