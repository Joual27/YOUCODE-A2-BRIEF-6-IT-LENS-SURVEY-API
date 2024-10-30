package org.youcode.ITLens.common.interfaces;

public interface BaseMapper <T , DTO>{
    T toEntity(DTO dto);
    DTO entityToDto(T entity);
}
