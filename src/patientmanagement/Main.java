package patientmanagement;

import java.sql.SQLException;

// SBA22075 - Sonel Ali

// Import list
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Main class
public class Main
{
    // Class scanner
    static final Scanner INPUT = new Scanner(System.in);

    // Current department
    static Department currentDepartment = null;
    // Current doctor
    static Doctor currentDoctor = null;
    // Current patient
    static CurrentPatient currentPatient = null;
    // Patient name
    static String patientName = null;
    // Patient Id
    static int patientId = 1;
    // Symptom list
    static List<String> complaintList = new ArrayList<>();
    // Department names array
    static DepartmentNames[] departmentArray = DepartmentNames.values();

    // Main check items
    static String department = null;
    static String speciality = null;
    static boolean needsSurgery = false;
    static boolean needsMedication = false;

    // Main method
    public static void main(String[] args)
    {
        // Loop until user ends program
        do
        {
            // Display menu - menuId determines what menu to display - main menu or doctor menu
            displayOptions(1);
            // Get and initialise menu selection - menuId determines what actions should be taken
            initialiseSelection(getMenuSelection(1), 1);
        }
        while (yesOrNo("\nContinue to main menu?"));
    }

    /*
     * 
     *     MENU METHODS
     * 
     */

    // Method for displaying main menu and doctor menu
    static void displayOptions(int menuId)
    {
        // If menuId == 1, and there is a current patient, show menu with doctor menu available
        if (menuId == 1 && currentPatient != null)
        {
            System.out.println();
            System.out.println("1. Add Patient");
            System.out.println("2. Doctor Menu");
            System.out.println();
        }
        // If menuId == 1, and there is no current patient, show menu without doctor menu
        else if ((menuId == 1 && currentPatient == null))
        {
            System.out.println();
            System.out.println("1. Add Patient");
            System.out.println();
        }
        // If menuId == 2 and currentPatient is an inPatient, show Doctor Inpatient Menu
        else if (menuId == 2 && currentPatient.isInpatient())
        {
            System.out.println();
            System.out.println("1. Transfer To Department");
            System.out.println("2. Perform Surgery");
            System.out.println("3. Prescribe Medication");
            System.out.println("4. Discharge in-patient");
            System.out.println();
        }
        // If menuId == 2 and currentPatient is not an inPatient, show Doctor Outpatient Menu
        else if (menuId == 2 && !currentPatient.isInpatient())
        {
            System.out.println();
            System.out.println("1. Admit In-Patient");
            System.out.println("2. Transfer To Department");
            System.out.println("3. Prescribe Medication");
            System.out.println("4. Discharge out-patient");
            System.out.println();
        }
    }
    // Method for getting users menu selection
    static int getMenuSelection(int menuId)
    {
        // Initialise menu item limit to 0;
        int limit = 0;

        // Determine what menu is in use, set menu item limit appropriately
        if (menuId == 1 && currentPatient != null)
        {
            limit = 2;
        }
        else if (menuId == 1 && currentPatient == null)
        {
            limit = 1;
        }
        else if (menuId == 2)
        {
            limit = 4;
        }

        // Declare user response string 
        String response;
        
        // Loop until valid selection has been retrieved
        do
        {
            // Prompt user for input
            System.out.println("Please enter the number of your menu selection");
            System.out.print(">");
            // Get input
            response = INPUT.nextLine();
            
            // Check response is numeric and also corresponding to menu options
            if (isNumeric(response) && isValidSelection(response, limit))
            {
                // Once validated, break from loop
                break;
            }
            else
            {
                // Inform user that input must be valid
                System.out.println("\nMenu selection must correspond to menu item!\n");
            }
        }
        while(true);
        
        // Once validated, return Integer value of response
        return Integer.parseInt(response);
    } // getMenuSelection
    // Method for validating menu selection
    static boolean isValidSelection(String str, int limit)
    {
        // Declare integer selection variable
        int selection;
        
        // Check if String is numeric, assign to int variable if it is, return false if not
        if (isNumeric(str))
        {
            selection = Integer.parseInt(str);
        }
        else
        {
            return false;
        }
        
        // Return true if selection is within range of menu options
        return selection > 0 && selection <= limit;
    } // isValidSelection
    // Method for executing users selection from Main Menu
    static void initialiseSelection(int selection, int menuId)
    {
        // Setup available menu actions corresponding to what menu was shown
        // If menuId == 1 and there is a current patient, show doctor options
        if (menuId == 1 && currentPatient != null)
        {
            // Switch statement to call methods corresponding to users menu selection
            switch (selection)
            {
                case 1 : addPatient(); break;
                case 2 : 
                {
                    // Display Doctor Menu, get and initialise user selection
                    displayOptions(2);
                    initialiseSelection(getMenuSelection(2), 2);
                }
            }
        }
        // If menuId == 1 and there is no current patient, hide doctor options
        else if ((menuId == 1 && currentPatient == null))
        {
            // Switch statement to call methods corresponding to users menu selection
            switch (selection)
            {
                case 1 : addPatient(); break;
            }
        }
        // If menuId == 2, show doctor options
        else
        {
            // Inpatient doctor options
            if (currentPatient.isInpatient())
            {
                switch (selection)
                {
                    case 1 : transferPatient(); break;
                    case 2 : haveSurgery(); break;
                    case 3 : prescribeMedication(); break;
                    case 4 : dischargeInpatient(); break;
                }
            }
            // Outpatient doctor options
            else if (!currentPatient.isInpatient())
            {
                switch (selection)
                {
                    case 1 : admitInpatient(); break;
                    case 2 : transferPatient(); break;
                    case 3 : prescribeMedication(); break;
                    case 4 : dischargeOutpatient(); break;
                }
            }
        }
    } // initialiseSelection

