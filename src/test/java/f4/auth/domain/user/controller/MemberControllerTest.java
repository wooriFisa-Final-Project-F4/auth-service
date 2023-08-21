package f4.auth.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.persist.entity.Member;
import f4.auth.domain.user.persist.repository.MemberRepository;
import f4.auth.domain.user.service.MemberService;
import f4.auth.global.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DisplayName("Member-Controller 테스트")
class MemberControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MemberService memberService;

    @Autowired

    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(memberService, memberRepository))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void success_registered() throws Exception {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .username("테스트 유저1")
                .gender("M")
                .birth("1996-12-16")
                .address("서울시 거마로 56 107-302")
                .email("soohyuk96@naver.com")
                .password("f4loveyou")
                .phoneNumber("010-3237-4176")
                .build();

        // then
        mockMvc.perform(post("/auth/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupRequestDto))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("기존에 있는 회원")
    void 기존에_회원가입한_이메일() throws Exception {
        //given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .username("테스트 유저1")
                .gender("M")
                .birth("1996-12-16")
                .address("서울시 거마로 56 107-302")
                .email("soohyuk96@naver.com")
                .password("f4loveyou")
                .phoneNumber("010-3237-4176")
                .build();

        //when
        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(Member.builder().email("soohyuk96@naver.com").build()));

        // then
        mockMvc.perform(post("/auth/v1/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(signupRequestDto))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.path").value("/user/v1/signup"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이미 회원 가입한 계정이 존재합니다."))
                .andReturn();
    }
}