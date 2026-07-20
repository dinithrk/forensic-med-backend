package com.forensys.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaAssetDto {
    private Long mediaId;
    private String filePath;
    private String fileName;
    private String type;
    
    private Long pmSerialNo;
    private Long mlefId;
}
