package Controller;

import Entity.Doctor;
import Entity.Receptionist;
import Entity.Specialization;
import Service.DocService;
import Service.ReceptionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AdminDesk {
    private ReceptionService receptionService;
    private DocService docService;

    public AdminDesk(){
        this.receptionService = ReceptionService.getInstance();
        this.docService = DocService.getInstance();
    }

    public void addDoctor(String docName, Specialization docSpecialization, LocalTime availableStartTime, LocalTime availableEndTime){
        docService.addDoctor( docName, docSpecialization, availableStartTime, availableEndTime);
    }


    public void deleteDoctor(Doctor doc) {
        docService.deleteDoctor(doc);
    }

    public void modifyAvailabilityOfDoctor(Doctor doc, LocalDate date, LocalTime availableStartTime, LocalTime availableEndTime){
        docService.modifyDocAvailability(doc, date, availableStartTime, availableEndTime,30);
    }

    public List<Doctor> getDoctorList(){
        return docService.getAllDoctors();
    }


    public List<Receptionist> getAllReceptionists(){
        return receptionService.getReceptionistList();
    }

    public void addReceptionist(String name){
        receptionService.addReceptionist(name);
    }

    public void deleteReceptionist(Receptionist receptionist){
        receptionService.deleteReceptionist(receptionist);
    }




}
