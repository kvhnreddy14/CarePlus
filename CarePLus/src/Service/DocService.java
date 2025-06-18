package Service;

import Entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DocService {
    private List<Doctor> doctorList = new ArrayList<>();

    private static DocService instance;

    private DocService(){

    }

    public static DocService getInstance(){
        if(instance == null){
            synchronized(DocService.class){
                if(instance==null){
                    instance = new DocService();
                }
            }
        }
        return instance;
    }

    public void addDoctor(String docName, Specialization docSpecialization, LocalTime availableStartTime, LocalTime availableEndTime){
        doctorList.add(new Doctor(docName, docSpecialization, availableStartTime, availableEndTime));
    }

    public void modifyDocAvailability(Doctor doc, LocalDate date, LocalTime availableStartTime, LocalTime availableEndTime, int appintmentSizeInMinutes){
        for(Day day: doc.getDayWiseTimeSlots()){
            if(day.getDate().equals(date)){
                day.setTimeSlotforParticularDay(availableStartTime, availableEndTime, appintmentSizeInMinutes);
            }
        }
    }

    public void deleteDoc(Doctor doc){
        doctorList.remove(doc);
    }

    public List<Doctor> getAllDoctors(){
        return doctorList;
    }



    public List<Doctor> getDoctorsBySpecialization(Specialization specialization){
        List<Doctor> resultList = doctorList.stream()
                .filter(doctor -> {
                            return doctor.getDocSpecialization().equals(specialization);
                        })
                .toList();

        return resultList;
    }

    public List<Doctor> getDoctorsByName(String name){
        List<Doctor> resultList = doctorList.stream()
                .filter(doctor -> {
                    return doctor.getDocName().equalsIgnoreCase(name);
                })
                .toList();
        return resultList;
    }

    public List<Doctor> getDoctorsBySpecializationAndTime(Specialization specialization, LocalDate date, LocalTime time){
        List<Doctor> resultList = doctorList.stream()
                .filter(doctor -> {
                    for(Day day: doctor.getDayWiseTimeSlots()){
                        if(day.getDate().equals(date)) {
                            for (TimeSlot timeSlot : day.getTimeSlotList()) {
                                if (timeSlot.getStartTime().equals(time) && timeSlot.getAppointment() == null) {
                                    return true;
                                }
                                ;
                            }
                        }
                    }
                    return false;
                })
                .toList();
        return resultList;
    }

    public List<Appointment> getDoctorBookedAppointments(Doctor doctor){
        List<Appointment> resultList = new ArrayList<>();
        for(Day day: doctor.getDayWiseTimeSlots()){
            for(TimeSlot timeSlot : day.getTimeSlotList()){
                if(timeSlot.getAppointment() != null){
                    resultList.add(timeSlot.getAppointment());
                }
                else{
                    continue;
                }
            }
        }
        return resultList;
    }

    public List<TimeSlot> getDoctorAvailableSlots(Doctor doctor){
        List<TimeSlot> resultList = new ArrayList<>();
        for(Day day: doctor.getDayWiseTimeSlots()){
            for(TimeSlot timeSlot : day.getTimeSlotList()){
                if(timeSlot.getAppointment() != null){
                    continue;
                }
                else{
                    resultList.add(timeSlot);
                }
            }
        }
        return resultList;
    }

    public Appointment bookAppointmentForDoc(Doctor doc, Patient patient,LocalDate date, LocalTime appointmentTime){
        for(Day day : doc.getDayWiseTimeSlots()){
            if(day.getDate().equals(date)){
                for(TimeSlot timeSlot: day.getTimeSlotList()){
                    if(timeSlot.getStartTime().equals(appointmentTime)){
                        Appointment appointment1 = new Appointment(doc, patient, timeSlot);
                        timeSlot.setAppointment(appointment1);
                        doc.getDocAppointments().add(appointment1);
                        return appointment1;
                    }
                }
            }
        }
        return null;
    }

    public boolean cancelAppointment(Doctor doc, LocalDate date, LocalTime appointmentTime){
        for(Day day : doc.getDayWiseTimeSlots()){
            if(day.getDate().equals(date)){
                for(TimeSlot timeSlot: day.getTimeSlotList()){
                    if(timeSlot.getStartTime().equals(appointmentTime)){
                        timeSlot.setAppointment(null);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Appointment rescheduleTocustomSlotForDoc(Doctor doc, Patient patient,LocalDate previousDate, LocalTime previousAppointmentTime, LocalDate newDate,  LocalTime newAppointmentTime){
        if(cancelAppointment(doc, previousDate, previousAppointmentTime)){
            return bookAppointmentForDoc(doc, patient, newDate, newAppointmentTime);
        }
        return null;
    }

    public Appointment rescheduleToNextAvailableAppointmentForDoc(Doctor doc, Patient patient,LocalDate date, LocalTime appointmentTime){
        Appointment appointment1 = null;
        outer : for(Day day : doc.getDayWiseTimeSlots()){
            if(day.getDate().equals(date)){
                inner : for(TimeSlot timeSlot: day.getTimeSlotList()){
                    boolean after = false;
                    if(timeSlot.getStartTime().equals(appointmentTime)){
                        after = true;
                        if(timeSlot.getAppointment() == null){
                            System.out.println("no appointment is booked here. can postpone");
                            break outer;
                        }
                        else{
                            appointment1 = timeSlot.getAppointment();
                            timeSlot.setAppointment(null);
                        }
                    }
                    if(after){
                        if(timeSlot.getAppointment() == null){
                            appointment1.setTimeSlot(timeSlot);
                            timeSlot.setAppointment(appointment1);
                            return appointment1;
                        }
                    }
                }
            }
        }
        return null;
    }
}
