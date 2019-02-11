package com.isofh.service.controller;

import com.isofh.service.constant.AppConst;
import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.constant.field.UserField;
import com.isofh.service.enums.*;
import com.isofh.service.model.*;
import com.isofh.service.utils.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    public UserController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<UserEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (UserEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(UserEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(USER, entity);
        mapData.put(DEPARTMENT, entity.getDepartment());
        mapData.put(PROVINCE, entity.getProvince());
        return mapData;
    }

    /**
     * @param object {"nicknameOrEmail":"admin","password":"e10adc3949ba59abbe56e057f20f883e","device":{"os":0,"deviceId":"","token":""}}
     * @return
     */
    @ApiOperation(value = "login a new user", notes = "{\"usernameOrEmail\":\"admin\",\"password\":\"e10adc3949ba59abbe56e057f20f883e\"}")
    @PutMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody Object object) {
        try {
            init(object);
            String usernameOrEmail = getString("usernameOrEmail");
            String password = getString(UserField.PASSWORD);
            UserEntity user = userRepository.login(usernameOrEmail, password);
            if (user == null) {
                throw getException(3, "Thong tin dang nhap khong dung");
            }
            if (Objects.equals(user.getBlock(), 1)) {
                throw getException(2, "tai khoan da bi khoa");
            }
            saveDeviceInfo(user);
            user.setLoginToken(JWTokenUtils.generateToken(user.getId()));
            return response(ok(getMapData(user)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "register a new user", notes = "{\"user\":{},\"device\":{}}")
    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody Object object) {
        try {
            init(object);

            UserEntity userEntity = getObject(USER, UserEntity.class);
            userEntity.setRole(UserType.USER.getValue());
            DeviceEntity deviceEntity = getObject(DEVICE, DeviceEntity.class);
            UserEntity existedUser = userRepository.findFirstByUsernameOrEmail(userEntity.getUsername(), userEntity.getEmail());
            if (existedUser != null) {
                throw getException(2, "Ten dang nhap hoac email da ton tai");
            }

            if (StrUtils.isNullOrWhiteSpace(userEntity.getName())) {
                userEntity.setName(userEntity.getUsername());
            }
            // kiem tra xem device nay da dang nhap o user nao thi bo lien ket
            if (!DeviceType.WEB.getValue().equals(deviceEntity.getOs()) && !StrUtils.isNullOrWhiteSpace(deviceEntity.getDeviceId(), deviceEntity.getToken())) {

                DeviceEntity entity = deviceRepository.findFirstByDeviceId(deviceEntity.getDeviceId());
                if (entity != null) {
                    deviceRepository.delete(entity);
                }
            }
            save(userEntity);
            deviceEntity.setUser(userEntity);
            save(deviceEntity);

            userEntity.setLoginToken(JWTokenUtils.generateToken(userEntity.getId()));
            return response(ok(getMapData(userEntity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    private void saveDeviceInfo(UserEntity user) {
        DeviceEntity device = getObject(DEVICE, DeviceEntity.class);
        if (device != null && !StrUtils.isNullOrWhiteSpace(device.getDeviceId())) {
            deleteDeviceInfo(device.getDeviceId());
            device.setUser(user);
            save(device);
        }
    }

    private void deleteDeviceInfo(String deviceId) {
        try {
            deviceRepository.deleteByDeviceId(deviceId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param object {"socialType":2,"socialId":"123456781","name":"Tuan dep zai","email":"ledangtuanbk2@gmail.com","device":{"os":1,"deviceId":"8453cbaf464b1ec","token":"efgGY55itgM:APA91bG1wpO7EBfasJ4NV"}}
     * @return code = 2 tai khoan bi khoa <br>
     * 3 thong tin dang nhap khong dung <br>
     */
    @ApiOperation(value = "login with email and password", notes = "{\"socialType\":2,\"socialId\":\"123456781\",\"name\":\"Tuan dep zai\",\"email\":\"ledangtuanbk2@gmail.com\",\"device\":{\"os\":1,\"deviceId\":\"8453cbaf464b1ec\",\"token\":\"efgGY55itgM:APA91bG1wpO7EBfasJ4NV\"}}")
    @PutMapping(path = "/login-social")
    public ResponseEntity<?> loginSocial(@RequestBody Object object) {
        try {
            init(object);
            String socialId = getString("socialId");
            String name = getString("name");
            String email = getString("email");
            String avatar = getString("avatar");
            int socialType = getInteger("socialType");
            if (socialType != SocialType.FACEBOOK.getValue() && socialType != SocialType.GOOGLE.getValue()) {
                throw getException(1, "SocialType is not valid");
            }

            if (StrUtils.isNullOrWhiteSpace(socialId, name)) {
                throw getException(2, "socialId and Fullname is not valid");
            }

            UserEntity entity = null;
            if (Objects.equals(socialType, SocialType.GOOGLE.getValue())) {
                entity = userRepository.findByGoogleId(socialId);
            }

            if (Objects.equals(socialType, SocialType.FACEBOOK.getValue())) {
                entity = userRepository.findByFacebookId(socialId);
            }

            /**
             * social nay chua dang nhap lan nao
             */
            if (entity == null) {
                // Co gui thong tin email len, ktra xem co tai khoan nao co email nay chua, neu co roi thi dang nhap bang tai khoan nay
                if (!StrUtils.isNullOrWhiteSpace(email)) {
                    entity = userRepository.findByEmail(StrUtils.convertToUnsignedLowerCase(email));
                    // co ton tai email, login with device nay
                    if (entity != null) {
                        entity.setLastLogin(LocalDateTime.now());
                        if (Objects.equals(socialType, SocialType.FACEBOOK.getValue())) {
                            entity.setFacebookId(socialId);
                        } else if (Objects.equals(socialType, SocialType.GOOGLE.getValue())) {
                            entity.setGoogleId(socialId);
                        }
                        save(entity);
                        entity.setLoginToken(JWTokenUtils.generateToken(String.valueOf(entity.getId())));
                        return response(ok(USER, entity));
                    }
                }
                // Khong gui email hoac chua chua ton tai tk nao voi email nay thi tao moi
                entity = new UserEntity();
                if (Objects.equals(socialType, SocialType.FACEBOOK.getValue())) {
                    entity.setFacebookId(socialId);
                } else if (Objects.equals(socialType, SocialType.GOOGLE.getValue())) {
                    entity.setGoogleId(socialId);
                }
                entity.setSocialType(socialType);
                entity.setName(name);
                entity.setEmail(email);
                entity.setVerify(1);
                entity.setUsername(createUsername(entity.getName()));
                String defaultAvatar = AppConst.DEFAULT_AVATAR;
                if (!StrUtils.isNullOrWhiteSpace(avatar)) {
                    defaultAvatar = avatar;
                }
                entity.setAvatar(defaultAvatar);
                entity.setThumbnail(defaultAvatar);
                entity.setRole(UserType.USER.getValue());
                save(entity);
                entity.setLoginToken(JWTokenUtils.generateToken(String.valueOf(entity.getId())));
                return response(ok(USER, entity));

            } else {
                if (Objects.equals(1, entity.getBlock())) {
                    throw getException(3, "Tai khoan nay da bi khoa");
                } else if (!Objects.equals(entity.getVerify(), 1)) {
                    throw getException(4, "tai khoan chua duoc kich hoat");
                }
                entity.setLastLogin(LocalDateTime.now());
                entity.setLoginToken(JWTokenUtils.generateToken(String.valueOf(entity.getId())));
                save(entity);
                saveDeviceInfo(entity);
                return response(ok(getMapData(entity)));
            }

        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    private String createUsername(String name) {
        int index = 0;
        String nickName = StrUtils.convertToUnsignedLowerCase(name).replace(" ", "");
        while (userRepository.findFirstByUsername(nickName + (index == 0 ? "" : "" + index)) != null) {
            index++;
        }
        return nickName + (index == 0 ? "" : "" + index);
    }


    @ApiOperation(value = "Tim kiem User", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "email", required = false, defaultValue = DefaultConst.STRING) String email,
            @RequestParam(value = "socialType", required = false, defaultValue = DefaultConst.NUMBER) Integer socialType,
            @RequestParam(value = "username", required = false, defaultValue = DefaultConst.STRING) String username,
            @RequestParam(value = "role", required = false, defaultValue = DefaultConst.NUMBER) Integer role,
            @RequestParam(value = "deleted", required = false, defaultValue = DefaultConst.NUMBER) Integer deleted,
            @RequestParam(value = "block", required = false, defaultValue = DefaultConst.NUMBER) Integer block,
            @RequestParam(value = "isHighlight", required = false, defaultValue = DefaultConst.NUMBER) Integer isHighlight,
            @RequestParam(value = "colIndex", required = false, defaultValue = DefaultConst.NUMBER) Integer colIndex,
            @RequestParam(value = "departmentId", required = false, defaultValue = DefaultConst.NUMBER) Long departmentId,
            @RequestParam(value = "gender", required = false, defaultValue = DefaultConst.NUMBER) Integer gender,
            @RequestParam(value = "memberCode", required = false, defaultValue = DefaultConst.STRING) String memberCode,
            @RequestParam(value = "provinceId", required = false, defaultValue = DefaultConst.NUMBER) Long provinceId,
            @RequestParam(value = "org", required = false, defaultValue = DefaultConst.STRING) String org,
            @RequestParam(value = "phone", required = false, defaultValue = DefaultConst.STRING) String phone,
            @RequestParam(value = "verifiedMemberLao", required = false, defaultValue = DefaultConst.NUMBER) Integer verifiedMemberLao,
            @RequestParam(value = "startActive", required = false, defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate startActive,
            @RequestParam(value = "endActive", required = false, defaultValue = DefaultConst.DATE) @DateTimeFormat(pattern = AppConst.DATE_FORMAT) LocalDate endActive
    ) {
        try {
            init();
            Page<UserEntity> results = userRepository.search(name, email, socialType, username, role, deleted, block, isHighlight, colIndex, departmentId, gender, memberCode, provinceId, org, phone, verifiedMemberLao, startActive, endActive, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Tim kiem User", notes = "")
    @GetMapping(path = "/get-avatar/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getAvatar(
            @PathVariable("id") long id
    ) {
        try {
            init();
            UserEntity entity = getUser(id);
            String pathToImage = AppConst.DATA_DIR;
            char charPath = '/';
            String path = pathToImage + charPath + entity.getThumbnail();
            if (StrUtils.isNullOrWhiteSpace(entity.getThumbnail()) || !FileUtils.existed(path)) {
                path = pathToImage + charPath + AppConst.DEFAULT_AVATAR;
            }
            File initialFile = new File(path);
            InputStream in = new FileInputStream(initialFile);
            return IOUtils.toByteArray(in);
        } catch (Exception ex) {
            return new byte[0];
        }
    }

    @ApiOperation(value = "Lay theo email", notes = "")
    @GetMapping(path = "/get-by-email/{email}")
    public ResponseEntity<?> getByEmail(
            @PathVariable("email") String email
    ) {
        try {
            init();
            UserEntity entity = userRepository.findByEmail(email);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Lay theo id", notes = "")
    @GetMapping(path = "/get-user-by-id/{id}")
    public ResponseEntity<?> search(
            @PathVariable("id") long id

    ) {
        try {
            init();
            UserEntity entity = getUser(id);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Create user or doctor or admin", notes = "{\"user\":{}}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> createByAdmin(
            @RequestBody Object object
    ) {
        try {
            init(object);
            UserEntity user = getObject("user", UserEntity.class);
            if (StrUtils.isNullOrWhiteSpace(user.getUsername())) {
                String nickName = createUsername(user.getName());
                user.setUsername(nickName);
            } else {
                UserEntity checkUser = userRepository.findFirstByUsername(user.getUsername());
                if (checkUser != null) {
                    throw getException("username %s da ton tai " + checkUser.getUsername());
                }
            }

            if (!StrUtils.isNullOrWhiteSpace(user.getEmail())) {
                if (userRepository.findByEmail(user.getEmail()) != null) {
                    throw getException(2, "email da ton tai");
                }
            } else {
                user.setEmail(user.getUsername() + "@benhvienphoi.com");
            }
            if (!StrUtils.isNullOrWhiteSpace(user.getPassword())) {
                String password = RandomUtils.getRandomPasswordNumber();
                user.setPassword(StrUtils.toMD5(password));
            }

            Long departmentId = getLong("departmentId");
            if (departmentId != null) {
                DepartmentEntity department = getDepartment(departmentId);
                user.setDepartment(department);
            }
            save(user);
            return response(ok(getMapData(user)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update info", notes = "{\"user\":{}}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            UserEntity userData = getObject("user", UserEntity.class);
            UserEntity entity = getUser(id);
            entity.setName(userData.getName());
            entity.setJob(userData.getJob());
            entity.setAddress(userData.getAddress());
            entity.setCompany(userData.getCompany());
            entity.setGender(userData.getGender());
            entity.setDob(userData.getDob());
            entity.setAvatar(userData.getAvatar());
            entity.setThumbnail(userData.getThumbnail());
            entity.setPhone(userData.getPhone());
            entity.setLastResetTime(LocalDateTime.now());
            entity.setIntroduction(userData.getIntroduction());
            entity.setOriginImage(userData.getOriginImage());
            entity.setIntroduction(userData.getIntroduction());
            entity.setRole(userData.getRole());
            entity.setAdminRole(userData.getAdminRole());
            entity.setIndex(userData.getIndex());
            entity.setEmail(userData.getEmail());
            entity.setOrg(userData.getOrg());
            entity.setDegree(userData.getDegree());
            entity.setProvince(userData.getProvince());
            entity.setTitle(userData.getTitle());
            save(entity);
            entity.setLoginToken(JWTokenUtils.generateToken(id));
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "block an user", notes = "{\"block\":1}")
    @PutMapping(path = "/block/{id}")
    public ResponseEntity<?> block(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init(object);
            UserEntity entity = getUser(id);
            Integer block = getInteger("block");
            entity.setBlock(block);
            if (block == 1) {
                entity.setLastResetTime(LocalDateTime.now());
            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "reset info", notes = "{}")
    @PutMapping(path = "/reset-password/{id}")
    public ResponseEntity<?> resetPassword(
            @RequestBody Object object,
            @PathVariable("id") long id

    ) {
        try {
            init();
            String password = "123456";
            String defaultPassword = StrUtils.toMD5(password);
            UserEntity entity = getUser(id);
            entity.setPassword(defaultPassword);
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "change info", notes = "{\"oldPassword\":\"asd\",\"newPassword\":\"gfgfd\"}")
    @PutMapping(path = "/change-password/{id}")
    public ResponseEntity<?> changePassword(@RequestBody Object object,
                                            @PathVariable("id") long id

    ) {
        try {
            init(object);
            String oldPass = getString("oldPassword");
            String newPass = getString("newPassword");
            UserEntity entity = getUser(id);
            if (!Objects.equals(oldPass, entity.getPassword())) {
                throw getException(2, "password khong dung");
            }
            entity.setPassword(newPass);
            save(entity);
            String newToken = JWTokenUtils.generateToken(entity.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("uid", newToken);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "create by email", notes = "{\"user\":{}}")
    @PostMapping(path = "/create-by-email")
    public ResponseEntity<?> createByEmail(
            @RequestBody Object object

    ) {
        try {
            init(object);
            UserEntity entity = getObject("user", UserEntity.class);
            UserEntity checkUser = userRepository.findByEmail(entity.getEmail());
            if (checkUser != null) {
                throw getException(2, "email da ton tai");
            }
            if (entity.getUsername() == null) {
                String username = createUsername(entity.getName());
                entity.setUsername(username);
            }
            String randomPassword = RandomUtils.getRandomPasswordNumber();
            String encodedPassword = StrUtils.toMD5(randomPassword);
            entity.setPassword(encodedPassword);
            save(entity);
            entity.setLoginToken(JWTokenUtils.generateToken(entity.getId()));

            String subject = "Tạo tài khoản cho email " + entity.getEmail();
//            WebType webType = WebType.BVP;
            String linkWeb = AppConst.WEB_ADDRESS_HOILAO;
            String appName = AppConst.APP_HL_NAME;
            String contactMail = AppConst.CONTACT_HL_EMAIL;

            String link = linkWeb + "/dang-nhap";
            save(new EmailEntity(entity.getEmail(), subject, EmailUtils.getBodySendAccountInfoHoiLao(entity.getUsername(), link, contactMail, randomPassword)));
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Forgot password", notes = "{\"email\":\"ledangtuanbk@gmail.com\",\"webType\":4}")
    @PutMapping(path = "/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestBody Object object
    ) {
        try {
            init(object);
            String email = getString("email");
            Integer webTypeValue = getInteger("webType");
            UserEntity entity = userRepository.findByEmail(email);
            if (entity == null) {
                throw getException(1, "Not found user with email: " + email);
            }
            TokenPasswordEntity tokenPasswordEntity = new TokenPasswordEntity();
            tokenPasswordEntity.setExpirationDate(LocalDateTime.now().plusDays(5));
            tokenPasswordEntity.setToken(RandomUtils.getRandomId());
            tokenPasswordEntity.setUser(entity);
            tokenPasswordRepository.save(tokenPasswordEntity);
            WebType webType = WebType.fromValue(webTypeValue);
            String linkWeb = "";
            String subject = "";
            String link = "";
            String appName = "";
            String contactMail = "";
            switch (webType) {
                case HOILAO: {
                    linkWeb = AppConst.WEB_ADDRESS_HOILAO;
                    link = linkWeb + String.format("/activate/recovery/%s", tokenPasswordEntity.getToken());
                    appName = AppConst.APP_HL_NAME;
                    contactMail = AppConst.CONTACT_HL_EMAIL;
                    break;
                }
                case CHONG_LAO: {
                    linkWeb = AppConst.WEB_ADDRESS_CHONGLAO;
                    link = linkWeb + String.format("/activate/recovery/%s", tokenPasswordEntity.getToken());
                    appName = AppConst.APP_CL_NAME;
                    contactMail = AppConst.CONTACT_CL_EMAIL;
                    break;
                }
                default: {
                    linkWeb = AppConst.WEB_ADDRESS;
                    link = linkWeb + String.format("/activate/recovery/%s", tokenPasswordEntity.getToken());
                    appName = AppConst.APP_BVP_NAME;
                    contactMail = AppConst.CONTACT_BVP_EMAIL;
                }
            }
            subject = format("Thông báo tài khoản - Từ ứng dụng %s", appName);
            save(new EmailEntity(email, subject, EmailUtils.getBodyResetPassword(entity.getName(), link, appName, contactMail)));
            // send email

            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "get token password info password", notes = "")
    @GetMapping(path = "/get-token-password-info/{token}")
    public ResponseEntity<?> getTokenPasswordInfo(
            @PathVariable("token") String token
    ) {
        try {
            init();
            TokenPasswordEntity tokenPasswordEntity = tokenPasswordRepository.findFirstByToken(token);
            return response(ok(Arrays.asList("token", "user"), Arrays.asList(tokenPasswordEntity.getToken(), tokenPasswordEntity.getUser())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update password", notes = "{\"password\":\"asasa\",\"token\":\"sassd\"}")
    @PutMapping(path = "/update-password")
    public ResponseEntity<?> updatePassword(
            @RequestBody Object object
    ) {
        try {
            init(object);
            String password = getString("password");
            String token = getString("token");
            TokenPasswordEntity tokenPasswordEntity = tokenPasswordRepository.findFirstByToken(token);
            if (tokenPasswordEntity != null && tokenPasswordEntity.getExpirationDate().compareTo(LocalDateTime.now()) < 0) {
                throw getException(1, "Token het han");
            }
            if (tokenPasswordEntity != null) {
                UserEntity entity = tokenPasswordEntity.getUser();
                if (entity == null) {
                    throw getException(1, "User null");
                }
                entity.setPassword(password);
                tokenPasswordEntity.setExpirationDate(LocalDateTime.now());
                save(entity);
                save(tokenPasswordEntity);
                return response(ok(getMapData(entity)));
            }
            throw getException(2, "Token nulé");
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Set hight light cho doctor, hien thi tren trang chủ
     *
     * @param object {"isHighlight":1}
     * @return
     */
    @ApiOperation(value = "Update high light doctor", notes = "{\"isHighlight\":1}")
    @PutMapping(path = "/set-high-light/{id}")
    public ResponseEntity<?> approval(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer isHighlight = getInteger("isHighlight");
            UserEntity entity = getUser(id);
            entity.setIsHighlight(isHighlight);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Them moi hoi vien cua Hoi Lao
     * byAdmin = <br>
     * 0 - dang ky tk moi<br>
     * 1 - Admin tao
     * <p>
     * existedUser: trang thai ton tai cua nguoi dung, neu da ton tai thi update thong tin vao tk cu
     *
     * @param object
     * @return
     */
    @ApiOperation(value = "Them moi hoi vien cua Hoi Lao", notes = "{}")
    @PostMapping(path = "/create-lao-member")
    public ResponseEntity<?> createLaoMember(@RequestBody Object object) {
        try {
            init(object);
            Integer existedUser = getInteger("existedUser", 0);
            UserEntity userEntity = getObject("user", UserEntity.class);
            if (!StrUtils.isNullOrWhiteSpace(userEntity.getEmail()) && existedUser == 0) {
                if (userRepository.findByEmail(userEntity.getEmail()) != null) {
                    throw getException(2, "email da ton tai");
                }
            }
            validatedMemberInfo(userEntity);

            Long provinceId = getLong("provinceId");
            Integer byAdmin = getInteger("byAdmin");
            if (Objects.equals(1, byAdmin)) {
                // khi admin thao tac, kiem tra voi email nay
                UserEntity oldUser = userRepository.findByEmail(userEntity.getEmail());
                if (oldUser == null) {
                    // user chua ton tai
                    userEntity.setVerifiedMemberLao(1);
                    String nickname = createUsername(userEntity.getName());
                    userEntity.setUsername(nickname);
                    String password = RandomUtils.getRandomPasswordNumber();
                    userEntity.setPassword(StrUtils.toMD5(password));
                    userEntity.setRole(UserType.USER.getValue() | UserType.MEMBER_HOILAO.getValue());
                    if (provinceId != null) {
                        userEntity.setProvince(getProvince(provinceId));
                    }
                    save(userEntity);

                    String randomPassword = RandomUtils.getRandomPasswordNumber();
                    String encodedPassword = StrUtils.toMD5(randomPassword);
                    userEntity.setPassword(encodedPassword);
                    String link = AppConst.WEB_ADDRESS_HOILAO + "/dang-nhap";
                    String subject = format("Thông báo tài khoản Hội viên Hội Lao");
                    save(new EmailEntity(userEntity.getEmail(), subject, EmailUtils.getBodySendAccountInfoHoiLao(userEntity.getUsername(), link, userEntity.getEmail(), randomPassword)));
                    return response(ok(Arrays.asList("user", "password"), Arrays.asList(userEntity, password)));

                } else {
                    // user da ton tai, update thong tin
                    oldUser.setName(userEntity.getName());
                    oldUser.setDob(userEntity.getDob());
                    oldUser.setTitle(userEntity.getTitle());
                    oldUser.setOrg(userEntity.getOrg());
                    oldUser.setPhone(userEntity.getPhone());
                    oldUser.setDegree(userEntity.getDegree());
                    if (provinceId != null) {
                        oldUser.setProvince(getProvince(provinceId));
                    }
                    oldUser.setRole(oldUser.getRole() | UserType.MEMBER_HOILAO.getValue());
                    oldUser.setVerifiedMemberLao(1);
                    save(oldUser);
                    return response(ok(Arrays.asList("user"), Arrays.asList(oldUser)));
                }
            } else {
                // nguoi dung tu dang ky
                if (Objects.equals(0, existedUser)) {
                    // Danh dau nguoi dung tu dang ky thanh vien hay admin tao
                    UserEntity oldUser = userRepository.findByEmail(userEntity.getEmail());
                    if (oldUser != null) {
                        throw getException(2, "email da ton tai");
                    }
                    String nickname = createUsername(userEntity.getName());
                    userEntity.setUsername(nickname);
                    String password = RandomUtils.getRandomPasswordNumber();
                    userEntity.setPassword(StrUtils.toMD5(password));
                    userEntity.setRole(UserType.USER.getValue() | UserType.MEMBER_HOILAO.getValue());
                    if (provinceId != null) {
                        userEntity.setProvince(getProvince(provinceId));
                    }
                    save(userEntity);

                    String link = AppConst.WEB_ADDRESS_HOILAO + "/dang-nhap";
                    String subject = format("Thông báo tài khoản Hội viên Hội Lao");
                    save(new EmailEntity(userEntity.getEmail(), subject, EmailUtils.getBodySendAccountInfoHoiLao(userEntity.getUsername(),
                            link, userEntity.getEmail(), password)));
                    return response(ok(Arrays.asList("user", "password"), Arrays.asList(userEntity, password)));
                } else {
                    UserEntity user = getCurrentUser();
                    user.setName(userEntity.getName());
                    user.setDob(userEntity.getDob());
                    user.setTitle(userEntity.getTitle());
                    user.setOrg(userEntity.getOrg());
                    user.setPhone(userEntity.getPhone());
                    user.setDegree(userEntity.getDegree());
                    if (provinceId != null) {
                        user.setProvince(getProvince(provinceId));
                    }
                    user.setRole(user.getRole() | UserType.MEMBER_HOILAO.getValue());
                    save(user);
                    return response(ok(Arrays.asList("user"), Arrays.asList(user)));
                }
            }
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    private boolean validatedMemberInfo(UserEntity userEntity) throws CustomException {
        return true;
    }

    /**
     * Duyet hay huy duyet 1 thanh vien hoi lao
     *
     * @param userId uid cua nguoi muon duyet/huy
     * @param object {"verifiedMemberLao":1} 1 duyet, 0 huy
     * @return {"code":0,"message":"no message","data":{"status":1}}
     */
    @ApiOperation(value = "Them moi hoi vien cua Hoi Lao", notes = "{}")
    @PutMapping(path = "verify-lao-member/{userId}")
    public ResponseEntity<?> verifyLaoMember(
            @PathVariable("userId") long userId,
            @RequestBody Object object) {
        try {
            init(object);
            Integer verifiedMemberLao = getInteger("verifiedMemberLao");
            UserEntity userEntity = getUser(userId);
            userEntity.setVerifiedMemberLao(verifiedMemberLao);
            save(userEntity);
            return response(ok(USER, userEntity));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Admin Chuyen trang thai thanh vien da nop hoi phi
     *
     * @param userId chuyen trang thai cua thanh vien "da thanh toan"
     * @param object {"user":{}}
     * @return {"code":0,"message":"no message","user":{}}
     */
    @ApiOperation(value = "Them moi hoi vien cua Hoi Lao", notes = "{}")
    @PutMapping(path = "update-lao-member/{userId}")
    public ResponseEntity<?> updateLaoMember(
            @PathVariable("userId") long userId,
            @RequestBody Object object) {
        try {
            init(object);
            UserEntity entity = getUser(userId);
            Long provinceId = getLong("provinceId");
            UserEntity data = getObject("user", UserEntity.class);

            if (!Objects.equals(data.getEmail(), entity.getEmail())) {
                // co thay doi email
                if (!StrUtils.isNullOrWhiteSpace(data.getEmail())) {
                    UserEntity checkUser = userRepository.findByEmail(data.getEmail());
                    if ((checkUser != null) && (!checkUser.getEmail().equals(entity.getEmail()))) {
                        throw getException(1, " Email nay da ton tai");
                    }
                }
            }
            entity.setName(data.getName());
            entity.setDob(data.getDob());
            entity.setEmail(data.getEmail());
            entity.setOrg(data.getOrg());
            entity.setTitle(data.getTitle());
            entity.setGender(data.getGender());
            entity.setPhone(data.getPhone());
            entity.setDegree(data.getDegree());
            if (provinceId != null) {
                entity.setProvince(getProvince(provinceId));
            }
            entity.setMemberCode(data.getMemberCode());
            save(entity);
            return response(ok(USER, entity));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Lay thong tin chi tiet cua nguoi dung theo memberCode
     *
     * @param memberCode lay theo member code cua hoi vien
     * @return {"code":0,"message":"no message","user":{}}
     */
    @ApiOperation(value = "Them moi hoi vien cua Hoi Lao", notes = "{}")
    @GetMapping(path = "get-lao-member-by-code/{memberCode}")
    public ResponseEntity<?> getByLaoMemberCode(
            @PathVariable("memberCode") String memberCode) {
        try {
            init();
            UserEntity entity = userRepository.findByMemberCode(memberCode);
            if (entity == null) {
                throw getException(1, "khong tim thay user voi code nay");
            }
            return response(ok(USER, entity));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Admin Chuyen trang thai thanh vien da nop hoi phi
     *
     * @param userId chuyen trang thai cua thanh vien "da thanh toan"
     * @param object {"startActive":12345, "endActive":456789}
     * @return {"code":0,"message":"no message","data":{"status":1}}
     */
    @ApiOperation(value = "Them moi hoi vien cua Hoi Lao", notes = "{}")
    @PutMapping(path = "pay-fees/{userId}")
    public ResponseEntity<?> payFees(
            @PathVariable("userId") long userId,
            @RequestBody Object object) {
        try {
            init(object);
            UserEntity entity = getUser(userId);
            LocalDate startActive = getDate(UserField.START_ACTIVE);
            LocalDate endActive = getDate(UserField.END_ACTIVE);
            entity.setStartActive(startActive);
            entity.setEndActive(endActive);
            entity.setVerifiedMemberLao(1);
            save(entity);
            return response(ok(USER, entity));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    @ApiOperation(value = "Them moi hoi vien cua Hoi Lao", notes = "{}")
    @PostMapping(path = "import-lao-member")
    public ResponseEntity<?> importLaoMember(@RequestBody Object object) {
        try {
            init(object);
            String fileName = getString("fileName");
            fileName = fileName.replace("/new_files/", "");
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String file = resource.getFile().getAbsolutePath();

            List<Integer> datas = importFile(file);

            return response(ok(Arrays.asList("total", "update", "insert"), Arrays.asList(datas.get(0), datas.get(1), datas.get(2))));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    public List<Integer> importFile(String file) {
        int total = 0;
        int update = 0;
        int insert = 0;
        try {
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = WorkbookFactory.create(new File(file));

            // Getting the Sheet at index zero
            Sheet sheet = workbook.getSheetAt(0);
            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            Iterator<Row> rowIterator = sheet.rowIterator();

            Iterable<ProvinceEntity> provinces = provinceRepository.findAll();
            Map<String, Long> mapProvinces = new HashMap<>();
            for (ProvinceEntity entity : provinces) {
                mapProvinces.put(entity.getName(), entity.getId());
            }
            // bo qua line dau tien
            boolean ignoreFirstRow = true;
            boolean finish = false;
            while (rowIterator.hasNext() && !finish) {
                try {
                    Row row = rowIterator.next();
                    if (ignoreFirstRow) {
                        ignoreFirstRow = false;
                        continue;
                    }

                    // Now let's iterate over the columns of the current row
                    Iterator<Cell> cellIterator = row.cellIterator();
                    int currentCol = 0;
                    UserEntity entity = new UserEntity();
                    if (!cellIterator.hasNext()) {
                        finish = true;
                        break;
                    }
                    while (cellIterator.hasNext() && !finish) {
                        Cell cell = cellIterator.next();
                        String cellValue = dataFormatter.formatCellValue(cell).trim();

                        switch (currentCol) {
                            // name
                            case 0:
                                if (StrUtils.isNullOrWhiteSpace(cellValue)) {
                                    finish = true;
                                    continue;
                                }
                                entity.setName(cellValue);
                                break;
                            // ngay sinh
                            case 1:
                                LocalDate dob = LocalDate.parse(cellValue, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                entity.setDob(dob);
                                break;
                            // dia chi email
                            case 2:
                                entity.setEmail(cellValue);
                                break;
                            // don vi cong tac
                            case 3:
                                entity.setOrg(cellValue);
                                break;
                            // chuc vu
                            case 4:
                                entity.setTitle(cellValue);
                                break;
                            case 5:
                                if (Objects.equals("Nam", cellValue)) {
                                    entity.setGender(GenderType.MALE.getType());
                                }
                                break;
                            case 6:
                                entity.setPhone(cellValue);
                                break;
                            case 7:
                                entity.setDegree(cellValue);
                                break;
                            case 8:
                                if (!StrUtils.isNullOrWhiteSpace(cellValue)) {
                                    if (mapProvinces.containsKey(cellValue)) {
                                        Long provinceId = mapProvinces.get(cellValue);
                                        entity.setProvince(getProvince(provinceId));
                                    }
                                }
                                break;
                            case 9:
                                entity.setMemberCode(cellValue);
                                break;
                        }
                        currentCol++;
                    }
                    if (finish) {
                        break;
                    }
                    total++;
                    entity.setVerifiedMemberLao(1);
                    String nickname = createUsername(entity.getName());
                    entity.setUsername(nickname);
                    boolean sendEmail = false;
                    if (StrUtils.isNullOrWhiteSpace(entity.getEmail())) {
                        entity.setEmail(nickname + "@benhvienphoi.com");

                    } else {
                        UserEntity checkEntity = userRepository.findByEmail(entity.getEmail());
                        // nguoi dung da ton tai
                        if (checkEntity != null) {
                            // kiem tra xem co quyen hoi lao chua
                            if ((checkEntity.getRole() & UserType.MEMBER_HOILAO.getValue()) != UserType.MEMBER_HOILAO.getValue()) {
                                checkEntity.setRole(checkEntity.getRole() | UserType.MEMBER_HOILAO.getValue());
                                save(checkEntity);
                                update++;
                            }
                            continue;
                        } else {
                            sendEmail = true;
                        }
                    }
                    insert++;
                    String password = RandomUtils.getRandomPasswordNumber();
                    entity.setPassword(StrUtils.toMD5(password));
                    entity.setRole(UserType.USER.getValue() | UserType.MEMBER_HOILAO.getValue());
                    save(entity);
                    if (sendEmail) {
                        try {
                            String subject = "Thông tin tài khoản cho email " + entity.getEmail();
                            // TODO: 11/12/18 send email
                            String linkWeb = AppConst.WEB_ADDRESS_HOILAO;
                            String link = linkWeb + "/dang-nhap";
                            save(new EmailEntity(entity.getEmail(), subject, EmailUtils.getBodySendAccountInfoHoiLao(entity.getUsername(),
                                    link, entity.getEmail(), password)));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            workbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Arrays.asList(total, update, insert);
    }

    @ApiOperation(value = "update email", notes = "{\"email\":\"ledangtuanbk@gmail.com\",\"password\":\"ffff\"}")
    @PutMapping(path = "/update-email/{id}")
    public ResponseEntity<?> updateEmail(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            UserEntity entity = getUser(id);
            String pass = getString("password");
            if (!pass.equals(entity.getPassword())) {
                throw getException(1, "Password sai!");
            }
            String email = getString("email");
            UserEntity checkUser = userRepository.findFirstByEmail(email);
            if (checkUser != null) {
                if (checkUser.getId() == entity.getId()) {
                    throw getException(1, "Email dang la cua user nay");
                } else {
                    throw getException(2, "Ton tai tai khoan chua email nay");
                }
            }
            // chua co user nao co email nay, tao email token
            EmailTokenEntity tokenEntity = new EmailTokenEntity();
            tokenEntity.setEmail(email);
            tokenEntity.setToken(RandomUtils.getRandomId());
            tokenEntity.setUser(entity);
            save(tokenEntity);
            Integer webTypeValue = getInteger("webType");
            WebType webType = WebType.fromValue(webTypeValue);
            String linkWeb = "";
            String appName = "";
            String contactEmail = "";
            switch (webType) {
                case HOILAO: {
                    linkWeb = AppConst.WEB_ADDRESS_HOILAO;
                    appName = AppConst.APP_HL_NAME;
                    contactEmail = AppConst.CONTACT_HL_EMAIL;
                    break;
                }
                case CHONG_LAO: {
                    linkWeb = AppConst.WEB_ADDRESS_CHONGLAO;
                    appName = AppConst.APP_CL_NAME;
                    contactEmail = AppConst.CONTACT_CL_EMAIL;
                    break;
                }
                default: {
                    linkWeb = AppConst.WEB_ADDRESS;
                    appName = AppConst.APP_BVP_NAME;
                    contactEmail = AppConst.CONTACT_BVP_EMAIL;
                }
            }

            String link = linkWeb + "/user/confirm-email/" + tokenEntity.getToken();
            String emailBody = EmailUtils.getBodyVerifyEmail(entity.getName(), link, appName, contactEmail);
            save(new EmailEntity(email, "Cập nhập thông tin Email", emailBody));
            return response(ok(USER, entity));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "", notes = "{}")
    @PutMapping(path = "verify-email/{token}")
    public ResponseEntity<?> verifyEmail(
            @PathVariable("token") String token) {
        try {
            init();
            EmailTokenEntity tokenEntity = emaiTokenRepository.findByToken(token);
            if (tokenEntity == null) {
                throw getException(4, "Token incorrect");
            }
            // Token da duoc su dung

            if (tokenEntity.getUser() == null) {
                throw getException(3, "user not existed");
            }

            tokenEntity.getUser().setEmail(tokenEntity.getEmail());
            save(tokenEntity);
            return response(ok(USER, tokenEntity.getUser()));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            UserEntity entity = getObject("user", UserEntity.class);
            Long provinceId = getLong("provinceId");
            if (provinceId != null) {
                ProvinceEntity province = provinceRepository.findFirstByHisProvinceId(provinceId);
                entity.setProvince(province);
            }
            LocalDateTime createdDate = entity.getCreatedDate();
            save(entity);
            if (createdDate != null) {
                entity.setCreatedDate(createdDate);
                save(entity);
            }
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-field", notes = "")
    @PostMapping(path = "/update-field")
    public ResponseEntity<?> updateField(@RequestBody Object object) {
        try {
            init(object);
            String userUid = getString("uid");
            String introduction = getString("introduction");
            UserEntity user = userRepository.findFirstByUid(userUid);
            if (user != null) {
                user.setIntroduction(introduction);
            }
            save(user);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-department", notes = "")
    @PostMapping(path = "/update-department")
    public ResponseEntity<?> updateFirstComment(@RequestBody Object object) {
        try {
            init(object);
            String userUid = getString("userUid");
            String departmentUid = getString("departmentUid");
            UserEntity user = userRepository.findFirstByUid(userUid);
            DepartmentEntity department = departmentRepository.findFirstByUid(departmentUid);
            if (user == null || department == null) {
                return response(error(new Exception("post null or comment null")));
            }
            user.setDepartment(department);
            save(user);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

}
