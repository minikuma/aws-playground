package me.minikuma.config;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AwsS3ConfigV1 {
    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentialsProvider credentials = null;

        // Profile
        credentials = InstanceProfileCredentialsProvider
                .createAsyncRefreshingProvider(true);

        if (credentials == null) {
            AWSCredentialsProvider profileCredentialsProvider = new ProfileCredentialsProvider();
        }

        return AmazonS3Client.builder()
                .withCredentials(credentials)
                .build();
    }
}
