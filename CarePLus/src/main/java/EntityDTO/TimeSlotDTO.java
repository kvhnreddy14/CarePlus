package EntityDTO;

import Entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlotDTO {
    private String tsId;
    private LocalDate date;
    private String dayId;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlotDTO(){

    }

    public TimeSlotDTO(String tsId, LocalDate date, String dayId, LocalTime startTime, LocalTime endTime) {
        this.tsId = tsId;
        this.date = date;
        this.dayId = dayId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTsId() {
        return tsId;
    }

    public void setTsId(String tsId) {
        this.tsId = tsId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
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
}
