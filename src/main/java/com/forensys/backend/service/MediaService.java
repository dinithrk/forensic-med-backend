package com.forensys.backend.service;

import com.forensys.backend.dto.MediaAssetDto;
import com.forensys.backend.dto.PhotographDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface MediaService {
    MediaAssetDto uploadMedia(MultipartFile file, MediaAssetDto dto) throws IOException;
    PhotographDto uploadPhotograph(MultipartFile file, PhotographDto dto) throws IOException;
    List<MediaAssetDto> getMediaForPostMortem(Long pmSerialNo);
    List<MediaAssetDto> getMediaForCase(Long mlefId);
}
