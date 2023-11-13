package me.minikuma.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

@Profile("v1")
@Configuration
public class AwsS3ConfigV1 {
    /**
     *  (1) Assume Role 기반 인증
     *  - ~./aws/credentials 파일 필요
     *  - 자동 갱신
     *  (2) S3Client Sync Access
     * */

    @Value("${cloud.aws.s3.role-arn}")
    private String roleArn;

    @Value("${cloud.aws.s3.role-session}")
    private String roleSessionName;

    @Value("${cloud.aws.s3.duration}")
    private int duration;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(stsAssumeRoleCredentialsProvider())
                .region(Region.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    public StsClient stsClient() {
        return StsClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(Region.AP_NORTHEAST_2)
                .build();
    }

    @Bean
    public StsAssumeRoleCredentialsProvider stsAssumeRoleCredentialsProvider() {
        return StsAssumeRoleCredentialsProvider.builder()
                .stsClient(stsClient())
                .refreshRequest(() -> AssumeRoleRequest.builder()
                                .roleArn(roleArn)
                                .roleSessionName(roleSessionName)
                                .durationSeconds(duration)
                                .build()
                )
                .asyncCredentialUpdateEnabled(true)
                .build();
    }
}