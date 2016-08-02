package uk.co.mjhoman.hystrix.demo.command;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class HystrixDemoCommand {

	@HystrixCommand(fallbackMethod = "requestFallback")
	public String request(String mode) throws InterruptedException {

		if (Thread.currentThread().isInterrupted())
			throw new InterruptedException();

		if ("exception".equals(mode))
			throw new IllegalArgumentException("forced error");

		if ("timeout".equals(mode))
			Thread.sleep(60000);

		return "worked";
	}

	public String requestFallback() {
		return "down";
	}
}
