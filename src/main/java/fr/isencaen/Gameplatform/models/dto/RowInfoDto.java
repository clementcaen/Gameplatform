package fr.isencaen.Gameplatform.models.dto;

import java.util.List;
import java.util.Map;

public record RowInfoDto(
   Map<String, CellInfoDto> cells
) {
}
