package com.example.personinfo.security;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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