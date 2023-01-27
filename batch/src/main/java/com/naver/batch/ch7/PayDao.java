package com.naver.batch.ch7;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PayDao {

    private static final RowMapper<Pay> ROW_MAPPER = (resultSet, rowNum) ->
            new Pay(resultSet.getLong("id"),
                    resultSet.getLong("amount"),
                    resultSet.getString("tx_name"),
                    resultSet.getObject("tx_date_time", LocalDateTime.class));

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PayDao(@Qualifier("applicationDataSource")DataSource dataSource) throws SQLException {
        System.out.println("@@@@@@@@@@");
        System.out.println(dataSource.getConnection());
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Pay> findAll() {
        String sql = "SELECT * FROM pay";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Pay.class));
    }
}
