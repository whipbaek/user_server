package devcamp.ottogi.user_server.controller;

import devcamp.ottogi.user_server.dto.MemberRequestDto;
import devcamp.ottogi.user_server.dto.MemberResponseDto;
import devcamp.ottogi.user_server.dto.TokenDto;
import devcamp.ottogi.user_server.dto.TokenRequestDto;
import devcamp.ottogi.user_server.service.AuthService;
import devcamp.ottogi.user_server.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;
    private String userEmail;
    private MemberRequestDto userMemberRequestDto;

    @PostMapping("/signup")
    public String signup(@RequestBody MemberRequestDto memberRequestDto) {

        userMemberRequestDto = new MemberRequestDto(memberRequestDto);
        userEmail = memberRequestDto.getEmail();

        // 로직 검사
        if(userEmail.length() < 8){
            log.error("비밀번호 길이는 8자 이상이어야 합니다.");
            return "비밀번호 길이 오류!";
        }

        // 메일 전송
        emailService.sendSimpleMessage(userEmail);
        log.info("이메일을 전송하였습니다.");

        return "이메일을 전송하였습니다."; // -> ok면 메일인증 화면으로 이동
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue") //재발급
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @PostMapping("/email")
    public String emailConfirm(@RequestBody String userCode) {
        userCode = userCode.substring(21,28);

        if(!emailService.validateCodeNumber(userCode)){
            return "인증코드가 틀렸습니다."; // code 보내주기
        }

        authService.signup(userMemberRequestDto);
        return "인증코드가 맞습니다. 회원가입 완료!"; //

    }
}
