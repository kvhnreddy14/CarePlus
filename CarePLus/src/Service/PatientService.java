package Service;

import Entity.Appointment;
import Entity.Patient;
import Entity.Specialization;

import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private List<Patient> patientList = new ArrayList<>();

    private static PatientService instance;

    private PatientService(){

    }

    public static PatientService getInstance(){
        if(instance == null){
            synchronized(PatientService.class){
                if(instance==null){
                    instance = new PatientService();
                }
            }
        }
        return instance;
    }

    public Patient addPatient(String patientName,int age, String patientPhoneNum, Specialization pateintProblemSpecialization){
        Patient patient = new Patient(patientName, age, patientPhoneNum, pateintProblemSpecialization);
        patientList.add(patient);
        return patient;
    }

    public List<Patient> getPatientsList(){
        return patientList;
    }


    public void addAppointmentToPatient(Patient patient, Appointment appointment){
        patient.addAppointment(appointment);
    }

    public void deleteAppointmentFromPatient(Patient patient, Appointment appointment){
        patient.deleteAppointment(appointment);
    }

    // need to be added when booking is completed by doc service
    public List<Appointment> getPatientAppointments(Patient patient){
        return patient.getPatientAppointments();
    }

    public List<Patient> getPatientByName(String name){
        return patientList.stream()
                .filter(patient -> patient.getPatientName().equalsIgnoreCase(name))
                .toList();
    }

    public List<Patient> getPatientByNameAndAge(String name, int age){
        return patientList.stream()
                .filter(patient -> patient.getPatientName().equalsIgnoreCase(name) && patient.getPatientAge() == age)
                .toList();
    }
}
