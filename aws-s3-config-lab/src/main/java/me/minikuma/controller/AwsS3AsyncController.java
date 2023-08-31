package me.minikuma.controller;

import lombok.RequiredArgsConstructor;
import me.minikuma.dto.FileInfoDto;
import me.minikuma.service.AwsS3AsyncService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Profile("v2")
@RestController
@RequiredArgsConstructor
public class AwsS3AsyncController {
    private final AwsS3AsyncService awsS3AsyncService;

    // TODO: Async Upload 구현
    @PostMapping("/async-uplaod")
    public ResponseEntity<?> asyncUpload(@RequestParam("dir") String directory,
                                        @RequestPart("file") MultipartFile multipartFile) {
        FileInfoDto.FileInfoResponse uploadResponse = awsS3AsyncService.asyncUpload(directory, multipartFile);
        return ResponseEntity.ok(uploadResponse);
    }
}
