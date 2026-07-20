package com.forensys.backend.mapper;

import com.forensys.backend.dto.MediaAssetDto;
import com.forensys.backend.dto.PhotographDto;
import com.forensys.backend.entity.MediaAsset;
import com.forensys.backend.entity.Photograph;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    @Mapping(source = "postMortem.pmSerialNo", target = "pmSerialNo")
    @Mapping(source = "mlefRecord.mlefId", target = "mlefId")
    MediaAssetDto toDto(MediaAsset entity);

    default MediaAssetDto toDtoPolymorphic(MediaAsset entity) {
        if (entity instanceof Photograph photograph) {
            return toDto(photograph);
        }
        return toDto(entity);
    }

    @Mapping(target = "postMortem", ignore = true)
    @Mapping(target = "mlefRecord", ignore = true)
    MediaAsset toEntity(MediaAssetDto dto);

    @Mapping(source = "postMortem.pmSerialNo", target = "pmSerialNo")
    @Mapping(source = "mlefRecord.mlefId", target = "mlefId")
    @Mapping(target = "captureDate", source = "captureDate")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "caption", source = "caption")
    PhotographDto toDto(Photograph entity);

    default Photograph toEntity(PhotographDto dto) {
        if (dto == null) {
            return null;
        }
        Photograph photograph = new Photograph();
        photograph.setMediaId(dto.getMediaId());
        photograph.setFilePath(dto.getFilePath());
        photograph.setType(dto.getType());
        photograph.setCaptureDate(dto.getCaptureDate());
        photograph.setDescription(dto.getDescription());
        photograph.setCaption(dto.getCaption());
        return photograph;
    }
}
