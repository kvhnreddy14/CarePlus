package EntityDTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class DayDTO {
    private String dayId;
    private String docId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public DayDTO(){

    }

    public DayDTO(String dayId, String docId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.dayId = dayId;
        this.docId = docId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }



    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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
}

