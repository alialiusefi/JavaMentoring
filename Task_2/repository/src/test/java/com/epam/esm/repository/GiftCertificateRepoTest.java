package com.epam.esm.repository;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.specification.FindGiftCertificateByID;
import com.epam.esm.repository.specification.FindGiftCertificatesByDescription;
import com.epam.esm.repository.specification.FindGiftCertificatesByName;
import com.epam.esm.repository.specification.FindGiftCertificatesByTagID;
import com.epam.esm.repository.specification.GiftCertificatesSpecificationConjunction;
import com.epam.esm.repository.specification.SortGiftCertificatesByDate;
import com.epam.esm.repository.specification.SortGiftCertificatesByName;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GiftCertificateRepoTest extends AbstractRepoTest {

    @Test
    public void findByID() {
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(1, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                5
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
        assertEquals(expected, giftCertificateRepositoryImpl.queryEntity(new FindGiftCertificateByID(1l)));
    }

    @Test
    public void add() {
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(3, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                9
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
        GiftCertificate actual = giftCertificateRepositoryImpl.add(expected);
        expected.setId(actual.getId());
        // revert
        giftCertificateRepositoryImpl.delete(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        //set-up
        GiftCertificate old = giftCertificateRepositoryImpl.queryEntity(new FindGiftCertificateByID(1l)).get();
        GiftCertificate expected = giftCertificateRepositoryImpl.queryEntity(new FindGiftCertificateByID(1l)).
                get();
        // test
        expected.setDescription("test");
        giftCertificateRepositoryImpl.update(expected);
        GiftCertificate actual = giftCertificateRepositoryImpl.queryEntity(new FindGiftCertificateByID(1l)).
                get();
        //revert changes
        giftCertificateRepositoryImpl.update(old);
        //assertion
        assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(3, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.69),
                9
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
        GiftCertificate actual = giftCertificateRepositoryImpl.add(expected);
        giftCertificateRepositoryImpl.delete(actual);
        try {
            actual = giftCertificateRepositoryImpl.queryEntity(new FindGiftCertificateByID(actual.getId())).get();
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testDelete() {
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(4, "ACME Discount Voucher",
                "Discount while shoppingtest",
                BigDecimal.valueOf(20.69),
                9
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
        GiftCertificate actual = giftCertificateRepositoryImpl.add(expected);
        giftCertificateRepositoryImpl.delete(new FindGiftCertificatesByDescription("Discount while" +
                " shoppingtest"));
        try {
            List<GiftCertificate> actualCertificates = giftCertificateRepositoryImpl.queryList(
                    new FindGiftCertificatesByDescription("Discount while " +
                            "shoppingtest"), 1, 1);
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testFindByNameAndSortByDate() {
        FindGiftCertificatesByName findGiftCertificatesByName = new FindGiftCertificatesByName("Discount");
        SortGiftCertificatesByDate sortGiftCertificatesByDate = new SortGiftCertificatesByDate(1);
        ArrayDeque deque = new ArrayDeque();
        deque.add(findGiftCertificatesByName);
        deque.add(sortGiftCertificatesByDate);
        GiftCertificatesSpecificationConjunction con = new GiftCertificatesSpecificationConjunction(
                deque, new ArrayList<>()
        );
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate.GiftCertificateBuilder(2, "Discount Voucher",
                        "Ze Discript",
                        BigDecimal.valueOf(20.00),
                        5).setDateOfCreation(LocalDate.of(2000, 9, 9))
                        .setDateOfModification(LocalDate.of(2000, 12, 1)).getResult(),
                new GiftCertificate.GiftCertificateBuilder(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        5).setDateOfCreation(LocalDate.of(2012, 9, 9))
                        .setDateOfModification(LocalDate.of(2014, 12, 1)).getResult());
        List<GiftCertificate> actual = giftCertificateRepositoryImpl.queryList(con, 1, 2);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByDescriptionAndSortByName() {
        FindGiftCertificatesByDescription findGiftCertificatesByDesc = new FindGiftCertificatesByDescription("Dis");
        SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(1);
        ArrayDeque deque = new ArrayDeque();
        deque.add(findGiftCertificatesByDesc);
        deque.add(sortGiftCertificatesByName);
        GiftCertificatesSpecificationConjunction con = new GiftCertificatesSpecificationConjunction(
                deque, new ArrayList<>()
        );
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate.GiftCertificateBuilder(2, "Discount Voucher",
                        "Ze Discript",
                        BigDecimal.valueOf(20.00),
                        5).setDateOfCreation(LocalDate.of(2000, 9, 9))
                        .setDateOfModification(LocalDate.of(2000, 12, 1)).getResult(),
                new GiftCertificate.GiftCertificateBuilder(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        5).setDateOfCreation(LocalDate.of(2012, 9, 9))
                        .setDateOfModification(LocalDate.of(2014, 12, 1)).getResult());
        List<GiftCertificate> actual = giftCertificateRepositoryImpl.queryList(con, 1, 2);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByTagIDAndDescriptionandSortByName() {
        FindGiftCertificatesByTagID findGiftCertificatesByTagID = new FindGiftCertificatesByTagID(new Long[]{1L}, false);
        FindGiftCertificatesByDescription findGiftCertificatesByDesc = new FindGiftCertificatesByDescription("Dis");
        SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(1);
        ArrayDeque deque = new ArrayDeque();
        deque.add(findGiftCertificatesByTagID);
        deque.add(findGiftCertificatesByDesc);
        deque.add(sortGiftCertificatesByName);
        GiftCertificatesSpecificationConjunction con = new GiftCertificatesSpecificationConjunction(
                deque, new ArrayList<>());
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate.GiftCertificateBuilder(2, "Discount Voucher",
                        "Ze Discript",
                        BigDecimal.valueOf(20.00),
                        5).setDateOfCreation(LocalDate.of(2000, 9, 9))
                        .setDateOfModification(LocalDate.of(2000, 12, 1)).getResult(),
                new GiftCertificate.GiftCertificateBuilder(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        5).setDateOfCreation(LocalDate.of(2012, 9, 9))
                        .setDateOfModification(LocalDate.of(2014, 12, 1)).getResult());
        List<GiftCertificate> actual = giftCertificateRepositoryImpl.queryList(con, 1, 2);
        assertEquals(expected, actual);
    }

}