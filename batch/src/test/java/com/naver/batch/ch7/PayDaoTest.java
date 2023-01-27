package com.naver.batch.ch7;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PayDaoTest {

    @Autowired
    private PayDao payDao;

    @Test
    void findAllTest() {
        List<Pay> pays = payDao.findAll();

        for (Pay pay: pays) {
            System.out.println(pay);
        }
        assertThat(pays).isNotEmpty();
    }
}
