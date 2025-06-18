package Entity;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    public static int patientNum;
    private final String patientId;
    private String patientName;
    private int patientAge;
    private String patientPhoneNum;
    private Specialization pateintProblemSpecialization;
    private List<Appointment> patientAppointments;

    public Patient(String patientName,int age, String patientPhoneNum, Specialization pateintProblemSpecialization){
        patientNum++;
        this.patientId = "P" + patientNum;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientPhoneNum = patientPhoneNum;
        this.pateintProblemSpecialization = pateintProblemSpecialization;
        this.patientAppointments = new ArrayList<>();
    }

    public static int getPatientNum() {
        return patientNum;
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

    public static void setPatientNum(int patientNum) {
        Patient.patientNum = patientNum;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Specialization getPateintProblemSpecialization() {
        return pateintProblemSpecialization;
    }

    public void setPateintProblemSpecialization(Specialization pateintProblemSpecialization) {
        this.pateintProblemSpecialization = pateintProblemSpecialization;
    }

    public void addAppointment(Appointment appointment){
        patientAppointments.add(appointment);
    }

    public void deleteAppointment(Appointment appointment){
        patientAppointments.remove(appointment);
    }

    public List<Appointment> getPatientAppointments() {
        return patientAppointments;
    }

    public void setPatientAppointments(List<Appointment> patientAppointments) {
        this.patientAppointments = patientAppointments;
    }
}
