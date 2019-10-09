package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.service.IGiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RequestMapping("/v1/giftcertificates")
@RestController
public class GiftCertificateController {

    @Autowired
    private IGiftCertificateService IGiftCertificateService;

    @GetMapping("/{id}")
    public GiftCertificateDTO getGiftCertificate(@PathVariable long id) {
        return IGiftCertificateService.getGiftCertificateByID(id);
    }

    @GetMapping()
    public List<GiftCertificateDTO> getGiftCertificate(@RequestParam(required = false) @Valid
                                                       @Pattern(regexp = "^[1-9][0-9]+$") String tagID,
                                                       @RequestParam(required = false) @Valid String giftCertificateName,
                                                       @RequestParam(required = false) @Valid String giftCertificateDesc,
                                                       @RequestParam(required = false) @Valid String sortByDate,
                                                       @RequestParam(required = false) @Valid String sortByName) {
        int sortByDateOrder = 0;
        int sortByNameOrder = 0;
        long tagIDLong = 0L;
        if (tagID != null) {
            tagIDLong = Long.parseLong(tagID);
        }
        if (sortByDate != null) {
            sortByDateOrder = Integer.parseInt(sortByDate);
        }
        if (sortByName != null) {
            sortByNameOrder = Integer.parseInt(sortByName);
        }
        return IGiftCertificateService.getGiftCertificate(tagIDLong,
                giftCertificateName,
                giftCertificateDesc,
                sortByDateOrder,
                sortByNameOrder);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDTO saveGiftCertificate(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        return IGiftCertificateService.addGiftCertificate(giftCertificateDTO);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDTO updateGiftCertificate(@RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        return IGiftCertificateService.updateGiftCertificate(giftCertificateDTO);
    }


    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGiftCertificate(@PathVariable long id) {
        IGiftCertificateService.deleteGiftCertificate(id);
    }


}
