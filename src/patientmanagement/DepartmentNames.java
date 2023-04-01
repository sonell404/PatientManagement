package patientmanagement;

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
