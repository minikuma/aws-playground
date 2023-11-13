package me.minikuma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.dto.FileInfoDto;
import me.minikuma.service.AwsS3Service;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Profile("v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    // TODO: upload 기능 구현
    @PostMapping("/upload")
    public ResponseEntity<FileInfoDto.FileInfoResponse> upload(@RequestParam("dir") String directory,
                                    @RequestPart("file")MultipartFile multipartFile) throws IOException {
        FileInfoDto.FileInfoResponse uploadResponse = awsS3Service.upload(directory, multipartFile);
        return ResponseEntity.ok(uploadResponse);
    }

    // TODO: file remove

    // TODO: bucket list 조회
}
