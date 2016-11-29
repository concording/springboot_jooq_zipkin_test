package com.grb.indonesia.schedule.task;

        import org.springframework.scheduling.annotation.Scheduled;
        import org.springframework.stereotype.Component;

@Component
public class DemoTask{

    @Scheduled(fixedRate = 5 * 1000) // per 5 second
    public void fiveSecond(){
        System.out.println(System.currentTimeMillis() + " Demo print hi 5 second");
    }
}