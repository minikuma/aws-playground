package me.minikuma.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.minikuma.dto.FileInfoDto;
import me.minikuma.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsS3Service {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public FileInfoDto.FileInfoResponse upload(String dir, MultipartFile file) throws IOException {
        String fileName = CommonUtils.convertFileName(dir, Objects.requireNonNull(file.getOriginalFilename()));
        log.info("Upload File Name = {}", fileName);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        try (InputStream in = file.getInputStream()) {
            RequestBody requestBody = RequestBody.fromInputStream(in, file.getSize());
            s3Client.putObject(putObjectRequest, requestBody);

        } catch (IOException ie) {
            throw new IOException("Upload Error");
        }

        List<String> uploadUrl = new ArrayList<>();

        S3Utilities s3Utilities = s3Client.utilities();
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket("ugps-test")
                .key(fileName)
                .build();

        URL url = s3Utilities.getUrl(getUrlRequest);

        uploadUrl.add(url.toString());

        return new FileInfoDto.FileInfoResponse(fileName, LocalDateTime.now(), uploadUrl);
    }
}
