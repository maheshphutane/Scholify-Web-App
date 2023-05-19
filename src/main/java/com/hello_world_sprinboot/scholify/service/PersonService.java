package com.hello_world_sprinboot.scholify.service;

import com.hello_world_sprinboot.scholify.constans.ScholifyConstants;
import com.hello_world_sprinboot.scholify.model.Person;
import com.hello_world_sprinboot.scholify.model.Roles;
import com.hello_world_sprinboot.scholify.repository.PersonRepository;
import com.hello_world_sprinboot.scholify.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepository rolesRepository;

    public boolean createNewPerson(Person person) {
        Roles role = rolesRepository.getByRoleName(ScholifyConstants.STUDENT);

        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person =  personRepository.save(person);
        if(person!=null && person.getPersonId()>0){
            return true;
        }
        return false;
    }
}