    /*
     * 
     *     PROGRAM METHODS
     * 
     */
    
    // Method to create and add patient
    static void addPatient() 
    {
        // Reset patient variables
        currentPatient = null;
        needsSurgery = false;
        needsMedication = false;

        // Get patient name
        patientName = takeName();

        // Take complaint string and scan for department names, medication, surgery needs.
        // Loop until valid input has been retrieved.
        while (!complaintScanner(takeComplaint()))
        {
            System.out.println("\nInvalid input. Must contain department name. Please try again");
        }

        // Create appropriate department object for patient
        createDepartment();
        // Create appropriate doctor object for patient
        createDoctor();
        // Create patient
        createPatient();
        // Assign patient to doctor object
        assignPatientToDoctor(currentPatient);

        // Inform user that patient entry has been successful
        System.out.println("\nPatient has successfully been added.\n");
        currentPatient.toString();

        // Ask user if they would like to see doctor options
        if (yesOrNo("\nContinue to doctor menu?"))
        {
            // Ensure options only show if doctor is available
            if (currentDoctor.getName() != "DOCTOR UNAVAILABLE")
            {
                displayOptions(2);
                initialiseSelection(getMenuSelection(2), 2);
            }
            // Inform user they need to wait for doctor to become available
            else
            {
                System.out.println("\nThere is no doctor currently available. Please wait");
            }
        }
    }

    // Method to get patient name
    static String takeName()
    {
        // Variables for response, first name and last name
        String response;
        String firstName;
        String lastName;

        // Loop until valid input has been retrieved
        do
        {
            // Prompt user
            System.out.println("\nPlease enter first name");
            System.out.print(">");
            // Take in user input
            response = INPUT.nextLine();

            // Invalid if input is numeric
            if (isNumeric(response))
            {
                // Inform user
                System.out.println("\nInvalid input.");
                // Return to beginning of loop
                continue;
            }
            else
            {
                // If valid, assign response to first name
                firstName = response;
            }

            // New loop for second name
            do
            {
                // Prompt user
                System.out.println("\nPlease enter last name");
                System.out.print(">");
                // Take in user input
                response = INPUT.nextLine();
                
                // Invalid if numeric
                if (isNumeric(response))
                {
                    // Prompt user
                    System.out.println("\nInvalid input.");
                    // Return to beginning of loop
                    continue;
                }
                else
                {
                    // If valid, assign response to lst name and break from loop
                    lastName = response;
                    break;
                }
            }
            while (true);

            // Return combination of first and last name
            return firstName + " " + lastName;
        }
        while (true);
    }
    
