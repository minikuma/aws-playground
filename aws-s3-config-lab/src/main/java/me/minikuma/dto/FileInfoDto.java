package me.minikuma.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class FileInfoDto {
    @Data
    @AllArgsConstructor
    public static class FileInfoResponse {
        private String fileName;
        private LocalDateTime dateTime;
        private List<String> uploadUrl = new ArrayList<>();
    }
}
