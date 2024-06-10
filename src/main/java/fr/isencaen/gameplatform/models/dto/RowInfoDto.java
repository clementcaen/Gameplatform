package fr.isencaen.gameplatform.models.dto;

import java.util.Map;

public record RowInfoDto(
   Map<String, CellInfoDto> cells
) {
}
