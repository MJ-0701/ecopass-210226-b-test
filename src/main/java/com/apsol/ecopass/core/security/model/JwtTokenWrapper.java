package com.apsol.ecopass.core.security.model;

import com.apsol.ecopass.core.security.model.AccessedUser;
import com.apsol.ecopass.entity.auth.AuthItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenWrapper {

    private String token;
    private Date expiresAt;
    private AccessedUser accessedUser;
    private List<AuthItem> authItemList;

}
