package Service;

import Entity.Receptionist;

import java.util.ArrayList;
import java.util.List;

public class ReceptionService {
    private List<Receptionist> receptionistList = new ArrayList<>();

    private static ReceptionService instance;

    private ReceptionService(){

    }

    public static ReceptionService getInstance(){
        if(instance == null){
            synchronized(ReceptionService.class){
                if(instance==null){
                    instance = new ReceptionService();
                }
            }
        }
        return instance;
    }

    public List<Receptionist> getReceptionistList() {
        return receptionistList;
    }

    public void addReceptionist(String name){
        receptionistList.add(new Receptionist(name));
    }

    public void deleteReceptionist(Receptionist receptionist){
        receptionistList.remove(receptionist);
    }
}
