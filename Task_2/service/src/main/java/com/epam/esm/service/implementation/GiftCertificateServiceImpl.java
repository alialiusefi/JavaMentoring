package com.epam.esm.service.implementation;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.FindAllGiftCertificates;
import com.epam.esm.repository.specification.FindGiftCertificateByID;
import com.epam.esm.repository.specification.FindGiftCertificatesByDescription;
import com.epam.esm.repository.specification.FindGiftCertificatesByName;
import com.epam.esm.repository.specification.FindGiftCertificatesByTagID;
import com.epam.esm.repository.specification.FindTagByName;
import com.epam.esm.repository.specification.SortGiftCertificatesByDate;
import com.epam.esm.repository.specification.SortGiftCertificatesByName;
import com.epam.esm.repository.specification.Specification;
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
        GiftCertificate giftCertificate = giftCertificateRepo.queryEntity(
                new FindGiftCertificateByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Certificate with this id doesn't exist!"));
        return giftCertificateConverter.toDTO(giftCertificate);
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
        List<Tag> tagsWithID = getTagsWithID(certificate.getTags());
        certificate.setTags(tagsWithID);
        certificate = giftCertificateRepo.add(certificate);
        GiftCertificateDTO giftCertificateDTOCreated = giftCertificateConverter.toDTO(certificate);
        return giftCertificateDTOCreated;
    }

    @Transactional
    @Override
    public GiftCertificateDTO update(GiftCertificateDTO certificateDTO) {
        if (getByID(certificateDTO.getId()) == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + certificateDTO.getId() + " was not found!");
        }
        addNewTagsIfTheyDontExist(certificateDTO);
        GiftCertificate certificate = giftCertificateConverter.toEntity(certificateDTO);
        certificate.setDateOfModification(LocalDate.now());
        List<Tag> tagsWithID = getTagsWithID(certificate.getTags());
        certificate.setTags(tagsWithID);
        return giftCertificateConverter.toDTO(giftCertificateRepo.update(certificate));
    }

    private List<Tag> getTagsWithID(List<Tag> tags) {
        List<Tag> tagsWithID = new ArrayList<>();
        for (Tag i : tags) {
            Optional<Tag> tagwithID = tagRepository.queryEntity(new FindTagByName(i.getName()));
            tagsWithID.add(tagwithID.get());
        }
        return tagsWithID;
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        if (getByID(id) == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + id + " was not found!");
        }
        giftCertificateRepo.delete(new FindGiftCertificateByID(id));
        return true;
    }


    @Transactional
    @Override
    public boolean delete(GiftCertificateDTO certificate) {
        if (getByID(certificate.getId()) == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + certificate.getId() + " was not found!");
        }
        giftCertificateRepo.delete(giftCertificateConverter.toEntity(certificate));
        return true;
    }


    private List<Tag> addNewTagsIfTheyDontExist(GiftCertificateDTO dto) {
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
        return newTagsWithID;
    }
    @Override
    public List<GiftCertificateDTO> getGiftCertificates(Long[] tagID, String name,
                                                        String desc, Integer sortByDate, Integer sortByName) {
        if (tagID != null) {
            //FindGiftCertificatesByTagID findGiftCertificatesByTagID = new FindGiftCertificatesByTagID(tagID);
        }
        if (name != null) {
            FindGiftCertificatesByName findGiftCertificatesByName = new FindGiftCertificatesByName(name);
        }
        if (desc != null) {
            FindGiftCertificatesByDescription findGiftCertificatesByDescription = new FindGiftCertificatesByDescription(desc);
        }
        if (sortByDate != null && sortByDate != 0) {
            if (sortByDate != 1 && sortByDate != -1) {
                throw new BadRequestException("Sort parameter should accept either 1 or -1");
            }
            SortGiftCertificatesByDate sortGiftCertificatesByDate = new SortGiftCertificatesByDate(sortByDate);
        }
        if (sortByName != null && sortByName != 0) {
            if (sortByName != 1 && sortByName != -1) {
                throw new BadRequestException("Sort parameter should accept either 1 or -1");
            }
            SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(sortByName);
        }
      /*  GiftCertificateSpecificationConjunction conjunction = new GiftCertificateSpecificationConjunction(specifications);*/
        //List<GiftCertificateDTO> dtoResult = giftCertificateConverter.toDTOList(giftCertificateRepo.query(conjunction));
        List<GiftCertificateDTO> dtoResultWithTags = new ArrayList<>();
        /*for (GiftCertificateDTO i : dtoResult) {
            List<TagDTO> tags = tagConverter.toDTOList(tagRepository.query(
                    new FindTagsByCertificateID(i.getId())));
            i.setTags(tags);
            dtoResultWithTags.add(i);
        }*/
        return dtoResultWithTags;
    }


}
