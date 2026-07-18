package com.forensys.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "audio_transcript")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AudioTranscript extends MediaAsset {
    private String transcriptText;
}
