package com.hello_world_sprinboot.scholify.controller;

import com.hello_world_sprinboot.scholify.model.Courses;
import com.hello_world_sprinboot.scholify.model.Person;
import com.hello_world_sprinboot.scholify.model.ScholifyClass;
import com.hello_world_sprinboot.scholify.repository.CoursesRepository;
import com.hello_world_sprinboot.scholify.repository.PersonRepository;
import com.hello_world_sprinboot.scholify.repository.ScholifyClassesRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private ScholifyClassesRepository scholifyClassesRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CoursesRepository coursesRepository;
    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        List<ScholifyClass> classList= scholifyClassesRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("scholifyClass", new ScholifyClass());
        modelAndView.addObject("scholifyClasses",classList);
        return modelAndView;
    }

    @RequestMapping("/addNewClass")
    public String addNewClass(Model model, @ModelAttribute("scholifyClass") ScholifyClass scholifyClass){
        scholifyClassesRepository.save(scholifyClass);
        return "redirect:/admin/displayClasses";

    }

    @RequestMapping("/deleteClass")
    public String deleteClass(Model model, @RequestParam int id){
        Optional<ScholifyClass> scholifyClass = scholifyClassesRepository.findById(id);
        for(Person person : scholifyClass.get().getPersons()){
            person.setScholifyClass(null);
            personRepository.save(person);
        }
        scholifyClassesRepository.deleteById(id);
        return "redirect:/admin/displayClasses";
    }

    @GetMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession session,
                                        @RequestParam(value = "error", required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<ScholifyClass> scholifyClass = scholifyClassesRepository.findById(classId);
        modelAndView.addObject("scholifyClass",scholifyClass.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("scholifyClass",scholifyClass.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        ScholifyClass scholifyClass = (ScholifyClass) session.getAttribute("scholifyClass");
        Person personEntity = personRepository.getByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+scholifyClass.getClassId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.setScholifyClass(scholifyClass);
        personRepository.save(personEntity);
        scholifyClass.getPersons().add(personEntity);
        scholifyClassesRepository.save(scholifyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+scholifyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model, @RequestParam int personId, HttpSession session) {
        ScholifyClass scholifyClass = (ScholifyClass) session.getAttribute("scholifyClass");
        Optional<Person> person = personRepository.findById(personId);
        person.get().setScholifyClass(null);
        scholifyClass.getPersons().remove(person.get());
        ScholifyClass scholifyClass1 = scholifyClassesRepository.save(scholifyClass);
        session.setAttribute("scholifyClass",scholifyClass1);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+scholifyClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model) {
       // List<Courses> courses = (List<Courses>) coursesRepository.findByOrderByNameDesc();
        List<Courses> courses = (List<Courses>) coursesRepository.findAll(Sort.by("courseId").and(Sort.by("fees")));
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses",courses);
        modelAndView.addObject("course", new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course) {
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam int id
            ,HttpSession session,@RequestParam(required = false) String error) {
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> courses = coursesRepository.findById(id);
        modelAndView.addObject("courses",courses.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("courses",courses.get());
        if(error != null) {
            errorMessage = "Invalid Email entered!!";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person,
                                           HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Courses courses = (Courses) session.getAttribute("courses");
        Person personEntity = personRepository.getByEmail(person.getEmail());
        if(personEntity==null || !(personEntity.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId()
                    +"&error=true");
            return modelAndView;
        }
        personEntity.getCourses().add(courses);
        courses.getPersons().add(personEntity);
        personRepository.save(personEntity);
        session.setAttribute("courses",courses);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

    @GetMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model, @RequestParam int personId,
                                                HttpSession session) {
        Courses courses = (Courses) session.getAttribute("courses");
        Optional<Person> person = personRepository.findById(personId);
        person.get().getCourses().remove(courses);
        courses.getPersons().remove(person);
        personRepository.save(person.get());
        session.setAttribute("courses",courses);
        ModelAndView modelAndView = new
                ModelAndView("redirect:/admin/viewStudents?id="+courses.getCourseId());
        return modelAndView;
    }

}
