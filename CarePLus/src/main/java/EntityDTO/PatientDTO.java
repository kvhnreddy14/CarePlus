package EntityDTO;


public class PatientDTO {
    private String patientId;
    private String patientName;
    private int patientAge;
    private String patientPhoneNum;
    private String problemSpecialization;


    public PatientDTO(){

    }
    public PatientDTO(String patientId, String patientName, int patientAge, String patientPhoneNum, String problemSpecialization) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientPhoneNum = patientPhoneNum;
        this.problemSpecialization = problemSpecialization;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientPhoneNum() {
        return patientPhoneNum;
    }

    public void setPatientPhoneNum(String patientPhoneNum) {
        this.patientPhoneNum = patientPhoneNum;
    }

    public String getProblemSpecialization() {
        return problemSpecialization;
    }

    public void setProblemSpecialization(String problemSpecialization) {
        this.problemSpecialization = problemSpecialization;
    }
}

