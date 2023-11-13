package me.minikuma.dto;


import lombok.*;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

public class FileInfoDto {

    @Getter
    @Builder
    public static class FileInfoResponse {
        private String fileName;
        private LocalDateTime dateTime;
        private List<String> uploadUrl;
    }
}