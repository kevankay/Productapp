package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void testAddProduct() throws Exception {
		String content = "{ \"productId\": 1,\"productName\": \"samsung\",\"productCategory\": \"phone\",\"productPrice\": 5000}";

		when(productService.addProduct(Mockito.isA(Product.class)))
				.thenReturn(new Product(1, "samsung", "phone", 5000));
		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content(content)).andDo(print())
				.andExpect(jsonPath("$.productId").value(1));

	}

	@Test
	public void testUpdateProduct() throws Exception {
		String content = "{ \"productId\": 1,\"productName\": \"nokia\",\"productCategory\": \"phone\",\"productPrice\": 5000}";

		when(productService.updateProduct(Mockito.isA(Product.class)))
				.thenReturn(new Product(1, "nokia", "phone", 5000));
		when(productService.findProductById(1)).thenReturn(new Product(1, "nokia", "phone", 5000));
		mockMvc.perform(put("/product").contentType(MediaType.APPLICATION_JSON_UTF8).content(content)).andDo(print())
				.andExpect(jsonPath("$.productId").value("1"));

	}

	@Test
	public void testfindproduct() throws Exception {
		when(productService.findProductById(1)).thenReturn(new Product(1, "nokia", "phone", 5000));
		mockMvc.perform(get("/products/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").exists()).andExpect(jsonPath("$.productId").value(1));

	}

	@Test
	public void testDeleteProduct() throws Exception {

		when(productService.findProductById(1)).thenReturn(new Product(1, "nokia", "phone", 5000));
		mockMvc.perform(delete("/products/1").accept(MediaType.APPLICATION_JSON))

				.andExpect(status().isOk()).andDo(print());
	}

}
