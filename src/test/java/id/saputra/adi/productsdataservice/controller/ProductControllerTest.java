package id.saputra.adi.productsdataservice.controller;

import id.saputra.adi.productsdataservice.domain.dao.ProductDao;
import id.saputra.adi.productsdataservice.domain.dto.ProductDto;
import id.saputra.adi.productsdataservice.domain.dto.ProductRiskDto;
import id.saputra.adi.productsdataservice.exception.ApplicationException;
import id.saputra.adi.productsdataservice.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setup() {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getAllExpectSuccess() throws Exception {
        when(productService.showAll()).thenReturn(ProductDto.builder().build());
        this.mockMvc.perform(get("/products")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getAllExpectException() throws Exception {
        when(productService.showAll()).thenThrow(ApplicationException.class);
        this.mockMvc.perform(get("/products")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void getDetailExpectSuccess() throws Exception {
        when(productService.showDetail("PRD12")).thenReturn(ProductDao.builder().build());
        this.mockMvc.perform(get("/products/detail/{code}", "PRD12")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getDetailExpectException() throws Exception {
        when(productService.showDetail("PRD12")).thenThrow(ApplicationException.class);
        this.mockMvc.perform(get("/products/detail/{username}", "PRD12")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void createExpectSuccess() throws Exception {
        when(productService.create(any())).thenReturn(ProductDao.builder().build());
        this.mockMvc.perform(post("/products/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createExpectException() throws Exception {
        when(productService.create(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(post("/products/create")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void updateExpectSuccess() throws Exception {
        when(productService.update(any())).thenReturn(ProductDao.builder().build());
        this.mockMvc.perform(put("/products/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateExpectException() throws Exception {
        when(productService.update(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(put("/products/update")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void deleteExpectSuccess() throws Exception {
        when(productService.delete(any())).thenReturn(ProductDao.builder().build());
        this.mockMvc.perform(delete("/products/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isAccepted())
                .andReturn();
    }

    @Test
    public void deleteExpectException() throws Exception {
        when(productService.delete(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(delete("/products/delete")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void searchExpectSuccess() throws Exception {
        when(productService.search(any())).thenReturn(List.of(ProductRiskDto.builder().build()));
        this.mockMvc.perform(post("/products/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void searchExpectException() throws Exception {
        when(productService.search(any())).thenThrow(ApplicationException.class);
        this.mockMvc.perform(post("/products/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }
}
