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
import com.epam.esm.repository.specification.*;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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
    public GiftCertificateDTO getByID(Long id) {
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

    @Transactional
    @Override
    public GiftCertificateDTO patch(Map<Object, Object> fields, Long id) {
        GiftCertificate oldGiftCertificate = giftCertificateRepo.queryEntity(
                new FindGiftCertificateByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Certificate with this id doesn't exist!"));
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(GiftCertificate.class, (String) k);
            field.setAccessible(true);
            if (!handleBigDecimal(field, oldGiftCertificate, v)) {
                ReflectionUtils.setField(field, oldGiftCertificate, v);
            }
            field.setAccessible(false);
        });
        return update(giftCertificateConverter.toDTO(oldGiftCertificate));
    }

    private boolean handleBigDecimal(Field field, GiftCertificate certificate, Object value) {
        if (field.getType().equals(BigDecimal.class)) {
            if (value instanceof Double) {
                BigDecimal bigDecimal = BigDecimal.valueOf((Double) value).setScale(
                        GiftCertificateDTO.SCALE,
                        GiftCertificateDTO.ROUNDING_MODE
                );
                ReflectionUtils.setField(field, certificate, bigDecimal);
                return true;
            }
        }
        return false;
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
                                                        String desc, Integer sortByDate, Integer sortByName,
                                                        Integer pageNumber, Integer pageSize) {
        Deque<Specification<GiftCertificate>> specifications = new ArrayDeque<>();
        if (tagID != null) {
            specifications.add(new FindGiftCertificatesByTagID(tagID));
        }
        if (name != null) {
            specifications.add(new FindGiftCertificatesByName(name));
        }
        if (desc != null) {
            specifications.add(new FindGiftCertificatesByDescription(desc));
        }
        if (sortByDate != null && sortByDate != 0) {
            if (sortByDate != 1 && sortByDate != -1) {
                throw new BadRequestException("Sort parameter should accept either 1 or -1");
            }
            specifications.add(new SortGiftCertificatesByDate(sortByDate));

        } else {
            if (sortByName != null && sortByName != 0) {
                if (sortByName != 1 && sortByName != -1) {
                    throw new BadRequestException("Sort parameter should accept either 1 or -1");
                }
                specifications.add(new SortGiftCertificatesByName(sortByName));
            }
        }
        GiftCertificatesSpecificationConjunction conjunction = new GiftCertificatesSpecificationConjunction(specifications);
        return giftCertificateConverter.toDTOList(giftCertificateRepo.queryList(conjunction, pageNumber, pageSize));
    }
}
