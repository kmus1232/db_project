package gov.example.appDemo.repository;

import gov.example.appDemo.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class StudentRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer getCountByName(String name) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM students WHERE name = '" + name +"'", Integer.class);
    }

    public Integer getCountByDegree(String degree) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM students WHERE degree = '" + degree +"'", Integer.class);
    }

    public List<Student> getStudentsByName(String name) {
        return jdbcTemplate.query("SELECT name, email, graduation, degree FROM students WHERE name = '" + name + "'",
                new RowMapper<Student>() {
                    @Override
                    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Student student = new Student();
                        student.setName(rs.getString("name"));
                        student.setEmail(rs.getString("email"));
                        student.setGraduation(rs.getInt("graduation"));
                        student.setDegree(rs.getString("degree"));
                        return student;
                    }
                });
    }

    public int insert(final Student student) {
        try {
            int updatedRow = jdbcTemplate.update("INSERT INTO students(name, email, graduation, degree) VALUES (?, ?, ?, ?)",
                    student.getName(), student.getEmail(), student.getGraduation(), student.getDegree());
            return updatedRow;
        } catch (DuplicateKeyException e) {
            return -1;
        }

    }
}
