import Controller.AdminDesk;
import Controller.ReceptionDesk;
import Entity.*;
import Service.OnStartService;

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
        OnStartService.getInstance().loadActiveDoctors();
        this.adminDesk = new AdminDesk();
    }

    public void run(){
        while(true){
            System.out.println("select one: ");
            System.out.println("1. Admin");
            System.out.println("2. Receptionist");
            System.out.println("3. Exit");

            Scanner sc = new Scanner(System.in);
            int adminOrRecOption = sc.nextInt();
            sc.nextLine();

            switch (adminOrRecOption){
                case 1:
                    adminScreen();
                    continue;
                case 2:
                    receptionistInitScreen();
                    continue;
                case 3:
                    return;
            }
        }
    }



    public void adminScreen() {
        while(true) {
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
            sc.nextLine();

            switch (adminChoice) {
                case 1:
                    addDoctor();
                    continue;
                case 2:
                    viewAllDoc();
                    continue;
                case 3:
                    modifyDoc();
                    continue;
                case 4:
                    deleteDoc();
                    continue;
                case 5:
                    addReceptionist();
                    continue;
                case 6:
                    viewReceptionists();
                    continue;
                case 7:
                    deleteReceptionist();
                    continue;
                case 8:
                    return;
            }
        }
    }


    private void modifyDoc() {
        System.out.println("select doctor to modify availability: ");

        if(adminDesk.getDoctorList().size() == 0){
            System.out.println("no doctors are available to modify...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }

        int i = 1;
        for(Doctor doc : adminDesk.getDoctorList()){
            System.out.println(i++ + ". "+ doc.getDocName());
        }

        Scanner sc = new Scanner(System.in);
        Doctor doctor = null;


        while(doctor == null) {
            int docOption = sc.nextInt();
            sc.nextLine();
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
            sc.nextLine();
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
        System.out.println("please make sure that end time is greater than starttime (hh:mm): ");

        String edtime;
        LocalTime endtime = null;
        while(endtime == null || (endtime!=null && endtime.isBefore(starttime))){
            try{
                edtime = sc.nextLine();
                endtime = LocalTime.parse(edtime, dtf);
                System.out.println("formatted time : " +endtime);
                if(endtime.isBefore(starttime)){
                    throw new RuntimeException();
                }
                break;
            }
            catch (Exception e){
                System.out.println("enter correct format time");
                System.out.println("make sure this is greater than " + starttime);
            }
        }

        adminDesk.modifyAvailabilityOfDoctor(doctor, day.getDate(), starttime, endtime);

        System.out.println("modified successfully");


    }

    private void viewAllDoc() {
        if(adminDesk.getDoctorList().size() == 0){
            System.out.println("no doctors are available...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }
        System.out.println("list of doctors:  ");
        int i = 1;
        for(Doctor doc : adminDesk.getDoctorList()){
            System.out.println("name :  "+ doc.getDocName() + "specialization : " + doc.getDocSpecialization());
            i++;
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
            sc.nextLine();
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
        System.out.println("please make sure that end time is greater than starttime (hh:mm): ");

        String edtime;
        LocalTime endtime = null;
        while(endtime == null || (endtime!=null && endtime.isBefore(starttime))){
            try{
                edtime = sc.nextLine();
                endtime = LocalTime.parse(edtime, dtf);
                System.out.println("formatted time : " +endtime);
                if(endtime.isBefore(starttime)){
                    throw new RuntimeException();
                }
                break;
            }
            catch (Exception e){
                System.out.println("enter correct format time");
                System.out.println("make sure this is greater than " + starttime);
            }
        }

        adminDesk.addDoctor(docName, specialization, starttime, endtime);

        System.out.println("added doctor successfully");
    }


    public void deleteDoc(){
        System.out.println("select doctor to delete: ");

        if(adminDesk.getDoctorList().size() == 0){
            System.out.println("no doctors are available to delete...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }

        int i = 1;
        for(Doctor doc : adminDesk.getDoctorList()){
            System.out.println(i++ + ". "+ doc.getDocName());
        }


        Scanner sc = new Scanner(System.in);
        Doctor doctor = null;


        while(doctor == null) {
            int docOption = sc.nextInt();
            sc.nextLine();

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


    public void addReceptionist(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter name :");
        String name = sc.nextLine().trim();
        adminDesk.addReceptionist(name);
        System.out.println("successfully added receptionist -- " + name);
    }

    public void viewReceptionists(){
        if(adminDesk.getAllReceptionists().size() == 0){
            System.out.println("no receptionists are available...");
            System.out.println();
            System.out.println();
            System.out.println();
            return ;
        }
        int i=1;
        for(Receptionist receptionist : adminDesk.getAllReceptionists()){
            System.out.println(i++ + ".  id : " + receptionist.getReceptionistId() + " name : " +receptionist.getReceptionistName());
        }
    }

    public void deleteReceptionist(){
        if(adminDesk.getAllReceptionists().size() == 0){
            System.out.println("no receptionists are there to delete...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }

        int i = 1;
        for(Receptionist rec : adminDesk.getAllReceptionists()){
            System.out.println(i++ + ". "+ rec.getReceptionistName());
        }

        Scanner sc = new Scanner(System.in);
        Receptionist rec = null;
        System.out.println("choose one to delete : ");

        while(rec == null) {
            int recOption = sc.nextInt();
            sc.nextLine();
            if(recOption > adminDesk.getAllReceptionists().size() || recOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                rec = adminDesk.getAllReceptionists().get(recOption-1);
                break;
            }
        }
        System.out.println("receptionist  : " + rec.getReceptionistName() + " is deleted");

    }




    private void receptionistInitScreen() {
        System.out.println("select one of the receptionists available : ");
        int i = 1;
        if(adminDesk.getAllReceptionists().size() == 0){
            System.out.println("sorry no receptionit available currently...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }
        for(Receptionist rec : adminDesk.getAllReceptionists()){
            System.out.println(i++ + ". "+ rec.getReceptionistName());
        }

        Scanner sc = new Scanner(System.in);
        Receptionist receptionist = null;


        while(receptionist == null) {
            int recOption = sc.nextInt();
            sc.nextLine();
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
        receptionDesk = null;
    }


    private void receptionistScreen() {
        while(true) {
            System.out.println("select one: ");
            System.out.println("1. Book apointment");
            System.out.println("2. Cancel apointment");
            System.out.println("3. Cancel and rescedule apointment");
            System.out.println("4. view all patients");
            System.out.println("5. view all apointements by patient");
            System.out.println("6. view all apointements by doctor");
            System.out.println("7. exit");


            Scanner sc = new Scanner(System.in);
            int recepchoice = sc.nextInt();
            sc.nextLine();

            switch (recepchoice) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    cancelAppointment();
                    break;
                case 3:
                    cancelAndReschedule();
                    break;
                case 4:
                    viewAllPatients();
                    break;
                case 5:
                    viewAllAppointmentsByPatient();
                    break;
                case 6:
                    viewAllAppointmentsByDoctor();
                    break;
                case 7:
                    return;
            }
        }
    }



    private void bookAppointment() {
        System.out.println("select an option :");
        System.out.println("1. excisting patient");
        System.out.println("2. new patient");
        Scanner sc = new Scanner(System.in);
        int bookOption = sc.nextInt();
        sc.nextLine();

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
        sc.nextLine();
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
            sc.nextLine();
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
        Scanner sc = new Scanner(System.in);
        List<Patient> allPatients = receptionDesk.getAllPatients();

        if(allPatients.size() == 0){
            System.out.println("no patients are there . create new one...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }

        List<Patient> patientList = new ArrayList<>();
        while (patientList.isEmpty()) {
            System.out.print("Enter starting characters of patient name: ");
            String nameStart = sc.nextLine().trim();

            if (nameStart.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            patientList = allPatients.stream()
                    .filter(p -> p.getPatientName().toLowerCase().startsWith(nameStart.toLowerCase()))
                    .toList();

            if (patientList.isEmpty()) {
                System.out.println("No patients found with that name start. Try again.");
            }
        }

        System.out.println("Matching patients:");
        for (int i = 0; i < patientList.size(); i++) {
            Patient p = patientList.get(i);
            System.out.printf("%d. %s (ID: %s, Age: %d)\n", i + 1, p.getPatientName(), p.getPatientId(), p.getPatientAge());
        }

        Patient selectedPatient = null;
        while (selectedPatient == null) {
            System.out.print("Select patient number: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= patientList.size()) {
                    selectedPatient = patientList.get(choice - 1);
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        System.out.println("Selected Patient: " + selectedPatient.getPatientName());

        appoinmentHelper(selectedPatient);

    }


    private void appoinmentHelper(Patient patient){
        System.out.println("select doctor(all are specaialized in "+ patient.getPateintProblemSpecialization()+ " : ");

        if(receptionDesk.getDoctorsBySpecialization(patient.getPateintProblemSpecialization()).stream().filter(doctor -> doctor.getNumberOfAvailableSlots() > 0).toList().size() == 0){
            System.out.println("no doctor are available for this specialization - " + patient.getPateintProblemSpecialization() + "...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }


        int i = 1;
        for(Doctor doc : receptionDesk.getDoctorsBySpecialization(patient.getPateintProblemSpecialization()).stream().filter(doctor -> doctor.getNumberOfAvailableSlots() > 0).toList()){
            System.out.println(i++ + ". "+ doc.getDocName());
        }
        Scanner sc = new Scanner(System.in);
        Doctor doctor = null;


        while(doctor == null) {
            int docOption = sc.nextInt();
            sc.nextLine();
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
        for(Day day : doctor.getDayWiseTimeSlots()){
            boolean available = false;
            for(TimeSlot ts: day.getTimeSlotList()){
                if(ts.getAppointment() == null){
                    available = true;
                }
            }
            if(!available){
                continue;
            }
            System.out.println(i++ + ". "+ day.getDate());
        }

        sc = new Scanner(System.in);
        Day day = null;


        while(day == null) {
            int dayOption = sc.nextInt();
            sc.nextLine();
            if(dayOption > doctor.getDayWiseTimeSlots().size()|| dayOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                day = doctor.getDayWiseTimeSlots().get(dayOption-1);
                break;
            }
        }


        i = 1;
        for(TimeSlot timeSlot : day.getTimeSlotList()){
            if(timeSlot.getAppointment() == null)
            System.out.println(i++ + ". "+ timeSlot.getStartTime());
        }

        sc = new Scanner(System.in);
        TimeSlot timeSlot = null;


        while(timeSlot == null) {
            int timOption = sc.nextInt();
            sc.nextLine();
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
        System.out.println("for patient : " + patient.getPatientName()+ " on" + timeSlot.getTimeSlotDate()+"  at " +timeSlot.getStartTime());

    }

    private Patient getPatientsByname(){
        Scanner sc = new Scanner(System.in);
        List<Patient> allPatients = receptionDesk.getAllPatients();

        List<Patient> patientList = new ArrayList<>();
        while (patientList.isEmpty()) {
            System.out.print("Enter starting characters of patient name: ");
            String nameStart = sc.nextLine().trim();

            if (nameStart.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            patientList = allPatients.stream()
                    .filter(p -> p.getPatientName().toLowerCase().startsWith(nameStart.toLowerCase()))
                    .toList();

            if (patientList.isEmpty()) {
                System.out.println("No patients found with that name start. Try again.");
            }
        }

        System.out.println("Matching patients:");
        for (int i = 0; i < patientList.size(); i++) {
            Patient p = patientList.get(i);
            System.out.printf("%d. %s (ID: %s, Age: %d)\n", i + 1, p.getPatientName(), p.getPatientId(), p.getPatientAge());
        }

        Patient selectedPatient = null;
        while (selectedPatient == null) {
            System.out.print("Select patient number: ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= patientList.size()) {
                    selectedPatient = patientList.get(choice - 1);
                } else {
                    System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        System.out.println("Selected Patient: " + selectedPatient.getPatientName());
        return selectedPatient;
    }


    private Patient cancelAppointment() {
        Patient patient = getPatientsByname();

        patient.getPatientAppointments();
        Scanner sc = new Scanner(System.in);


        if(patient.getPatientAppointments().size() == 0){
            System.out.println("no appointmnets are there for this patient...");
            System.out.println();
            System.out.println();
            System.out.println();
            return null;
        }
        int i = 1;
        for(Appointment appointment : patient.getPatientAppointments()){
            System.out.println(i++ + ". " + appointment.getDoc().getDocName() + " -- " + appointment.getDoc().getDocSpecialization() + " --  at " +appointment.getTimeSlot().getStartTime()+" on "+ appointment.getTimeSlot().getTimeSlotDate() );
        }

        sc = new Scanner(System.in);
        Appointment appointment = null;

        System.out.println("select an option :");

        while(appointment == null) {
            int apOption = sc.nextInt();
            sc.nextLine();
            if(apOption > patient.getPatientAppointments().size()|| apOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                appointment = patient.getPatientAppointments().get(apOption-1);
                break;
            }
        }

        receptionDesk.cancelAppointment(appointment);
        System.out.println("appointment cancelled succesfully...");
        return appointment.getPatient();
    }


    public void cancelAndReschedule(){
        Patient patient =  cancelAppointment();
        appoinmentHelper(patient);
    }

    public void viewAllPatients(){
        if(receptionDesk.getAllPatients().size() == 0){
            System.out.println("no patients are there...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }
        int i = 1;
        for(Patient patient : receptionDesk.getAllPatients()){
            System.out.println(i++ + ". id: " + patient.getPatientId() + " , name :  " + patient.getPatientName() +" , age :  " + patient.getPatientAge() +" problem related to :  "+ patient.getPateintProblemSpecialization());
        }
    }

    public void viewAllAppointmentsByPatient(){
        Patient patient = getPatientsByname();
        System.out.println("here are the appointments booked by patient : + "+ patient.getPatientName() + " : ");
        if(receptionDesk.getPatientAppointmentList(patient).size() == 0){
            System.out.println("no appointmnets are there for this patient -- "+ patient.getPatientName()+ " ...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }
        int i = 1;
        for(Appointment appointment : receptionDesk.getPatientAppointmentList(patient)){
            System.out.println(i++ + ". " + appointment.getDoc().getDocName() + " -- " + appointment.getDoc().getDocSpecialization() + " --  at " +appointment.getTimeSlot().getStartTime()+" on "+ appointment.getTimeSlot().getTimeSlotDate() );
        }
    }

    public void viewAllAppointmentsByDoctor(){
        int i = 1;
        for(Doctor doc : adminDesk.getDoctorList()){
            System.out.println(i++ + ". "+ doc.getDocName());
        }

        Scanner sc = new Scanner(System.in);
        Doctor doctor = null;


        while(doctor == null) {
            int docOption = sc.nextInt();
            sc.nextLine();
            if(docOption > adminDesk.getDoctorList().size() || docOption == 0) {
                System.out.println("invalid input. select again");
                continue;
            }
            else{
                doctor = adminDesk.getDoctorList().get(docOption-1);
                break;
            }
        }

        if(doctor.getDocAppointments().size() == 0){
            System.out.println("no appointmnets are there for this doctor...");
            System.out.println();
            System.out.println();
            System.out.println();
            return;
        }

        System.out.println("here are the appointments made by -  " + doctor.getDocName() + " : ");
        for(Appointment appointment : doctor.getDocAppointments()){
            System.out.println(i++ + ". with --" + appointment.getPatient().getPatientName() + " -- " + appointment.getPatient().getPateintProblemSpecialization() + " --  at " +appointment.getTimeSlot().getStartTime()+" on "+ appointment.getTimeSlot().getTimeSlotDate() );
        }

    }



}

