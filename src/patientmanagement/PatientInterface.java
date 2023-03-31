/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pooaintegratedca2;

/**
 *
 * @author sweiss
 * You may need to change the package name to reflect your package structure.
 */
public interface PatientInterface {
    public String getName();
    public int getPatientId();
    public String getDepartment();
    public boolean isInpatient();
    public String[] getComplaints();
    
    /**
     * This is set whenever a patient object is created or when it is transferred to a new department.
     * @return Whether the patient needs medication from this department.
     */
    public boolean needsMedication();
    
    /**
     * This is set whenever a patient object is created or when it is transferred to a new department.
     * @return Whether the patient needs surgery from this department.
     */
    public boolean needsSurgery();
    
    /**
     * This will have a chance of causing the patient to need medication and/or surgery in this department.
     * @param department 
     */
    public void transferDepartment(String department);
    
    /**
     * 
     * @param speciality This is the speciality of the doctor performing the surgery.
     */
    public void haveSurgery(String speciality);
    
    /**
     * 
     * @param speciality This is the speciality of the doctor prescribing the medication.
     */
    public void prescribeMedication(String speciality);
    
    /**
     * 
     * @param department This is the name of the admitting department.
     */
    public void admitInpatient(String department);
    public void dischargeInpatient();

    
}
