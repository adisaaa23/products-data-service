package id.saputra.adi.productsdataservice.controller;

import id.saputra.adi.productsdataservice.domain.dto.ProductDto;
import id.saputra.adi.productsdataservice.domain.dto.SearchRequestDto;
import id.saputra.adi.productsdataservice.services.ProductService;
import id.saputra.adi.productsdataservice.util.TransformUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Api(tags = "Products Services API", value = "Created by Adi Saputra")
@Controller
@RequestMapping("/products")
public class ProductController {

    private static final String PREFIX_TRACE_ERROR = "Trace error : ";
    
    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseBody
    public ResponseEntity<Object> getAll (){
        try {
            return ResponseEntity.ok(productService.showAll());
        } catch (Exception ex){
            log.error("Happened error when get data products. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity<Object> search (@RequestBody SearchRequestDto searchRequestDto){
        try {
            return ResponseEntity.ok(productService.search(searchRequestDto));
        } catch (Exception ex){
            log.error("Happened error when search data products. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/detail/{code}")
    @ResponseBody
    public ResponseEntity<Object> getDetail(@PathVariable String code){
        try {
            ProductDto productDto = (ProductDto) TransformUtil.transform(productService.showDetail(code), new ProductDto());
            return ResponseEntity.ok(productDto);
        } catch (Exception ex){
            log.error("Happened error when get data products. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<Object> create (@RequestBody ProductDto productDto){
        try {
            productService.create(productDto);
            return ResponseEntity.ok().body(productDto);
        } catch (Exception ex){
            log.error("Happened error when create products. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(productDto);
        }
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<Object> update (@RequestBody ProductDto productDto){
        try {
            productService.update(productDto);
            return ResponseEntity.ok().body(productDto);
        } catch (Exception ex){
            log.error("Happened error when update data products. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(productDto);
        }
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<Object> delete (@RequestBody ProductDto productDto){
        try {
            productService.delete(productDto);
            return ResponseEntity.accepted().body(productDto);
        } catch (Exception ex){
            log.error("Happened error when soft delete data products. Error {}", ex.getMessage());
            log.trace(PREFIX_TRACE_ERROR, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(productDto);
        }
    }
}
