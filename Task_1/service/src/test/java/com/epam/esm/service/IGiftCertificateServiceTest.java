package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IGiftCertificateServiceTest {

    @Mock
    public IGiftCertificateService IGiftCertificateService;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getGiftCertificateByID() {
        when(IGiftCertificateService.getGiftCertificateByID(1)).thenReturn(
                new GiftCertificateDTO(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2012, 9, 9),
                        LocalDate.of(2014, 12, 1),
                        5, Arrays.asList(
                        new TagDTO(1, "Accesories"),
                        new TagDTO(2, "Food")
                )
                ));
        Assert.assertEquals(IGiftCertificateService.getGiftCertificateByID(1),
                new GiftCertificateDTO(1, "ACME Discount Voucher",
                        "Discount while shopping",
                        BigDecimal.valueOf(20.00),
                        LocalDate.of(2012, 9, 9),
                        LocalDate.of(2014, 12, 1),
                        5, Arrays.asList(
                        new TagDTO(1, "Accesories"),
                        new TagDTO(2, "Food")
                )
                ));
    }

    @Test
    public void getGiftCertificate() {
        long tagID = 1;
        String name = "ACME";
        String desc = "Voucher";
        int sortByDate = 1;
        int sortByName = -1;
        List<GiftCertificateDTO> expected = Arrays.asList(new GiftCertificateDTO(1, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                LocalDate.of(2012, 9, 9),
                LocalDate.of(2014, 12, 1),
                5, Arrays.asList(
                new TagDTO(1, "Accesories"),
                new TagDTO(2, "Food")
        )));
        when(IGiftCertificateService.getGiftCertificate(tagID, name, desc, sortByDate, sortByName)).thenReturn(expected);
        Assert.assertEquals(expected, IGiftCertificateService.getGiftCertificate(tagID, name, desc, sortByDate, sortByName));
    }

    @Test
    public void addGiftCertificate() {
        GiftCertificateDTO expected = new GiftCertificateDTO(
                2,
                "GiftName",
                "GiftDesc",
                BigDecimal.valueOf(69.69),
                LocalDate.of(2019, 01, 01),
                LocalDate.of(2019, 01, 03),
                3,
                Arrays.asList(new TagDTO(1, "Accesories"))
        );
        when(IGiftCertificateService.addGiftCertificate(expected))
                .thenReturn(expected);
        GiftCertificateDTO actual = IGiftCertificateService.addGiftCertificate(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateGiftCertificate() {
        GiftCertificateDTO expected = new GiftCertificateDTO(
                2,
                "GiftName",
                "GiftDesc",
                BigDecimal.valueOf(69.69),
                LocalDate.of(2019, 01, 01),
                LocalDate.of(2019, 01, 03),
                5,
                Arrays.asList(new TagDTO(1, "Accesories"))
        );
        when(IGiftCertificateService.addGiftCertificate(expected))
                .thenReturn(expected);
        GiftCertificateDTO actual = IGiftCertificateService.addGiftCertificate(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteGiftCertificate() {
        boolean expected = true;
        when(IGiftCertificateService.deleteGiftCertificate(2))
                .thenReturn(true);
        boolean actual = IGiftCertificateService.deleteGiftCertificate(2);
        Assert.assertTrue(actual);
    }

    @Test
    public void testDeleteGiftCertificate() {
        boolean expected = true;
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO(
                2,
                "GiftName",
                "GiftDesc",
                BigDecimal.valueOf(69.69),
                LocalDate.of(2019, 01, 01),
                LocalDate.of(2019, 01, 03),
                5,
                Arrays.asList(new TagDTO(1, "Accesories"))
        );
        when(IGiftCertificateService.deleteGiftCertificate(giftCertificateDTO))
                .thenReturn(true);
        boolean actual = IGiftCertificateService.deleteGiftCertificate(giftCertificateDTO);
        Assert.assertTrue(actual);
    }


}
