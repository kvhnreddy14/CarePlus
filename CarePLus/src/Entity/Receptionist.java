package Entity;

public class Receptionist {
    public static int receptionistNum;
    private final String receptionistId;
    private String receptionistName;

    public Receptionist(String receptionistName) {
        receptionistNum++;
        this.receptionistId = "R" + receptionistNum;
        this.receptionistName = receptionistName;
    }

    public static int getReceptionistNum() {
        return receptionistNum;
    }

    public static void setReceptionistNum(int receptionistNum) {
        Receptionist.receptionistNum = receptionistNum;
    }

    public String getReceptionistId() {
        return receptionistId;
    }

    public String getReceptionistName() {
        return receptionistName;
    }

    public void setReceptionistName(String receptionistName) {
        this.receptionistName = receptionistName;
    }
}
