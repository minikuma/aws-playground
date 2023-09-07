package me.minikuma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.dto.FileInfoDto;
import me.minikuma.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.Upload;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Profile("v2")
@RequiredArgsConstructor
public class AwsS3AsyncService {

    private final S3TransferManager s3TransferManager;
    private final S3AsyncClient s3AsyncClient;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public FileInfoDto.FileInfoResponse asyncUpload(String dir, MultipartFile file) {

        String fileName = CommonUtils.convertFileName(dir, Objects.requireNonNull(file.getOriginalFilename()));
        log.info("Async Upload File Name = {}", fileName);

        AsyncRequestBody asyncRequestBody = AsyncRequestBody.fromString(fileName.split("/")[1]);

        UploadRequest uploadRequest = UploadRequest.builder()
                .putObjectRequest(builder -> builder.bucket(bucketName)
                        .key(fileName)
                        .build()
                )
                .requestBody(asyncRequestBody)
                .build();

        Upload upload = s3TransferManager.upload(uploadRequest);

        long startTIme = System.currentTimeMillis();
        log.info("Object Upload started....{}", startTIme);

        upload.completionFuture().join();

        long endTime = System.currentTimeMillis();
        log.info("Object Upload finished {} elapsed time {}", endTime, endTime - startTIme);

        URL resultUrl = s3AsyncClient.utilities().getUrl(
                GetUrlRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build()
        );

        List<String> uploadUrl = new ArrayList<>();
        uploadUrl.add(resultUrl.toString());

        return FileInfoDto.FileInfoResponse.builder()
                .dateTime(LocalDateTime.now())
                .uploadUrl(uploadUrl)
                .fileName(fileName)
                .build();
    }

    public FileInfoDto.FileInfoResponse deleteFile(String dir, String fileName) {
        String key = dir + "/" + fileName;
        s3AsyncClient.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        );

        return FileInfoDto.FileInfoResponse.builder()
                .fileName(key)
                .dateTime(LocalDateTime.now())
                .uploadUrl(Collections.EMPTY_LIST)
                .build();
    }
}