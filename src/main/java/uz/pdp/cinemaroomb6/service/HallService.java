package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.dto.HallDto;
import uz.pdp.cinemaroomb6.model.Distributor;
import uz.pdp.cinemaroomb6.model.Hall;
import uz.pdp.cinemaroomb6.repository.HallRepository;

import java.util.List;
import java.util.UUID;


@Service
public class HallService {

    @Autowired
    HallRepository hallRepository;

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }

    public Hall saveHall(HallDto hallDto) {
        Hall hall=new Hall();
        if(hallDto.getId()!=null){
            hall=hallRepository.getById(hallDto.getId());
        }
        hall.setName(hallDto.getName());
        hall.setVipAddFeeInPercent(hallDto.getVipAddFeeInPercent());
        hallRepository.save(hall);
        return hall;
    }

    public void deleteHall(UUID hallId) {
        hallRepository.deleteById(hallId);
    }
}
