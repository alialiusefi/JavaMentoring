package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.specfication.FindGiftCertificatesByDescription;
import com.epam.esm.repository.specfication.FindGiftCertificatesByName;
import com.epam.esm.repository.specfication.FindGiftCertificatesByTagID;
import com.epam.esm.repository.specfication.GiftCertificateSpecificationConjunction;
import com.epam.esm.repository.specfication.SortGiftCertificatesByDate;
import com.epam.esm.repository.specfication.SortGiftCertificatesByName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class GiftCertificateRepoTest extends AbstractRepoTest {


    @Test
    public void findByID() {
        GiftCertificate expected = new GiftCertificate(1, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                LocalDate.of(2012, 9, 9),
                LocalDate.of(2014, 12, 1),
                5
        );
        assertEquals(expected, giftCertificateRepository.findByID(1));
    }

    @Test
    public void add() {
        GiftCertificate expected = new GiftCertificate(3, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                LocalDate.of(2012, 9, 9),
                LocalDate.of(2014, 12, 1),
                9
        );
        GiftCertificate actual = giftCertificateRepository.add(expected);
        expected.setId(actual.getId());
        // revert
        giftCertificateRepository.delete(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        //set-up
        GiftCertificate old = giftCertificateRepository.findByID(1);
        GiftCertificate expected = giftCertificateRepository.findByID(1);
        // test
        expected.setDescription("test");
        giftCertificateRepository.update(expected);
        GiftCertificate actual = giftCertificateRepository.findByID(1);
        //revert changes
        giftCertificateRepository.update(old);
        //assertion
        assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        GiftCertificate expected = new GiftCertificate(3, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.69),
                LocalDate.of(2012, 9, 9),
                LocalDate.of(2014, 12, 1),
                9
        );
        GiftCertificate actual = giftCertificateRepository.add(expected);
        giftCertificateRepository.delete(actual);
        try {
            actual = giftCertificateRepository.findByID(actual.getId());
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testDelete() {
        GiftCertificate expected = new GiftCertificate(4, "ACME Discount Voucher",
                "Discount while shoppingtest",
                BigDecimal.valueOf(20.69),
                LocalDate.of(2012, 9, 9),
                LocalDate.of(2014, 12, 1),
                9
        );
        GiftCertificate actual = giftCertificateRepository.add(expected);
        giftCertificateRepository.delete(new FindGiftCertificatesByDescription("Discount while" +
                " shoppingtest"));
        try {
            List<GiftCertificate> actualCertificates = giftCertificateRepository.query(
                    new FindGiftCertificatesByDescription("Discount while " +
                            "shoppingtest"));
        } catch (EmptyResultDataAccessException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testFindByNameAndSortByDate() {
        FindGiftCertificatesByName findGiftCertificatesByName = new FindGiftCertificatesByName("Discount");
        SortGiftCertificatesByDate sortGiftCertificatesByDate = new SortGiftCertificatesByDate(1);
        GiftCertificateSpecificationConjunction con = new GiftCertificateSpecificationConjunction(
                Arrays.asList(findGiftCertificatesByName,
                        sortGiftCertificatesByDate)
        );
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate(2, "Discount Voucher",
                        "Ze Discript",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2000, 9, 9),
                        LocalDate.of(2000, 12, 1),
                        5),
                new GiftCertificate(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2012, 9, 9),
                        LocalDate.of(2014, 12, 1),
                        5)

        );
        List<GiftCertificate> actual = giftCertificateRepository.query(con);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByDescriptionAndSortByName() {
        FindGiftCertificatesByDescription findGiftCertificatesByDesc = new FindGiftCertificatesByDescription("Dis");
        SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(1);
        GiftCertificateSpecificationConjunction con = new GiftCertificateSpecificationConjunction(
                Arrays.asList(findGiftCertificatesByDesc,
                        sortGiftCertificatesByName)
        );
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate(2, "Discount Voucher",
                        "Ze Discript",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2000, 9, 9),
                        LocalDate.of(2000, 12, 1),
                        5),
                new GiftCertificate(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2012, 9, 9),
                        LocalDate.of(2014, 12, 1),
                        5)

        );
        List<GiftCertificate> actual = giftCertificateRepository.query(con);
        assertEquals(expected, actual);
    }

    @Test
    public void testFindByTagIDAndDescriptionandSortByName() {
        FindGiftCertificatesByTagID findGiftCertificatesByTagID = new FindGiftCertificatesByTagID(1L);
        FindGiftCertificatesByDescription findGiftCertificatesByDesc = new FindGiftCertificatesByDescription("Dis");
        SortGiftCertificatesByName sortGiftCertificatesByName = new SortGiftCertificatesByName(1);
        GiftCertificateSpecificationConjunction con = new GiftCertificateSpecificationConjunction(
                Arrays.asList(
                        findGiftCertificatesByTagID,
                        findGiftCertificatesByDesc,
                        sortGiftCertificatesByName)
        );
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate(2, "Discount Voucher",
                        "Ze Discript",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2000, 9, 9),
                        LocalDate.of(2000, 12, 1),
                        5),
                new GiftCertificate(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2012, 9, 9),
                        LocalDate.of(2014, 12, 1),
                        5)

        );
        List<GiftCertificate> actual = giftCertificateRepository.query(con);
        assertEquals(expected, actual);
    }

}