package com.epam.esm.service.implementation;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.BaseCRUDRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specfication.FindGiftCertificateByID;
import com.epam.esm.repository.specfication.FindGiftCertificatesByDescription;
import com.epam.esm.repository.specfication.FindGiftCertificatesByName;
import com.epam.esm.repository.specfication.FindGiftCertificatesByTagID;
import com.epam.esm.repository.specfication.FindTagByName;
import com.epam.esm.repository.specfication.FindTagsByCertificateID;
import com.epam.esm.repository.specfication.GiftCertificateSpecificationConjunction;
import com.epam.esm.repository.specfication.SortGiftCertificatesByDate;
import com.epam.esm.repository.specfication.SortGiftCertificatesByName;
import com.epam.esm.repository.specfication.Specification;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private BaseCRUDRepository<GiftCertificate> giftCertificateRepo;
    private BaseCRUDRepository<Tag> tagRepository;
    private GiftCertificateConverter giftCertificateConverter;
    private TagConverter tagConverter;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepo, TagRepository tagRepository,
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

    public List<GiftCertificateDTO> getGiftCertificate(Long tagID, String name, String desc, Integer sortByDate, Integer sortByName) {
        List<Specification<GiftCertificate>> specifications = new ArrayList<>();
        if (tagID != null) {
            if (tagID < 0) {
                throw new BadRequestException("TagID should be positive");
            }
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
        if (sortByDate != null && sortByDate != 0) {
            if (sortByDate != 1 && sortByDate != -1) {
                throw new BadRequestException("Sort parameter should accept either 1 or -1");
            }
            SortGiftCertificatesByDate sortGiftCertificatesByDate = new SortGiftCertificatesByDate(sortByDate);
            specifications.add(sortGiftCertificatesByDate);
        }
        if (sortByName != null && sortByName != 0) {
            if (sortByName != 1 && sortByName != -1) {
                throw new BadRequestException("Sort parameter should accept either 1 or -1");
            }
            SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(sortByName);
            specifications.add(sortGiftCertificatesByName);
        }
        GiftCertificateSpecificationConjunction conjunction = new GiftCertificateSpecificationConjunction(specifications);
        List<GiftCertificateDTO> dtoResult = giftCertificateConverter.toDTOList(giftCertificateRepo.query(conjunction));
        List<GiftCertificateDTO> dtoResultWithTags = new ArrayList<>();
        for (GiftCertificateDTO i : dtoResult) {
            List<TagDTO> tags = tagConverter.toDTOList(tagRepository.query(
                    new FindTagsByCertificateID(i.getId())));
            i.setTagDTOList(tags);
            dtoResultWithTags.add(i);
        }
        return dtoResultWithTags;
    }

    @Transactional
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

    @Transactional
    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
        List<TagDTO> newTagsDTO = addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        giftCertificateRepo.update(certificate);
        certificateDTO.setId(certificate.getId());
        setNewTagsIDinDTO(certificateDTO, newTagsDTO);
        updateReferences(certificateDTO);
        return getByID(certificateDTO.getId());
    }

    private void setNewTagsIDinDTO(GiftCertificateDTO certificateDTO, List<TagDTO> newTagsDTO) {
        List<TagDTO> tagDTOList = certificateDTO.getTagDTOList();
        List<TagDTO> tagDTOListWithoutNullElements = new ArrayList<>();
        for (TagDTO i : tagDTOList) {
            if (i.getId() == null) {
                for (TagDTO newTag : newTagsDTO) {
                    if (i.getName().equals(newTag.getName())) {
                        i.setId(newTag.getId());
                        tagDTOListWithoutNullElements.add(i);
                        break;
                    }
                }
            } else {
                tagDTOListWithoutNullElements.add(i);
            }
        }
        certificateDTO.setTagDTOList(tagDTOListWithoutNullElements);
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        giftCertificateRepo.delete(new FindGiftCertificateByID(id));
        return true;
    }

    @Transactional
    @Override
    public boolean delete(GiftCertificateDTO certificate) {
        giftCertificateRepo.delete(giftCertificateConverter.toEntity(certificate));
        return true;
    }

    private List<TagDTO> addNewTagsIfTheyDontExist(GiftCertificateDTO dto) {
        List<TagDTO> tags = dto.getTagDTOList();
        List<Tag> newTags = new ArrayList<>();
        List<Tag> newTagsWithID = new ArrayList<>();
        for (TagDTO i : tags) {
            List<Tag> tagFound = tagRepository.query(new FindTagByName(i.getName()));
            if (tagFound.isEmpty()) {
                newTags.add(tagConverter.toEntity(i));
            }
        }
        for (Tag i : newTags) {
            newTagsWithID.add(tagRepository.add(i));
        }
        return tagConverter.toDTOList(newTagsWithID);
    }

    private void addReferences(GiftCertificateDTO dto) {
        GiftCertificate certificate = giftCertificateConverter.toEntity(dto);
        List<TagDTO> tagsDTO = dto.getTagDTOList();
        List<Tag> tags = tagConverter.toEntityList(tagsDTO);
        GiftCertificateRepository repo = (GiftCertificateRepository) giftCertificateRepo;
        for (Tag i : tags) {
            repo.addReference(certificate, i);
        }
    }

    private void updateReferences(GiftCertificateDTO dto) {
        GiftCertificate certificate = giftCertificateConverter.toEntity(dto);
        List<TagDTO> newTagsDTO = dto.getTagDTOList();
        List<Tag> newTags = tagConverter.toEntityList(newTagsDTO);
        List<Tag> oldTags = tagRepository.query(new FindTagsByCertificateID(certificate.getId()));
        newTags.removeAll(oldTags);
        GiftCertificateRepository repo = (GiftCertificateRepository) giftCertificateRepo;
        for (Tag i : newTags) {
            repo.addReference(certificate, i);
        }
    }


}
