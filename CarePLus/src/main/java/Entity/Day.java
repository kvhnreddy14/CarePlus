package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Day {
    private final String dayId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<TimeSlot> timeSlotList;
    private int appintmentSizeInMinutes = 30;

    public Day(LocalDate date,LocalTime startTime, LocalTime endTime){
        this.dayId = UUID.randomUUID().toString().substring(0, 5);
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotList = new ArrayList<>();
        regenerateTimeSlots();
    }

    public Day(String dayId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.dayId = dayId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeSlotList = new ArrayList<>();
    }

    public void regenerateTimeSlots() {
        List<TimeSlot> slots = new ArrayList<>();
        LocalTime current = startTime;
        while (current.plusMinutes(appintmentSizeInMinutes).isBefore(endTime) || current.plusMinutes(appintmentSizeInMinutes).equals(endTime)) {
            TimeSlot slot = new TimeSlot(current, current.plusMinutes(appintmentSizeInMinutes), this.date);
            slots.add(slot);
            current = current.plusMinutes(appintmentSizeInMinutes);
        }
        this.timeSlotList = slots;
    }

    public String getDayId() {
        return dayId;
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
