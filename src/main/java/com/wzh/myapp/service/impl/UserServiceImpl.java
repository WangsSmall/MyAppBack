package com.wzh.myapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wzh.myapp.common.UserContent;
import com.wzh.myapp.common.UserEnum;
import com.wzh.myapp.common.ErrorCode;
import com.wzh.myapp.config.KaptchaConfig;
import com.wzh.myapp.exception.BusinessException;
import com.wzh.myapp.model.domain.User;
import com.wzh.myapp.model.request.UserLoginRequest;
import com.wzh.myapp.model.request.UserRegisterRequest;
import com.wzh.myapp.model.response.UserInfoResponse;
import com.wzh.myapp.service.UserService;
import com.wzh.myapp.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 75654
 * @description 针对表【user(用户信息表)】的数据库操作Service实现
 * @createDate 2024-05-09 10:58:04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private KaptchaConfig kaptchaConfig;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long userRegister(UserRegisterRequest userRegisterRequest) {
        // 1. 参数校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "注册信息为null");
        }
        String username = userRegisterRequest.getUsername();
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        // String avatarUrl = userRegisterRequest.getAvatarUrl();
        // Object gender = userRegisterRequest.getGender();
        // String phone = userRegisterRequest.getPhone();
        // String email = userRegisterRequest.getEmail();
        // String myCode = userRegisterRequest.getMyCode();
        // String tags = userRegisterRequest.getTags();

        if (StringUtils.isAnyBlank(username, userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 2. 账户长度 不能小于 4 位
        if (userAccount.length() < 2) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度 不能小于 4 位");
        }
        // 3. 密码长度 不能大于 8 位吧
        if (userPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度 不能小于 8 位吧");
        }

        // 4. 账户不能重复
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount, userAccount);
        long count = this.count(lqw);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户已存在");
        }

        // 5. 账号不包含特殊字符
        String regex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不包含特殊字符");
        }
        // 6. 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和校验密码不一致");
        }
        // 加密
        String newPass = DigestUtils.md5DigestAsHex((UserContent.USER_SALT + userPassword).getBytes());
        userRegisterRequest.setUserPassword(newPass);

        User user = new User();
        BeanUtils.copyProperties(user, userRegisterRequest);

        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.ERROR);
        }

        // 返回当前用户id
        return user.getUserId();

    }

    @Override
    public UserInfoResponse userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 登录
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 校验
        String newPass = DigestUtils.md5DigestAsHex((UserContent.USER_SALT + userPassword).getBytes());
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserAccount, userAccount).eq(User::getUserPassword, newPass);
        User user = userMapper.selectOne(lqw);
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 用户信息脱敏，隐藏敏感信息，防止数据库中的字段泄露
        UserInfoResponse reUser = this.getSafetyUser(user);
        // 存入 session
        request.getSession().setAttribute(UserContent.USER_SESSION_KEY, reUser);

        return reUser;
    }

    @Override
    public UserInfoResponse getSafetyUser(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "参数错误");
        }
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(user, userInfoResponse);
        return userInfoResponse;
    }

    @Override
    public String getCode() {
        return this.codeByBase64().get("code");
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除 session
        request.getSession().removeAttribute(UserContent.USER_SESSION_KEY);
        return 1;
    }

    public Map<String, String> codeByBase64() {
        DefaultKaptcha defaultKaptcha = kaptchaConfig.getDefaultKaptcha();

        String kaptchaText = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(kaptchaText);

        String base64Code = "";
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            base64Code = Base64.encodeBase64String(outputStream.toByteArray());
        } catch (Exception e) {
            System.out.println("verificationCode exception: ");
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    System.out.println("verificationCode outputStream close exception: ");
                }
            }
        }

        // uuid; 唯一标识code
        // code; 验证码图片的Base64串
        Map<String, String> kaptchaVoMap = new HashMap<>();
        String uuid = UUID.randomUUID().toString();
        kaptchaVoMap.put("uuid", uuid);
        kaptchaVoMap.put("code", "data:image/png;base64," + base64Code);
        ValueOperations redisValueOperations = redisTemplate.opsForValue();
        redisValueOperations.set(uuid, kaptchaText, 60L, TimeUnit.SECONDS);

        return kaptchaVoMap;
    }
}




