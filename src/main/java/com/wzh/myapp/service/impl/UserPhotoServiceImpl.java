package com.wzh.myapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.myapp.service.UserPhotoService;
import com.wzh.myapp.model.domain.UserPhoto;
import com.wzh.myapp.mapper.UserPhotoMapper;
import org.springframework.stereotype.Service;

/**
* @author 75654
* @description 针对表【user_photo(照片集表)】的数据库操作Service实现
* @createDate 2024-05-09 10:58:04
*/
@Service
public class UserPhotoServiceImpl extends ServiceImpl<UserPhotoMapper, UserPhoto>
    implements UserPhotoService {

}




