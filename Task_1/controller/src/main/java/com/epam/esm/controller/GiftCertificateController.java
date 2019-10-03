package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/giftcertificates")
@RestController
public class GiftCertificateController extends ResourceController{

    @Autowired
    private GiftCertificateService giftCertificateService;

    @GetMapping("/")
    public @ResponseBody
    GiftCertificateDTO getGiftCertificate() {
        return giftCertificateService.getGiftCertificateByID(1);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    GiftCertificateDTO getGiftCertificate(@PathVariable long id) {
        return giftCertificateService.getGiftCertificateByID(id);
    }

    @GetMapping("")
    public @ResponseBody
    GiftCertificateDTO getGiftCertificate(@RequestParam(required = false) String tagName,
                                          @RequestParam(required = false) String giftCertificateName,
                                          @RequestParam(required = false) String giftCertificateDesc,
                                          @RequestParam(required = false) String sortByDate,
                                          @RequestParam(required = false) String sortByName) {
        return giftCertificateService.getGiftCertificateByID(1);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveGiftCertificate(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.addGiftCertificate(giftCertificateDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void updateGiftCertificate(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        giftCertificateService.updateGiftCertificate(giftCertificateDTO);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable long id) {
        giftCertificateService.deleteGiftCertificate(id);
    }


}
