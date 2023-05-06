package com.hello_world_sprinboot.scholify.repository;

import com.hello_world_sprinboot.scholify.constans.ScholifyConstants;
import com.hello_world_sprinboot.scholify.model.Contact;
import com.hello_world_sprinboot.scholify.rowmapper.ContactRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;



/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface ContactRepository extends CrudRepository<Contact,Integer> {

    List<Contact> findByStatus(String status);

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
