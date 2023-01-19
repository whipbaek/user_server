package devcamp.ottogi.user_server.repository;

import devcamp.ottogi.user_server.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findMemberByName(String name);
    Optional<Member> findByEmail(String email);
    boolean existsByEmail(String email);
    Member findMemberByEmail(String email);

}
