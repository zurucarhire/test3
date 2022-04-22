package com.cellulant.iprs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@Builder
public class ExperienceDTO {
    private Long experienceID;
    private String name;
    private String experienceDescription;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date dateCreated;
    private String procedureName;
    private String procedureDescription;

    public ExperienceDTO(Long experienceID, String name, String experienceDescription, Date dateCreated, String procedureName, String procedureDescription) {
        this.experienceID = experienceID;
        this.name = name;
        this.experienceDescription = experienceDescription;
        this.dateCreated = dateCreated;
        this.procedureName = procedureName;
        this.procedureDescription = procedureDescription;
    }
}
