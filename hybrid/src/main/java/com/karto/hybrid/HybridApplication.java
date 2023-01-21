package com.karto.hybrid;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;

@SpringBootApplication
public class HybridApplication {

	public static void main(String[] args) {
		SpringApplication.run(HybridApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner getCommandLineRunner(){
		return (args) -> {
			String PROJECT_ID = ServiceOptions.getDefaultProjectId();
			String SUBSCRIPTION_ID = "karto-pubsub-java-sub";
			ProjectSubscriptionName subcriptioName = ProjectSubscriptionName.of(
				PROJECT_ID, SUBSCRIPTION_ID);
			Subscriber subscriber = null;

			try {
				subscriber = Subscriber.newBuilder(subcriptioName, new GMessageReceiver()).build();
				subscriber.startAsync().awaitRunning();
				subscriber.awaitTerminated();
			} catch (IllegalStateException illegal){
				illegal.printStackTrace();
			}

		};

	}

}
