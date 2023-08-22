package f4.auth.domain.user.controller;

import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.persist.repository.MemberRepository;
import f4.auth.domain.user.service.MemberService;
import f4.auth.global.constant.CustomErrorCode;
import f4.auth.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/health-check")
    public String status() {
        return "It's Working in Auth-Service";
    }

    @PostMapping("/signup")
    public String register(@Valid @RequestBody SignupRequestDto signupRequestDto) {

        memberRepository.findByEmail(signupRequestDto.getEmail())
                .ifPresent(data -> {
                    throw new CustomException(CustomErrorCode.ALREADY_REGISTERED_MEMBER);
                });

        memberService.register(signupRequestDto);
        return "회원 가입에 성공하셨습니다.";
    }
}
