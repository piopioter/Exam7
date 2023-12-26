package com.example.personinfo.people.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @SpyBean
//    private UserRepository  userRepository;
//    @Autowired
//    private ObjectMapper om;
//
//    @Test
//    public void shouldCreateUser() throws Exception {
//        //given
//        CreateUserCommand  user = new CreateUserCommand("Pawel","pawel@o2.pl","pawel123",Role.ADMIN);
//        //when
//        mockMvc.perform(post("/register")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(user)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userName").value("Pawel"))
//                .andExpect(jsonPath("$.email").value("pawel@o2.pl"))
//                .andExpect(jsonPath("$.password").value("pawel123"))
//                .andExpect(jsonPath("$.role").value("ADMIN"));
//
//        //then
//        verify(userRepository, times(1)).save(any(User.class));
//    }

}