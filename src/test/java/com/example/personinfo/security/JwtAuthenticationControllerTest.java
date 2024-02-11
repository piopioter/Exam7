package com.example.personinfo.security;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JwtAuthenticationControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//    @SpyBean
//    private UserRepository userRepository;
//    @Autowired
//    private ObjectMapper om;
//    @Autowired
//    private PasswordEncoder encoder;
//
//
//    @Test
//    public void shouldCreateAuthenticationToken() throws Exception {
//        //given
//        userRepository.saveAndFlush(new User("Jan", "jan@wp.pl",
//                encoder.encode("jan123"), Role.ADMIN));
//        JwtRequest request = new JwtRequest("Jan", "jan123");
//        //when
//        mockMvc.perform(post("/authenticate")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(request)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.jwtToken").isNotEmpty());
//        //then
//        verify(userRepository, times(2)).findByUserName(request.getUsername());
//    }
//
//    @Test
//    public void shouldReturn401WhenRequestIsIncorrect() throws Exception {
//        //given
//        userRepository.saveAndFlush(new User("Piotr", "piotr@wp.pl",
//                encoder.encode("piotr123"), Role.ADMIN));
//        JwtRequest request = new JwtRequest("Piotr", "123");
//        //when
//        mockMvc.perform(post("/authenticate")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(request)))
//                .andDo(print())
//                .andExpect(status().isUnauthorized());
//
//        //then
//        verify(userRepository, times(1)).findByUserName(request.getUsername());
//    }



}