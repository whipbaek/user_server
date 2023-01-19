package devcamp.ottogi.user_server.entity;

import devcamp.ottogi.user_server.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
//@Transactional
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void 데이터삽입(){
        Member member = Member.builder()
                .email("whipbaek@gmail.com")
                .password("lskH534@$sdf122W")
                .name("백종인")
                .profile("tempProfile")
                .status(0)
                .build();

        memberRepository.save(member);
        log.info("백종인 데이터 삽입");
    }

    @Test
    void 데이터수정(){
        //given
        Member findMember = memberRepository.findMemberByName("백종인");

        //when
        findMember.setName("백종인_변경");
        log.info("백종인 데이터 변경");
        memberRepository.save(findMember);
        findMember = memberRepository.findMemberByName("백종인_변경");

        log.info("created_at : {}",findMember.getCreatedAt());
        log.info("updated_at : {}",findMember.getUpdatedAt());

        //then
        assertThat(findMember.getCreatedAt()).isNotEqualTo(findMember.getUpdatedAt());

    }
}
