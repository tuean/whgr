package com.tuean.whgr.func.login;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String loginUserName;

    private String nickName;

    private String token;

}
