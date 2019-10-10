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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IGiftCertificateService implements IService<GiftCertificateDTO> {

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

    @Override
    public GiftCertificateDTO getByID(long id) {
        try {
            GiftCertificate giftCertificate = giftCertificateRepo.findByID(id);
            GiftCertificateDTO giftCertificateDTO = giftCertificateConverter.toDTO(
                    giftCertificate);
            giftCertificateDTO.setTagDTOList(
                    tagConverter.toDTOList(tagRepository.query(
                            new FindTagsByCertificateID(giftCertificateDTO.getId()))));
            return giftCertificateDTO;
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Gift Certificate with ID: " + id + " was not found!");
        }
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

    @Override
    public GiftCertificateDTO add(GiftCertificateDTO certificateDTO) {
        addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        GiftCertificate giftCertificateCreated = giftCertificateRepo.add(certificate);
        certificateDTO.setId(giftCertificateCreated.getId());
        addReferences(certificateDTO);
        GiftCertificateDTO giftCertificateDTOCreated = giftCertificateConverter.toDTO(giftCertificateCreated);
        List<TagDTO> tagDTOList = tagConverter.toDTOList(tagRepository.query(
                new FindTagsByCertificateID(giftCertificateDTOCreated.getId())));
        giftCertificateDTOCreated.setTagDTOList(tagDTOList);
        return giftCertificateDTOCreated;
    }

    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
        addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        giftCertificateRepo.update(certificate);
        certificateDTO.setId(certificate.getId());
        updateReferences(certificateDTO);
        return getByID(certificateDTO.getId());
    }

    @Override
    public boolean delete(long id) {
        giftCertificateRepo.delete(new FindGiftCertificateByID(id));
        return true;
    }

    @Override
    public boolean delete(GiftCertificateDTO certificate) {
        giftCertificateRepo.delete(giftCertificateConverter.toEntity(certificate));
        return true;
    }

    private void addNewTagsIfTheyDontExist(GiftCertificateDTO dto) {
        List<TagDTO> tags = dto.getTagDTOList();
        List<Tag> newTags = new ArrayList<>();
        for (TagDTO i : tags) {
            List<Tag> tagFound = tagRepository.query(new FindTagByName(i.getName()));
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
        List<Tag> tags = tagConverter.toEntityList(tagsDTO);
        GiftCertificateRepo repo = (GiftCertificateRepo) giftCertificateRepo;
        for (Tag i : tags) {
            repo.addReference(certificate, i);
        }
    }

    //todo: fix tag.id from null value
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
