package com.epam.esm.repository;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GiftCertificateRepoTest extends AbstractRepoTest {


  /*  @Test
    public void findByID() {
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(1, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                5
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
        assertEquals(expected, giftCertificateRepository.findByID(1));
    }

    @Test
    public void add() {
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(3, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                9
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
        ;
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
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(3, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.69),
                9
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
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
        GiftCertificate expected = new GiftCertificate.GiftCertificateBuilder(4, "ACME Discount Voucher",
                "Discount while shoppingtest",
                BigDecimal.valueOf(20.69),
                9
        ).setDateOfCreation(LocalDate.of(2012, 9, 9)).setDateOfModification(
                LocalDate.of(2014, 12, 1)).getResult();
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
        List<GiftCertificate> actual = giftCertificateRepository.query(con);
        assertEquals(expected, actual);
    }
*/
}