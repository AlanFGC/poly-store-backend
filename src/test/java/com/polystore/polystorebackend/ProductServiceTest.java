package com.polystore.polystorebackend;

import com.polystore.polystorebackend.model.Product;
import com.polystore.polystorebackend.model.Role;
import com.polystore.polystorebackend.model.User;
import com.polystore.polystorebackend.repository.*;
import com.polystore.polystorebackend.service.ProductService;
import com.polystore.polystorebackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
public class ProductServiceTest {

    @Autowired
    public ProductService productService;

    @Autowired
    public UserService userService;


    @MockBean
    public UserRepository userRepository;


    @MockBean
    public ProductRepository productRepository;


    @MockBean public ReviewRepository reviewRepository;

    @MockBean public LikesRepository likesRepository;

    @MockBean
    public SceneRepository sceneRepository;


    @Test
    void contextLoads() {
    }

    @Test
    public void postProductTest(){
        Product product = productService.createProduct(Product.builder()
                .price(1500.000)
                .likes(0)
                .resourceURL("/example/example")
                .views(0)
                .owner(userService.getUserByName("alan"))
                .thumbnailURL("/thumbnail/example")
                .build());

        System.out.println(product.toString());
    }



    @Test
    public void getUsername(){
        createUser();
        System.out.println(userService.getUserByName("alan").toString());
    }

    @Test
    public void createUser(){
        User user = userService.createUser(User.builder().username("wtf").email("afgcz01@gmail.com").password("testPassword").role(Role.USER).build());
        System.out.println(user.toString());
    }

    @Test
    public void getAllUsers(){
        System.out.println(userService.findAll().toString());
    }

}
