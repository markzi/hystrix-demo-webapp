
package uk.co.mjhoman.hystrix.demo.web.spring.controller;

import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import uk.co.mjhoman.hystrix.demo.command.SomeSuperServiceCommand;

@Controller
public class HystrixController
{

    private static final Logger LOGGER = LoggerFactory.getLogger(HystrixController.class);

    @Autowired
    private SomeSuperServiceCommand command;

    @RequestMapping("/")
    public String root()
    {
        return "forward:/hystrix";
    }

    @RequestMapping("/demo")
    public String demo()
    {
        return "index";
    }

    @RequestMapping("/ok")
    @ResponseStatus(value = HttpStatus.OK)
    public void good()
    {

        Slf4JStopWatch start = new Slf4JStopWatch("ok");

        try
        {
            String result = command.someSuperService("ok");
            LOGGER.info("Result {}", result);
            start.stop();
        }
        catch (Exception e)
        {
            LOGGER.error("exception", e);
            start.stop(e);
        }
    }

    @RequestMapping("/timeout")
    @ResponseStatus(value = HttpStatus.OK)
    public void timeout()
    {

        Slf4JStopWatch start = new Slf4JStopWatch("timout");

        try
        {
            String result = command.someSuperService("timeout");
            LOGGER.info("Result {}", result);
            start.stop();
        }
        catch (Exception e)
        {
            LOGGER.error("exception", e);
            start.stop(e);
        }
    }

    @RequestMapping("/exception")
    @ResponseStatus(value = HttpStatus.OK)
    public void exception()
    {

        Slf4JStopWatch start = new Slf4JStopWatch("exception");

        try
        {
            String result = command.someSuperService("exception");
            LOGGER.info("Result {}", result);
            start.stop();
        }
        catch (Exception e)
        {
            LOGGER.error("exception", e);
            start.stop(e);
        }
    }

}
