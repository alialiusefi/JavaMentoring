package com.epam.esm.service.implementation;

import com.epam.esm.converter.GiftCertificateConverter;
import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.specification.CountFindAllGiftCertificates;
import com.epam.esm.repository.specification.CountGiftCertificatesSpecificationConjunction;
import com.epam.esm.repository.specification.FindAllGiftCertificates;
import com.epam.esm.repository.specification.FindGiftCertificateByID;
import com.epam.esm.repository.specification.FindGiftCertificatesByDescription;
import com.epam.esm.repository.specification.FindGiftCertificatesByName;
import com.epam.esm.repository.specification.FindGiftCertificatesByTagID;
import com.epam.esm.repository.specification.FindTagByName;
import com.epam.esm.repository.specification.FindTagsByCertificateID;
import com.epam.esm.repository.specification.GiftCertificatesSpecificationConjunction;
import com.epam.esm.repository.specification.NativeSpecification;
import com.epam.esm.repository.specification.SortGiftCertificatesByDate;
import com.epam.esm.repository.specification.SortGiftCertificatesByName;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateRepository giftCertificateRepo;
    private GiftCertificateConverter giftCertificateConverter;
    private TagRepository tagRepository;
    private TagConverter tagConverter;
    private Validator validator;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepo, TagRepository tagRepository,
                                      GiftCertificateConverter giftCertificateConverter,
                                      TagConverter tagConverter) {
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagRepository = tagRepository;
        this.giftCertificateConverter = giftCertificateConverter;
        this.tagConverter = tagConverter;
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public GiftCertificateDTO getByID(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepo.queryEntity(
                new FindGiftCertificateByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Certificate with this id doesn't exist!"));
        return giftCertificateConverter.toDTO(giftCertificate);
    }

    @Override
    public PageDTO getAllPage(int pageNumber, int pageSize) {
        try {
            List<GiftCertificateDTO> dto = giftCertificateConverter.toDTOList(
                    giftCertificateRepo.queryList(new FindAllGiftCertificates(), pageNumber, pageSize));
            Long totalCount = giftCertificateRepo.queryCount(new CountFindAllGiftCertificates()).orElseThrow(
                    () -> new ResourceNotFoundException("Cannot find amount of results!"));
            PageDTO pageDTO = new PageDTO(dto, totalCount);
            return pageDTO;
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
        certificate.setForSale(true);
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
            if (field == null) {
                throw new BadRequestException("Field not found!");
            }
            field.setAccessible(true);
            if (!isFieldPatchable(field)) {
                throw new BadRequestException("Cannot patch this field");
            }
            if (!handleBigDecimal(field, oldGiftCertificate, v) &&
                    !handleTagList(field, oldGiftCertificate, v)) {
                ReflectionUtils.setField(field, oldGiftCertificate, v);
            }
            field.setAccessible(false);
        });
        GiftCertificateDTO giftCertificateDTO = giftCertificateConverter.toDTO(oldGiftCertificate);
        Set<ConstraintViolation<GiftCertificateDTO>> violations =
                validator.validate(giftCertificateDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Incorrect Data!", violations);
        }
        return update(giftCertificateDTO);
    }

    private boolean isFieldPatchable(Field field) {
        Class type = field.getType();
        return !type.equals(LocalDate.class)
                && !type.equals(Long.class);
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

    private boolean handleTagList(Field field, GiftCertificate certificate, Object value) {
        if (field.getName().equals("tags")) {
            Field[] fields = {ReflectionUtils.findField(Tag.class, "id"),
                    ReflectionUtils.findField(Tag.class, "name")};
            for (Field i : fields) {
                i.setAccessible(true);
            }
            ArrayList<LinkedHashMap<Object, Object>> list = (ArrayList<LinkedHashMap<Object, Object>>) value;
            List<Tag> tags = new ArrayList<>();
            for (LinkedHashMap<Object, Object> i : list) {
                Long id = ((Integer) i.get("id")).longValue();
                String name = (String) i.get("name");
                Tag tag = new Tag.TagBuilder(id, name).getResult();
                tags.add(tag);
            }
            ReflectionUtils.setField(field, certificate, tags);
            for (Field i : fields) {
                i.setAccessible(false);
            }
            return true;
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
        GiftCertificate certificate = giftCertificateRepo.queryEntity(new FindGiftCertificateByID(id))
                .orElseThrow(() -> new ResourceNotFoundException("Gift Certificate with ID: "
                        + id + " was not found!"));
        certificate.setForSale(false);
        certificate = giftCertificateRepo.update(certificate);
        return true;
    }


    @Transactional
    @Override
    public boolean delete(GiftCertificateDTO certificate) {
        if (getByID(certificate.getId()) == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + certificate.getId() + " was not found!");
        }
        GiftCertificate certificatefound = giftCertificateRepo.queryEntity(new FindGiftCertificateByID(
                certificate.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Gift Certificate with ID: "
                        + certificate.getId() + " was not found!"));
        certificatefound.setForSale(false);
        certificatefound = giftCertificateRepo.update(certificatefound);
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
    public PageDTO getGiftCertificatesPage(Long[] tagID, String name,
                                           String desc, Integer sortByDate, Integer sortByName,
                                           Integer pageNumber, Integer pageSize) {
        ArrayDeque<NativeSpecification<GiftCertificate>> specifications = new ArrayDeque<>();
        List<Object> parameters = new ArrayList<>();
        if (tagID != null) {
            specifications.add(new
                    FindGiftCertificatesByTagID(tagID));
            parameters.addAll(Arrays.asList(tagID));
        }
        if (name != null) {
            specifications.add(new
                    FindGiftCertificatesByName(name));
            parameters.add(name);
        }
        if (desc != null) {
            specifications.add(new FindGiftCertificatesByDescription(desc));
            parameters.add(desc);
        }
        if (sortByDate != null && sortByDate != 0) {
            if (sortByDate != 1 && sortByDate != -1) {
                throw new BadRequestException("Sort parameter should accept either 1 or -1");
            }
            SortGiftCertificatesByDate sortGiftCertificatesByDate =
                    new SortGiftCertificatesByDate(sortByDate);
            specifications.add(sortGiftCertificatesByDate);
        } else {
            if (sortByName != null && sortByName != 0) {
                if (sortByName != 1 && sortByName != -1) {
                    throw new BadRequestException("Sort parameter should accept either 1 or -1");
                }
                SortGiftCertificatesByName sortGiftCertificatesByName =
                        new SortGiftCertificatesByName(sortByName);
                specifications.add(sortGiftCertificatesByName);
            }
        }
        ArrayDeque<NativeSpecification<GiftCertificate>> specifications2 = specifications.clone();
        GiftCertificatesSpecificationConjunction conjunction = new GiftCertificatesSpecificationConjunction(
                specifications, parameters);
        List giftCertificates = giftCertificateRepo.queryList(conjunction, pageNumber, pageSize);
        List<GiftCertificate> handledGiftCertificates = handleGiftCertifcates(giftCertificates);
        List<GiftCertificateDTO> result = giftCertificateConverter.toDTOList(handledGiftCertificates);
        conjunction.setSpecifications(specifications2);
        Long totalCount = giftCertificateRepo.queryCount(new CountGiftCertificatesSpecificationConjunction(conjunction))
                .orElseThrow(() -> new ResourceNotFoundException("Cannot get total count!"));
        return new PageDTO(result, totalCount);
    }

    public List<GiftCertificate> handleGiftCertifcates(List giftcertificates) {
        List<Object[]> giftcertificatesObjects = (List<Object[]>) giftcertificates;
        List<GiftCertificate> handledGiftCertificates = new ArrayList<>();
        for (Object[] i : giftcertificatesObjects) {
            Long id = ((Integer) i[0]).longValue();
            String name = (String) i[1];
            String desc = (String) i[2];
            Double price = (Double) i[3];
            Date dateCreated = (Date) i[4];
            Date dateModified = null;
            if (i[5] != null) {
                dateModified = (Date) i[5];
            }
            Integer duration = (Integer) i[6];
            boolean isForSale = (Boolean) i[7];
            GiftCertificate giftCertificate = new GiftCertificate.GiftCertificateBuilder(id, name, desc,
                    BigDecimal.valueOf(price), isForSale, duration).getResult();
            giftCertificate.setDateOfCreation(new java.sql.Date(dateCreated.getTime()).toLocalDate());
            if (dateModified != null) {
                giftCertificate.setDateOfModification(new java.sql.Date(dateModified.getTime()).toLocalDate());
            }
            List<Tag> tags = tagRepository.queryList(new FindTagsByCertificateID(giftCertificate.getId()),
                    null, null);
            giftCertificate.setTags(tags);
            handledGiftCertificates.add(giftCertificate);
        }
        return handledGiftCertificates;
    }


}
