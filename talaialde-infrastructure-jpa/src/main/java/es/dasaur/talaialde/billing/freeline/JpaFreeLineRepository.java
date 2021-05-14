package es.dasaur.talaialde.billing.freeline;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.dasaur.talaialde.management.clients.JpaClient;

public interface JpaFreeLineRepository
        extends JpaRepository<JpaFreeLine, Long> {

    String PARAM_CLIENT = "client";
    String PARAM_BEGIN_DATE = "beginDate";
    String PARAM_END_DATE = "endDate";
    
    String QUERY = "from FreeLine l where " +
            "(:" + PARAM_CLIENT + " is null or l.client = :" + PARAM_CLIENT + ") and " +
            "l.lineDate >= :" + PARAM_BEGIN_DATE + " and " +
            "l.lineDate <= :" + PARAM_END_DATE + " " +
            "order by l.lineDate asc";

    @Query(QUERY)
    List<JpaFreeLine> findByFilters(
            @Param(PARAM_CLIENT) JpaClient client,
            @Param(PARAM_BEGIN_DATE) Date beginDate,
            @Param(PARAM_END_DATE) Date endDate);

}
