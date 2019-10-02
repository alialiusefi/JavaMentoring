package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepo;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.specfication.FindGiftCertificateByID;
import com.epam.esm.repository.specfication.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateService extends BaseService {

    private GiftCertificateRepo giftCertificateRepo;
    private TagRepo tagRepository;

    @Autowired
    public GiftCertificateService(GiftCertificateRepo giftCertificateRepo, TagRepo tagRepository) {
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagRepository = tagRepository;
    }

    public GiftCertificate getGiftCertificateByID(int id) {
        List<GiftCertificate> giftCertificates = giftCertificateRepo.query(
                new FindGiftCertificateByID(id));
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException("Gift Certificate with this id doesn't exist!");
        }
        return giftCertificates.get(0);
    }

    public void addGiftCertificate(GiftCertificate certificate) {
        giftCertificateRepo.add(certificate);
    }

    public void addGiftCertificate(GiftCertificate certificate, List<Tag> tags) {
        //todo: ask should i use service or repository here when adding new tags?
        /*for(Tag tag : tags){
            List<Tag> foundTags = tagRepository.query(new FindTagByID(tag.getId()));
            if(foundTags.isEmpty()){
                tagRepository.add(tag);
            }
        }*/
    }

    public void updateGiftCertificate(GiftCertificate certificate) {
        giftCertificateRepo.update(certificate);
    }

    public void deleteGiftCertificate(Specification specification) {
        giftCertificateRepo.delete(specification);
    }

    public void deleteGiftCertificate(GiftCertificate certificate) {
        giftCertificateRepo.delete(certificate);
    }

}
