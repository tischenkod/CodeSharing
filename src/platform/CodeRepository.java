package platform;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends CrudRepository<Code, Integer> {

    List<Code> findTop10ByTimeLessThanEqualAndViewsLessThanEqualOrderByDateDesc(int maxTime, int maxViews);

    default List<Code> latestCode() {
        return findTop10ByTimeLessThanEqualAndViewsLessThanEqualOrderByDateDesc(0, 0);
    }

    @Query("SELECT c FROM Code c WHERE c.uuid = :uuid AND " +
            "c.views IS NOT NULL AND " +
            "(c.time <= 0 OR " +
            "FUNCTION('DATEADD', ss, time, date) > FUNCTION('now') )")
    Code findCode(@Param("uuid") String uuid);

}
