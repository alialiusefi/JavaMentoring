package com.epam.esm.service;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.CRUDRepo;
import com.epam.esm.repository.GiftCertificateRepo;
import com.epam.esm.repository.TagRepo;
import com.epam.esm.repository.specfication.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IGiftCertificateService {

    private CRUDRepo<GiftCertificate> giftCertificateRepo;
    private CRUDRepo<Tag> tagRepository;
    private GiftCertificateConverter giftCertificateConverter;
    private TagConverter tagConverter;

    @Autowired
    public IGiftCertificateService(GiftCertificateRepo giftCertificateRepo, TagRepo tagRepository,
                                   GiftCertificateConverter giftCertificateConverter,
                                   TagConverter tagConverter) {
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagRepository = tagRepository;
        this.giftCertificateConverter = giftCertificateConverter;
        this.tagConverter = tagConverter;
    }

    public GiftCertificateDTO getGiftCertificateByID(long id) {
        List<GiftCertificate> giftCertificates = giftCertificateRepo.query(
                new FindGiftCertificateByID(id));
        if (giftCertificates.isEmpty()) {
            throw new ResourceNotFoundException("Gift Certificate with this id doesn't exist!");
        }
        GiftCertificateDTO giftCertificateDTO = giftCertificateConverter.toDTO(giftCertificates.get(0));
        giftCertificateDTO.setTagDTOList(
                tagConverter.toDTOList(tagRepository.query(
                        new FindTagsByCertificateID(giftCertificateDTO.getId()))));
        return giftCertificateDTO;
    }

    public List<GiftCertificateDTO> getGiftCertificate(long tagID, String name, String desc, int sortByDate, int sortByName) {
        List<Specification<GiftCertificate>> specifications = new ArrayList<>();
        if (tagID != 0L) {
            FindGiftCertificatesByTagID findGiftCertificatesByTagID = new FindGiftCertificatesByTagID(tagID);
            specifications.add(findGiftCertificatesByTagID);
        }
        if (name != null) {
            FindGiftCertificatesByName findGiftCertificatesByName = new FindGiftCertificatesByName(name);
            specifications.add(findGiftCertificatesByName);
        }
        if (desc != null) {
            FindGiftCertificatesByDescription findGiftCertificatesByDescription = new FindGiftCertificatesByDescription(desc);
            specifications.add(findGiftCertificatesByDescription);
        }
        if (sortByDate != 0) {
            SortGiftCertificatesByDate sortGiftCertificatesByDate = new SortGiftCertificatesByDate(sortByDate);
            specifications.add(sortGiftCertificatesByDate);
        }
        if (sortByName != 0) {
            SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(sortByName);
            specifications.add(sortGiftCertificatesByName);
        }
        GiftCertificateSpecificationConjunction conjunction = new GiftCertificateSpecificationConjunction(specifications);
        return giftCertificateConverter.toDTOList(giftCertificateRepo.query(conjunction));
    }

    public GiftCertificateDTO addGiftCertificate(GiftCertificateDTO certificateDTO) {
        addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        giftCertificateRepo.add(certificate);
        addReferences(certificateDTO);
        return getGiftCertificateByID(certificateDTO.getId());
    }

    public GiftCertificateDTO updateGiftCertificate(GiftCertificateDTO certificateDTO) {
        addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        giftCertificateRepo.update(certificate);
        updateReferences(certificateDTO);
        return getGiftCertificateByID(certificateDTO.getId());
    }


    public boolean deleteGiftCertificate(long id) {
        giftCertificateRepo.delete(new FindGiftCertificateByID(id));
        GiftCertificateDTO dto = getGiftCertificateByID(id);
        if (dto == null) {
            return true;
        }
        return false;
    }

    public boolean deleteGiftCertificate(GiftCertificateDTO certificate) {
        giftCertificateRepo.delete(giftCertificateConverter.toEntity(certificate));
        GiftCertificateDTO dto = getGiftCertificateByID(certificate.getId());
        if (dto == null) {
            return true;
        }
        return false;
    }

    private void addNewTagsIfTheyDontExist(GiftCertificateDTO dto) {
        List<TagDTO> tags = dto.getTagDTOList();
        List<Tag> newTags = new ArrayList<>();
        for (TagDTO i : tags) {
            List<Tag> tagFound = tagRepository.query(new FindTagByID(i.getId()));
            if (tagFound.isEmpty()) {
                newTags.add(tagConverter.toEntity(i));
            }
        }
        for (Tag i : newTags) {
            tagRepository.add(i);
        }
    }

    private void addReferences(GiftCertificateDTO dto) {
        GiftCertificate certificate = giftCertificateConverter.toEntity(dto);
        List<TagDTO> tagsDTO = dto.getTagDTOList();
        List<Tag> tags = new ArrayList<>();
        for (TagDTO i : tagsDTO) {
            tags.add(tagConverter.toEntity(i));
        }
        GiftCertificateRepo repo = (GiftCertificateRepo) giftCertificateRepo;
        for (Tag i : tags) {
            repo.addReference(certificate, i);
        }
    }

    private void updateReferences(GiftCertificateDTO dto) {
        GiftCertificate certificate = giftCertificateConverter.toEntity(dto);
        List<TagDTO> newTagsDTO = dto.getTagDTOList();
        List<Tag> newTags = new ArrayList<>();
        for (TagDTO i : newTagsDTO) {
            newTags.add(tagConverter.toEntity(i));
        }
        List<Tag> oldTags = tagRepository.query(new FindTagsByCertificateID(certificate.getId()));
        newTags.removeAll(oldTags);
        GiftCertificateRepo repo = (GiftCertificateRepo) giftCertificateRepo;
        for (Tag i : newTags) {
            repo.addReference(certificate, i);
        }
    }


}
