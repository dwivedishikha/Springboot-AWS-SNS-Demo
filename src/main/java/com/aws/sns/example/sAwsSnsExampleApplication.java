package com.aws.sns.example;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@SpringBootApplication (exclude ={ContextRegionProviderAutoConfiguration.class, ContextStackAutoConfiguration.class})
@RestController
public class sAwsSnsExampleApplication {

    AmazonSNSClient amazonSNSClient;

    String TOPIC_ARN = "";

    @RequestMapping(path = "/addSubscription/{email}", method = RequestMethod.GET)
    public String addSubscription(String email) {
        SubscribeRequest subscribeRequest = new SubscribeRequest(TOPIC_ARN, "email", email);
        amazonSNSClient.subscribe(subscribeRequest);
        return "Subscription request is pending. To confirm the subscription, check your email : " + email;
    }

    @RequestMapping(path = "/sendNotification", method = RequestMethod.GET)
    public String publishMessageToTopic() {
        PublishRequest request = new PublishRequest(TOPIC_ARN, buildEmailBody(), "Notification: Network connectivity issue");
        amazonSNSClient.publish(request);
        return "Notification send successfully !!";
    }

    private String buildEmailBody() {
        return "Dear Friend ,\n" +
                "\n" +
                "\n" +
                "Thank you the subscription.";
    }

    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        SpringApplication.run(sAwsSnsExampleApplication.class, args);
    }
}

