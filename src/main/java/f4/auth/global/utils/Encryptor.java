package f4.auth.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Slf4j
@Component
public class Encryptor {

    // SHA-256 알고리즘을 활용한 비밀번호 암호화
    // 단반향 알고리즘이기 때문에 혹시나 비밀번호를 잃어버린 경우 비밀번호를 재발급 해준다.
    public String encrypt(String text) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            log.warn("error : { }", e);
        }
        return bytesToHex(Objects.requireNonNull(md).digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
