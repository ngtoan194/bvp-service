package com.isofh.service.service;

import com.isofh.service.model.OnlineExchangeQuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OnlineExchangeQuestionRepository extends CrudRepository<OnlineExchangeQuestionEntity, Long> {

//    @Query(value = "select n.* from tbl_online_exchange_question n left join tbl_user u on n.col_created_user_id = u.id"
//            + " where true "
//            + " and (n.col_online_exchange_id =:exchangeId or :exchangeId = '-1') "
//            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
//            + " and (n.col_approval = :approval or :approval= -1) "
//            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
//            + " and (u.col_email like concat('%',:email,'%') or :email = '') "
//            + " and (n.col_doctor_id = :doctorId or :doctorId= -1) "
//            + " where true "
//            + " and (n.col_online_exchange_id =:exchangeId or :exchangeId = '-1') "
//            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
//            + " and (n.col_approval = :approval or :approval= -1) "
//            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
//            + " and (u.col_email like concat('%',:email,'%') or :email = '') "
//            + " and (n.col_doctor_id = :doctorId or :doctorId= -1) "
//            , nativeQuery = true)
//    Page<OnlineExchangeQuestionEntity> search(
//            @Param("exchangeId") Long exchangeId,
//            @Param("content") String content,
//            @Param("approval") Integer approval,
//            @Param("name") String name,
//            @Param("email") String email,
//            @Param("doctorId") Integer doctorId,
//            Pageable defaultPage);

    @Query(value = "select n.* from tbl_online_exchange_question n left join tbl_user u on n.col_created_user_id = u.id"
            + " where true "
            + " and (n.col_online_exchange_id =:exchangeId or :exchangeId = '-1') "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            + " and (n.col_approval = :approval or :approval= -1) "
            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
            + " and (u.col_email like concat('%',:email,'%') or :email = '') "
            + " and (n.col_doctor_id = :doctorId or :doctorId= -1) "
            , countQuery = "select count(n.id) from tbl_online_exchange_question n left join tbl_user u on n.col_created_user_id = u.id"
            + " where true "
            + " and (n.col_online_exchange_id =:exchangeId or :exchangeId = '-1') "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            + " and (n.col_approval = :approval or :approval= -1) "
            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
            + " and (u.col_email like concat('%',:email,'%') or :email = '') "
            + " and (n.col_doctor_id = :doctorId or :doctorId= -1) "
            , nativeQuery = true)
    Page<OnlineExchangeQuestionEntity> search(
            @Param("exchangeId") Long exchangeId,
            @Param("content") String content,
            @Param("approval") Integer approval,
            @Param("name") String name,
            @Param("email") String email,
            @Param("doctorId") Integer doctorId,
            Pageable defaultPage);


//    @Query(value = "select q.id, q.col_answer,"
//            + "q.col_approval, q.col_content,"
//            + "q.col_created_date, q.col_hide_info,"
//            + "q.col_updated_date, q.col_created_user_id,"
//            + "q.col_doctor_id, q.col_online_exchange_id,"
//            + "u.col_username, u.col_name, u.col_email"
//            + "from tbl_online_exchange_question q "
//            + "LEFT JOIN tbl_user u ON q.col_created_user_id = u.id"
//            + " where true "
//            + " and (q.col_online_exchange_id =:exchangeId or :exchangeId = '-1') "
//            + " and (q.col_content like concat('%',:content,'%') or :content = '') "
//            + " and (q.col_approval =:approval or :approval = '-1') "
//            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
//            + " and (u.col_email like concat('%',:email,'%') or :email = '') "
//            + " and (q.col_doctor_id =:doctorId or :doctorId = '-1') "
//            , countQuery = "select count(q.id) from tbl_online_exchange_question q left join tbl_user u on n.col_created_user_id = u.id"
//            + " where true "
//            + " and (q.col_online_exchange_id =:exchangeId or :exchangeId = '-1') "
//            + " and (q.col_content like concat('%',:content,'%') or :content = '') "
//            + " and (q.col_approval =:approval or :approval = '-1') "
//            + " and (u.col_name like concat('%',:name,'%') or :name = '') "
//            + " and (u.col_email like concat('%',:email,'%') or :email = '') "
//            + " and (q.col_doctor_id =:doctorId or :doctorId = '-1') "
//            , nativeQuery = true)
//    Page<OnlineExchangeQuestionEntity> search(
//            @Param("exchangeId") Long exchangeId,
//            @Param("content") String content,
//            @Param("approval") Integer approval,
//            @Param("name") String name,
//            @Param("email") String email,
//            @Param("doctorId") Integer doctorId,
//            Pageable defaultPage);

}
