package com.isofh.service.service;

import com.isofh.service.model.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface NewsRepository extends CrudRepository<NewsEntity, Long> {

    @Query(value = "select n.* from tbl_news n "
            + " where true "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            + " and (n.col_is_hot_news =:isHotNews or :isHotNews= -1) "
            + " and (n.col_menu_id =:menuId or :menuId= -1) "
            + " and (n.col_web_type & :webType != 0 or :webType= -1) "
            + " and ((:fromHoiLao=0 and n.col_from_hoi_lao=:fromHoiLao) or n.col_from_hoi_lao&:fromHoiLao!=0 or :fromHoiLao= -1) "
            + " and (n.col_charity =:charity or :charity= -1) "
            + " and (n.col_highlight_noti =:highlightNoti or :highlightNoti= -1) "
            + " and (n.col_belong_department =:belongDepartment or :belongDepartment= -1) "
            + " and (n.col_page_id =:pageId or :pageId= -1) "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_title like concat('%',:title,'%') or :title = '') "
            + " and (date(n.col_created_date) >= :startDate or :startDate= '1970-01-01') "
            + " and (date(n.col_created_date) <= :endDate or :endDate= '1970-01-01') "

            , countQuery = "select count(n.id) from tbl_news n "
            + " where true "
            + " and (n.col_content like concat('%',:content,'%') or :content = '') "
            + " and (n.col_is_hot_news =:isHotNews or :isHotNews= -1) "
            + " and (n.col_menu_id =:menuId or :menuId= -1) "
            + " and (n.col_web_type & :webType != 0 or :webType= -1) "
            + " and ((:fromHoiLao=0 and n.col_from_hoi_lao=:fromHoiLao) or n.col_from_hoi_lao&:fromHoiLao!=0 or :fromHoiLao= -1) "
            + " and (n.col_charity =:charity or :charity= -1) "
            + " and (n.col_highlight_noti =:highlightNoti or :highlightNoti= -1) "
            + " and (n.col_belong_department =:belongDepartment or :belongDepartment= -1) "
            + " and (n.col_page_id =:pageId or :pageId= -1) "
            + " and (n.col_type =:type or :type= -1) "
            + " and (n.col_title like concat('%',:title,'%') or :title = '') "
            + " and (date(n.col_created_date) >= :startDate or :startDate= '1970-01-01') "
            + " and (date(n.col_created_date) <= :endDate or :endDate= '1970-01-01') "

            , nativeQuery = true)
    Page<NewsEntity> search(
            @Param("content") String content,
            @Param("isHotNews") Integer isHotNews,
            @Param("menuId") Long menuId,
            @Param("webType") Integer webType,
            @Param("fromHoiLao") Integer fromHoiLao,
            @Param("charity") Integer charity,
            @Param("highlightNoti") Integer highlightNoti,
            @Param("belongDepartment") Integer belongDepartment,
            @Param("pageId") Long pageId,
            @Param("type") Integer type,
            @Param("title") String title,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable defaultPage);

    NewsEntity findFirstByLinkAlias(String linkAlias);

    NewsEntity findFirstByUid(String uid);
}
