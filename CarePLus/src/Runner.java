import Controller.AdminDesk;
import Controller.ReceptionDesk;
import Entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Runner {
    public DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m");
    private AdminDesk adminDesk;
    private ReceptionDesk receptionDesk;

    Runner(){
        this.adminDesk = new AdminDesk();
        adminDesk.renderAllDoctorSlotsToNextKdays(3);

    }

    public void run(){
        while(true){
            System.out.println("select one: ");
            System.out.println("1. Admin");
            System.out.println("2. Receptionist");

            Scanner sc = new Scanner(System.in);
            int adminOrRecOption = sc.nextInt();

            switch (adminOrRecOption){
                case 1:
                    adminScreen();
                    break;
                case 2:
                    receptionistScreen();
                    break;

            }
        }
    }



    public void adminScreen() {
        System.out.println("select one: ");
        System.out.println("1. Add doctor");
        System.out.println("2. view doctors");
        System.out.println("3. modify availability of doctor");
        System.out.println("4. delete doctor");
        System.out.println("5. Add receptionist");
        System.out.println("6. view receptionists");
        System.out.println("7. delete receptionist");
        System.out.println("8. exit");


        Scanner sc = new Scanner(System.in);
        int adminChoice = sc.nextInt();

        switch (adminChoice) {
            case 1:
                addDoctor();
                break;
            case 2:
                viewAllDoc();
            case 3:
                modifyDoc();
            case 4:
                deleteDoc();
                break;


        }
    }

    private void modifyDoc() {
        System.out.println("select doctor to modify availability: ");
        int i = 1;
        for(Doctor doc : adminDesk.getDoctorList()){
            System.out.println(i + ". "+ doc.getDocName());
        }

        Scanner sc = new Scanner(System.in);
        Doctor doctor = null;


        while(doctor == null) {
            int docOption = sc.nextInt();
            if(docOption > adminDesk.getDoctorList().size() || docOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                doctor = adminDesk.getDoctorList().get(docOption-1);
                break;
            }
        }
        System.out.println("enter choice of dates available to modify:  ");
        for(Day day : doctor.getDayWiseTimeSlots()){
            System.out.println(i + ". "+ day.getDate());
        }

        Day day = null;
        while(day == null) {
            int dayOption = sc.nextInt();
            if(dayOption > doctor.getDayWiseTimeSlots().size() || dayOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                day = doctor.getDayWiseTimeSlots().get(dayOption-1);
                break;
            }
        }

        System.out.println("enter the starttime doctor will be avilable (hh:mm): ");

        String strtime;
        LocalTime starttime = null;
        while(starttime == null){
            try{
//                sc.nextLine();
                strtime = sc.nextLine();
                starttime = LocalTime.parse(strtime, dtf);
                System.out.println("choosen time : " +strtime);
                break;
            }
            catch (Exception e){
                System.out.println("enter correct format time");
            }
        }

        System.out.println("enter the endtime till the doctor will be avilable (hh:mm): ");

        String edtime;
        sc.nextLine();
        LocalTime endtime = null;
        while(endtime == null){
            try{
//                sc.nextLine();
                edtime = sc.nextLine();
                endtime = LocalTime.parse(edtime, dtf);
                System.out.println("formatted time : " +endtime);
                break;
            }
            catch (Exception e){
                System.out.println("enter correct format time");
            }
        }

        adminDesk.modifyAvailabilityOfDoctor(doctor, day.getDate(), starttime, endtime);

        System.out.println("modified successfully");


    }

    private void viewAllDoc() {
        System.out.println("list of doctors:  ");
        int i = 1;
        for(Doctor doc : adminDesk.getDoctorList()){
            System.out.println("name :  "+ doc.getDocName() + "specialization : " + doc.getDocSpecialization());
        }
    }


    public void addDoctor(){
        System.out.println("please enter name of doctor: ");
        Scanner sc = new Scanner(System.in);
        String docName = sc.nextLine();

        System.out.println("please select specialization: ");
        int i = 1;
        for(Specialization speci : Specialization.values()){
            System.out.println(i + ". " + speci);
            i++;
        }

        Specialization specialization = null;
        while(specialization == null) {
            int speciOption = sc.nextInt();
            if(speciOption > Specialization.values().length || speciOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                specialization = Arrays.stream(Specialization.values()).toList().get(speciOption-1);
                break;
            }
        }


        System.out.println("enter the general start time (hh:mm):  ");
        String strtime;
        LocalTime starttime = null;
        while(starttime == null){
            try{
//                sc.nextLine();
                strtime = sc.nextLine();
                starttime = LocalTime.parse(strtime, dtf);
                System.out.println("formatted time : " +strtime);
                break;
            }
            catch (Exception e){
                System.out.println("enter correct format time");
            }
        }

        System.out.println("  ");
        System.out.println("enter the general end time (hh:mm): ");

        String edtime;
        sc.nextLine();
        LocalTime endtime = null;
        while(endtime == null){
            try{
//                sc.nextLine();
                edtime = sc.nextLine();
                endtime = LocalTime.parse(edtime, dtf);
                System.out.println("formatted time : " +endtime);
                break;
            }
            catch (Exception e){
                System.out.println("enter correct format time");
            }
        }

        adminDesk.addDoctor(docName, specialization, starttime, endtime);

        System.out.println("added doctor successfully");
    }


    public void deleteDoc(){
        System.out.println("select doctor to delete: ");
        int i = 1;
        for(Doctor doc : adminDesk.getDoctorList()){
            System.out.println(i + ". "+ doc.getDocName());
        }


        Scanner sc = new Scanner(System.in);
        Doctor doctor = null;


        while(doctor == null) {
            int docOption = sc.nextInt();
            if(docOption > adminDesk.getDoctorList().size() || docOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                doctor = adminDesk.getDoctorList().get(docOption-1);
                break;
            }
        }

        adminDesk.deleteDoctor(doctor);
        System.out.println("deleted doc with name " +doctor.getDocName());
    }



    private void receptionistInitScreen() {
        System.out.println("select one of the receptionists available : ");
        int i = 1;
        for(Receptionist rec : adminDesk.getAllReceptionists()){
            System.out.println(i + ". "+ rec.getReceptionistName());
        }

        Scanner sc = new Scanner(System.in);
        Receptionist receptionist = null;


        while(receptionist == null) {
            int recOption = sc.nextInt();
            if(recOption > adminDesk.getAllReceptionists().size() || recOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                receptionist = adminDesk.getAllReceptionists().get(recOption-1);
                break;
            }
        }

        System.out.println(receptionist.getReceptionistName() + " is selected");
        this.receptionDesk = new ReceptionDesk(receptionist);
        receptionistScreen();
    }


    private void receptionistScreen() {
        System.out.println("select one: ");
        System.out.println("1. Book apointment");
        System.out.println("2. Cancel apointment");
        System.out.println("3. Cancel and rescedule apointment");
        System.out.println("4. view all apointements by patient");
        System.out.println("5. view all apointements by doctor");
        System.out.println("8. exit");


        Scanner sc = new Scanner(System.in);
        int recepchoice = sc.nextInt();

        switch (recepchoice) {
            case 1:
                bookAppointment();
                break;
            case 2:
//                cancelAppointment();
                break;

        }
    }

    private void bookAppointment() {
        System.out.println("select and option :");
        System.out.println("1. excisting patient");
        System.out.println("2. new patient");
        Scanner sc = new Scanner(System.in);
        int bookOption = sc.nextInt();
        switch (bookOption) {
            case 1:
                bookAppointmentFromExisting();
                break;
            case 2:
                bookAppointmentNewPatient();
                break;

        }

    }
    private void bookAppointmentNewPatient() {
        Scanner sc = new Scanner(System.in);
        System.out.println("please enter the patient name : ");
        String patName = sc.nextLine();
        System.out.println("please enter the patient age : ");
        int patAge = sc.nextInt();
        System.out.println("please enter the patient phone number : ");
        String patPhone = sc.nextLine();
        System.out.println("select the patient problem specialization : ");
        int i = 1;
        for(Specialization speci : Specialization.values()){
            System.out.println(i + ". " + speci);
            i++;
        }

        Specialization specialization = null;
        while(specialization == null) {
            int speciOption = sc.nextInt();
            if(speciOption > Specialization.values().length || speciOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                specialization = Arrays.stream(Specialization.values()).toList().get(speciOption-1);
                break;
            }
        }
        Patient patient = receptionDesk.addPatient(patName,patAge,patPhone,specialization);

        System.out.println("patient added successfully");

        appoinmentHelper(patient);
    }

    private void bookAppointmentFromExisting() {
        System.out.println("Enter patient name: ");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        List<Patient> patientList = new ArrayList<>();
        while(patientList.size() == 0){
            System.out.println("");
        }

    }

    private void appoinmentHelper(Patient patient){
        System.out.println("select doctor(all are specaialized in "+ patient.getPateintProblemSpecialization()+ " : ");
        int i = 1;
        for(Doctor doc : receptionDesk.getDoctorsBySpecialization(patient.getPateintProblemSpecialization()).stream().filter(doctor -> doctor.getNumberOfAvailableSlots() > 0).toList()){
            System.out.println(i + ". "+ doc.getDocName());
        }
        Scanner sc = new Scanner(System.in);
        Doctor doctor = null;


        while(doctor == null) {
            int docOption = sc.nextInt();
            if(docOption > receptionDesk.getDoctorsBySpecialization(patient.getPateintProblemSpecialization()).size() || docOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                doctor = receptionDesk.getDoctorsBySpecialization(patient.getPateintProblemSpecialization()).get(docOption-1);
                break;
            }
        }


        System.out.println("Doctor " + doctor.getDocName() + " is selected");
        i = 1;
        for(TimeSlot timeSlot : receptionDesk.getDoctorsAvailableTimeSlots(doctor)){
            System.out.println(i + ". "+ timeSlot.getStartTime());
        }

        sc = new Scanner(System.in);
        TimeSlot timeSlot = null;


        while(timeSlot == null) {
            int timOption = sc.nextInt();
            if(timOption > receptionDesk.getDoctorsAvailableTimeSlots(doctor).size()|| timOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                timeSlot = receptionDesk.getDoctorsAvailableTimeSlots(doctor).get(timOption-1);
                break;
            }
        }

        receptionDesk.bookAppointment(doctor, patient, timeSlot.getTimeSlotDate(), timeSlot.getStartTime());

        System.out.println("booked successfully");
        System.out.println("choosen doctor : " + doctor.getDocName());
        System.out.println("for patient : " + patient.getPatientName()+ " at" + timeSlot.getTimeSlotDate()+"  " +timeSlot.getStartTime());

    }



}

