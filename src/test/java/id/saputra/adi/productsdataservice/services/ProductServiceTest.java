package id.saputra.adi.productsdataservice.services;

import id.saputra.adi.productsdataservice.domain.dao.ProductDao;
import id.saputra.adi.productsdataservice.domain.dto.ProductDto;
import id.saputra.adi.productsdataservice.domain.dto.ProductRiskDto;
import id.saputra.adi.productsdataservice.domain.dto.SearchRequestDto;
import id.saputra.adi.productsdataservice.exception.ApplicationException;
import id.saputra.adi.productsdataservice.repository.ProductRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Before
    public void setup(){
        List<ProductDao> productDaos = List.of(
                ProductDao.builder()
                        .code("PRD1")
                        .riskProfile("1")
                        .balance(BigInteger.valueOf(1000000))
                        .minAmount(BigInteger.ONE)
                        .maxAmount(BigInteger.valueOf(100000))
                        .multipleAmount(BigInteger.valueOf(1000))
                        .build(),
                ProductDao.builder()
                        .code("PRD2")
                        .riskProfile("2")
                        .balance(BigInteger.valueOf(10000000))
                        .minAmount(BigInteger.valueOf(1000))
                        .maxAmount(BigInteger.valueOf(1000000))
                        .multipleAmount(BigInteger.valueOf(10000))
                        .build()
        );
        productRepository.saveAll(productDaos);
    }

    @After
    public void afterTest(){
        productRepository.deleteAll();
    }

    @Test
    public void createExpectSuccess(){
        ProductDao productDao = productService.create(
                ProductDto.builder()
                        .code("PRD212")
                        .riskProfile("2")
                        .balance(BigInteger.valueOf(10000000))
                        .minAmount(BigInteger.valueOf(1000))
                        .maxAmount(BigInteger.valueOf(1000000))
                        .multipleAmount(BigInteger.valueOf(10000))
                        .build()
        );
        assertEquals("PRD212", productDao.getCode());
    }

    @Test(expected = ApplicationException.class)
    public void createExpectRqIncomplete(){
        productService.create(
                null
        );
    }

    @Test(expected = ApplicationException.class)
    public void createExpectUserExists(){
        productRepository.save(ProductDao.builder()
                .code("PRD212")
                .riskProfile("2")
                .balance(BigInteger.valueOf(10000000))
                .minAmount(BigInteger.valueOf(1000))
                .maxAmount(BigInteger.valueOf(1000000))
                .multipleAmount(BigInteger.valueOf(10000))
                .build());
        productService.create(
                ProductDto.builder()
                        .code("PRD212")
                        .riskProfile("2")
                        .balance(BigInteger.valueOf(10000000))
                        .minAmount(BigInteger.valueOf(1000))
                        .maxAmount(BigInteger.valueOf(1000000))
                        .multipleAmount(BigInteger.valueOf(10000))
                        .build()
        );
    }

    @Test
    public void updateExpectSuccess(){
        ProductDao productDao = productService.update(
                ProductDto.builder()
                        .code("PRD2")
                        .riskProfile("2")
                        .balance(BigInteger.valueOf(10000000))
                        .minAmount(BigInteger.valueOf(10000))
                        .maxAmount(BigInteger.valueOf(1000000))
                        .multipleAmount(BigInteger.valueOf(10000))
                        .build()
        );
        assertEquals(BigInteger.valueOf(10000), productDao.getMinAmount());
    }

    @Test(expected = ApplicationException.class)
    public void updateExpectUserNotFound(){
        productService.update(
                ProductDto.builder()
                        .code("PRD2123xxx")
                        .riskProfile("2")
                        .balance(BigInteger.valueOf(10000000))
                        .minAmount(BigInteger.valueOf(1000))
                        .maxAmount(BigInteger.valueOf(1000000))
                        .multipleAmount(BigInteger.valueOf(10000))
                        .build()
        );
    }

    @Test(expected = ApplicationException.class)
    public void updateExpectRqIncomplete(){
        productService.update(
                ProductDto.builder().build()
        );
    }

    @Test
    public void deleteExpectSuccess(){
        ProductDao productDao = productService.delete(
                ProductDto.builder()
                        .code("PRD1")
                        .isDeleted(true)
                        .build()
        );
        assertTrue(productDao.isDeleted());
    }

    @Test(expected = ApplicationException.class)
    public void deleteExpectUserNotFound(){
        productService.delete(
                ProductDto.builder()
                        .code("ccccc12")
                        .build()
        );
    }

    @Test(expected = ApplicationException.class)
    public void deleteExpectRqIncomplete(){
        productService.delete(
                ProductDto.builder().build()
        );
    }

    @Test
    public void showAllExpectSuccess(){
        List<ProductDao> productDaos = (List<ProductDao>) productService.showAll();
        assertNotNull(productDaos);
    }

    @Test
    public void showDetailExpectSuccess(){
        productRepository.save( ProductDao.builder()
                .code("PRD2123xxxDetail")
                .riskProfile("2")
                .balance(BigInteger.valueOf(10000000))
                .minAmount(BigInteger.valueOf(1000))
                .maxAmount(BigInteger.valueOf(1000000))
                .multipleAmount(BigInteger.valueOf(10000))
                .build());
        ProductDao productDao = productService.showDetail("PRD2123xxxDetail");
        assertEquals("PRD2123xxxDetail", productDao.getCode());
    }

    @Test(expected = ApplicationException.class)
    public void showDetailExpectRqIncomplete(){
        productService.showDetail(
                null
        );
    }

    @Test(expected = ApplicationException.class)
    public void showDetailExpectUserNotFound(){
        productService.showDetail(
                "xxxxx"
        );
    }

    @Test
    public void searchExpectSuccess(){
        List<ProductRiskDto> productRiskDtos = productService.search(SearchRequestDto.builder()
                        .riskProfile("2").build());
        assertNotNull(productRiskDtos);
    }

    @Test(expected = ApplicationException.class)
    public void searchExpectException(){
        productService.search(null);
    }
}
