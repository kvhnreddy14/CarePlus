package Controller;

import Entity.*;
import Service.DocService;
import Service.PatientService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReceptionDesk {
    private Receptionist receptionist;
    private DocService docService;
    private PatientService patientService;

    public ReceptionDesk(Receptionist receptionist) {
        this.receptionist = receptionist;
        this.docService = DocService.getInstance();
        this.patientService = PatientService.getInstance();
    }

    public Patient addPatient(String patientName,int age, String patientPhoneNum, Specialization pateintProblemSpecialization){
        return patientService.addPatient(patientName,age,patientPhoneNum,pateintProblemSpecialization);
    }

    public List<Patient> getAllPatients(){
        return patientService.getPatientsList();
    }

    public void bookAppointment(Doctor doc, Patient patient, LocalDate date, LocalTime appointmentTime){
        Appointment appointment = docService.bookAppointmentForDoc(doc, patient,date,appointmentTime);
        if(appointment == null){
            System.out.println("not booked select other slot");
        }
        else{
            patientService.addAppointmentToPatient(patient, appointment);
        }
    }

    public List<Appointment> getPatientAppointmentList(Patient patient){
        return patientService.getPatientAppointments(patient);
    }

    public void cancelAppointment(Doctor doc, Patient patient, LocalDate date, LocalTime appointmentTime){
        Appointment appointment = docService.bookAppointmentForDoc(doc, patient,date,appointmentTime);
        docService.cancelAppointment(doc, date, appointmentTime);
        patientService.deleteAppointmentFromPatient(patient, appointment);
    }

//    public void cancelAndReschedule(Doctor doc, Patient patient,LocalDate previousDate, LocalTime previousAppointmentTime, LocalDate newDate,  LocalTime newAppointmentTime){
//        Appointment appointment = docService.rescheduleTocustomSlotForDoc( doc, patient, previousDate, previousAppointmentTime, newDate, newAppointmentTime);
//        patientService.deleteAppointmentFromPatient();
//    }

    public List<Patient> getPatientsByName(String name){
        return patientService.getPatientByName(name);
    }

    public List<Doctor> getDoctorsByName(String name){
        return docService.getDoctorsByName(name);
    }

    public List<Doctor> getDoctorsBySpecialization(Specialization specialization){
        return docService.getDoctorsBySpecialization(specialization);
    }

    public List<TimeSlot> getDoctorsAvailableTimeSlots(Doctor doctor){
        return docService.getDoctorAvailableSlots(doctor);
    }


//    public void cancelAppointment()

}
