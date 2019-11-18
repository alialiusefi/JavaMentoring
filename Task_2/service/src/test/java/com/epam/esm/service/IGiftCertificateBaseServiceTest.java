package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.implementation.GiftCertificateServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IGiftCertificateBaseServiceTest {


    @Mock
    public GiftCertificateServiceImpl IGiftCertificateServiceImpl;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getGiftCertificateByID() {
        when(IGiftCertificateServiceImpl.getByID(1l)).thenReturn(
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
        Assert.assertEquals(IGiftCertificateServiceImpl.getByID(1l),
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
        List<GiftCertificateDTO> expected = Arrays.asList(
                new GiftCertificateDTO(1, "ACME Discount Voucher",
                "Discount while shopping",
                BigDecimal.valueOf(20.00),
                LocalDate.of(2012, 9, 9),
                LocalDate.of(2014, 12, 1),
                5, Arrays.asList(
                new TagDTO(1, "Accesories"),
                new TagDTO(2, "Food")
        )));
        when(IGiftCertificateServiceImpl.getGiftCertificates(new Long[]{tagID},
                name, desc, sortByDate, sortByName, 1, 1)).thenReturn(expected);
        Assert.assertEquals(expected, IGiftCertificateServiceImpl.getGiftCertificates(
                new Long[]{tagID}, name, desc, sortByDate, sortByName, 1, 1));
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
        when(IGiftCertificateServiceImpl.add(expected))
                .thenReturn(expected);
        GiftCertificateDTO actual = IGiftCertificateServiceImpl.add(expected);
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
        when(IGiftCertificateServiceImpl.add(expected))
                .thenReturn(expected);
        GiftCertificateDTO actual = IGiftCertificateServiceImpl.add(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteGiftCertificate() {
        boolean expected = true;
        when(IGiftCertificateServiceImpl.delete(2))
                .thenReturn(true);
        boolean actual = IGiftCertificateServiceImpl.delete(2);
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
        when(IGiftCertificateServiceImpl.delete(giftCertificateDTO))
                .thenReturn(true);
        boolean actual = IGiftCertificateServiceImpl.delete(giftCertificateDTO);
        Assert.assertTrue(actual);
    }


}
