package EntityDTO;


import Entity.Appointment;

public class AppointmentDTO {
    private String appointmentId;
    private String docId;
    private String patientId;
    private String tsId;


    public AppointmentDTO(){

    }
    public AppointmentDTO(String appointmentId, String docId, String patientId, String tsId) {
        this.appointmentId = appointmentId;
        this.docId = docId;
        this.patientId = patientId;
        this.tsId = tsId;
    }

    public AppointmentDTO(Appointment appt) {
        this.appointmentId = appt.getAppointmentId();
        this.docId = appt.getAppointmentId();
        this.patientId = appt.getPatient().getPatientId();
        this.tsId = tsId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }


    public String getTsId() {
        return tsId;
    }

    public void setTsId(String tsId) {
        this.tsId = tsId;
    }
}
