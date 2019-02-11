package com.isofh.service.service;

import com.isofh.service.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query(value = "select n.* from tbl_user n" +
            " where true " +
            " and (n.col_username = :username or n.col_email = :email)" +
            " and n.col_deleted = 0 limit 1",
            nativeQuery = true)
    UserEntity findFirstByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    @Query(value = "select u.* from tbl_user u" +
            " where ((u.col_username = :usernameOrEmail or u.col_email = :usernameOrEmail) and u.col_password = :password )  limit 1"
            , nativeQuery = true)
    UserEntity login(
            @Param("usernameOrEmail") String usernameOrEmail,
            @Param("password") String password);

    @Query(value = "select n.* from tbl_user n" +
            " where n.col_email = :email limit 1"
            , nativeQuery = true)
    UserEntity findByEmail(@Param("email") String email);

    UserEntity findByGoogleId(String googleId);

    UserEntity findByFacebookId(String facebookId);

    /**
     * Tim kiem theo ten dang nhap
     *
     * @param username
     * @return
     */
    UserEntity findFirstByUsername(String username);


    @Query(value = "select n.* from tbl_user n" +
            " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_email like concat('%',:email,'%') or :email = '' ) "
            + " and (n.col_social_type =:socialType or :socialType = '-1') "
            + " and (n.col_username like concat('%',:username,'%') or :username = '') "
            + " and (n.col_role & :role != 0 or :role = '-1') "
            + " and (n.col_deleted =:deleted or :deleted = '-1') "
            + " and (n.col_block =:block or :block = '-1') "
            + " and (n.col_is_highlight =:isHighlight or :isHighlight = '-1') "
            + " and (n.col_index =:index or :index = '-1') "
            + " and (n.col_department_id =:departmentId or :departmentId = '-1') "
            + " and (n.col_gender =:gender or :gender = '-1') "
            + " and (n.col_member_code like concat('%',:memberCode,'%') or :memberCode = '') "
            + " and (n.col_province_id =:provinceId or :provinceId = '-1') "
            + " and (n.col_org like concat('%',:org,'%') or :org = '') "
            + " and (n.col_phone like concat('%',:phone,'%') or :phone = '') "
            + " and (n.col_verified_member_lao =:verifiedMemberLao or :verifiedMemberLao = '-1') "
            + " and (date(n.col_start_active) >= :startActive or :startActive = '1970-01-01') "
            + " and (date(n.col_end_active) <= :endActive or :endActive = '1970-01-01') "
            , countQuery = "select count(n.id) from tbl_user n" +
            " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_email like concat('%',:email,'%') or :email = '' ) "
            + " and (n.col_social_type =:socialType or :socialType = '-1') "
            + " and (n.col_username like concat('%',:username,'%') or :username = '') "
            + " and (n.col_role & :role != 0 or :role = '-1') "
            + " and (n.col_deleted =:deleted or :deleted = '-1') "
            + " and (n.col_block =:block or :block = '-1') "
            + " and (n.col_is_highlight =:isHighlight or :isHighlight = '-1') "
            + " and (n.col_index =:index or :index = '-1') "
            + " and (n.col_department_id =:departmentId or :departmentId = '-1') "
            + " and (n.col_gender =:gender or :gender = '-1') "
            + " and (n.col_member_code like concat('%',:memberCode,'%') or :memberCode = '') "
            + " and (n.col_province_id =:provinceId or :provinceId = '-1') "
            + " and (n.col_org like concat('%',:org,'%') or :org = '') "
            + " and (n.col_phone like concat('%',:phone,'%') or :phone = '') "
            + " and (n.col_verified_member_lao =:verifiedMemberLao or :verifiedMemberLao = '-1') "
            + " and (date(n.col_start_active) >= :startActive or :startActive = '1970-01-01') "
            + " and (date(n.col_end_active) <= :endActive or :endActive = '1970-01-01') "
            , nativeQuery = true)
    Page<UserEntity> search(
            @Param("name") String name,
            @Param("email") String email,
            @Param("socialType") Integer socialType,
            @Param("username") String username,
            @Param("role") Integer role,
            @Param("deleted") Integer deleted,
            @Param("block") Integer block,
            @Param("isHighlight") Integer isHighlight,
            @Param("index") Integer index,
            @Param("departmentId") Long departmentId,
            @Param("gender") Integer gender,
            @Param("memberCode") String memberCode,
            @Param("provinceId") Long provinceId,
            @Param("org") String org,
            @Param("phone") String phone,
            @Param("verifiedMemberLao") Integer verifiedMemberLao,
            @Param("startActive") LocalDate startActive,
            @Param("endActive") LocalDate endActive,
            Pageable defaultPage);

    @Query(value = "select n.* from tbl_user n" +
            " where n.col_member_code = :memberCode limit 1"
            , nativeQuery = true)
    UserEntity findByMemberCode(@Param("memberCode") String memberCode);

    UserEntity findFirstByEmail(String email);

    UserEntity findFirstByUid(String uid);

    @Query(nativeQuery = true,
            value = "select n.* from tbl_user n" +
                    " join tbl_user_course uc on n.id = uc.col_user_id" +
                    " join tbl_course_lao cl on cl.id = uc.col_course_lao_id" +
                    " where cl.id = :courseLaoId"
    )
    List<UserEntity> getByCourseLao(@Param("courseLaoId") Long courseLaoId);
}
