package com.epam.esm.dto;

import java.util.List;

public class PageDTO extends DTO {

    private List<GiftCertificateDTO> results;
    private Long totalResults;

    public PageDTO(List<GiftCertificateDTO> results, Long totalResults) {
        this.results = results;
        this.totalResults = totalResults;
    }

    public List<GiftCertificateDTO> getResults() {
        return results;
    }

    public void setResults(List<GiftCertificateDTO> results) {
        this.results = results;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}
