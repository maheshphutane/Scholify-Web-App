package com.hello_world_sprinboot.scholify.controller;
import com.hello_world_sprinboot.scholify.model.Contact;
import com.hello_world_sprinboot.scholify.service.ContactService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
@Slf4j
@Controller
public class ContactController {
    //private static Logger log = LoggerFactory.getLogger(ContactController.class);
    // As we have used @Slf4j annotation which automatically create log object
    private final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService){

        this.contactService = contactService;
    }
    @RequestMapping("/contact")
    public String displayContactPage(Model model1){
        model1.addAttribute("contact", new Contact());
        return "contact.html";
    }
//    @RequestMapping(value = "/saveMsg",method = POST)
//    //@PostMapping("/saveMsg")
//    public ModelAndView saveMessage(@RequestParam String name,@RequestParam String mobileNum,@RequestParam String email,
//                                    @RequestParam String subject,@RequestParam String message){
//        log.info("Name: "+name);
//        log.info("Mobile No: "+mobileNum);
//        log.info("Email: "+email);
//        log.info("Subject: "+subject);
//        log.info("Message: "+message);
//        return new ModelAndView("redirect:/contact");
//
//    }
    @RequestMapping(value = "/saveMsg",method = POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors){

        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : " + errors.toString());
            return "contact.html";
        }

        contactService.saveMessageDetails(contact);
        return "redirect:/contact";
    }

    @RequestMapping(value = "/displayMessages/page/{pageNum}")
    public ModelAndView displayMessage(Model model,
                                       @PathVariable(name = "pageNum") int pageNum, @RequestParam("sortField") String sortField,
                                       @RequestParam("sortDir") String sortDir){
        Page<Contact> msgPage = contactService.findMsgsWithOpenStatus(pageNum,sortField,sortDir);
        List<Contact> contactMsgs = msgPage.getContent();
        ModelAndView modelAndView = new ModelAndView("messages.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", msgPage.getTotalPages());
        model.addAttribute("totalMsgs", msgPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("contactMsgs",contactMsgs);
        return modelAndView;
    }

    @RequestMapping(value = "/closeMsg" ,method = GET)
    public String updateStatus(@RequestParam int id){
        contactService.updateStatusOfMsg(id);
        return "redirect:/displayMessages/page/1?sortField=name&sortDir=asc";
    }
}
