package com.forensys.backend.service.impl;

import com.forensys.backend.dto.MediaAssetDto;
import com.forensys.backend.dto.PhotographDto;
import com.forensys.backend.entity.MediaAsset;
import com.forensys.backend.entity.Photograph;
import com.forensys.backend.mapper.MediaMapper;
import com.forensys.backend.repository.AutopsyExamRepository;
import com.forensys.backend.repository.MlefRecordRepository;
import com.forensys.backend.repository.MediaAssetRepository;
import com.forensys.backend.repository.PhotographRepository;
import com.forensys.backend.service.FileStorageService;
import com.forensys.backend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaAssetRepository mediaAssetRepository;
    private final PhotographRepository photographRepository;
    private final AutopsyExamRepository autopsyExamRepository;
    private final MlefRecordRepository mlefRecordRepository;
    private final FileStorageService fileStorageService;
    private final MediaMapper mapper;

    @Override
    @Transactional
    public MediaAssetDto uploadMedia(MultipartFile file, MediaAssetDto dto) throws IOException {
        String filePath = fileStorageService.storeFile(file);
        MediaAsset asset = mapper.toEntity(dto);
        asset.setFilePath(filePath);
        asset.setFileName(file.getOriginalFilename());
        asset.setType(file.getContentType());
        linkEntities(asset, dto.getPmSerialNo(), dto.getMlefId());
        
        asset = mediaAssetRepository.save(asset);
        return mapper.toDto(asset);
    }

    @Override
    @Transactional
    public PhotographDto uploadPhotograph(MultipartFile file, PhotographDto dto) throws IOException {
        String filePath = fileStorageService.storeFile(file);
        Photograph photo = mapper.toEntity(dto);
        photo.setFilePath(filePath);
        photo.setFileName(file.getOriginalFilename());
        photo.setType(file.getContentType());
        linkEntities(photo, dto.getPmSerialNo(), dto.getMlefId());
        
        photo = photographRepository.save(photo);
        return mapper.toDto(photo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaAssetDto> getMediaForPostMortem(Long pmSerialNo) {
        return mediaAssetRepository.findAll().stream()
                .filter(m -> m.getPostMortem() != null && m.getPostMortem().getPmSerialNo().equals(pmSerialNo))
                .map(mapper::toDtoPolymorphic)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MediaAssetDto> getMediaForCase(Long mlefId) {
        return mediaAssetRepository.findAll().stream()
                .filter(m -> m.getMlefRecord() != null && m.getMlefRecord().getMlefId().equals(mlefId))
                .map(mapper::toDtoPolymorphic)
                .toList();
    }
    
    private void linkEntities(MediaAsset asset, Long pmSerialNo, Long mlefId) {
        if (pmSerialNo != null) {
            com.forensys.backend.entity.PostMortem postMortem = new com.forensys.backend.entity.PostMortem();
            postMortem.setPmSerialNo(pmSerialNo);
            asset.setPostMortem(postMortem);
        }
        if (mlefId != null) {
            com.forensys.backend.entity.MlefRecord mlefRecord = new com.forensys.backend.entity.MlefRecord();
            mlefRecord.setMlefId(mlefId);
            asset.setMlefRecord(mlefRecord);
        }
    }
}
