package patientmanagement;

// SBA22075 - Sonel Ali

// IMPORT LIST
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Main CLASS
public class Main
{
    // CLASS SCANNER
    static final Scanner INPUT = new Scanner(System.in);
    // CURRENT DEPARTMENT
    static Department currentDepartment = null;
    // CURRENT DOCTOR
    static Doctor currentDoctor = null;
    // CURRENT PATIENT
    static CurrentPatient currentPatient = null;
    // PATIENT TYPE
    static boolean inPatient = false;
    // PATIENT LIST
    static List<Patient> patientList = new ArrayList<>();
    // SYMPTOM LIST
    static List<String> complaintList = new ArrayList<>();
    // DEPARTMENT NAMES ARRAY
    static DepartmentNames[] departmentArray = DepartmentNames.values();
    // MAIN CHECK ITEMS
    static boolean isEmergency = false;
    static String department = null;
    static String speciality = null;
    static boolean needsSurgery = false;
    static boolean needsMedication = false;
    // PATIENT NAME
    static String patientName = null;
    // PATIENT ID
    static int patientId = 1;

    // main METHOD
    public static void main(String[] args)
    {
        // Loop until user ends program
        do
        {
            // Display menu
            displayOptions(1);
            // Get and initialise menu selection
            initialiseSelection(getMenuSelection(1), 1);
        }
        while (yesOrNo("\nContinue to main menu?"));
    }

    // Methods for Main Menu display and Doctor Menu display
    static void displayOptions(int menuId)
    {
        // If menuId == 1, show main menu
        if (menuId == 1)
        {
            System.out.println();
            System.out.println("1. Add Patient");
            System.out.println("2. Update Patient");
            System.out.println("3. Doctor Menu");
            System.out.println();
        }
        // If menuId == 2 and currentPatient is an inPatient, show Doctor Inpatient Menu
        else if (menuId == 2 && inPatient)
        {
            System.out.println();
            System.out.println("1. Transfer To Department");
            System.out.println("2. Perform Surgery");
            System.out.println("3. Prescribe Medication");
            System.out.println("4. Discharge in-patient");
            System.out.println();
        }
        // If menuId == 2 and currentPatient is not an inPatient, show Doctor Outpatient Menu
        else if (menuId == 2 && !inPatient)
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
        if (menuId == 1)
        {
            limit = 3;
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
                System.out.println("\nMenu selection must correspond to menu item!");
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
    static void initialiseSelection(int x, int menuId)
    {
        if (menuId == 1)
        {
            // Switch statement to call methods corresponding to users menu selection
            switch (x)
            {
                case 1 : addPatient(); break;
                case 2 : updatePatient(); break;
                case 3 : 
                {
                    // Display Doctor Menu, get and initialise user selection
                    displayOptions(2);
                    initialiseSelection(getMenuSelection(2), 2);
                }
            }
        }
        else
        {
            if (inPatient)
            {
                switch (x)
                {
                    case 1 : transferPatient(); break;
                    case 2 : haveSurgery(); break;
                    case 3 : prescribeMedication(); break;
                    case 4 : dischargeInpatient(); break;
                }
            }
            else if (!inPatient)
            {
                switch (x)
                {
                    case 1 : admitInpatient(); break;
                    case 2 : transferPatient(); break;
                    case 3 : prescribeMedication(); break;
                    case 4 : dischargeOutpatient(); break;
                }
            }
        }
    } // initialiseSelection
    
    // Method to create and add patient to patientList
    static void addPatient() 
    {
        // Remove current patient
        currentPatient = null;

        // Get patient name
        patientName = takeName();
        // Take complaint string and scan for department names, medication 
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
        // Assign patient to department object
        assignPatientToDepartment(currentPatient);
        // Assign patient to doctor object
        assignPatientToDoctor(currentPatient);
        // Set the department on the patient object
        currentPatient.setDepartment(currentDepartment.getName());
        // Set the doctor on the patient object
        currentPatient.setDoctor(currentDoctor.getName());
        // Add patient to patient list
        patientList.add(currentPatient);

        // Inform user that patient entry has been successful
        System.out.println("\nPatient has successfully been added.");

        // Ask user if they would like to see doctor options
        if (yesOrNo("\nContinue to doctor menu?"))
        {
            displayOptions(2);
            initialiseSelection(getMenuSelection(2), 2);
        }
    }

    // Method to get patient name
    static String takeName()
    {
        String response;
        String firstName;
        String lastName;

        do
        {
            System.out.println("\nPlease enter first name");
            System.out.print(">");
            response = INPUT.nextLine();

            if (isNumeric(response))
            {
                System.out.println("\nInvalid input.");
                continue;
            }
            else
            {
                firstName = response;
            }

            System.out.println("\nPlease enter last name");
            System.out.print(">");
            response = INPUT.nextLine();
            
            if (isNumeric(response))
            {
                System.out.println("\nInvalid input.");
                continue;
            }
            else
            {
                lastName = response;
            }

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
            // If complaint contains a department name, ask about surgery
            else
            {
                return response;
            }
        }
        while (true);
    }
    static boolean complaintScanner(String complaint)
    {
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
            complaintList.add("obgyn");
            speciality = "obgyn";
            department = "obgyn";
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
            isEmergency = true;
        }
        
        // Check for medication key word excluded from department name check because only 1 department name can be selected
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
            return true;
        }
        else
        {
            return false;
        }
    }

