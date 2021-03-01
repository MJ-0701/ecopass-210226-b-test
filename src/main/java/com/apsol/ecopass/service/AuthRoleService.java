package com.apsol.ecopass.service;

import com.apsol.ecopass.entity.auth.Auth;
import com.apsol.ecopass.entity.auth.AuthItem;

import java.util.List;

public interface AuthRoleService {

    List<AuthItem> findAuthItemList(Long authCode);

    List<AuthItem> findAuthItemList(Auth auth);

}
