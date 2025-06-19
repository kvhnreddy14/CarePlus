package Entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Doctor {
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m");
    private final String docId;
    private String docName;
    private Specialization docSpecialization;
    private LocalTime availableStartTime = LocalTime.parse("9:00", dtf);
    private LocalTime availableEndTime = LocalTime.parse("6:00", dtf);
    private List<Appointment> docAppointments;
    private List<Day> dayWiseTimeSlots;

    public Doctor(String docName, Specialization docSpecialization, LocalTime availableStartTime, LocalTime availableEndTime) {
        this.docId = UUID.randomUUID().toString().substring(0, 5);
        this.docName = docName;
        this.docSpecialization = docSpecialization;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.dayWiseTimeSlots = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 3; i++) {
            dayWiseTimeSlots.add(new Day(today, availableStartTime, availableEndTime));
            today = today.plusDays(1);
        }
    }

    //for db
    public Doctor(String docId, String docName, Specialization docSpecialization, LocalTime availableStartTime, LocalTime availableEndTime) {
        this.docId = docId;
        this.docName = docName;
        this.docSpecialization = docSpecialization;
        this.availableStartTime = availableStartTime;
        this.availableEndTime = availableEndTime;
        this.docAppointments = new ArrayList<>();
        this.dayWiseTimeSlots = new ArrayList<>();
    }


    public void renderTonextKDays(int k) {
        LocalDate today = LocalDate.now();
        dayWiseTimeSlots.removeIf(day -> day.getDate().isBefore(today));
        while (dayWiseTimeSlots.size() < k) {
            LocalDate dateToAdd = today.plusDays(dayWiseTimeSlots.size());
            Day newDay = new Day(dateToAdd, availableStartTime, availableEndTime);
            dayWiseTimeSlots.add(newDay);
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