    static void createDepartment()
    {
        currentDepartment = new Department(department);
    }
    static String getSpeciality()
    {
        String response;

        do 
        {
            System.out.println();
            for (DepartmentNames dpName : DepartmentNames.values())
            {
                // Skip emergency value as it is not a valid speciality
                if (dpName.getValue() == 9)
                {
                    continue;
                }

                System.out.println(dpName.getValue() + " " + dpName.toString());
            }
            System.out.println("\nPlease select a speciality");

            response = INPUT.nextLine();

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
                return response;
            }
        }
        while (true);
    }
    static void createDoctor()
    {
        if (currentDepartment != null && speciality != null)
        {
            currentDoctor = new Doctor(currentDepartment, speciality, needsSurgery);
        }
        else
        {
            System.out.println("\nNo current department");
        }
    }
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
    static void assignPatientToDoctor(CurrentPatient patient)
    {
        currentDoctor.setCurrentPatient(patient);
    }
    static void assignPatientToDepartment(CurrentPatient patient)
    {
        currentDepartment.addPatient(patient);
        currentDepartment.setCurrentPatient(patient);
    }

    static void updatePatient()
    {
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            while (!complaintScanner(takeComplaint()))
            {
                System.out.println("\nInvalid complaint. Must contain a department name. Please try again");
            }
        }
    }
    static void transferPatient()
    {
        String response;

        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else if (currentDepartment == null)
        {
            System.out.println("\nNo current department");
        }
        else if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        else
        {
            do 
            {
                System.out.println();
                for (DepartmentNames dpName : DepartmentNames.values())
                {
                    System.out.println(dpName.getValue() + " " + dpName.toString());
                }
                System.out.println("\nPlease select a department to transfer patient to");

                response = INPUT.nextLine();

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
                    currentDepartment.removePatient(currentPatient);
                    currentDoctor.transferDepartment(response);
                    currentDoctor.removeCurrentPatient();

                    System.out.println("\nEnter new patient complaints");
                    while (!complaintScanner(takeComplaint()))
                    {
                        System.out.println("\nInvalid complaint. Must contain department name. Please try again");
                    }
                    determineSurgeryNeed();
                    currentDepartment = new Department(response);
                    currentDoctor = new Doctor(currentDepartment, response, needsSurgery);

                    break;
                }
            }
            while (true);
        }
    }
    static void haveSurgery()
    {
        if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        else
        {
            currentDoctor.haveSurgery();
        }
    }
    static void prescribeMedication()
    {
        if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        else
        {
            currentDoctor.prescribeMedication();
        }
    }
    static void dischargeInpatient()
    {
        if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        else
        {
            currentDoctor.dischargeInpatient();
            inPatient = false;
            patientList.remove(currentPatient);
            currentPatient = null;
            System.out.println("\nPatient has successfully been discharged");
        }
    }
    static void admitInpatient()
    {
        if (currentDoctor == null)
        {
            System.out.println("\nNo current doctor");
        }
        else
        {
            currentDoctor.admitInpatient();
            inPatient = true;
            System.out.println("\nPatient has successfully been admitted to inpatients");
        }
    }
    static void dischargeOutpatient()
    {
        if (currentPatient == null)
        {
            System.out.println("\nNo current patient");
        }
        else
        {
            patientList.remove(currentPatient);
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
    // Method to check if String is alphabetical
    public static boolean isAlpha(String s)
    {
        // Regex pattern allows letters and space only
        return s.matches("^[a-zA-Z\\s]+$");
    }
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

    // Method to quit program with a 'goodbye' message
    static void quitProgram()
    {
        System.out.println("\nGoodbye!");
        System.exit(0);
    } // quitProgram
}
