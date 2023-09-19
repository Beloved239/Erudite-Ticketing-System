package com.tobi.Erudite_Event_System.images;

import com.tobi.Erudite_Event_System.images.entity.EventImages;
import com.tobi.Erudite_Event_System.images.service.EventImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("api/image")
public class EventImageController {
    private final EventImageService service;

    @PostMapping("/save")
    public String saveProduct(@RequestParam("user")Long id,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("name") String name,
                              @RequestParam("desc") String desc) throws IOException {
        service.saveImage(id,file, name, desc);
        return "Product Saved Successfully";
    }



    @GetMapping("picture")
    public EventImages getProduct(@RequestParam("id")Long id) throws IOException {
        return service.getProductImage(id);
    }


}









