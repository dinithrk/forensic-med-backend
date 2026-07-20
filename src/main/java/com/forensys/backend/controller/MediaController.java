package com.forensys.backend.controller;

import com.forensys.backend.dto.MediaAssetDto;
import com.forensys.backend.dto.PhotographDto;
import com.forensys.backend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'POLICE_OFFICER')")
    public ResponseEntity<MediaAssetDto> uploadMedia(
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") MediaAssetDto dto) throws IOException {
        return new ResponseEntity<>(mediaService.uploadMedia(file, dto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/upload/photograph", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'POLICE_OFFICER')")
    public ResponseEntity<PhotographDto> uploadPhotograph(
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") PhotographDto dto) throws IOException {
        return new ResponseEntity<>(mediaService.uploadPhotograph(file, dto), HttpStatus.CREATED);
    }

    @GetMapping("/postmortems/{pmSerialNo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'POLICE_OFFICER')")
    public ResponseEntity<List<MediaAssetDto>> getMediaForPostMortem(@PathVariable Long pmSerialNo) {
        return ResponseEntity.ok(mediaService.getMediaForPostMortem(pmSerialNo));
    }

    @GetMapping("/cases/{mlefId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'JMO', 'MEDICAL_OFFICER', 'POLICE_OFFICER')")
    public ResponseEntity<List<MediaAssetDto>> getMediaForCase(@PathVariable Long mlefId) {
        return ResponseEntity.ok(mediaService.getMediaForCase(mlefId));
    }
}
