package com.isofh.service.service;

import com.isofh.service.model.SponsorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SponsorRepository extends CrudRepository<SponsorEntity, Long> {
    @Query(value = "select n.* from tbl_sponsor n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId= -1) "

            , countQuery = "select count(n.id) from tbl_sponsor n "
            + " where true "
            + " and (n.col_name like concat('%',:name,'%') or :name = '') "
            + " and (n.col_conference_id =:conferenceId or :conferenceId= -1) "
            , nativeQuery = true)
    Page<SponsorEntity> search(
            @Param("name") String name,
            @Param("conferenceId") Integer conferenceId,
            Pageable defaultPage);

    SponsorEntity findFirstByUid(String sponsorUid);
}
