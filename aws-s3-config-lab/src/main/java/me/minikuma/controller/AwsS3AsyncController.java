package me.minikuma.controller;

import lombok.RequiredArgsConstructor;
import me.minikuma.dto.FileInfoDto;
import me.minikuma.service.AwsS3AsyncService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Profile("v2")
@RestController
@RequiredArgsConstructor
public class AwsS3AsyncController {
    private final AwsS3AsyncService awsS3AsyncService;

    @PostMapping("/async-upload")
    public ResponseEntity<FileInfoDto.FileInfoResponse> asyncUpload(@RequestParam("dir") String directory,
                                        @RequestPart("file") MultipartFile multipartFile) {
        FileInfoDto.FileInfoResponse uploadResponse = awsS3AsyncService.asyncUpload(directory, multipartFile);
        return ResponseEntity.ok(uploadResponse);
    }

    @DeleteMapping("/del")
    public ResponseEntity<FileInfoDto.FileInfoResponse> deleteFile(@RequestParam("dir") String directory,
                                        @RequestParam("file") String fileName) {
        FileInfoDto.FileInfoResponse response = awsS3AsyncService.deleteFile(directory, fileName);
        return ResponseEntity.ok(response);
    }
}
