package id.saputra.adi.productsdataservice.repository;

import id.saputra.adi.productsdataservice.domain.dao.ProductDao;
import id.saputra.adi.productsdataservice.domain.dto.ProductRiskDto;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductDao, Integer> {
    Optional<ProductDao> findByCodeAndIsDeleted(String code, boolean isDeleted);

    @Query(value = "SELECT new id.saputra.adi.productsdataservice.domain.dto.ProductRiskDto(prd.code, prd.name, prd.riskProfile," +
            " prd.balance, prd.minAmount, prd.maxAmount, prd.multipleAmount, rpfl.name)" +
            " FROM ProductDao prd JOIN RiskProfileDao rpfl ON rpfl.code = prd.riskProfile" +
            " WHERE prd.riskProfile <= :riskProfile OR (prd.minAmount BETWEEN :startMinAmount AND :endMinAmount)" +
            " OR (prd.maxAmount BETWEEN :startMaxAmount AND :endMaxAmount)")
    List<ProductRiskDto> filterByRequest(
            @Param("riskProfile") String riskProfile, @Param("startMinAmount") BigInteger startMinAmount,
            @Param("endMinAmount") BigInteger endMinAmount, @Param("startMaxAmount") BigInteger startMaxAmount,
            @Param("endMaxAmount") BigInteger endMaxAmount
    );

}
