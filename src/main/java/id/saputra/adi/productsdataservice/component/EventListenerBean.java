package id.saputra.adi.productsdataservice.component;

import id.saputra.adi.productsdataservice.domain.dao.ProductDao;
import id.saputra.adi.productsdataservice.domain.dao.RiskProfileDao;
import id.saputra.adi.productsdataservice.repository.ProductRepository;
import id.saputra.adi.productsdataservice.repository.RiskProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
@Slf4j
public class EventListenerBean {

    @Autowired
    private RiskProfileRepository riskProfileRepository;

    @Autowired
    private ProductRepository productRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        /* Initial Data RiskProfile */
        log.info("Prepare insert initiate risk profile data");
        List<RiskProfileDao> riskProfileDaoList = List.of(
                RiskProfileDao.builder().code("3").name("High").build(),
                RiskProfileDao.builder().code("2").name("Medium").build(),
                RiskProfileDao.builder().code("1").name("Low").build()
        );
        riskProfileRepository.saveAll(riskProfileDaoList);
        log.info("Successfully insert initiate risk profile data");

        /* Initial Data Products */
        log.info("Prepare insert initiate products data");
        List<ProductDao> productDaoList = List.of(
                ProductDao.builder().code("AA1").name("Equity Index")
                        .balance(BigInteger.valueOf(100000000)).maxAmount(BigInteger.valueOf(1000000))
                        .minAmount(BigInteger.valueOf(100000)).multipleAmount(BigInteger.valueOf(100000))
                        .riskProfile("3").build(),
                ProductDao.builder().code("AA2").name("Product AA2")
                        .balance(BigInteger.valueOf(10000000)).maxAmount(BigInteger.valueOf(700000))
                        .minAmount(BigInteger.valueOf(50000)).multipleAmount(BigInteger.valueOf(50000))
                        .riskProfile("2").build(),
                ProductDao.builder().code("AA3").name("Product AA3")
                        .balance(BigInteger.valueOf(10000000)).maxAmount(BigInteger.valueOf(500000))
                        .minAmount(BigInteger.valueOf(50000)).multipleAmount(BigInteger.valueOf(50000))
                        .riskProfile("1").build()
        );
        productRepository.saveAll(productDaoList);
        log.info("Successfully insert initiate products data");
    }
}
