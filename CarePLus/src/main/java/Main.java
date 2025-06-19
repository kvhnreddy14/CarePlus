import Entity.Doctor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.run();
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("H:m");
//        LocalTime t = LocalTime.parse("8:30", dtf);
//        System.out.println(t);
//        LocalTime starttime = LocalTime.parse("3:00");
//        System.out.println(starttime);
        int i = 0;
        for(int j = 0; j < 10; j++){
            System.out.println(i++ + ". ");
        }

    }
}
