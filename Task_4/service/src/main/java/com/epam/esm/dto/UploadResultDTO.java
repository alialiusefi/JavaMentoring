package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"id"})
public class UploadResultDTO extends DTO {

    private ImageMetaDataDTO imageMetaDataDTO;

    private String error;


    public UploadResultDTO(ImageMetaDataDTO imageMetaDataDTO, String error) {
        this.imageMetaDataDTO = imageMetaDataDTO;
        this.error = error;
    }

    public ImageMetaDataDTO getImageMetaDataDTO() {
        return imageMetaDataDTO;
    }

    public void setImageMetaDataDTO(ImageMetaDataDTO imageMetaDataDTO) {
        this.imageMetaDataDTO = imageMetaDataDTO;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
