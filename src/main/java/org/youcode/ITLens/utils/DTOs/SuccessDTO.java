package org.youcode.ITLens.utils.DTOs;

public record SuccessDTO<T>(String status , String message , T data) {
}
