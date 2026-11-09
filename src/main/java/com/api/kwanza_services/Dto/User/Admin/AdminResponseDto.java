package com.api.kwanza_services.Dto.User.Admin;

import com.api.kwanza_services.Dto.DefaultDto;
import com.api.kwanza_services.Dto.User.DefaultUser.DefaultUserResponseDto;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AdminResponseDto  extends DefaultDto {

    private DefaultUserResponseDto usuario;
}
