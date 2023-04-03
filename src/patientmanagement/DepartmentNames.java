package patientmanagement;

// SBA22075 - Sonel Ali

/*
 * Main enum for department names
 * 
 * I used an enum for the department names because they are unchanging values
 * and they ensure type-safety and readability. Enums are also easy to iterate over,
 * so they can also be used for menu displays quite easily. It would also be quite
 * easy to add a new department without having to change code anywhere else.
 */
public enum DepartmentNames 
{
    CARDIOLOGY(1),
    RHEUMATOLOGY(2),
    ENT(3),
    OPHTHALMOLOGY(4),
    OCCUPATIONAL_THERAPY(5),
    RADIOLOGY(6),
    ONCOLOGY(7),
    OB_GYN(8),
    EMERGENCY(9);

    private final int value;

    private DepartmentNames(int value) 
    {
        this.value = value;
    }

    public int getValue() 
    {
        return value;
    }
}
