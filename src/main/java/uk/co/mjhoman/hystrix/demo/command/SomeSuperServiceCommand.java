
package uk.co.mjhoman.hystrix.demo.command;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class SomeSuperServiceCommand
{

    private static final Logger LOGGER = LoggerFactory.getLogger(SomeSuperServiceCommand.class);

    @HystrixCommand(fallbackMethod = "requestFallback")
    public String someSuperService(String mode) throws InterruptedException
    {

        if (Thread.currentThread().isInterrupted())
            return "interrupted";

        if ("exception".equals(mode))
            throw new IllegalArgumentException("forced error");

        if ("timeout".equals(mode))
        {
            LOGGER.info("Simulating a long running process");
            TimeUnit.MILLISECONDS.sleep(1000);
        }

        TimeUnit.MILLISECONDS.sleep(450);
        return "ok";
    }

    public String requestFallback(String mode)
    {
        return "fallback";
    }
}
