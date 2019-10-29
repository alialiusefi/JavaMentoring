package com.epam.esm.service.implementation;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.FindAllGiftCertificates;
import com.epam.esm.repository.specification.FindGiftCertificateByID;
import com.epam.esm.repository.specification.FindTagByID;
import com.epam.esm.repository.specification.FindTagByName;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepo;
    private GiftCertificateConverter giftCertificateConverter;
    private TagRepository tagRepository;
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
            GiftCertificate giftCertificate = giftCertificateRepo.queryEntity(
                    new FindGiftCertificateByID(id)).orElseThrow(ResourceNotFoundException::new);
            return giftCertificateConverter.toDTO(giftCertificate);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Gift Certificate with ID: " + id + " was not found!");
        }
    }

    @Override
    public List<GiftCertificateDTO> getAll(int pageNumber, int pageSize) {
        try {
            return giftCertificateConverter.toDTOList(
                    giftCertificateRepo.queryList(new FindAllGiftCertificates(), pageNumber, pageSize));
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Didn't find gift certificates");
        }
    }

    @Transactional
    @Override
    public GiftCertificateDTO add(GiftCertificateDTO certificateDTO) {
        addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        certificate.setDateOfCreation(LocalDate.now());
        certificate.setDateOfModification(null);
        certificate = giftCertificateRepo.add(certificate);
        //addReferences(certificateDTO);
        GiftCertificateDTO giftCertificateDTOCreated = giftCertificateConverter.toDTO(certificate);
        /*List<TagDTO> tagDTOList = tagConverter.toDTOList(tagRepository.query(
                new FindTagsByCertificateID(giftCertificateDTOCreated.getId())));
        if (tagDTOList != null) {
            giftCertificateDTOCreated.setTags(tagDTOList);
        }*/
        return giftCertificateDTOCreated;
    }

    @Transactional
    @Override
    public boolean delete(long id) {
       /* if (getByID(id) == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + id + " was not found!");
        }
        giftCertificateRepo.delete(new FindGiftCertificateByID(id));*/
        return true;
    }


    @Transactional
    @Override
    public boolean delete(GiftCertificateDTO certificate) {
      /*  if (getByID(certificate.getId()) == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + certificate.getId() + " was not found!");
        }
        giftCertificateRepo.delete(giftCertificateConverter.toEntity(certificate));*/
        return true;
    }

    @Transactional
    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
        /*if (getByID(certificateDTO.getId()) == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + certificateDTO.getId() + " was not found!");
        }
        addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        certificate.setDateOfModification(LocalDate.now());
        giftCertificateRepo.update(certificate);
        List<TagDTO> newTags = certificateDTO.getTags();
        for (TagDTO i : newTags) {
            i.setId(tagRepository.findByName(i.getName()).getId());
        }
        certificateDTO.setTags(newTags);
        updateReferences(certificateDTO);
        return getByID(certificateDTO.getId());*/
        return null;
    }

    private List<TagDTO> addNewTagsIfTheyDontExist(GiftCertificateDTO dto) {
        List<Tag> newTagsWithID = new ArrayList<>();
        if (dto.getTags() != null) {
            List<TagDTO> tags = dto.getTags();
            List<Tag> newTags = new ArrayList<>();
            for (TagDTO i : tags) {
                Optional<Tag> optionalTag = tagRepository.queryEntity(new FindTagByName(i.getName()));
                if (!optionalTag.isPresent()) {
                    newTags.add(tagConverter.toEntity(i));
                }
            }
            for (Tag i : newTags) {
                newTagsWithID.add(tagRepository.add(i));
            }
        }
        return tagConverter.toDTOList(newTagsWithID);
    }

/*
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
            i.setTags(tags);
            dtoResultWithTags.add(i);
        }
        return dtoResultWithTags;
    }


   *//* private void setNewTagsIDinDTO(GiftCertificateDTO certificateDTO, List<TagDTO> newTagsDTO) {
        List<TagDTO> tagDTOList = certificateDTO.getTags();
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
        certificateDTO.setTags(tagDTOListWithoutNullElements);
    }*//*



    private void addReferences(GiftCertificateDTO dto) {
        if (dto.getTags() != null) {
            GiftCertificate certificate = giftCertificateConverter.toEntity(dto);
            List<TagDTO> tagsDTO = dto.getTags();
            List<Tag> tags = tagConverter.toEntityList(tagsDTO);
            GiftCertificateRepository repo = (GiftCertificateRepository) giftCertificateRepo;
            for (Tag i : tags) {
                i.setId(tagRepository.findByName(i.getName()).getId());
                repo.addReference(certificate, i);
            }
        }

    }

    private void updateReferences(GiftCertificateDTO dto) {
        if (dto.getTags() != null) {
            GiftCertificate certificate = giftCertificateConverter.toEntity(dto);
            List<TagDTO> newTagsDTO = dto.getTags();
            List<Tag> newTags = tagConverter.toEntityList(newTagsDTO);
            GiftCertificateRepository repo = (GiftCertificateRepository) giftCertificateRepo;
            repo.deleteAllReferences(certificate);
            for (Tag i : newTags) {
                i.setId(tagRepository.findByName(i.getName()).getId());
                repo.addReference(certificate, i);
            }
        }
    }*/

}
