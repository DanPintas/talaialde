package es.dasaur.talaialde.billing.line;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;

public interface LineRepository 
        extends JpaRepository<Line, Long> {

    String PARAM_CLIENT = "client";
    String PARAM_ROUTE = "route";
    String PARAM_TRACTOR = "tractor";
    String PARAM_BEGIN_DATE = "beginDate";
    String PARAM_END_DATE = "endDate";
    
    String QUERY = "from Line l where " +
            "(:" + PARAM_CLIENT + " is null or l.client = :" + PARAM_CLIENT + ") and " +
            "(:" + PARAM_ROUTE + " is null or l.route = :" + PARAM_ROUTE + ") and " +
            "(:" + PARAM_TRACTOR + " is null or l.tractor = :" + PARAM_TRACTOR + ") and " +
            "l.lineDate >= :" + PARAM_BEGIN_DATE + " and " +
            "l.lineDate <= :" + PARAM_END_DATE + " " +
            "order by l.lineDate asc";

    @Query(QUERY)
    List<Line> findByFilters(
            @Param(PARAM_CLIENT) Client client, 
            @Param(PARAM_ROUTE) Route route, 
            @Param(PARAM_TRACTOR) Tractor tractor,
            @Param(PARAM_BEGIN_DATE) Date beginDate,
            @Param(PARAM_END_DATE) Date endDate);

}
