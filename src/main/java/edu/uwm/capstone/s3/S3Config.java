package edu.uwm.capstone.s3;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public AmazonS3 s3client(){
        final Logger LOGGER = LoggerFactory.getLogger(S3Config.class);
        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAIAR6QLI6XLWJ4FWQ", "lHELWR4eRGlS0J9kPNS3S8AFYRouA+Jc1Mo2R+Qk");
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName("us-east-2"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        LOGGER.info("Initializing s3 client");
        return s3;
    }
}