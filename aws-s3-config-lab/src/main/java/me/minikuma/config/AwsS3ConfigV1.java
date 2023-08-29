package me.minikuma.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;

@Slf4j
@Profile("v1")
@Configuration
public class AwsS3ConfigV1 {

    /**
     * Assume Role 기반 인증
     *  - ~./aws/credentials 파일 필요
     *  - 자동 갱신 가능한 구조
     * */

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(stsAssumeRoleCredentialsProvider())
                .build();
    }

    @Bean
    public AwsCredentialsProvider stsAssumeRoleCredentialsProvider() {
        StsClient stsClient = StsClient.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        AssumeRoleRequest assumeRoleRequest = AssumeRoleRequest.builder()
                .roleArn("arn:aws:iam::985984781526:role/ugps-role-test")
                .roleSessionName("ugps-s3-test")
                .durationSeconds(900)
                .build();

        StsAssumeRoleCredentialsProvider stsAssumeCredentials = StsAssumeRoleCredentialsProvider.builder()
                .stsClient(stsClient)
                .refreshRequest(assumeRoleRequest)
                .asyncCredentialUpdateEnabled(true)
                .build();

        AwsCredentials tempAwsCredentials = stsAssumeCredentials.resolveCredentials();
        AwsCredentialsProvider awsCredentialsProvider = () -> tempAwsCredentials;

        log.info("access key {}", awsCredentialsProvider.resolveCredentials().accessKeyId());

        return awsCredentialsProvider;
    }
}