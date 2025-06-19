package Service;

import Entity.Receptionist;
import EntityDTO.ReceptionistDTO;
import Repository.ReceptionistRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReceptionService {
    private List<Receptionist> receptionistList = new ArrayList<>();
    private final ReceptionistRepository dbReceptionistRepo = new ReceptionistRepository();


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


    public void loadAllReceptionists() {
        List<ReceptionistDTO> dtoList = dbReceptionistRepo.findAll();
        List<Receptionist> receptionists = dtoList.stream()
                .map(dto -> new Receptionist(dto.getReceptionistId(), dto.getReceptionistName()))
                .collect(Collectors.toList());
        this.receptionistList = receptionists;
    }

    public List<Receptionist> getReceptionistList() {
        return receptionistList;
    }

    public void addReceptionist(String name) {
        Receptionist receptionist = new Receptionist(name);
        receptionistList.add(receptionist);

        ReceptionistDTO dto = new ReceptionistDTO(receptionist.getReceptionistId(), receptionist.getReceptionistName());
        dbReceptionistRepo.save(dto);
    }

    public void deleteReceptionist(Receptionist receptionist) {
        receptionistList.remove(receptionist);
        dbReceptionistRepo.delete(receptionist.getReceptionistId());
    }
}
