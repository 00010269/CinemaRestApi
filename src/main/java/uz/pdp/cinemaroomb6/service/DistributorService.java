package uz.pdp.cinemaroomb6.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.cinemaroomb6.dto.DistributorDto;
import uz.pdp.cinemaroomb6.model.Attachment;
import uz.pdp.cinemaroomb6.model.AttachmentContent;
import uz.pdp.cinemaroomb6.model.Distributor;
import uz.pdp.cinemaroomb6.projection.DistributorProjection;
import uz.pdp.cinemaroomb6.repository.AttachmentContentRepository;
import uz.pdp.cinemaroomb6.repository.AttachmentRepository;
import uz.pdp.cinemaroomb6.repository.DistributorRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DistributorService {

    @Autowired
    DistributorRepository distributorRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;


    public List<Distributor> getAllDistributors() {
        return distributorRepository.findAll();

    }


    public List<DistributorProjection> getAllDistributorsByMovieId(@PathVariable UUID movieId) {
        return distributorRepository.findByMovieId(movieId);
    }

    public void saveDistributor(DistributorDto distributorDto, MultipartFile multipartFile) {
        try {
            Distributor distributor = new Distributor();
            if (distributorDto.getId() != null) {
                distributor = distributorRepository.getById(distributorDto.getId());
            }
            distributor.setName(distributorDto.getName());
            distributor.setDescription(distributorDto.getDescription());
            if (multipartFile != null) {
                Attachment attachment = new Attachment(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize());
                attachmentRepository.save(attachment);
                AttachmentContent attachmentContent = new AttachmentContent(multipartFile.getBytes(), attachment);
                attachmentContentRepository.save(attachmentContent);
                distributor.setLogo(attachment);
            }
            distributorRepository.save(distributor);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDistributor(UUID distributorId) {
        distributorRepository.deleteById(distributorId);
    }
}
