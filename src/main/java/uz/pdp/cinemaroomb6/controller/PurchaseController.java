package uz.pdp.cinemaroomb6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.cinemaroomb6.model.Users;
import uz.pdp.cinemaroomb6.repository.UserRepository;
import uz.pdp.cinemaroomb6.service.PurchaseServiceImpl;

@RestController
@RequestMapping("api/purchase")
public class PurchaseController {

    @Autowired
    PurchaseServiceImpl purchaseService;

    @Autowired
    UserRepository userRepository;

    //    @PostMapping
//    public HttpEntity<?> purchaseTicket(@RequestBody PurchaseDto dto) {
//        return purchaseService.purchaseTicket(dto);
//    }


    @GetMapping
    public HttpEntity<?> createStripeSession() {
        Users nurbek = userRepository.findByUsername("nurbek");
        return purchaseService.createStripeSession(nurbek.getId());
    }




}
