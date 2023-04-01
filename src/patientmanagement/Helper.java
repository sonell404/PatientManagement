package patientmanagement;

public interface Helper 
{
    public int getMenuSelection(int limit);
    public boolean isValidSelection(String str, int limit);
    public boolean yesOrNo(String prompt);
    public boolean isNumeric(String s);
    public void quitProgram(); 
}
