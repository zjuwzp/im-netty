package com.zhss.im.dispathcer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class JdbcTemplateTest {

    public static void main(String[] args) {
        DataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true",
                "root",
                "root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("insert into customer(id,name,age) values(1,'leo',20)");

        List<Map<String, Object>> customers = jdbcTemplate.queryForList(
                "select * from customer where id=1");
        for(Map<String, Object> customer : customers) {
            System.out.println(customer);
        }
    }

}
