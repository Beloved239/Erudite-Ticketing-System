package com.tobi.Erudite_Event_System.images.service;

import com.tobi.Erudite_Event_System.dto.ImageResponse;
import com.tobi.Erudite_Event_System.images.entity.EventImages;
import com.tobi.Erudite_Event_System.images.repository.EventImageRepository;
import com.tobi.Erudite_Event_System.users.entity.Users;
import com.tobi.Erudite_Event_System.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventImageService {

        private final EventImageRepository repository;
        private final UserRepository organizerRepository;

        public ResponseEntity<ImageResponse> saveImage(Long id, MultipartFile file, String name, String description) throws IOException {
            if (!organizerRepository.existsById(id)){
                return ResponseEntity.badRequest().body(ImageResponse.builder()
                                .responseCode(HttpStatus.BAD_REQUEST.toString())
                                .responseMessage("The Provided User does not exist")
                        .build());
            }else {
                Users organizer= organizerRepository.findById(id).get();
            EventImages product = EventImages.builder()
                    .name(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())))
                    .image(Base64.getEncoder().encodeToString(file.getBytes()))
                    .description(name + description)
                    .organizer(organizer)
                    .build();
            if (product.getName().contains("..")){
                return ResponseEntity.badRequest().body(ImageResponse.builder()
                                .responseCode(HttpStatus.BAD_REQUEST.toString())
                                .responseMessage("\"not a valid file name format\"")
                                .build());
            }
            repository.save(product);
        }
        return ResponseEntity.ok().body(ImageResponse.builder()
                        .responseCode(HttpStatus.OK.toString())
                        .responseMessage("File Successfully Uploaded")
                .build());
        }



    public ResponseEntity<ImageResponse> updatePicture(Long imageId, Long id, MultipartFile file, String name, String description) throws IOException {
        if (!organizerRepository.existsById(id)){
            return ResponseEntity.badRequest().body(ImageResponse.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.toString())
                    .responseMessage("The Provided User does not exist")
                    .build());
        }
        if (!repository.existsById(imageId)){
            return ResponseEntity.badRequest().body(ImageResponse.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.toString())
                    .responseMessage("The Provided Picture does not exist")
                    .build());
        }
        else {
            Users organizer= organizerRepository.findById(id).get();
            EventImages image = repository.findById(imageId).get();
                    image.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
                    image.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
                    image.setDescription(name + description);
                    image.setOrganizer(organizer);
            if (image.getName().contains("..")){
                return ResponseEntity.badRequest().body(ImageResponse.builder()
                        .responseCode(HttpStatus.BAD_REQUEST.toString())
                        .responseMessage("\"not a valid file name format\"")
                        .build());
            }
            repository.save(image);
        }
        return ResponseEntity.ok().body(ImageResponse.builder()
                .responseCode(HttpStatus.OK.toString())
                .responseMessage("File Updated Successfully")
                .build());
    }

        public EventImages getProductImage(Long id) throws IOException {

//        String imageData = "data:image/png;base64,iVBORw0KGgoAAAANSUhE....";
//            String imageData = product.getImage();
//            String base64Data = imageData.split(",")[0];
//
//            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
//            ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
//            BufferedImage image = ImageIO.read(bis);
//
//            File outputFile = new File("output.jpeg");
//            ImageIO.write(image, "jpeg", outputFile);

            return repository.findById(id).get();
        }



}
