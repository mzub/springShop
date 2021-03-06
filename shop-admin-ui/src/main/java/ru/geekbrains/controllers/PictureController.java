package ru.geekbrains.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.geekbrains.model.Picture;
import ru.geekbrains.repo.PictureRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class PictureController {

    private PictureRepository pictureRepository;

    @Autowired
    public void setPictureRepository(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @GetMapping("/picture/{pictureId}")
    public void adminDownloadProductPicture(@PathVariable("pictureId") Long pictureId,
                                            HttpServletResponse response) throws IOException {
        Optional<Picture> picture = pictureRepository.findById(pictureId);
        if (picture.isPresent()) {
            response.setContentType(picture.get().getContentType());
            response.getOutputStream().write(picture.get().getPictureData().getData());
        }
    }
}
