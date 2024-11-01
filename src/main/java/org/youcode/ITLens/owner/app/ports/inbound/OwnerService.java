package org.youcode.ITLens.owner.app.ports.inbound;

import org.youcode.ITLens.owner.core.entities.DTOs.OwnerResponseDTO;
import org.youcode.ITLens.owner.core.entities.Owner;

public interface OwnerService {
    OwnerResponseDTO getOwnerById(Long id);
    Owner getOwnerEntityById(Long id);
}
