package com.simulated_3d.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class Base_Time {

    /*
        @생성일자을 저장,업데이트 불가
    */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime reg_time;

    /*
        @수정일자을 저장 , 마지막으로 수정한 날짜로 덮어쓰워집니다.
    */
    @LastModifiedDate
    private LocalDateTime update_time;
}