    // Method to populate list with user input
    static String takeComplaint()
    {
        String response;
        
        do
        {
            // Prompt user for input
            System.out.println("\nPlease enter a complaint");
            System.out.print(">");
            
            // Retrieve input
            response = INPUT.nextLine();
            
            // If input is invalid, inform user
            if(isNumeric(response))
            {
                System.out.println("\nInvalid input");
            }
            // Return response if valid
            else
            {
                return response;
            }
        }
        while (true);
    }
    // Method to parse user complaint string for keywords
    static boolean complaintScanner(String complaint)
    {
        // Clear complaint list if already populated from previous entry
        if (complaintList.size() > 0)
        {
            complaintList.clear();
        }
        // Valid complaint checker
        boolean validComplaint = false;

        // DEPARTMENT NAME PATTERNS

        // Cardiology pattern and matcher
        Pattern cardiologyPattern = Pattern.compile("(?i)\\bcardiology\\b");
        Matcher cardiologyMatcher = cardiologyPattern.matcher(complaint);
        // Rheumatology pattern and matcher
        Pattern rheumatologyPattern = Pattern.compile("(?i)\\brheumatology\\b");
        Matcher rheumatologyMatcher = rheumatologyPattern.matcher(complaint);
        // ENT pattern and matcher
        Pattern entPattern = Pattern.compile("(?i)\\b(ent)\\b");
        Matcher entMatcher = entPattern.matcher(complaint);
        // Ophthalmology pattern and matcher
        Pattern ophthalmologyPattern = Pattern.compile("(?i)\\bophthalmology\\b");
        Matcher ophthalmologyMatcher = ophthalmologyPattern.matcher(complaint);
        // Occupational Therapy pattern and matcher
        Pattern occupationalTherapyPattern = Pattern.compile("(?i)\\boccupational therapy\\b");
        Matcher occupationalTherapyMatcher = occupationalTherapyPattern.matcher(complaint);
        // Radiology pattern and matcher
        Pattern radiologyPattern = Pattern.compile("(?i)\\bradiology\\b");
        Matcher radiologyMatcher = radiologyPattern.matcher(complaint);
        // Oncology pattern and matcher
        Pattern oncologyPattern = Pattern.compile("(?i)\\boncology\\b");
        Matcher oncologyMatcher = oncologyPattern.matcher(complaint);
        // OB/GYN pattern and matcher
        Pattern obgnPattern = Pattern.compile("(?i)(OB[/\\s]?GYN|OB|GYN)");
        Matcher obgnMatcher = obgnPattern.matcher(complaint);
        // Emergency pattern and matcher
        Pattern emergencyPattern = Pattern.compile("(?i)\\bemergency\\b");
        Matcher emergencyMatcher = emergencyPattern.matcher(complaint);
        // Medication pattern and matcher
        Pattern medicationPattern = Pattern.compile("(?i)\\bmedication\\b");
        Matcher medicationMatcher = medicationPattern.matcher(complaint);

        // If complaint contains a department name, take appropriate action
        // Add key word to complaints list, set speciality, department, needsSurgery, validComplaint 
        if (cardiologyMatcher.find())
        {
            complaintList.add("cardiology");
            speciality = "cardiology";
            department = "cardiology";
            needsSurgery = determineSurgeryNeed();
            validComplaint = true;
        }
        else if (rheumatologyMatcher.find())
        {
            complaintList.add("rheumatology");
            speciality = "rheumatology";
            department = "rheumatology";
            validComplaint = true;
        }
        else if (entMatcher.find())
        {
            complaintList.add("ent");
            speciality = "ent";
            department = "ent";
            needsSurgery = determineSurgeryNeed();
            validComplaint = true;
        }
        else if (ophthalmologyMatcher.find())
        {
            complaintList.add("ophthalmology");
            speciality = "ophthalmology";
            department = "ophthalmology";
            needsSurgery = determineSurgeryNeed();
            validComplaint = true;
        }
        else if (occupationalTherapyMatcher.find())
        {
            complaintList.add("occupational therapy");
            speciality = "occupational therapy";
            department = "occupational therapy";
            validComplaint = true;
        }
        else if (radiologyMatcher.find())
        {
            complaintList.add("radiology");
            speciality = "radiology";
            department = "radiology";
            validComplaint = true;
        }
        else if (oncologyMatcher.find())
        {
            complaintList.add("oncology");
            speciality = "oncology";
            department = "oncology";
            needsSurgery = determineSurgeryNeed();
            validComplaint = true;
        }
        else if (obgnMatcher.find())
        {
            complaintList.add("OB/GYN");
            speciality = "OB/GYN";
            department = "OB/GYN";
            needsSurgery = determineSurgeryNeed();
            validComplaint = true;
        }
        else if (emergencyMatcher.find())
        {
            complaintList.add("emergency");
            speciality = getSpeciality();
            department = "emergency";
            needsSurgery = determineSurgeryNeed();
            validComplaint = true;
        }
        
        // Department name check must be if,else if structure, so only 1 is selected
        // Medication check is separate as this could turn up as well as department name
        if (medicationMatcher.find())
        {
            complaintList.add("medication");
            needsMedication = true;
            validComplaint = true;
        }

        // Return true if complaint is valid, false if not
        return validComplaint;
    }

