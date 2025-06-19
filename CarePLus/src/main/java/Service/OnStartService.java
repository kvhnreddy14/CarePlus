package Service;

import Entity.*;
import EntityDTO.AppointmentDTO;
import EntityDTO.DayDTO;
import EntityDTO.DoctorDTO;
import EntityDTO.TimeSlotDTO;
import Repository.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OnStartService {

    private PatientService patientService = PatientService.getInstance();
    private DocService docService = DocService.getInstance();
    private ReceptionService receptionService = ReceptionService.getInstance();


    private final DoctorRepository dbDocRepo = new DoctorRepository();
    private final DayRepository dbDayRepo = new DayRepository();
    private final TimeSlotRepository dbTsRepo = new TimeSlotRepository();
    private final AppointmentRepository dbAppointmetRepo = new AppointmentRepository();
    private final PatientRepository dbPatientRepo = new PatientRepository();


    private static OnStartService instance;

    private OnStartService(){

    }

    public static OnStartService getInstance(){
        if(instance == null){
            synchronized(OnStartService.class){
                if(instance==null){
                    instance = new OnStartService();
                }
            }
        }
        return instance;
    }


    public void loadActiveDoctors() {
        List<DoctorDTO> doctorDTOs = dbDocRepo.findAll();
        List<DayDTO> dayDTOs;
        List<TimeSlotDTO> timeSlotDTOs;

        List<Doctor> doctorList = new ArrayList<>();
        List<Day> dayList = new ArrayList<>();
        List<TimeSlot> timeSlotList = new ArrayList<>();

        LocalDate today = LocalDate.now();


        for (DoctorDTO doctorDTO : doctorDTOs) {
            Doctor doctor = new Doctor(doctorDTO.getDocId(), doctorDTO.getDocName(), Specialization.valueOf(doctorDTO.getSpecialization()), doctorDTO.getAvailableStartTime(), doctorDTO.getAvailableEndTime());
            doctorList.add(doctor);
            dayDTOs = dbDayRepo.findAllByDoctorId(doctorDTO.getDocId());

            for (DayDTO dayDTO : dayDTOs) {
                if (today.isAfter(dayDTO.getDate())) continue;
                Day day = new Day(dayDTO.getDayId(), dayDTO.getDate(), dayDTO.getStartTime(), dayDTO.getEndTime());
                dayList.add(day);

                timeSlotDTOs = dbTsRepo.findByDayId(dayDTO.getDayId());

                for (TimeSlotDTO timeSlotDTO : timeSlotDTOs) {
                    TimeSlot timeSlot = new TimeSlot(timeSlotDTO.getTsId(), timeSlotDTO.getStartTime(), timeSlotDTO.getEndTime(), timeSlotDTO.getDate());
                    timeSlotList.add(timeSlot);
                }
            }
        }

        List<Patient> patientList = patientService.getPatientsList();

        List<AppointmentDTO> appointmentDTOS = dbAppointmetRepo.findAllFromDate(today);
        for(AppointmentDTO appointmentDTO : appointmentDTOS){
            Doctor doc = null;
            Patient pat = null;
            TimeSlot ts = null;
            for(Doctor doctor : doctorList){
                if(appointmentDTO.getDocId().equals(doctor.getDocId())){
                    doc = doctor;
                }
            }
            for(Patient patient : patientList){
                if(appointmentDTO.getPatientId().equals(patient.getPatientId())){
                    pat = patient;
                }
            }
            for(TimeSlot timeSlot : timeSlotList){
                if(appointmentDTO.getTsId().equals(timeSlot.getTsId())){
                    ts = timeSlot;
                }
            }
            if(doc!=null && pat!=null && ts!=null){
                Appointment appointment = new Appointment(appointmentDTO.getAppointmentId(),doc, pat, ts);
                doc.getDocAppointments().add(appointment);
                pat.getPatientAppointments().add(appointment);
                ts.setAppointment(appointment);
            }
        }
        docService.setDoctorList(doctorList);
        docService.setNextDays();
        receptionService.loadAllReceptionists();
    }
}
