package com.example.POD_BookingSystem;

import com.example.POD_BookingSystem.Configuration.TwilioConfig;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class PodBookingSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(PodBookingSystemApplication.class, args);
	}

}
