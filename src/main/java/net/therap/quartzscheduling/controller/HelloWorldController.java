package net.therap.quartzscheduling.controller;

import net.therap.quartzscheduling.model.TimerInfo;
import net.therap.quartzscheduling.service.HelloWorldScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author adyel.ullahil
 * @since 5/17/21
 */
@RestController
@RequestMapping("/api/timer")
public class HelloWorldController {

    @Autowired
    protected HelloWorldScheduleService helloScheduleService;

    @PostMapping("/runHello")
    public void runHello(){
        helloScheduleService.runHelloWorld();
    }

    @GetMapping
    public List<TimerInfo> getAllRunningTimers(){
        return helloScheduleService.getAllRunningTimers();
    }

    @GetMapping("/{timerId}")
    public TimerInfo getRunningTimer(@PathVariable String timerId){
        return helloScheduleService.getRunningTimer(timerId);
    }

    @DeleteMapping("/{timerId}")
    public boolean deleteTimer(@PathVariable String timerId){
        return helloScheduleService.deleteTimer(timerId);
    }
}
