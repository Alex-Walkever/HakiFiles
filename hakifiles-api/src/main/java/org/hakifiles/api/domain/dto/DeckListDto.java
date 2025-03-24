package org.hakifiles.api.domain.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hakifiles.api.domain.entities.CardInfo;

public class DeckListDto {
    @NotBlank
    private String name;

    @Size(max = 2500)
    private String description;

    private String youtubeLink;

    @NotNull
    private CardInfo leader;

    @NotNull
    private Long userId;
}
