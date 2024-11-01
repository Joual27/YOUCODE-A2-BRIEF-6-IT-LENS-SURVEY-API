package org.youcode.ITLens.survey.core.entity.DTOs;

import jakarta.validation.constraints.NotNull;
import org.youcode.ITLens.owner.core.entities.Owner;
import org.youcode.ITLens.utils.validators.interfaces.Exists;

public record CreateSurveyDTO(@NotNull String title ,@NotNull String description , @Exists(entityClass = Owner.class) Long ownerId) {
}
