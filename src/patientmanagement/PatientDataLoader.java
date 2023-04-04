package patientmanagement;

// SBA22075 - Sonel Ali

// SQL imports 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientDataLoader 
{
    // Final SQL login variables
    private final String DB_URL = "jdbc:mysql://localhost";
    private final String USER = "root";
    private final String PASSWD = "";

    // Method to create database - method takes argument for database name
    public boolean createDB() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        // Initialise JDBC driver to obtain connection
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        // Try with resources for making connection and initiating Statement variable
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
        Statement stmnt = conn.createStatement();)
        {
            try
            {
                // If database already exists, delete database
                stmnt.execute("DROP DATABASE IF EXISTS patients");
                // Create database
                stmnt.execute("CREATE DATABASE patients");

                // Inform user database has been created
                System.out.println("\nDatabase 'patients' has successfully been created");

                // Method returns true
                return true;
            }
            catch (SQLException e)
            {
                // Inform user if database creation failed
                System.out.println("\nCould not create database");
                // Print stack trace
                e.printStackTrace();
                // Method returns false
                return false;
            }
        }
        catch (SQLException e)
        {
            // Inform user if connection fails
            System.out.println("\nFailed to connect");
            
            // Print stack trace
            e.printStackTrace();

            // Method returns false
            return false;
        }
    } // createDB

    // Method to create table within database - method takes arguments for table name, database name
    public boolean createTable()
    {
        // Try with resources for making connection and initiating Statement variable
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
        Statement stmnt = conn.createStatement();)
        {
            // Try block for statement executions
            try
            {
                // Declare which database to use
                stmnt.execute("USE patients");
                // Table creation statement
                stmnt.execute("CREATE TABLE patient_data(" 
                + "patient_id int PRIMARY KEY AUTO_INCREMENT NOT NULL," 
                + "name varchar(40) NOT NULL,"
                + "is_inpatient bool NOT NULL,"
                + "complaints varchar(255) NOT NULL,"
                + "needs_medication bool,"
                + "needs_surgery bool NOT NULL,"
                + "department varchar(30) NOT NULL,"
                + "doctor varchar(30) NOT NULL)"
                );
                
                // Inform user table has successfully been created
                System.out.println("\nTable 'patient_data' has successfully been created");
                // Method returns true
                return true;
            }
            catch (SQLException e)
            {
                // Inform user if table creation failed
                System.out.println("\nCould not create table");
                // Print stack trace
                e.printStackTrace();
                // Method returns false
                return false;
            }
        }
        catch (SQLException e)
        {
            // Inform user if connection has failed
            System.out.println("\nFailed to connect");
            // Print stack trace
            e.printStackTrace();
            // Method returns false
            return false;
        }
    } // createTable

    // Method to insert data into table
    public boolean insertData(String[] patientDataArray)
    {
        // Try with resources for making connection and initiating Statement variable
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWD);
        Statement stmnt = conn.createStatement();)
        {
            // Try block for statement executions
            try
            {
                // Declare database being used
                stmnt.execute("USE patients");

                // Declare variables for data columns
                String name = "";
                boolean is_inpatient = false;
                String complaints = "";
                boolean needs_medication = false;
                boolean needs_surgery = false;
                String department = "";
                String doctor = "";

                // Loop through array, use a switch statement within array to assign to a different variable each time
                for (int i = 0 ; i < patientDataArray.length ; i++)
                {
                    switch (i)
                    {
                        case 0 : name = patientDataArray[i]; break;
                        case 1 : is_inpatient = Boolean.parseBoolean(patientDataArray[i]); break;
                        case 2 : complaints = patientDataArray[i]; break;
                        case 3 : needs_medication = Boolean.parseBoolean(patientDataArray[i]); break;
                        case 4 : needs_surgery = Boolean.parseBoolean(patientDataArray[i]); break;
                        case 5 : department = patientDataArray[i]; break;
                        case 6 : doctor = patientDataArray[i]; break;
                    }
                }

                String query = "INSERT INTO patient_data (name, is_inpatient, complaints, needs_medication, needs_surgery, department, doctor) VALUES ('"
                                + name + "', "
                                + is_inpatient + ", '" 
                                + complaints + "', " 
                                + needs_medication + ", "
                                + needs_surgery + ", '"
                                + department + "', '"
                                + doctor + "')";

                // Execute query
                stmnt.execute(query);
                // Inform user table has successfully been created
                System.out.println("\nTable 'patient_data' has been successfully updated");
                // Method returns true
                return true;
            }
            catch (SQLException e)
            {
                // Inform user if table population has failed
                System.out.println("\nCould not populate table");
                // Print stack trace
                e.printStackTrace();
                // Method returns false
                return false;
            }
        }
        catch (SQLException e)
        {
            // Inform user if connection failed
            System.out.println("\nFailed to connect");
            // Method returns false
            return false;
        }
    } // insertData

    public boolean databaseExists()
    {
        try 
        {
            DriverManager.getConnection(DB_URL, USER, PASSWD);
            return true;
        } 
        catch (SQLException e) 
        {
            return false;
        }
    }
} // Class