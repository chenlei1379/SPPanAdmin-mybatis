package net.sppan.base.mapper;

import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;

import net.sppan.base.common.Mappers;
import net.sppan.base.entity.User;

@Component
public interface SysUserMapper extends Mappers<User, Integer> {

	User findByUserName(String username);

	PageInfo<User> findAllByNickNameContaining(String searchText, PageInfo pageable);

}
