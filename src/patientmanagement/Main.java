package patientmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;
import javax.xml.ws.RespectBinding;

public class Main
{
    // CLASS SCANNER
    static final Scanner INPUT = new Scanner(System.in);
    // CURRENT DEPARTMENT
    static Department currentDepartment = null;
    // CURRENT DOCTOR
    static Doctor currentDoctor = null;
    // CURRENT PATIENT
    static Patient currentPatient = null;
    // PATIENT TYPE
    static boolean inPatient = false;
    // PATIENT LIST
    static List<Patient> patientList = new ArrayList<>();
    // SYMPTOM LIST
    static List<String> symptomList = new ArrayList<>();
    // MAIN CHECK ITEMS
    static boolean isEmergency = false;
    static String department = null;
    static String speciality = null;
    static boolean needsSurgery = false;

    public static void main(String[] args)
    {
        
    }

    // Methods for Main Menu display and Write Menu display
    static void displayOptions()
    {
        System.out.println();
        System.out.println("1. Add Patient");
        System.out.println("2. Select Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Doctor Menu");
        System.out.println();
    }
    // Method for displaying 'edit' menu
    static void displayDoctorMenu()
    {
        if (inPatient)
        {
            System.out.println();
            System.out.println("1. Transfer To Department");
            System.out.println("2. Perform Surgery");
            System.out.println("3. Prescribe Medication");
            System.out.println("4. Discharge in-patient");
            System.out.println();
        }
        else
        {
            System.out.println();
            System.out.println("1. Admit In-Patient");
            System.out.println("2. Transfer To Department");
            System.out.println("3. Prescribe Medication");
            System.out.println("4. Discharge out-patient");
            System.out.println();
        }
    } // displayEditMenu
    // Method for getting users menu selection
    static int getMenuSelection(int limit)
    {
        String response;
        
        // Loop until valid selection has been retrieved
        do
        {
            // Prompt user for input
            System.out.println("\nPlease enter the number of your menu selection");
            System.out.print(">");
            response = INPUT.nextLine();
            
            // Check response is numeric and also corresponding to menu options
            if (isNumeric(response) && isValidSelection(response, limit))
            {
                // Once validated, break from loop
                break;
            }
            else
            {
                // Inform user input must be valid
                System.out.println("\nMenu selection must correspond to menu item!");
            }
        }
        while(true);
        
        return Integer.parseInt(response);
    } // getMenuSelection
    // Method for validating menu selection
    static boolean isValidSelection(String str, int limit)
    {
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

    // Method for executing users selection
    static void initialiseSelection(int x)
    {
        // Perform action corresponding to users selection
        switch (x)
        {
            case 1 -> addPatient();
            case 2 -> selectPatient();
            case 3 -> updatePatient();
            case 4 -> displayDoctorMenu();
        }
    } // initialiseSelection

    static void addPatient() 
    {
        populateSymptomList();
        assessPatient();
        createDepartment();
        createDoctor();
        createPatient();
    }
    
    // Method to populate list with user input
    static void populateSymptomList()
    {
        String response;
        
        do
        {
            // Prompt user for input
            System.out.println("\nEnter a symptom, or type 'continue'");
            System.out.print(">");
            
            // Retrieve input
            response = INPUT.nextLine();
            
            // If input equals 'continue', break from loop
            if (response.equalsIgnoreCase("continue"))
            {
                break;
            }
            // If input is numeric, add to list
            else if(isNumeric(response))
            {
                symptomList.add(response);
            }
            // If input is neither 'continue' or numeric, inform user
            else
            {
                System.out.println("Invalid input! A numeric input or 'continue' is accepted.");
            }
        }
        while (true);
    }

    // Method to ask user main questions about patient
    static void assessPatient()
    {
        DepartmentNames[] departmentArray = DepartmentNames.values();
        String response;

        // EMERGENCY?
        if (yesOrNo("Is it an emergency?"))
        {
            isEmergency = true;
            department = departmentArray[departmentArray.length - 1].name();
        }
        else
        {
            isEmergency = false;
        }

        // WHAT IS SPECIALITY? 
        do
        {
            for (DepartmentNames departmentName : departmentArray)
            {
                if (departmentName.ordinal() == departmentArray.length - 1)
                {
                    continue;
                }

                System.out.println(departmentName.getValue() + ". " + departmentName.name());
            }
            System.out.println("Select a speciality");
            System.out.print(">");

            response = INPUT.nextLine();

            if (!isNumeric(response))
            {
                System.out.println("Invalid input. Please select a valid number");
            }
            else if (Integer.parseInt(response) >= departmentArray.length || Integer.parseInt(response) < 0)
            {
                System.out.println("Invalid input. Please select a valid number");
            }
            else
            {
                speciality = department = departmentArray[Integer.parseInt(response)].name();
                break;
            }
        }
        while (true);

        // SURGERY?
        if (yesOrNo("Is surgery needed?"))
        {
            needsSurgery = true;
        }
        else
        {
            needsSurgery = false;
        }
    }

    static void createDepartment()
    {
        if (department != null)
        {
            currentDepartment = new Department(department);
        }
        else
        {
            System.out.println("No department available");
        }
    }
    static void createDoctor()
    {
        if (currentDepartment != null && speciality != null)
        {
            currentDoctor = new Doctor(currentDepartment, speciality, needsSurgery);
        }
        else
        {
            System.out.println("No department available");
        }
    }
    static void createPatient()
    {
        if (currentDepartment != null && currentDoctor != null)
        {
            currentPatient = new Patient(speciality, department, null)
        }
    }

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
                System.out.println("Invalid input!");
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
