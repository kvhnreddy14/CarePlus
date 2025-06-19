package Entity;

import java.util.UUID;

public class Receptionist {
    private final String receptionistId;
    private String receptionistName;

    public Receptionist(String receptionistName) {
        this.receptionistId = UUID.randomUUID().toString().substring(0, 5);
        this.receptionistName = receptionistName;
    }

    public Receptionist(String receptionistId, String receptionistName) {
        this.receptionistId = receptionistId;
        this.receptionistName = receptionistName;
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
