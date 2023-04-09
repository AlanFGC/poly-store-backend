package com.polystore.polystorebackend.service;


import com.polystore.polystorebackend.model.*;
import com.polystore.polystorebackend.repository.LikesRepository;
import com.polystore.polystorebackend.repository.ProductRepository;
import com.polystore.polystorebackend.repository.ReviewRepository;
import com.polystore.polystorebackend.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.sound.sampled.Port;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {


    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private SceneRepository sceneRepository;

    public List<Product> getNRandomProducts(int k){
        List<Product> productList =  productRepository.findAll();
        if (k >= productList.size()) k = productList.size() -1;
        Collections.shuffle(productList);
        if (k < 0) k = 0;
        productList.subList(0, k);
        return productList;
    }
    public Collection<Product> getAll(){
        return productRepository.findAll();
    }

    public Product findById(int id){
        Product product = productRepository.findById(id).orElse(new Product());
        return product;
    }




    public Product deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.deleteById(product.getProductId());
        return product;
    }

    public Product createProduct(Product product){
        User user = userService.getUserById(product.getOwner().getId());
        product.setOwner(user);
        return productRepository.save(product);
    }



    public Scene getSceneByProductId(int productId){
        return sceneRepository.getSceneByProductId(productId).orElse(new Scene());
    }

    public Review createtReview(Review review){
        productRepository.findById(review.getReviewId().getProductId().getProductId()).orElseThrow();
        Review newReview = reviewRepository.save(review);
        return newReview;
    }

    // this query is super tricky
    public List<Review> getReviewsByProductId(int productId){
        return reviewRepository.findByProductId(productId);
    }

    public Scene getSceneById(int sceneId) {
        return sceneRepository.findById(sceneId).orElseThrow();
    }
    public Scene createScene(Scene scene, int productId){
        Product product = productRepository.findById(productId).orElseThrow();
        scene.setProduct(product);
        sceneRepository.save(scene);
        return scene;
    }


    public void likeProduct(int userId, int productid){
        User user = userService.getReferenceById(userId);
        Product product = productRepository.getReferenceById(productid);
        product.setLikes(product.getLikes() + 1);
        LikesId likesId = new LikesId().builder().productId(product).userId(user).build();
        Likes like = likesRepository.findById(likesId).orElse(null);
        if (like == null){
            likesRepository.save(new Likes().builder().id(likesId).liked(true).build());
        }else{
            boolean liked = !like.isLiked();

        }
    }

    public void deleteScene(int id) {
        sceneRepository.deleteById(id);
    }
}
