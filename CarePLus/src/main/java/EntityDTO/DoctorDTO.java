package EntityDTO;

import java.time.LocalTime;

public class DoctorDTO {
    private String docId;
    private String docName;
    private String specialization;
    private LocalTime availableStartTime;
    private LocalTime availableEndTime;

    public DoctorDTO(){

    }

    public DoctorDTO(String docId, String docName, String specialization, LocalTime availableStartTime, LocalTime availableEndTime) {
        this.docId = docId;
        this.docName = docName;
        this.specialization = specialization;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalTime getAvailableStartTime() {
        return availableStartTime;
    }

    public void setAvailableStartTime(LocalTime availableStartTime) {
        this.availableStartTime = availableStartTime;
    }

    public LocalTime getAvailableEndTime() {
        return availableEndTime;
    }

    public void setAvailableEndTime(LocalTime availableEndTime) {
        this.availableEndTime = availableEndTime;
    }
}
