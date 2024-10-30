package org.youcode.ITLens.owner.core.entities.mappers;

import org.mapstruct.Mapper;
import org.youcode.ITLens.common.interfaces.BaseMapper;
import org.youcode.ITLens.owner.core.entities.DTOs.OwnerResponseDTO;
import org.youcode.ITLens.owner.core.entities.Owner;

@Mapper(componentModel = "spring")
public interface OwnerEntityToOwnerResponseDTOMapper extends BaseMapper<Owner , OwnerResponseDTO> {
}
