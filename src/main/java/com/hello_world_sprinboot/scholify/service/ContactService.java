package com.hello_world_sprinboot.scholify.service;

import com.hello_world_sprinboot.scholify.constans.ScholifyConstants;
import com.hello_world_sprinboot.scholify.model.Contact;
import com.hello_world_sprinboot.scholify.repository.ContactRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.repository.util.ClassUtils.ifPresent;

@Data
@Slf4j
@Service
@ApplicationScope
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;
    //private static Logger log = LoggerFactory.getLogger(ContactService.class);
    // As we have used @Slf4j annotation which automatically create log object
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(ScholifyConstants.OPEN);
        contact.setCreatedBy(ScholifyConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());

        Contact res = contactRepository.save(contact);
        if(res!=null && res.getContactId()>0){
            isSaved = true;
        }
        return isSaved;
    }

    public List<Contact> getListOfContactWithOpenStatus() {
        List<Contact> contMsg = contactRepository.findByStatus(ScholifyConstants.OPEN);
        return  contMsg;
    }

    public boolean updateStatusOfMsg(int id, String name) {
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(id);
        contact.ifPresent(contact1->{
            contact1.setStatus(ScholifyConstants.CLOSE);
            contact1.setUpdatedBy(name);
            contact1.setUpdatedAt(LocalDateTime.now());
        });
        Contact updatedContact = contactRepository.save(contact.get());

        if(updatedContact!=null && updatedContact.getUpdatedBy()!=null){
            isUpdated = true;
        }
        return isUpdated;
    }
}
