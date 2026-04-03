package com.anurag.examhall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "DB_URL=jdbc:h2:mem:testdb",
    "DB_USER=sa",
    "DB_PASS=",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "JWT_SECRET=testSecretKeyForTestingPurposesOnlyNotForProduction123456",
    "TWILIO_ACCOUNT_SID=",
    "TWILIO_AUTH_TOKEN=",
    "TWILIO_PHONE_NUMBER="
})
class ExamHallApplicationTests {
    @Test
    void contextLoads() {
    }
}
