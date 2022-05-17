package id.saputra.adi.productsdataservice.services;

import id.saputra.adi.productsdataservice.domain.dao.ProductDao;
import id.saputra.adi.productsdataservice.domain.dto.ProductDto;
import id.saputra.adi.productsdataservice.domain.dto.ProductRiskDto;
import id.saputra.adi.productsdataservice.domain.dto.SearchRequestDto;
import id.saputra.adi.productsdataservice.exception.ApplicationException;
import id.saputra.adi.productsdataservice.repository.ProductRepository;
import id.saputra.adi.productsdataservice.util.TransformUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ProductService implements CrudBase {

    private static final String CACHE_NAME = "products";
    private static final String MESSAGE_DATA_NOT_FOUND = "Data not found";
    private static final String MESSAGE_REQ_INCOMPLETE = "Request incomplete";

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public ProductDao create(Object objRq) {
        log.info("Starting to create Product ...");
        if (Objects.isNull(objRq)){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        ProductDto productDto = (ProductDto) objRq;
        Optional<ProductDao> productDaoOptional =
                productRepository.findByCodeAndIsDeleted(productDto.getCode(), false);
        if (productDaoOptional.isPresent()){
            throw new ApplicationException("Product already exists", HttpStatus.FOUND);
        }
        ProductDao productDao = (ProductDao) TransformUtil.transform(productDto, new ProductDao());
        productRepository.saveAndFlush(productDao);
        log.info("Create data Product successfully ...");
        return productDao;
    }

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public ProductDao update(Object objRq) {
        log.info("Starting update data Product customer ....");
        ProductDto productDto = (ProductDto) objRq;
        if (Objects.isNull(productDto) || Objects.isNull(productDto.getCode())){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        Optional<ProductDao> optionalProductDao =
                productRepository.findByCodeAndIsDeleted(productDto.getCode(), false);
        if (optionalProductDao.isPresent()){
            ProductDao productDao = (ProductDao) TransformUtil.transform(productDto, optionalProductDao.get());
            productRepository.saveAndFlush(productDao);
            log.info("Update data Product successfully ...");
            return productDao;
        }
        throw new ApplicationException(MESSAGE_DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @Transactional
    @CachePut(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public ProductDao delete(Object objRq) {
        log.info("Starting soft delete data Product ...");
        ProductDto productDto = (ProductDto) objRq;
        if (Objects.isNull(productDto) || Objects.isNull(productDto.getCode())){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        Optional<ProductDao> optionalProductDao =
                productRepository.findByCodeAndIsDeleted(productDto.getCode(), false);
        if (optionalProductDao.isPresent()){
            ProductDao productDao = optionalProductDao.get();
            productDao.setDeleted(true);
            productRepository.saveAndFlush(productDao);
            log.info("Soft delete data Product successfully ...");
            return productDao;
        }
        throw new ApplicationException(MESSAGE_DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public Object showAll() {
        log.info("Starting get all data Product ...");
        List<ProductDao> productDaos = productRepository.findAll();
        log.info("Starting get all data Product successfully ...");
        return TransformUtil.transform(productDaos, new ProductDto());
    }

    @Override
    @Cacheable(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public ProductDao showDetail(String code) {
        log.info("Starting get detail data Product ...");
        if (Objects.isNull(code)){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        Optional<ProductDao> productDaoOptional =
                productRepository.findByCodeAndIsDeleted(code, false);
        if (productDaoOptional.isPresent()){
            ProductDao productDao = productDaoOptional.get();
            log.info("get data detail Product successfully ...");
            return productDao;
        }
        throw new ApplicationException(MESSAGE_DATA_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    @Cacheable(value = CACHE_NAME, keyGenerator = "customerKeyGenerator")
    public List<ProductRiskDto> search(SearchRequestDto searchRequestDto) {
        log.info("Starting search data Product ...");
        if (Objects.isNull(searchRequestDto)){
            throw new ApplicationException(MESSAGE_REQ_INCOMPLETE, HttpStatus.BAD_REQUEST);
        }
        List<ProductRiskDto> productRiskDtos = productRepository.filterByRequest(
                searchRequestDto.getRiskProfile(), searchRequestDto.getStartMinAmount(),
                searchRequestDto.getEndMinAmount(), searchRequestDto.getStartMaxAmount(),
                searchRequestDto.getEndMaxAmount());
        log.info("Data search Product successfully ...");
        return productRiskDtos;
    }
}
