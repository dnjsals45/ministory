package seongmin.minilife.api.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seongmin.minilife.api.user.service.UserUtilService;
import seongmin.minilife.domain.user.entity.User;

@Tag(name = "유저 API", description = "유저 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserUtilService userUtilService;

    @Operation(summary = "테스트용 로그인", description = "DB에 있는 유저의 id만을 이용해서 로그인해야 가능한 기능 테스트")
    @GetMapping("/{user_id}")
    public ResponseEntity<?> login(@Parameter(name = "user_id", description = "유저 회원번호")
                                   @PathVariable(name = "user_id") Long userId) {
        User user = userUtilService.findById(userId);

        String accessToken = userUtilService.login(user);

        return ResponseEntity.noContent()
                .header("Authorization", "Bearer " + accessToken)
                .build();
    }
}
