package EntityDTO;

public class ReceptionistDTO {
    private String receptionistId;
    private String receptionistName;


    public ReceptionistDTO(){

    }
    public ReceptionistDTO(String receptionistId, String receptionistName) {
        this.receptionistId = receptionistId;
        this.receptionistName = receptionistName;
    }

    public String getReceptionistId() {
        return receptionistId;
    }

    public void setReceptionistId(String receptionistId) {
        this.receptionistId = receptionistId;
    }

    public String getReceptionistName() {
        return receptionistName;
    }

    public void setReceptionistName(String receptionistName) {
        this.receptionistName = receptionistName;
    }
}


