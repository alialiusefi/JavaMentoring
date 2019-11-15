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

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
        return update(giftCertificateConverter.toDTO(oldGiftCertificate));
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
        Deque<NativeSpecification<GiftCertificate>> specifications = new ArrayDeque<>();
        List<Object> parameters = new ArrayList<>();

        if (tagID != null) {
            specifications.add(new
                    FindGiftCertificatesByTagID(tagID, false));
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
            SortGiftCertificatesByDate sortGiftCertificatesByDate = new SortGiftCertificatesByDate(sortByDate);
            specifications.add(sortGiftCertificatesByDate);
        } else {
            if (sortByName != null && sortByName != 0) {
                if (sortByName != 1 && sortByName != -1) {
                    throw new BadRequestException("Sort parameter should accept either 1 or -1");
                }
                SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(sortByName);
                specifications.add(sortGiftCertificatesByName);
            }
        }
        GiftCertificatesSpecificationConjunction conjunction = new GiftCertificatesSpecificationConjunction(
                specifications, parameters);
        return giftCertificateConverter.toDTOList(giftCertificateRepo.queryList(conjunction, pageNumber, pageSize));
    }


}
