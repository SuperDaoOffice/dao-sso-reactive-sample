package com.dao.sso.reactive.handler.entity;

import com.dao.sso.reactive.entity.dto.LoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author HuChiHui
 * @date 2018/09/08 上午 11:13
 * @description
 */
@Mapper
public interface LoginTransform {

    LoginTransform INSTANCE = Mappers.getMapper(LoginTransform.class);

    /**
     * LoginUserInfoBO 转 LoginDTO
     *
     * @param loginUserInfo
     * @return
     */
    LoginDTO userInfoBoToDto(LoginUserInfoBO loginUserInfo);

    /**
     * LoginDTO 转 LoginUserInfoBO
     *
     * @param loginDTO
     * @return
     */
    LoginUserInfoBO loginDtoToLoginUserInfoBo(LoginDTO loginDTO);
}
