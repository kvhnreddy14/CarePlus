package Entity;


import java.util.UUID;

public class Appointment {
    private final String appointmentId;
    private Patient patient;
    private Doctor doc;
    private TimeSlot timeSlot;

    public Appointment(Doctor doc, Patient patient, TimeSlot timeSlot){
        this.appointmentId = UUID.randomUUID().toString().substring(0, 5);
        this.doc =doc;
        this.patient = patient;
        this.timeSlot = timeSlot;
    }

    public Appointment(String appointmentId, Doctor doc, Patient patient, TimeSlot timeSlot) {
        this.appointmentId = appointmentId;
        this.doc = doc;
        this.patient = patient;
        this.timeSlot = timeSlot;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoc() {
        return doc;
    }

    public void setDoc(Doctor doc) {
        this.doc = doc;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}
