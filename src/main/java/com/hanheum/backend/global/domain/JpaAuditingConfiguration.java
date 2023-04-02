package com.hanheum.backend.global.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //jpa auditing(입력,수정일시 데이터)
@Configuration
public class JpaAuditingConfiguration {
}
