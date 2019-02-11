package com.isofh.service.controller;

import com.isofh.service.constant.DefaultConst;
import com.isofh.service.constant.field.CommonField;
import com.isofh.service.model.*;
import com.isofh.service.utils.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(path = "/user-conference")
public class UserConferenceController extends BaseController {

    public UserConferenceController() {
        super();
    }

    private List<Map<String, Object>> getMapDatas(Iterable<UserConferenceEntity> entities) {
        List<Map<String, Object>> mapResults = new ArrayList<>();
        for (UserConferenceEntity entity : entities) {
            mapResults.add(getMapData(entity));
        }
        return mapResults;
    }

    private Map<String, Object> getMapData(UserConferenceEntity entity) {
        Map<String, Object> mapData = new HashMap<>();
        mapData.put(USER_CONFERENCE, entity);
        mapData.put(USER, entity.getUser());
        mapData.put(CONFERENCE, entity.getConference());
        mapData.put(COURSE_LAO, entity.getCourseLaos());
        return mapData;
    }

    @ApiOperation(value = "Create a user conference, byAdmin: khong danh thi bang 1, day thi lay theo gia tri day ", notes = "{\"userConference\":{\"company\":\"Khoa khám bệnh\",\"address\":\"dfdggtgrg\"},\"userId\":1,\"conferenceId\":1}")
    @PostMapping(path = "/create")
    public ResponseEntity<?> create(@RequestBody Object object) {
        try {
            init(object);
            Long userId = getLong("userId");
            Integer byAdmin = getInteger("byAdmin", 1);
            UserEntity userEntity = getUser(userId);
            if (userEntity == null) {
                throw getException(1, "thieu thong tin nguoi dung");
            }

            Long conferenceId = getLong("conferenceId");
            ConferenceEntity conferenceEntity = getConference(conferenceId);
            if (conferenceEntity == null) {
                throw getException(1, "thieu thong tin hoi nghi");
            }
            String str= conferenceEntity.getAdminIds();
           String str1=str.substring(0, str.length()-1) +"," +userEntity.getId()+"]";
            conferenceEntity.setAdminIds(str1);
            save(conferenceEntity);
            // kiem tra xem nguoi dung da tham du khoa hoc chua
            UserConferenceEntity checkUser = userConferenceRepository.getByUserConference(userId, conferenceId);
            if(checkUser !=null){
                throw getException(3, "nguoi dung nay da dang ky khoa hoc");
            }


            UserConferenceEntity userConferenceEntity = getObject(USER_CONFERENCE, UserConferenceEntity.class);
            if (!StrUtils.isNullOrWhiteSpace(userConferenceEntity.getEmail())) {
                if (userConferenceRepository.findByEmail(userConferenceEntity.getEmail(), conferenceId) != null) {
                    throw getException(2, "email da ton tai");
                }
            }
            userConferenceEntity.setUser(userEntity);
            if (conferenceEntity.getUserIndex() == null) {
                conferenceEntity.setUserIndex(1l);
            } else {
                conferenceEntity.setUserIndex(conferenceEntity.getUserIndex() + 1);
            }
            save(conferenceEntity);
            userConferenceEntity.setConference(conferenceEntity);
            userConferenceEntity.setApproved(byAdmin == 1 ? 1 : 0);
            userConferenceEntity.setCode(String.format("%06d", conferenceEntity.getUserIndex()));
            Long sponsorId = getLong("sponsorId");
            if (sponsorId != null) {
                SponsorEntity sponsorEntity = getSponsor(sponsorId);
                userConferenceEntity.setSponsor(sponsorEntity);
            }
            Long[] courseLaoIds = getObject("courseLaoIds", Long[].class);
            if (!ArrayUtils.isNullOrEmpty(courseLaoIds)) {
                for (Long courseLaoId : courseLaoIds) {
                    CourseLaoEntity courseLaoEntity = getCourseLao(courseLaoId);
                    userConferenceEntity.getCourseLaos().add(courseLaoEntity);
                }
            }

            save(userConferenceEntity);
            if (Objects.equals(userConferenceEntity.getIsAdmin(), 1)) {
                conferenceEntity = getConference(conferenceId);
                String adminIds = conferenceEntity.getAdminIds();
                List<Long> listAdminIds = new ArrayList<>();
                if(!StrUtils.isNullOrWhiteSpace(adminIds)){
                    Long[] arrAdmins = GsonUtils.toObject(adminIds, Long[].class);
                    listAdminIds = Arrays.asList(arrAdmins);
                }
                listAdminIds.add(userId);
                conferenceEntity.setAdminIds(GsonUtils.toStringCompact(listAdminIds));
                save(conferenceEntity);
            }
            return response(ok(getMapData(userConferenceEntity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Search a user conference", notes = "")
    @GetMapping(path = "/search")
    public ResponseEntity<?> search(
            @RequestParam(value = CommonField.PAGE, required = false, defaultValue = DefaultConst.PAGE) Integer page,
            @RequestParam(value = CommonField.SIZE, required = false, defaultValue = DefaultConst.SIZE) Integer size,
            @RequestParam(value = "name", required = false, defaultValue = DefaultConst.STRING) String name,
            @RequestParam(value = "degree", required = false, defaultValue = DefaultConst.STRING) String degree,
            @RequestParam(value = "phone", required = false, defaultValue = DefaultConst.STRING) String phone,
            @RequestParam(value = "memberVatld", required = false, defaultValue = DefaultConst.NUMBER) Integer memberVatld,
            @RequestParam(value = "username", required = false, defaultValue = DefaultConst.STRING) String username,
            @RequestParam(value = "fee", required = false, defaultValue = DefaultConst.NUMBER) Long fee,
            @RequestParam(value = "role", required = false, defaultValue = DefaultConst.NUMBER) Integer role,
            @RequestParam(value = "approved", required = false, defaultValue = DefaultConst.NUMBER) Integer approved,
            @RequestParam(value = "code", required = false, defaultValue = DefaultConst.STRING) String code,
            @RequestParam(value = "conferenceId", required = false, defaultValue = DefaultConst.NUMBER) Integer conferenceId,
            @RequestParam(value = "email", required = false, defaultValue = DefaultConst.STRING) String email,
            @RequestParam(value = "company", required = false, defaultValue = DefaultConst.STRING) String company
    ) {
        try {
            init();
            Page<UserConferenceEntity> results = userConferenceRepository.search(name, degree, phone, memberVatld, username, fee, role, approved, code, conferenceId,email, company, getDefaultPage(page, size));
            return response(ok(results.getTotalElements(), getMapDatas(results.getContent())));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Update user conference", notes = "{}")
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<?> update(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            UserConferenceEntity data = getObject(USER_CONFERENCE, UserConferenceEntity.class);
            UserConferenceEntity entity = getUserConference(id);

            entity.setName(data.getName());
            entity.setMemberCode(data.getMemberCode());
            entity.setAvatar(data.getAvatar());
            entity.setDob(data.getDob());
            entity.setGender(data.getGender());
            entity.setDegree(data.getDegree());
            entity.setTitles(data.getTitles());
            entity.setDepartment(data.getDepartment());
            entity.setCompany(data.getCompany());
            entity.setAddress(data.getAddress());
            entity.setEmail(data.getEmail());
            entity.setPhone(data.getPhone());
            entity.setProfile(data.getProfile());
            entity.setRole(data.getRole());
            entity.setMemberVatld(data.getMemberVatld());
            entity.setType(data.getType());
            entity.setFee(data.getFee());


            if(!Objects.equals(entity.getIsAdmin(), data.getIsAdmin())) {
                if (data.getIsAdmin() == 0) {
                    ConferenceEntity conferenceEntity = entity.getConference();
                    String str = conferenceEntity.getAdminIds();
                    String thu=String.valueOf(entity.getUser().getId());
                    String h=str.replace(thu, "");
                    String h1=h.replace(",,", ",");
                    char a = h1.charAt(h1.length() - 2);
                    String s = Character.toString(a);
                    if (s.equals(",")) {
                        String h2=h1.substring(0, h1.length() - 2)+"]";
                        conferenceEntity.setAdminIds(h2);
                    } else
                        conferenceEntity.setAdminIds(h1);
                    save(conferenceEntity);
                }

                if (data.getIsAdmin() == 1) {
                    ConferenceEntity conferenceEntity = entity.getConference();
                    String str = conferenceEntity.getAdminIds();
                    String str1=str.substring(0, str.length()-1) +"," +String.valueOf(entity.getUser().getId()) +"]";
                    conferenceEntity.setAdminIds(str1);
                    conferenceEntity.setAdminIds(str1);
                    save(conferenceEntity);
                }
            }
            entity.getCourseLaos().clear();
            Long[] courseLaoIds = getObject("courseLaoIds", Long[].class);
            if (!ArrayUtils.isNullOrEmpty(courseLaoIds)) {
                for (Long courseLaoId : courseLaoIds) {
                    CourseLaoEntity courseLaoEntity = getCourseLao(courseLaoId);
                    entity.getCourseLaos().add(courseLaoEntity);
                }
            }
            if(Objects.equals(data.getIsAdmin(),1) || Objects.equals(data.getIsAdmin(), 0)){
                entity.setIsAdmin(data.getIsAdmin());
            }
//            if (Objects.equals(data.getIsAdmin(), 1)) {
//                ConferenceEntity conference = entity.getConference();
//                UserEntity user = entity.getUser();
//                if (user != null && conference != null) {
//                    //conference.getAdminIds().add(user.getUid());
//                    save(conference);
//                }
//            }
            save(entity);
            return response(ok(getMapData(entity)));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "Delete user conference", notes = "")
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            init();
            userConferenceRepository.deleteById(id);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    /**
     * Duyet or huy thanh vien tham du hoi nghi
     *
     * @param object {"approval":1}
     * @return
     */
    @ApiOperation(value = "Update approval user conference", notes = "{\"approval\":1}")
    @PutMapping(path = "/approval/{id}")
    public ResponseEntity<?> approval(@RequestBody Object object, @PathVariable("id") long id) {
        try {
            init(object);
            Integer approval = getInteger("approval");
            UserConferenceEntity entity = getUserConference(id);
            if (approval == null) {
                throw getException(2, "Khong co tham so approval");
            }
            entity.setApproved(approval);
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }


    @ApiOperation(value = "sync", notes = "")
    @PostMapping(path = "/sync")
    public ResponseEntity<?> sync(@RequestBody Object object) {
        try {
            init(object);
            UserConferenceEntity entity = getObject(USER_CONFERENCE, UserConferenceEntity.class);
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

    @ApiOperation(value = "update-conference", notes = "")
    @PostMapping(path = "/update-conference")
    public ResponseEntity<?> oupdateConference(@RequestBody Object object) {
        try {
            init(object);
            String conferenceUid = getString("conferenceUid");
            String userConferenceUid = getString("userConferenceUid");
            ConferenceEntity conference = conferenceRepository.findFirstByUid(conferenceUid);
            UserConferenceEntity userConference = userConferenceRepository.findFirstByUid(userConferenceUid);
            if (conference == null || userConference == null) {
                return response(error(new Exception("conference null or userConference null")));
            }
            userConference.setConference(conference);
            save(userConference);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-user", notes = "")
    @PostMapping(path = "/update-user")
    public ResponseEntity<?> updateUser(@RequestBody Object object) {
        try {
            init(object);
            String userUid = getString("userUid");
            String userConferenceUid = getString("userConferenceUid");
            UserEntity user = userRepository.findFirstByUid(userUid);
            UserConferenceEntity userConference = userConferenceRepository.findFirstByUid(userConferenceUid);
            if (user == null || userConference == null) {
                return response(error(new Exception("conference null or userConference null")));
            }
            userConference.setUser(user);
            save(userConference);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "update-field", notes = "")
    @PostMapping(path = "/update-field")
    public ResponseEntity<?> updateField(@RequestBody Object object) {
        try {
            init(object);
            String uid = getString("uid");
            String code = getString("code");
            UserConferenceEntity entity = userConferenceRepository.findFirstByUid(uid);
            if (entity != null) {
                entity.setCode(code);
            }
            save(entity);
            return response(ok());
        } catch (Exception ex) {
            return response(error(ex));
        }
    }

    @ApiOperation(value = "", notes = "{\"file\": \"/file/view/import-csyt-len-isofhcare_9e3ceba2_7f0c_445f_97c9_6d1646f0f19b.xlsx\", \"conferenceId\":5}")
    @PostMapping(path = "/importMember")
    public ResponseEntity<?> importMember(@RequestBody Object object) {
        try {
            init(object);
            String fileName = getString("file");
            Long conferenceId = getLong("conferenceId");
            fileName = fileName.replace("/file/view/", "");
            Resource resource = fileStorageService.loadFileAsResource(fileName);
            String file = resource.getFile().getAbsolutePath();

            List<Integer> datas = importFile(file,conferenceId);

            return response(ok(Arrays.asList("total", "update", "insert"), Arrays.asList(datas.get(0), datas.get(1), datas.get(2))));
        } catch (Exception ex) {
            return response(error(ex));
        }
    }
    public List<Integer> importFile(String file, Long conferenceId) {
        int total = 0;
        int update = 0;
        int insert = 0;
        try {
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            Workbook workbook = WorkbookFactory.create(new File(file));

            // Getting the Sheet at index zero

            for(int countSheet=0; countSheet<1; countSheet++){
                Sheet sheet = workbook.getSheetAt(countSheet);
                // Create a DataFormatter to format and get each cell's value as String
                DataFormatter dataFormatter = new DataFormatter();

                Iterator<Row> rowIterator = sheet.rowIterator();
//            Iterable<StaffEntity> staffs = staffRepository.findAll();
//            Map<String, Long> mapUsers = new HashMap<>();
//            for (StaffEntity entity : staffs) {
//                mapUsers.put(entity.getCode(), entity.getId());
//            }


                // bo qua line dau tien
                int ignoreRow = 1;
                boolean finish = false;
                int count = 0;
                while (rowIterator.hasNext() && !finish) {
                    try {
                        System.out.println(count++);
                        Row row = rowIterator.next();
                        if (ignoreRow > 0) {
                            ignoreRow--;
                            continue;
                        }

                        // Now let's iterate over the columns of the current row
                        Iterator<Cell> cellIterator = row.cellIterator();
                        int currentCol = 0;
                        if (!cellIterator.hasNext()) {
                            finish = true;
                            break;
                        }
                        UserConferenceEntity entity = new UserConferenceEntity();
                        LocalDate dateValue = null;
                        while (cellIterator.hasNext() && !finish) {
                            Cell cell = cellIterator.next();
                            String cellValue = dataFormatter.formatCellValue(cell).trim();
                            if (dateValue != null) {
                                break;
                            }
                            switch (currentCol) {
                                // name
                                case 0:
                                    if(cellValue!= null) {
                                        String codeMember = cellValue;
                                        entity.setMemberCode(codeMember);
                                    }
                                    break;
                                case 1:
                                    if(cellValue!= null) {
                                        String nameMember = cellValue;
                                        entity.setName(nameMember);
                                        ConferenceEntity conferenceEntity = getConference(conferenceId);
                                        entity.setCode(String.format("%06d", conferenceEntity.getUserIndex()));
                                        entity.setConference(conferenceEntity);
                                    }
                                    break;
                                case 2:
                                    if(cellValue!= null) {
                                        String dobValue = cellValue;
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                                        //convert String to LocalDate
                                        LocalDate localDate = LocalDate.parse(dobValue, formatter);
                                        entity.setDob(localDate);
                                    }
                                    break;
                                case 3:
                                    if(cellValue!= null) {
                                    String degree = cellValue;
                                    entity.setDegree(degree);
                                    }
                                    break;
                                // chuc vu
                                case 4:
                                    if(cellValue!= null) {
                                        String company = cellValue;
                                        entity.setCompany(company);
                                    }
                                    break;
                                case 5:
                                    if(cellValue!= null) {
                                        String phone = cellValue;
                                        entity.setPhone(phone);
                                    }
                                    break;
                                case 6:
                                    if(cellValue!= null) {
                                        String email = cellValue;
                                        UserEntity userEntity= userRepository.findByEmail(email);
                                        if(userEntity!= null){
                                            entity.setUser(userEntity);
                                        } else{
                                            UserEntity userEntity1= new UserEntity();
                                            String username = createUsername(entity.getName());
                                            userEntity1.setUsername(username);
                                            String randomPassword = RandomUtils.getRandomPasswordNumber();
                                            String encodedPassword = StrUtils.toMD5(randomPassword);
                                            userEntity1.setPassword(encodedPassword);
                                            userEntity1.setLoginToken(JWTokenUtils.generateToken(entity.getId()));
                                            save(userEntity1);
                                            entity.setUser(userEntity1);
                                        }
                                    }
                                    break;
                                case 7:
                                    if(cellValue!= null) {
                                        String role = cellValue;
                                        if(role.equals("Chủ tịch hội nghị") || role.equals("Chu tich hoi nghi") || role.equals("chủ tịch hội nghị")){
                                            entity.setRole(1);
                                        } else if(role.equals("Chủ tịch hội đồng khoa học") || role.equals("chủ tịch hội đồng khoa học") || role.equals("Chu tich hoi dong khoa hoc")){
                                            entity.setRole(2);
                                        } else if(role.equals("Hoi dong co van") || role.equals("Hội đồng cố vấn")){
                                            entity.setRole(4);
                                        } else if(role.equals("Ban chi dao") || role.equals("Ban chỉ đạo")){
                                            entity.setRole(8);
                                        } else if(role.equals("Chủ tọa") || role.equals("Chu toa")){
                                            entity.setRole(16);
                                        } else if(role.equals("Thư ký") || role.equals("Thu ky")){
                                            entity.setRole(32);
                                        } else if(role.equals("Báo cáo viên") || role.equals("Bao cao vien")){
                                            entity.setRole(64);
                                        } else if(role.equals("Ban tổ chức") || role.equals("Ban to chuc")){
                                            entity.setRole(128);
                                        } else if(role.equals("Đại biểu") || role.equals("Dai bieu")){
                                            entity.setRole(256);
                                        } else {
                                            entity.setRole(512);
                                        }
                                    }
                                    break;
                                case 8:
                                    if(cellValue!= null) {
                                        String fee = cellValue;
                                        long fee1 = Long.parseLong(fee);
                                        entity.setFee(fee1);
                                    }
                                    break;
                                case 9:
                                    break;
                                default: {
                                    break;
                                }
                            }
                            currentCol++;

                        }
                        save(entity);
                        insert++;
                        if (finish) {
                            break;
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            workbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Arrays.asList(total, update, insert);
    }

    private String createUsername(String name) {
        int index = 0;
        String nickName = StrUtils.convertToUnsignedLowerCase(name).replace(" ", "");
        while (userRepository.findFirstByUsername(nickName + (index == 0 ? "" : "" + index)) != null) {
            index++;
        }
        return nickName + (index == 0 ? "" : "" + index);
    }


}