    // Method to ask user main questions about patient
    static boolean determineSurgeryNeed()
    {
        // SURGERY?
        if (yesOrNo("\nIs surgery needed?"))
        {
            // Add to complaint list if yes
            complaintList.add("surgery");
            return true;
        }
        else
        {
            // return false if not
            return false;
        }
    }
    // Method to create Department object
    static void createDepartment()
    {
        currentDepartment = new Department(department);
    }
    // Method to et speciality needed
    static String getSpeciality()
    {
        // Response variable
        String response;

        // Loop unitl valid input has been retrieved
        do 
        {
            // Display department names for speciality choices
            System.out.println();
            for (DepartmentNames dpName : departmentArray)
            {
                // Skip emergency value as it is not a valid speciality
                if (dpName.getValue() == 9)
                {
                    continue;
                }

                System.out.println(dpName.getValue() + " " + dpName.toString());
            }
            System.out.println("\nPlease select a speciality");

            // Take user response
            response = INPUT.nextLine();

            // Validate response
            if (!isNumeric(response))
            {
                System.out.println("\nInvalid input. Please select a valid option");
            }
            else if (Integer.parseInt(response) >= departmentArray.length || Integer.parseInt(response) <= 0)
            {
                System.out.println("\nInvalid input. Please select a valid option");
            }
            else
            {
                // If valid, display and return chosen speciality
                System.out.println("\n" + departmentArray[Integer.parseInt(response) - 1].name());
                return departmentArray[Integer.parseInt(response) - 1].name().replace("_", " ");
            }
        }
        while (true);
    }
    // Method to create Doctor object
    static void createDoctor()
    {
        // Only create doctor object if a valid Department object has been created and there is a valid speciality
        if (currentDepartment != null && speciality != null)
        {
            currentDoctor = new Doctor(currentDepartment, speciality, needsSurgery);
        }
        else
        {
            // Inform user if there is no department
            System.out.println("\nNo current department");
        }
    }
    // Method to create patient object
    static void createPatient()
    {
        if (currentDepartment != null && currentDoctor != null)
        {
            currentPatient 
                = new CurrentPatient
                    (patientName, 
                    needsSurgery, 
                    needsMedication, 
                    complaintList.toArray(new String[complaintList.size()]),
                    patientId++,
                    currentDepartment.getName(),
                    currentDoctor.getName());
        }
    }
    // Method to assign patient to doctor object
    static void assignPatientToDoctor(CurrentPatient patient)
    {
        currentDoctor.setCurrentPatient(patient);
    }
    // Method to save patient data to database
    static void savePatientData(String[] patientData)
    {
        // Declare and initialise PatientDataLoader object
        PatientDataLoader myLoader = new PatientDataLoader();
        // Try block for creating database, creating table and inserting data
        try 
        {
            if (!myLoader.databaseExists())
            {
                myLoader.createDB();
                myLoader.createTable();
            }
            myLoader.insertData(patientData);
        } 
        // Multi-catch clause
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) 
        {
            e.printStackTrace();
        }
    }

    /*
     * 
     *     DOCTOR METHODS
     * 
     */

    // Method to transfer patient to new department
    static void transferPatient()
    {
        // Inform user if there is no current patient in use, skip functionality if so
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        // Validate if doctor has the ability to transfer patients
        else if (!currentDoctor.getCanTransferPatient())
        {
            System.out.println("\nDoctor does not have the ability to transfer patients");
        }
        else
        {
            // Take complaint string and scan for department names, medication 
            while (!complaintScanner(takeComplaint()))
            {
                System.out.println("\nInvalid input. Must contain department name. Please try again");
            }
            // Create appropriate department object for patient
            createDepartment();
            // Create appropriate doctor object for patient
            createDoctor();
            // Assign patient to doctor object
            assignPatientToDoctor(currentPatient);
            // Set the department on the patient object
            currentPatient.transferDepartment(currentDepartment.getName());
            // Set the doctor on the patient object
            currentPatient.setDoctor(currentDoctor.getName());
            // Update patient complaints list
            currentPatient.setComplaints(complaintList.toArray(new String[complaintList.size()]));

            // Inform user that patient entry has been successful
            System.out.println("\nPatient has successfully been updated.\n");
            // Display patient details
            currentPatient.toString();

            // Ask user if they would like to see doctor options
            if (yesOrNo("\nContinue to doctor menu?"))
            {
                // Only show doctor options if doctor is available, inform user if they aren't
                if (currentDoctor.getName() != "DOCTOR UNAVAILABLE")
                {
                    displayOptions(2);
                    initialiseSelection(getMenuSelection(2), 2);
                }
                else
                {
                    System.out.println("\nThere is no doctor currently available. Please wait");
                }
            }
        }
    }
    // Method to perform surgery on patient
    static void haveSurgery()
    {
        // Only if there is a current doctor in use
        if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        // Only if doctor has the ability to perform surgery
        else if (!currentDoctor.getCanHaveOperations())
        {
            System.out.println("\nDoctor does not have the ability to perform surgery");
        }
        // Only if there is a current patient in use
        else if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            currentDoctor.haveSurgery();
        }
    }
    // Method to prescribe medication
    static void prescribeMedication()
    {
        // Only if there is a current doctor in use
        if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        // Only if doctor has the ability to prescribe medication
        else if(!currentDoctor.getCanPrescribeMedication())
        {
            System.out.println("\nDoctor does not have the ability to prescribe medication.");
        }
        // Only if there is a current patient in use
        else if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            currentDoctor.prescribeMedication();
        }
    }
    // Method to discharge inpatients
    static void dischargeInpatient()
    {
        // Only if there is a current doctor in use
        if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        // Only if the doctor has the ability to discharge inpatients
        else if (!currentDoctor.getCanDischargeInPatients())
        {
            System.out.println("\nDoctor does not have the ability to discharge inpatients.");
        }
        // Only if there is a current patient in use
        else if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            savePatientData(currentPatient.patientToArray());
            currentDoctor.dischargeInpatient();
            currentPatient = null;
            System.out.println("\nPatient has successfully been discharged");
        }
    }
    // Method to admit inpatients
    static void admitInpatient()
    {
        // Only if there is a doctor available
        if (currentDoctor == null || currentDoctor.getName() == "DOCTOR UNAVAILABLE")
        {
            System.out.println("\nNo current doctor");
        }
        // Only if the doctor has the ability to admit inpatients
        else if (!currentDoctor.getCanAdmitPatients())
        {
            System.out.println("\nDoctor does not have the ability to admit inpatients.");
        }
        // Only if there is a current patient in use
        else if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            currentDoctor.admitInpatient();
        }
    }
    // Method to discharge outpatient
    static void dischargeOutpatient()
    {
        // Only if there is a current patient in use
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        // Only if there is a current doctor in use and available
        else if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor available");
        }
        else
        {
            savePatientData(currentPatient.patientToArray());
            currentPatient = null;
            System.out.println("\nPatient has successfully been discharged");
        }
    }

    /*
     * 
     *     HELPER METHODS
     * 
     */

    // Method for asking user YES or NO questions
    static boolean yesOrNo(String question)
    {
        String response;
        
        // Loop until valid input has been retrieved
        while(true)
        {
            // Present given question to user
            System.out.println(question + "\nY/N");
            System.out.print(">");
            
            // Retrieve response
            response = INPUT.nextLine();
            
            // If response is 'Y', return true
            if (response.equalsIgnoreCase("Y"))
            {
                return true;
            }
            // If response is 'N', return false
            else if (response.equalsIgnoreCase("N"))
            {
                return false;
            }
            // If response is anything else, inform user of invalid input
            else
            {
                System.out.println("\nInvalid input!");
            }
        }
    } // yesOrNo

    // Method for validating numeric input
    static boolean isNumeric(String str)
    {
        // Try convert String to integer
        try
        {
            Integer.valueOf(str);
        }
        // Return false if String is not numeric
        catch(NumberFormatException e)
        {
            return false;
        }
        // Return true once validated
        return true;
    } // isNumeric
}
