package com.apsol.ecopass.entity.auth;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor()
@Builder
public class Role {

    /**
     * PK
     */
    @Id
    @Column(name = "name", nullable = false, length = 50)
    @Setter(AccessLevel.NONE)
    private String name;

    /**
     * 설명
     */
    @Column(name = "memo", nullable = false)
    private String memo = "";

    /**
     * 삭제유무
     */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;


    public Role(String name) {
        this.name = name;
    }

}
