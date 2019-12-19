package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Map;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public GiftCertificateDTO saveGiftCertificate(@RequestBody @Valid GiftCertificateDTO
                                                          giftCertificateDTO) {
        return giftCertificateBaseService.add(giftCertificateDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public GiftCertificateDTO updateGiftCertificate(@PathVariable long id,
                                                    @RequestBody @Valid GiftCertificateDTO giftCertificateDTO) {
        giftCertificateDTO.setId(id);
        return giftCertificateBaseService.update(giftCertificateDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteGiftCertificate(@PathVariable long id) {
        giftCertificateBaseService.delete(id);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public GiftCertificateDTO patchGiftCertificate(@PathVariable Long id,
                                                   @RequestBody Map<Object, Object> fields) {
        return giftCertificateBaseService.patch(fields, id);
    }

    @GetMapping
    public PageDTO getGiftCertificates(@RequestParam(required = false) String[] tagName,
                                       @RequestParam(required = false) Long[] tagID,
                                       @RequestParam(required = false)
                                       @Size(min = 1, max = 50) String giftCertificateName,
                                       @RequestParam(required = false)
                                       @Size(min = 1, max = 256) String giftCertificateDesc,
                                       @RequestParam(required = false) Integer sortByDate,
                                       @RequestParam(required = false) Integer sortByName,
                                       @RequestParam(defaultValue = "${app.pagedefault.defaultPageNumber}") Integer page,
                                       @RequestParam(defaultValue = "${app.pagedefault.defaultPageSize}") Integer size) {
        if (tagName == null && tagID == null &&
                giftCertificateName == null &&
                giftCertificateDesc == null &&
                sortByDate == null &&
                sortByName == null) {
            return giftCertificateBaseService.getAllPage(page, size);
        }
        return giftCertificateBaseService.getGiftCertificatesPage(tagName, tagID, giftCertificateName,
                giftCertificateDesc,
                sortByDate,
                sortByName,
                page, size);
    }


}
