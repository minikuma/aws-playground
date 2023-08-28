package me.minikuma.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import me.minikuma.utils.CommonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AwsS3Controller {
    private final AmazonS3 amazonS3;
    private String bucketName = "ugps-test";


    // TODO: upload 기능 구현
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("dir") String directory,
                                    @RequestPart("file")MultipartFile multipartFile) {
        String fileName = CommonUtils.convertFileName(directory, Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try (InputStream inputStream = multipartFile.getInputStream()){
            byte[] byteArray = IOUtils.toByteArray(inputStream);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(byteArray.length);

            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, byteArrayInputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(amazonS3.getUrl(bucketName, fileName).toString());
    }

    // TODO: file remove

    // TODO: bucket list 조회
}
