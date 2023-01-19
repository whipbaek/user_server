package devcamp.ottogi.user_server.repository;

import com.sun.xml.bind.v2.model.core.Ref;
import devcamp.ottogi.user_server.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByKey(String key);
}
