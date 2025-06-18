package Entity;


public class Appointment {
    private Patient patient;
    private Doctor doc;
    private TimeSlot timeSlot;

    public Appointment(Doctor doc, Patient patient, TimeSlot timeSlot){
        this.doc =doc;
        this.patient = patient;
        this.timeSlot = timeSlot;
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
