package me.minikuma.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class FileInfoDto {

    @Getter
    @Builder
    public static class FileInfoResponse {
        private String fileName;
        private LocalDateTime dateTime;
        private List<String> uploadUrl;
    }
}