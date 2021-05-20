package es.danpintas.talaialde.billing.line;

import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaLineRepository
    extends JpaRepository<JpaLine, Long> {

  String PARAM_CLIENT = "client";
  String PARAM_ROUTE = "route";
  String PARAM_TRACTOR = "tractor";
  String PARAM_BEGIN_DATE = "beginDate";
  String PARAM_END_DATE = "endDate";
  String QUERY = "from JpaLine l where " +
      "(:" + PARAM_CLIENT + " is null or l.client = :" + PARAM_CLIENT + ") and " +
      "(:" + PARAM_ROUTE + " is null or l.route = :" + PARAM_ROUTE + ") and " +
      "(:" + PARAM_TRACTOR + " is null or l.tractor = :" + PARAM_TRACTOR + ") and " +
      "l.lineDate >= :" + PARAM_BEGIN_DATE + " and " +
      "l.lineDate <= :" + PARAM_END_DATE + " " +
      "order by l.lineDate asc";

  @Query(QUERY)
  List<JpaLine> findByFilters(
      @Param(PARAM_CLIENT) JpaClient client,
      @Param(PARAM_ROUTE) JpaRoute route,
      @Param(PARAM_TRACTOR) JpaTractor tractor,
      @Param(PARAM_BEGIN_DATE) Date beginDate,
      @Param(PARAM_END_DATE) Date endDate);

}
