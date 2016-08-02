package uk.co.mjhoman.hystrix.demo.web.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uk.co.mjhoman.hystrix.demo.command.HystrixDemoCommand;

@Controller
public class HystrixController {

	@Autowired
	private HystrixDemoCommand command;

	@RequestMapping("/")
	public String root() {
		return "forward:/hystrix";
	}

	@RequestMapping("/request")
	public String request(@RequestParam("mode") String mode) {

		try {
			command.request(mode);
		} catch (IllegalArgumentException illegalArgumentException) {
			// do nothing
		} catch (InterruptedException interruptedException) {
			// do nothing
		}

		return "index";
	}

}
