package com.isofh.service.service;

import com.isofh.service.model.UserConferenceEntity;
import com.isofh.service.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserConferenceRepository extends CrudRepository<UserConferenceEntity, Long> {

    @Query(value = "select n.* from tbl_user_conference n left join tbl_user u on n.col_user_id = u.id"
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_degree like concat('%',:degree,'%') or :degree = '') "
            + " and (n.col_phone like concat('%',:phone,'%') or :phone = '') "
            + " and (n.col_member_vatld =:memberVatld or :memberVatld= -1) "
            + " and (u.col_username like concat('%',:username,'%') or u.col_email like concat('%',:username,'%') or :username = '') "
            + " and (n.col_fee =:fee or :fee= -1) "
            + " and (n.col_role & :role != 0 or :role = '-1') "
            + " and (n.col_approved =:approved or :approved= -1) "
            + " and (n.col_code like concat('%',:code,'%') or :code = '') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId= -1) "
            + " and (n.col_email like concat('%',:email,'%') or :email = '') "
            + " and (n.col_company like concat('%',:company,'%') or :company = '') "
            , countQuery = "select count(n.id) from tbl_user_conference n left join tbl_user u on n.col_user_id = u.id"
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_degree like concat('%',:degree,'%') or :degree = '') "
            + " and (n.col_phone like concat('%',:phone,'%') or :phone = '') "
            + " and (n.col_member_vatld =:memberVatld or :memberVatld= -1) "
            + " and (u.col_username like concat('%',:username,'%') or u.col_email like concat('%',:username,'%') or :username = '') "
            + " and (n.col_fee =:fee or :fee= -1) "
            + " and (n.col_role & :role != 0 or :role = '-1') "
            + " and (n.col_approved =:approved or :approved= -1) "
            + " and (n.col_code like concat('%',:code,'%') or :code = '') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId= -1) "
            + " and (n.col_email like concat('%',:email,'%') or :email = '') "
            + " and (n.col_company like concat('%',:company,'%') or :company = '') "

            , nativeQuery = true)
    Page<UserConferenceEntity> search(
            @Param("name") String name,
            @Param("degree") String degree,
            @Param("phone") String phone,
            @Param("memberVatld") Integer memberVatld,
            @Param("username") String username,
            @Param("fee") Long fee,
            @Param("role") Integer role,
            @Param("approved") Integer approved,
            @Param("code") String code,
            @Param("conferenceId") Integer conferenceId,
            @Param("email") String email,
            @Param("company") String company,
            Pageable defaultPage);

    @Query(value = "select n.* from tbl_user_conference n" +
            " where n.col_user_id = :userId " +
            " and n.col_conference_id = :conferenceId limit 1 "
            , nativeQuery = true)
    UserConferenceEntity getByUserConference(
            @Param("userId") long userId,
            @Param("conferenceId") long conferenceId);

    UserConferenceEntity findFirstByUid(String userConferenceUid);

    @Query(value = "select n.* from tbl_user_conference n" +
            " where n.col_email = :email " +
            " and n.col_conference_id = :conferenceId limit 1"
            , nativeQuery = true)
    UserConferenceEntity findByEmail(
            @Param("email") String email,
            @Param("conferenceId") long conferenceId);

}
