package uz.pdp.cinemaroomb6.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.model.Attachment;
import uz.pdp.cinemaroomb6.repository.AttachmentRepository;

import java.util.List;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    public List<Attachment> allAttachments (){
        return attachmentRepository.findAll();
    }
}
