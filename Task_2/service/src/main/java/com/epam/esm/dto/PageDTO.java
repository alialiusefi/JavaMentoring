package com.epam.esm.dto;

public class PageDTO extends DTO {

    private DTO dto;
    private Integer totalResults;
    
    public PageDTO(DTO dto, Integer totalResults) {
        this.dto = dto;
        this.totalResults = totalResults;
    }

    public DTO getDto() {
        return dto;
    }

    public void setDto(DTO dto) {
        this.dto = dto;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

}
