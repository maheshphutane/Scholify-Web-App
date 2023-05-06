package com.hello_world_sprinboot.scholify.controller;

import com.hello_world_sprinboot.scholify.model.Holiday;
import com.hello_world_sprinboot.scholify.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@Configuration
public class HolidayController {
    @Autowired
    private HolidayRepository holidayRepository;
    @GetMapping("/holidays")
    public String displayHolidays(@RequestParam (required = false) boolean festival,
                                  @RequestParam (required = false) boolean federal, Model model){
        model.addAttribute("festival",festival);
        model.addAttribute("federal",federal);
        Iterable<Holiday> holidays = holidayRepository.findAll();
        List<Holiday> holidayList = StreamSupport.stream(holidays.spliterator(),false).collect(Collectors.toList());
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    (holidayList.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
    }
}
