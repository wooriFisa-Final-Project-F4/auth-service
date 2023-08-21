package f4.auth.domain.user.service;

import f4.auth.domain.user.dto.request.SignupRequestDto;
import f4.auth.domain.user.persist.entity.Member;
import f4.auth.domain.user.persist.repository.MemberRepository;
import f4.auth.global.utils.Encryptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static f4.auth.domain.user.constant.Role.USER;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final Encryptor crypto;

    @Override
    public void register(SignupRequestDto signupRequestDto) {
        String encryptPassword = crypto.encrypt(signupRequestDto.getPassword());

        Member save = memberRepository.save(
                Member.builder()
                        .username(signupRequestDto.getUsername())
                        .gender(signupRequestDto.getGender())
                        .birth(signupRequestDto.getBirth())
                        .address(signupRequestDto.getAddress())
                        .email(signupRequestDto.getEmail())
                        .password(encryptPassword)
                        .phoneNumber(signupRequestDto.getPhoneNumber())
                        .role(USER)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
    }
}
