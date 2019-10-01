package com.epam.esm.service;

import com.java.esm.entity.GiftCertificate;
import com.java.esm.entity.Tag;
import com.java.esm.repository.GiftCertificateRepo;
import com.java.esm.repository.TagRepo;
import com.java.esm.repository.specfication.FindGiftCertificateByID;
import com.java.esm.repository.specfication.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class GiftCertificateService extends BaseService {

    @Autowired
    @Qualifier("giftCertificateRepo")
    private GiftCertificateRepo giftCertificateRepo;

    @Autowired
    private TagRepo tagRepository;



    public GiftCertificate getGiftCertificateByID(int id) {
        List<GiftCertificate> giftCertificates = giftCertificateRepo.query(
                new FindGiftCertificateByID(id)
        );
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
