package com.wzh.myapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.myapp.service.PhotoService;
import com.wzh.myapp.model.domain.Photo;
import com.wzh.myapp.mapper.PhotoMapper;
import org.springframework.stereotype.Service;

/**
* @author 75654
* @description 针对表【photo(用户照片表)】的数据库操作Service实现
* @createDate 2024-05-09 10:58:04
*/
@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo>
    implements PhotoService {

}




