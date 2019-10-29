package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v1/giftcertificates")
@RestController
@Validated
public class GiftCertificateController {

    private GiftCertificateService giftCertificateBaseService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateBaseService = giftCertificateService;
    }

    @GetMapping("/{id}")
    public GiftCertificateDTO getGiftCertificate(@PathVariable long id) {
        return giftCertificateBaseService.getByID(id);
    }

    @GetMapping()
    public List<GiftCertificateDTO> getAllGiftCertificates(@RequestParam(defaultValue = "1") int page,
                                                           @RequestParam(defaultValue = "5") int size) {
        return giftCertificateBaseService.getAll(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO saveGiftCertificate(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        return giftCertificateBaseService.add(giftCertificateDTO);
    }

/*
    @GetMapping
    public List<GiftCertificateDTO> getGiftCertificate(@RequestParam(required = false)
                                                               Long tagID,
                                                       @RequestParam(required = false)
                                                       @Size(min = 1, max = 50) String giftCertificateName,
                                                       @RequestParam(required = false)
                                                       @Size(min = 1, max = 256) String giftCertificateDesc,
                                                       @RequestParam(required = false)
                                                               Integer sortByDate,
                                                       @RequestParam(required = false)
                                                               Integer sortByName) {
        return giftCertificateBaseService.getGiftCertificate(tagID, giftCertificateName,
                giftCertificateDesc,
                sortByDate,
                sortByName);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.setId(id);
        return giftCertificateBaseService.update(giftCertificateDTO);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGiftCertificate(@PathVariable long id) {
        giftCertificateBaseService.delete(id);
    }

*/

}
