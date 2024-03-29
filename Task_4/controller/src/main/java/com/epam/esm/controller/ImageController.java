package com.epam.esm.controller;

import com.epam.esm.dto.ImageMetaDataDTO;
import com.epam.esm.dto.UploadResultDTO;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.service.ImageMetaService;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/v1/image")
@RestController
@Validated
public class ImageController {

    private ImageMetaService imageMetaService;
    private Logger logger = LogManager.getLogger(ImageController.class);
    private ServletContext context;

    @Autowired
    public ImageController(ImageMetaService imageMetaService, ServletContext context) {
        this.imageMetaService = imageMetaService;
        this.context = context;
    }

    @GetMapping("/random")
    public ResponseEntity<byte[]> getRandomImage() {
        InputStream file = imageMetaService.getRandomImage();
        byte[] media = new byte[0];
        try {
            media = IOUtils.toByteArray(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return createResponse(media);
    }

    @GetMapping("/{filenamewithextension}")
    public ResponseEntity<byte[]> getImageByName(@PathVariable String filenamewithextension) {
        InputStream file = imageMetaService.getImageByName(filenamewithextension);
        byte[] media = new byte[0];
        try {
            media = IOUtils.toByteArray(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return createResponse(media);
    }

    @PostMapping("/")
    public List<UploadResultDTO> uploadImage(@RequestParam("file") List<MultipartFile> files) {
        List<UploadResultDTO> uploadResultDTOS = new ArrayList<>();
        if(files.isEmpty()) {
            throw new BadRequestException("At least one .png file should be uploaded!");
        }
        for (MultipartFile uploadedFile : files) {
            if (!MediaType.IMAGE_PNG.toString().equals(uploadedFile.getContentType())) {
                uploadResultDTOS.add(new UploadResultDTO(
                        new ImageMetaDataDTO(uploadedFile.getName(), null), ".png files only are accepted"
                ));
                continue;
            }
            String path = context.getRealPath("/tmp/");
            File tmpPicsDir = new File(path);
            if (!tmpPicsDir.exists()) {
                tmpPicsDir.mkdir();
            }
            String filename = uploadedFile.getOriginalFilename();
            File file = new File(path + filename);
            try {
                uploadedFile.transferTo(file);
                ImageMetaDataDTO dto = imageMetaService.uploadImage(file);
                uploadResultDTOS.add(new UploadResultDTO(dto, null));
            } catch (IOException e) {
                logger.debug(e.getMessage(), e);
            } catch (BadRequestException e) {
                uploadResultDTOS.add(new UploadResultDTO(new ImageMetaDataDTO(file.getName(), null), e.getMessage()));
            }
        }
        return uploadResultDTOS;
    }

    private ResponseEntity<byte[]> createResponse(byte[] image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }


}
