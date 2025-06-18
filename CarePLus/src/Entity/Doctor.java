package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Doctor {
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m");
    public static int docNum;
    private final String docId;
    private String docName;
    private Specialization docSpecialization;
    private LocalTime availableStartTime = LocalTime.parse("9:00", dtf);
    private LocalTime availableEndTime = LocalTime.parse("6:00", dtf);
    private List<Appointment> docAppointments;
    private List<Day> dayWiseTimeSlots;

    public Doctor(String docName, Specialization docSpecialization, LocalTime availableStartTime, LocalTime availableEndTime) {
        docNum++;
        this.docId = "D" + docNum;
        this.docName = docName;
        this.docSpecialization = docSpecialization;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.dayWiseTimeSlots = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 3; i++) {
            dayWiseTimeSlots.add(new Day(today, availableStartTime, availableEndTime, 30));
            today = today.plusDays(1);
        }
    }

    public void renderTonextKDays(int k){
        LocalDate today = LocalDate.now();
        List<Day> dayWiseTimeSlots1 = new ArrayList<>();
        for(Day day: dayWiseTimeSlots){
            if(today.isAfter(day.getDate()) ){
                continue;
            }
            else{
                dayWiseTimeSlots1.add(day);
            }
        }

        if(dayWiseTimeSlots1.size() != 0){
            today = today.plusDays(1);
        }

        for (int i = 0; i < k; i++) {
            if(i < dayWiseTimeSlots1.size()){
                continue;
            }
            dayWiseTimeSlots.add(new Day(today, availableStartTime, availableEndTime, 30));
            today = today.plusDays(i);
        }
    }

    public int getNumberOfAvailableSlots(){
        int count=0;
        for(Day day: dayWiseTimeSlots){
            for(TimeSlot timeSlot : day.getTimeSlotList()){
                if(timeSlot.getAppointment() == null)
                count++;
            }
        }
        return count;
    }

    public static int getDocNum() {
        return docNum;
    }

    public static void setDocNum(int docNum) {
        Doctor.docNum = docNum;
    }

    public String getDocId() {
        return docId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Specialization getDocSpecialization() {
        return docSpecialization;
    }

    public void setDocSpecialization(Specialization docSpecialization) {
        this.docSpecialization = docSpecialization;
    }

    public LocalTime getAvailableStartTime() {
        return availableStartTime;
    }

    public void setAvailableStartTime(LocalTime availableStartTime) {
        this.availableStartTime = availableStartTime;
    }

    public LocalTime getAvailableEndTime() {
        return availableEndTime;
    }

    public void setAvailableEndTime(LocalTime availableEndTime) {
        this.availableEndTime = availableEndTime;
    }

    public List<Appointment> getDocAppointments() {
        return docAppointments;
    }

    public void setDocAppointments(List<Appointment> docAppointments) {
        this.docAppointments = docAppointments;
    }

    public List<Day> getDayWiseTimeSlots() {
        return dayWiseTimeSlots;
    }

    public void setDayWiseTimeSlots(List<Day> dayWiseTimeSlots) {
        this.dayWiseTimeSlots = dayWiseTimeSlots;
    }
}

