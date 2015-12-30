package cmei.spring.threadmanager;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("simpleScheduledTask")
public class SimpleScheduledTask {
	
	/*
	 * The pattern of cron expression is a list of six single space-separated fields: representing
	 * second, minute, hour, day, month, weekday. Month and weekday names can be
	 * given as the first three letters of the English names.
	
	 * <p>Example patterns:
	 * <ul>
	 * <li>"0 0 * * * *" = the top of every hour of every day.</li>
	 * <li>"*&#47;10 * * * * *" = every ten seconds.</li>
	 * <li>"0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.</li>
	 * <li>"0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.</li>
	 * <li>"0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays</li>
	 * <li>"0 0 0 25 12 ?" = every Christmas Day at midnight</li>
	 * </ul>
	 * 
	 */
	//@Scheduled(fixedRate=5000)
	//@Scheduled(cron="*/5 * * * * MON-FRI")
	@Scheduled(fixedDelay=5000)
	public void doSimpleThing(){
		System.out.println(System.currentTimeMillis());
	}
	
	public void doSimpleThing2(){
		System.out.println("simpleThing2");
	}
	
	public void doSimpleThing3(){
		System.out.println("simpleThing3");
	}

}
