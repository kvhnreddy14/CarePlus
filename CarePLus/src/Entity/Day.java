package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Day {


    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<TimeSlot> timeSlotList;
    private int appintmentSizeInMinutes;

    public Day(LocalDate date,LocalTime startTime, LocalTime endTime,int appintmentSizeInMinutes ){
        this.date = date;
        this.timeSlotList = new ArrayList<>();
        this.appintmentSizeInMinutes = appintmentSizeInMinutes;
        while(startTime != endTime){
            timeSlotList.add(new TimeSlot(startTime, startTime.plus(appintmentSizeInMinutes, ChronoUnit.MINUTES), date));
            startTime = startTime.plus(appintmentSizeInMinutes,ChronoUnit.MINUTES);
        }
    }

    public void setTimeSlotforParticularDay(LocalTime startTime, LocalTime endTime,int appintmentSizeInMinutes){
        timeSlotList.clear();
        while(startTime != endTime){
            timeSlotList.add(new TimeSlot(startTime, startTime.plus(appintmentSizeInMinutes, ChronoUnit.MINUTES), date));
            startTime = startTime.plus(appintmentSizeInMinutes,ChronoUnit.MINUTES);
        }
    }

    public List<TimeSlot> getTimeSlotList() {
        return timeSlotList;
    }

    public void setTimeSlotList(List<TimeSlot> timeSlotList) {
        this.timeSlotList = timeSlotList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public int getAppintmentSizeInMinutes() {
        return appintmentSizeInMinutes;
    }

    public void setAppintmentSizeInMinutes(int appintmentSizeInMinutes) {
        this.appintmentSizeInMinutes = appintmentSizeInMinutes;
    }
}
