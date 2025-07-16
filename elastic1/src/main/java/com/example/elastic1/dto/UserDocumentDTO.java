package com.example.elastic1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDocumentDTO {

    private String id;

    private String name;

    private Long age;

    private Boolean isActive;

}
