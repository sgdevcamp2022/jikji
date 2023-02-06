package com.example.neo4j.basic.repository;

import com.example.neo4j.entity.User;
import com.example.neo4j.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

@Transactional
@Testcontainers
@SpringBootTest
public class CRUDTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    private static final String PASSWORD = "movies";

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", () -> "neo4j://localhost:7687"); //도커 사용하지 않고 테스트 진행
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> PASSWORD);
        registry.add("spring.data.neo4j.database", () -> "movies");
    } //테스트환경에서 connection을 제공한다.


    @BeforeEach
    @Transactional
    @Rollback(value = false)
    public void setUp() {

        userRepository.deleteAll();
        relationshipRepository.deleteAll();

        User user1 = userRepository.save(new User(1L));
        User user2 = userRepository.save(new User(2L));
        User user3 = userRepository.save(new User(3L));
        User user4 = userRepository.save(new User(4L));
        User user5 = userRepository.save(new User(5L));
        User user6 = userRepository.save(new User(6L));

        user1.getFollower().add(user2);
        user2.getFollower().add(user3);
        user3.getFollower().add(user4);
        user4.getFollower().add(user3);
        user4.getFollower().add(user5);
        user4.getFollower().add(user6);
        user5.getFollower().add(user4);

    }


    @Test
    public void deleteTest(){ //depth = infinite
        System.out.println("=======deleteTest===========");

        User user4 = userRepository.findFirstById(4L);
        User user6 = userRepository.findFirstById(6L);

        user4.getFollower().remove(user6);

        System.out.println("==========deleteTest end===========");

    }

}
