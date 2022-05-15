package id.saputra.adi.productsdataservice.repository;

import id.saputra.adi.productsdataservice.domain.dao.RiskProfileDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskProfileRepository extends JpaRepository<RiskProfileDao, Integer> {

}
