This software will allow staff to input patient data, have the patient assigned to the appropriate department and doctors, and keep track of the patient and their data as they move through their stages of care.

According to the brief, there will be a Patient class used in program testing that implements an interface, but currently we only have access to the interface and not the class. To ensure the program works for who ever is testing next, I have created a Patient class to replicate the class we currently have no access to, with bare functionality according to the interface, and then another CurrentPatient class that extends the Patient class. This has allowed me to implement the functionality provided in the interface and also some extra functionality without prohibiting the next person from running the program with the currently unavailable Patient class.

Program structure:

CLASSES:

Patient (Parent of CurrentPatient)
    - Methods:
        Getters & Setters:
            name
            patientId
            department
            complaints
        isInpatient (Returns whether patient is n inpatient)
        needsSurgery (Returns whether patient needs surgery)
        needsMedication (Returns whether patient needs medication)
        haveSurgery (Unknown)
        prescribeMedication (Unknown)
        transferDepartment (Unknown)
        admitInpatient (Changes inPatient to true)
        dischargeInpatient (changes inPatient to false)

CurrentPatient (Child of Patient)
    - Methods:
        Getters & Setters:
            name
            patientId
            department
            complaints
        isInpatient (Returns whether patient is n inpatient)
        needsSurgery (Returns whether patient needs surgery)
        needsMedication (Returns whether patient needs medication)
        haveSurgery (Prints out that patient has received surgery)
        prescribeMedication (Prints out that patient has been given medication)
        transferDepartment (Checks complaints list for mention of surgery or medication, sets new department)
        admitInpatient (Changes inPatient to true)
        dischargeInpatient (changes inPatient to false)
        patientToArray (Puts patient details into a String array - passed to insertData method of the PatientDataLoader class)
        toString (Prints patient details)
    - Constructor
        Arguments:
            name
            needsSurgery
            needsMedication
            complaints
            patientId
            department
            doctor

PatientDataLoader
    - Methods:
        createDB (Creates patient database)
        createTable (Creates table to hold patient information)
        insertData (Takes a string array, creates an INSERT query from the values in the array, executes query)
        databaseExists (Checks to see if the database has been created already)
Department
    - Methods:
        Getters & Setters:
            canPrescribeMedication
            canAdmitPatients
            canDischargeInPatients
            canTransferPatient
            canHaveOperations
    - Constructor
        Arguments:
            name (Determines the values of the above variables)
Doctor
    - Methods:
        Getters & Setters:
            name
            department
            speciality
            currentPatient
            canPrescribeMedication
            canAdmitPatients
            canDischargeInPatients
            canTransferPatient
            canHaveOperations
        transferDepartment (Uses CurrentPatient method through CurrentPatient object)
        haveSurgery (Uses CurrentPatient method through CurrentPatient object)
        prescribeMedication (Uses CurrentPatient method through CurrentPatient object)
        admitInpatient (Uses CurrentPatient method through CurrentPatient object)
        dischargeInpatient (Uses CurrentPatient method through CurrentPatient object)
    - Constructor
        Arguments:
            department (Department object)
            speciality 
            isSurgeon
        Takes the ability variables and department name from the Department object
        Sets speciality and surgeon variables with given arguments
        Parses the given csv file of doctors, finds a match to department, speciality and isSurgeon and assigs the name of that doctor.
Main
    - Methods:
        main (Initialises program)
        MENU METHODS
            displayOptions (Displays different menus)
            getMenuSelection (Takes users menu selection)
            isValidSelection (Determines if users menu selection is valid)
            initialiseSelection (Performs appropriate actions corresponding to users menu selection)
        PROGRAM METHODS
            addPatient (Resets current patient, takes patient details and complaints, assigns to department and doctor)
            takeName (Gets patient name from user)
            takeComplaint (Gets patient complaints from user)
            complaintScanner (Scans patient complaint for keywords such as department name, surgery and medication)
            determineSurgeryNeed (Asks user if surgery is needed)
            createDepartment (Creates a new department and assigns to currentDepartment variable)
            getSpeciality (In the case of the department being 'emergency', this method gets the speciality within emergency department)
            createDoctor (Creates a new doctor and assigns to currentDoctor variable)
            createPatient (Creates a new patient and assigns to currentPatient variable)
            assignPatientToDoctor (Sets the current patient of the doctor object to the current patient)
            savePatientData (Saves the patient data to database)
        DOCTOR METHODS
            transferPatient (Takes a new set of complaints, assigns patient to new department and doctor)
            haveSurgery (Performs checks before calling the doctors haveSurgery method)
            prescribeMedication (Performs checks before calling the doctors prescribeMedication method)
            dischargeInpatient (Performs checks, saves patient data to database, discharges inpatient, sets currentPatient to null)
            admitInpatient (Performs checks, admits inpatient)
            dischargeInpatient (Performs checks, saves patient data to database, discharges outpatient, sets currentPatient to null)
        HELPER METHODS
            yesOrNo (Prompts user with a yes or no question, returns true if yes, false if no)
            isNumeric (Checks if a string is numeric. Returns true if it is, false if it isn't)

INTERFACES:

PatientInterface (Interface implemented by Patient)
    - Methods:
        getName
        getPatientId
        getDepartment
        isInpatient
        getComplaints
        needsMedication
        needsSurgery
        transferDepartment
        haveSurgery
        prescribeMedication
        admitInpatient
        dischargeInpatient

ENUMS:

DepartmentNames
    - Values:
        CARDIOLOGY (1)
        RHEUMATOLOGY (2)
        ENT (3)
        OPHTHALMOLOGY (4)
        OCCUPATIONAL_THERAPY (5)
        RADIOLOGY (6)
        ONCOLOGY (7)
        OB_GYN (8)
        EMERGENCY (9)
    - Methods:
        getValue (Returns the value of each element, eg. 1, 2, 3, etc.)