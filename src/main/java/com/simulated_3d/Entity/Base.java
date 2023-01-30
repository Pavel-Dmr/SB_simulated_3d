package com.simulated_3d.Entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class Base extends Base_Time{

    /*
        @생성자을 저장, 업데이트 불가
    */
    @CreatedBy
    @Column(updatable = false)
    private String create_by;

    /*
        @수정자을 저장, 마지막으로 수정한 사람으로 덮어쓰워집니다.
    */
    @LastModifiedBy
    private String modified_by;
}
