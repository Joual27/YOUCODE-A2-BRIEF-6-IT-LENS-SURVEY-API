package org.youcode.ITLens.owner.infra.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.youcode.ITLens.owner.app.ports.outbound.OwnerDAO;
import org.youcode.ITLens.owner.core.entities.Owner;

@Repository
public interface OwnerPersistenceAdapter extends OwnerDAO , JpaRepository<Owner , Long> {
}

