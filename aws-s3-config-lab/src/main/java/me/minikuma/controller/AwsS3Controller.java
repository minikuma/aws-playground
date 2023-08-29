package me.minikuma.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.dto.FileInfoDto;
import me.minikuma.service.AwsS3Service;
import me.minikuma.utils.CommonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    // TODO: upload 기능 구현
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("dir") String directory,
                                    @RequestPart("file")MultipartFile multipartFile) throws IOException {
        FileInfoDto.FileInfoResponse uploadResponse = awsS3Service.upload(directory, multipartFile);
        return ResponseEntity.ok(uploadResponse);
    }

    // TODO: file remove

    // TODO: bucket list 조회
}
