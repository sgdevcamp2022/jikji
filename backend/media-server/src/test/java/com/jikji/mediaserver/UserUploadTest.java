package com.jikji.mediaserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.mediaserver.model.User;
import com.example.mediaserver.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserUploadTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    @Transactional
    @Rollback(value = false)
    public void beforeEach() {
        // 유저를 미리 생성
        userRepository.deleteAll();
        User tempUser = new User();
        tempUser.setUserName("jongha");
        tempUser.setFullName("sdasda");
        userRepository.save(tempUser);
    }

    @DisplayName("이미지를 업로드 한 후 성공 시 200 OK 를 반환한다. ")
    @Test
    public void uploadTest() throws Exception {
        // given
        // when
        Resource resource = resourceLoader.getResource("classpath:/1.jpg");
        // Mock 파일 생성
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "1.jpg", "image/jpg",
                resource.getInputStream());

        // then
        mvc.perform(
                multipart("/media")
                        .file(file)
                        .param("userId", "1")
        ).andExpect(status().isOk());

    }
}
