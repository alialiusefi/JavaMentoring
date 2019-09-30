package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.java.esm.entity.GiftCertificate;
import com.java.esm.entity.Tag;
import com.java.esm.repository.GiftCertificateRepo;
import com.java.esm.repository.TagRepo;
import com.java.esm.repository.specfication.FindGiftCertificateByID;
import com.java.esm.repository.specfication.FindTagByID;
import com.java.esm.repository.specfication.Specification;

import java.util.List;


public class GiftCertificateService extends Service<GiftCertificateRepo> {

    private TagRepo tagRepository;

    public GiftCertificateService(GiftCertificateRepo giftCertificateRepo,
                                  TagRepo tagRepository) {
        super(giftCertificateRepo);
        this.tagRepository = tagRepository;
    }

    public GiftCertificate getGiftCertificateByID(int id){
        List<GiftCertificate> giftCertificates = repository.query(
                new FindGiftCertificateByID(id)
        );
        return giftCertificates.get(0);
    }

    public void addGiftCertificate(GiftCertificate certificate){
        repository.add(certificate);
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
        repository.update(certificate);
    }

    public void deleteGiftCertificate(Specification specification) {
        repository.delete(specification);
    }

    public void deleteGiftCertificate(GiftCertificate certificate) {
        repository.delete(certificate);
    }

}
