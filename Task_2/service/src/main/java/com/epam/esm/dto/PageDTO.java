package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties({"id"})
public class PageDTO extends DTO {

    private List<? extends DTO> results;
    private Long totalResults;

    public PageDTO(List<DTO> results, Long totalResults) {
        this.results = results;
        this.totalResults = totalResults;
    }

    public List<? extends DTO> getResults() {
        return results;
    }

    public void setResults(List<DTO> results) {
        this.results = results;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}
