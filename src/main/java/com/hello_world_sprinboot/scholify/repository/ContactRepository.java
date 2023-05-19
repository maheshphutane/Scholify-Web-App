package com.hello_world_sprinboot.scholify.repository;

import com.hello_world_sprinboot.scholify.model.Contact;
import com.hello_world_sprinboot.scholify.model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;



/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
@RepositoryRestResource(exported = false)
public interface ContactRepository extends CrudRepository<Contact,Integer>, PagingAndSortingRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);
    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    Page<Contact> findByStatusWithQuery(@Param("status") String status, Pageable pageable); // for pagination



//    private final JdbcTemplate jdbcTemplate;
//    @Autowired
//    public ContactRepository(JdbcTemplate jdbcTemplate){
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public int saveMessageDetails(Contact contact){
//        String query = "INSERT INTO contact_msg (name,mobile_num,email,subject,message,status, "+
//                "created_at,created_by) VALUES (?,?,?,?,?,?,?,?)";
//        return jdbcTemplate.update(query,contact.getName(),contact.getMobileNum(),contact.getEmail(),contact.getSubject(),contact.getMessage(),
//                contact.getStatus(),contact.getCreatedAt(),contact.getCreatedBy());
//    }
//
//    public List<Contact> getContactWithStatus(String status) {
//        String sql = "SELECT * FROM contact_msg WHERE status = ?";
//        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1,status);
//            }
//        },new ContactRowMapper());
//    }
//
//    public int updateStatusOfMsg(int id, String name) {
//        String query = "UPDATE contact_msg SET status = ? ,updated_by = ? ,updated_at = ? WHERE contact_id = ?";
//        return jdbcTemplate.update(query, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                ps.setString(1, ScholifyConstants.CLOSE);
//                ps.setString(2,name);
//                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//                ps.setInt(4,id);
//            }
//        });
//    }
}
