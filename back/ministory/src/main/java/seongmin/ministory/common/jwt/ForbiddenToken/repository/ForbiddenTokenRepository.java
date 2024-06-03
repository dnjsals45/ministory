package seongmin.ministory.common.jwt.ForbiddenToken.repository;

import org.springframework.data.repository.CrudRepository;
import seongmin.ministory.common.jwt.ForbiddenToken.dto.ForbiddenToken;

public interface ForbiddenTokenRepository extends CrudRepository<ForbiddenToken, Long> {
    boolean existsByToken(String refreshToken);
}
