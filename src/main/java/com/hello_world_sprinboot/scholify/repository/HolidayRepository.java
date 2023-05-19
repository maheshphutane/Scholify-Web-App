package com.hello_world_sprinboot.scholify.repository;

import com.hello_world_sprinboot.scholify.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday,String> {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public HolidayRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Holiday> getAllHolidays(){
//        String sql = "SELECT * FROM holidays";
//        var rowMapper = BeanPropertyRowMapper.newInstance(Holiday.class);
//        return jdbcTemplate.query(sql,rowMapper);
//    }
}
