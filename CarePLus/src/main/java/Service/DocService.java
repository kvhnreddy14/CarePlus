package Service;

import Entity.*;
import EntityDTO.AppointmentDTO;
import EntityDTO.DayDTO;
import EntityDTO.DoctorDTO;
import EntityDTO.TimeSlotDTO;
import Repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DocService {
    private List<Doctor> doctorList;


    private final DoctorRepository dbDocRepo = new DoctorRepository();
    private final DayRepository dbDayRepo = new DayRepository();
    private final TimeSlotRepository dbTsRepo = new TimeSlotRepository();
    private final AppointmentRepository dbAppointmetRepo = new AppointmentRepository();
    private final PatientRepository dbPatientRepo = new PatientRepository();


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



    public void setNextDays(){
        for (Doctor doc : doctorList) {
            int existingDays = doc.getDayWiseTimeSlots().size();

            doc.renderTonextKDays(3);

            List<Day> allDays = doc.getDayWiseTimeSlots();
            List<Day> newDays = allDays.subList(existingDays, allDays.size());

            for (Day newDay : newDays) {
                DayDTO dayDTO = new DayDTO(newDay.getDayId(), doc.getDocId(), newDay.getDate(), newDay.getStartTime(), newDay.getEndTime());
                dbDayRepo.save(dayDTO);
                for (TimeSlot ts : newDay.getTimeSlotList()) {
                    TimeSlotDTO timeSlotDTO = new TimeSlotDTO(ts.getTsId(), ts.getTimeSlotDate(), newDay.getDayId(), ts.getStartTime(), ts.getEndTime());
                    dbTsRepo.save(timeSlotDTO);
                }
            }
        }
    }



    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public void addDoctor(String name, Specialization spec,LocalTime start, LocalTime end) {
        Doctor doc = new Doctor(name, spec, start, end);
        doctorList.add(doc);

        DoctorDTO dto = new DoctorDTO(doc.getDocId(), name, spec.name(), start, end);
        dbDocRepo.save(dto);

        List<Day> dayList = doc.getDayWiseTimeSlots();
        for (Day newDay : dayList) {
            DayDTO dayDTO = new DayDTO(newDay.getDayId(), doc.getDocId(), newDay.getDate(), newDay.getStartTime(), newDay.getEndTime());
            dbDayRepo.save(dayDTO);
            for (TimeSlot ts : newDay.getTimeSlotList()) {
                TimeSlotDTO timeSlotDTO = new TimeSlotDTO(ts.getTsId(), ts.getTimeSlotDate(), newDay.getDayId(), ts.getStartTime(), ts.getEndTime());
                dbTsRepo.save(timeSlotDTO);
            }
        }
    }



    public void modifyDocAvailability(Doctor doc, LocalDate date, LocalTime start, LocalTime end, int appointmentSizeMinutes) {
        doc.getDayWiseTimeSlots().stream()
                .filter(d -> d.getDate().equals(date))
                .forEach(day -> {
                    day.setStartTime(start);
                    day.setEndTime(end);
                    day.regenerateTimeSlots();
                    DayDTO dayDto = new DayDTO(day.getDayId(), doc.getDocId(), date, start, end);
                    dbDayRepo.update(dayDto);

                    dbTsRepo.deleteByDayId(day.getDayId());
                    day.getTimeSlotList().forEach(ts -> {
                        TimeSlotDTO tsDto = new TimeSlotDTO(ts.getTsId(),ts.getTimeSlotDate(), day.getDayId(), ts.getStartTime(), ts.getEndTime() );
                        dbTsRepo.save(tsDto);
                    });
                });
    }
    public void deleteDoctor(Doctor doc) {
        doctorList.remove(doc);
        dbDocRepo.delete(doc.getDocId());
    }

    public List<Doctor> getAllDoctors(){
        return doctorList;
    }

    public Doctor getDoctorById(String docId){
        for (Doctor doc : doctorList) {
            if (doc.getDocId().equals(docId)) {
                return doc;
            }
        }
        return null;
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

    public Appointment bookAppointmentForDoc(Doctor doc, Patient patient, LocalDate date, LocalTime appointmentTime) {
        for (Day day : doc.getDayWiseTimeSlots()) {
            if (day.getDate().equals(date)) {
                for (TimeSlot timeSlot : day.getTimeSlotList()) {
                    if (timeSlot.getStartTime().equals(appointmentTime) && timeSlot.getAppointment() == null) {

                        Appointment appt = new Appointment(doc, patient, timeSlot);
                        timeSlot.setAppointment(appt);
                        doc.getDocAppointments().add(appt);
                        patient.getPatientAppointments().add(appt);

                        AppointmentDTO dto = new AppointmentDTO(appt);
                        dbAppointmetRepo.save(dto);

                        return appt;
                    }
                }
            }
        }
        return null;
    }


    public boolean cancelAppointment(Doctor doc, LocalDate date, LocalTime appointmentTime) {
        for (Day day : doc.getDayWiseTimeSlots()) {
            if (day.getDate().equals(date)) {
                for (TimeSlot timeSlot : day.getTimeSlotList()) {
                    if (timeSlot.getStartTime().equals(appointmentTime) && timeSlot.getAppointment() != null) {
                        Appointment appt = timeSlot.getAppointment();
                        timeSlot.setAppointment(null);
                        doc.getDocAppointments().remove(appt);
                        appt.getPatient().getPatientAppointments().remove(appt);

                        dbAppointmetRepo.delete(appt.getAppointmentId());

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
}
