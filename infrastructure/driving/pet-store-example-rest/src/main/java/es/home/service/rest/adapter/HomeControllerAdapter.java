package es.home.service.rest.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class HomeControllerAdapter {

  @GetMapping
  public String home() {
    return "Hello World";
  }
}
