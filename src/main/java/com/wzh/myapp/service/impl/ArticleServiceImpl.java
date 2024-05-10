package com.wzh.myapp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzh.myapp.service.ArticleService;
import com.wzh.myapp.model.domain.Article;
import com.wzh.myapp.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
* @author 75654
* @description 针对表【article(文章表)】的数据库操作Service实现
* @createDate 2024-05-09 10:58:04
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService {

}




