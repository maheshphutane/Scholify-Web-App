package com.hello_world_sprinboot.scholify.controller;

import com.hello_world_sprinboot.scholify.model.Person;
import com.hello_world_sprinboot.scholify.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Controller
public class DashboardController {
    @Autowired
    PersonRepository personRepository;
    @Value("${scholify.pageSize}")
    private int defaultPageSize;

    @Autowired
    Environment environment;

    @RequestMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication, HttpSession session){
        Person person = personRepository.getByEmail(authentication.getName());
        model.addAttribute("username",person.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        if(null != person.getScholifyClass() && null != person.getScholifyClass().getName()){
            model.addAttribute("enrolledClass", person.getScholifyClass().getName());
        }
        session.setAttribute("loggedInPerson", person);
        logMessages();
        return "dashboard.html";
    }

    private void logMessages() {
        log.error("Error message from the Dashboard page");
        log.warn("Warning message from the Dashboard page");
        log.info("Info message from the Dashboard page");
        log.debug("Debug message from the Dashboard page");
        log.trace("Trace message from the Dashboard page");

        log.error("defaultPageSize value with @Value annotation is : "+defaultPageSize);

        log.error("Java Home environment variable using Environment is : "+environment.getProperty("JAVA_HOME"));
    }
}
