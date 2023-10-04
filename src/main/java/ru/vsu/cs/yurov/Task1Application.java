package ru.vsu.cs.yurov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootApplication
public class Task1Application {

	public static void main(String[] args) {


		//Calendar calendar = new GregorianCalendar();
		//Period period = Period.between(LocalDate.parse());
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime time = now.plusDays(1);

		long secs = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() -
				now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		System.out.println(secs);

		SpringApplication.run(Task1Application.class, args);
	}

}
