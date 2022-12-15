package com.simulated_3d.Config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// 모든 데이터를 누가 등록했는 구별하는 컬럼이 존재합니다
// 해당 컬럼을 일일이  입력해주는 건 너무 반복적입니다
// JPA의 AuditorAware를 사용하면 다음과 같이 간단한 매핑을 통해 특정 필드에 지금 로그인한
// 사람의 정보로 등록자를 자동으로 입력 해줄 수 있습니다
public class Auditor_Aware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // 시큐리티콘텍스트홀더 - 누가 인증했는지에 대한 정보들을 저장하고 있습니다.
        // 다음 선언은 현재 세션 사용자의 정보 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String user_id = "";

        if(authentication != null)
        {
            user_id = authentication.getName();
        }
        //  인증정보에서 알아낸 사용자의 이름을 리턴하여, 특정 글의 게시,수정시 등록자,수정자로 저장합니다.
        return Optional.of(user_id);
    }
}
