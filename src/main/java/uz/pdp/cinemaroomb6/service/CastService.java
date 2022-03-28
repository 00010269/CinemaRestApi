package uz.pdp.cinemaroomb6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.cinemaroomb6.dto.CastDto;
import uz.pdp.cinemaroomb6.model.Attachment;
import uz.pdp.cinemaroomb6.model.AttachmentContent;
import uz.pdp.cinemaroomb6.model.Cast;
import uz.pdp.cinemaroomb6.model.Distributor;
import uz.pdp.cinemaroomb6.payload.ApiResponse;
import uz.pdp.cinemaroomb6.projection.CastProjection;
import uz.pdp.cinemaroomb6.repository.AttachmentContentRepository;
import uz.pdp.cinemaroomb6.repository.AttachmentRepository;
import uz.pdp.cinemaroomb6.repository.CastRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CastService {

    @Autowired
    CastRepository castRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public List<CastProjection> getAllCastsByMovieId(@PathVariable(required = true) UUID movieId) {
        return castRepository.findByMovieId(movieId);
    }

    public List<Cast> getAllCasts() {
        return castRepository.findAll();

    }

    public void saveCast(CastDto castDto, MultipartFile multipartFile) {
        try {
            Cast cast = new Cast();
            if(castDto.getId()!=null){
                cast=castRepository.getById(castDto.getId());
            }
            cast.setFullName(castDto.getFullName());
            cast.setCastType(castDto.getCastType());
            if (multipartFile!=null) {
                Attachment attachment = new Attachment(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getSize());
                attachmentRepository.save(attachment);
                AttachmentContent attachmentContent = new AttachmentContent(multipartFile.getBytes(), attachment);
                attachmentContentRepository.save(attachmentContent);
                cast.setPhoto(attachment);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCast(UUID castId) {
        castRepository.deleteById(castId);
    }
}
