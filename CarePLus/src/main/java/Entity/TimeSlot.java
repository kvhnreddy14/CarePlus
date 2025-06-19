package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TimeSlot {
    private final String tsId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate timeSlotDate;
    private Appointment appointment = null;

    public TimeSlot(LocalTime startTime, LocalTime endTime,LocalDate timeSlotDate){
        this.tsId = UUID.randomUUID().toString().substring(0, 5);
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotDate = timeSlotDate;
    }


    public TimeSlot(String tsId, LocalTime startTime, LocalTime endTime, LocalDate timeSlotDate) {
        this.tsId = tsId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotDate = timeSlotDate;
        this.appointment = null;
    }


//    public TimeSlot(LocalTime startTime, LocalTime endTime, Appointment appointment){
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.appointment = appointment;
//        tsId = "";
//    }



    public String getTsId() {
        return tsId;
    }

    public LocalDate getTimeSlotDate() {
        return timeSlotDate;
    }

    public void setTimeSlotDate(LocalDate timeSlotDate) {
        this.timeSlotDate = timeSlotDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
