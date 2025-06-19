package Service;

import Entity.Appointment;
import Entity.Patient;
import Entity.Specialization;
import EntityDTO.PatientDTO;
import Repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;

public class PatientService {
    private List<Patient> patientList;
    private final PatientRepository dbpatientRepository = new PatientRepository();


    private static PatientService instance;

    private PatientService(){
        this.patientList = new ArrayList<>();
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


    public List<Patient> renderAllPatients(){
        List<PatientDTO> patientDTOS = dbpatientRepository.findAll();
        List<Patient> patients = new ArrayList<>();
        for(PatientDTO patientDTO : patientDTOS){
            Patient patient = new Patient(patientDTO.getPatientId(),patientDTO.getPatientName(),patientDTO.getPatientAge(),patientDTO.getPatientPhoneNum(),Specialization.valueOf(patientDTO.getProblemSpecialization()));
            patients.add(patient);
        }
        this.patientList = patients;
        return patients;
    }

    public Patient addPatient(String patientName, int age, String phoneNum, Specialization specialization) {
        Patient patient = new Patient(patientName, age, phoneNum, specialization);
        patientList.add(patient);

        PatientDTO dto = new PatientDTO(patient.getPatientId(), patientName, age, phoneNum, specialization.name());
        dbpatientRepository.save(dto);

        return patient;
    }



    public Patient getPatientById(String patientId) {
        for (Patient patient : patientList) {
            if (patient.getPatientId().equals(patientId)) {
                return patient;
            }
        }
        return null;
    }

    public void deletePatient(Patient patient) {
        patientList.remove(patient);
        dbpatientRepository.delete(patient.getPatientId());
    }



    public List<Patient> getPatientsList(){
        return patientList;
    }

    public void addAppointmentToPatient(Patient patient, Appointment appointment) {
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
