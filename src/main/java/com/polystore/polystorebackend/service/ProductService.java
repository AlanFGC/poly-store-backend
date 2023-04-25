package com.polystore.polystorebackend.service;

import com.polystore.polystorebackend.api.requests.ReviewRequest;
import com.polystore.polystorebackend.api.requests.ReviewUpdateRequest;
import com.polystore.polystorebackend.api.requests.SceneRequest;
import com.polystore.polystorebackend.model.*;
import com.polystore.polystorebackend.repository.LikesRepository;
import com.polystore.polystorebackend.repository.ProductRepository;
import com.polystore.polystorebackend.repository.ReviewRepository;
import com.polystore.polystorebackend.repository.SceneRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private SceneRepository sceneRepository;


    @Autowired
    private LikesRepository likesRepository;


    // PRODUCT
    public List<Product> getPageOfSize(int k) {
        if (k < 0 || k > 50) {
            throw new IllegalArgumentException();
        }
        return productRepository.findAll(PageRequest.of(1, k).getSort());
    }


    public List<Product> getAll() {
        return productRepository.findAll();
    }


    public Product findProductById(int id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.save(product);
        String owner = product.getOwner().getUsername();
        product.setOwner(User.builder().username(owner).build());
        return product;
    }

    public void giveView(int id){
        Product product = productRepository.findById(id).orElseThrow();
        product.setViews(product.getViews() + 1);
        productRepository.save(product);
    }


    public Product deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.deleteById(id);
        return product;
    }

    public Product createProduct(Product product) {
        User user = userService.getUserByName(product.getOwner().getUsername());
        product.setOwner(user);

        product.setLikes(0);

        return productRepository.save(product);
    }

    public List<Product> getProductsFromUsername(String username) {
        return productRepository.getProductsByUser(username);
    }


    public List<Product> searchProducts(String keyword) {
        String regexPattern = "^[a-zA-Z0-9_\\s]+$";
        boolean result = keyword.matches(regexPattern);
        if (!result) {
            return new ArrayList<>();
        }
        return productRepository.getProductsByKeyword(keyword);
    }


    public List<Product> findAll(){
        return productRepository.findAll();
    }


    // SCENE

    public Scene createScene(Scene scene) {
        Product product = productRepository.getReferenceById(scene.getProduct().getProductId());
        productRepository.save(product);
        if (product == null) throw new NullPointerException();
        return sceneRepository.save(scene);
    }

    public Scene updateScene(Scene scene) {
        Scene existingScene = sceneRepository.getReferenceById(scene.getSceneId());
        existingScene.setFov(scene.getFov());
        existingScene.setColor(scene.getColor());
        existingScene.setHdriUrl(scene.getHdriUrl());
        existingScene.setCameraX(scene.getCameraX());
        existingScene.setCameraY(scene.getCameraY());
        existingScene.setCameraZ(scene.getCameraZ());
        existingScene.setAutoRotate(scene.getAutoRotate());
        existingScene.setHdriUrl(scene.getHdriUrl());
        return sceneRepository.save(existingScene);
    }


    public void deleteScene(int sceneId) {
        sceneRepository.deleteById(sceneId);
    }

    public Scene getSceneByProductId(int productId) {
        return sceneRepository.getSceneByProductId(productId).orElseThrow();
    }

    public Scene getSceneById(int sceneId) {
        return sceneRepository.findById(sceneId).orElseThrow();
    }


    // REVIEW
    public Review createtReview(ReviewRequest review, String username) {

        User user = userService.getUserByName(username);

        Product product = productRepository.getReferenceById(review.productId);

        if (user == null || product == null) {
            throw new InvalidParameterException("product or user or both couldn't be found");
        }

        ReviewId reviewId = new ReviewId();
        reviewId.setUsername(user);
        reviewId.setProductId(product);


        if (reviewRepository.existsById(reviewId)) {
            throw new EntityExistsException("a review has been posted before.");
        }

        Review newReview = new Review();
        newReview.setReviewId(reviewId);
        newReview.setDate(review.date);
        newReview.setReview(review.getReview());

        return reviewRepository.save(newReview);
    }


    public Review updateReview(ReviewUpdateRequest review, int productId, String username){

        User user = userService.getUserByName(username);
        Product product = productRepository.getReferenceById(productId);
        ReviewId reviewId = new ReviewId();

        reviewId.setProductId(product);
        reviewId.setUsername(user);


        if (!reviewRepository.existsById(reviewId)) {
            throw new EntityExistsException("No review to perform update on");
        }

        Review existingReview = reviewRepository.getReferenceById(reviewId);

        existingReview.setReview(review.getReview());

        return reviewRepository.save(existingReview);
    }






    public List<Review> getReviewsByProductId(int productId) {
        return reviewRepository.findByProductId(productId);
    }



    public Review updateReview(String name, int productId, ReviewUpdateRequest request) {
        User user = userService.getUserByName(name);
        Product product = productRepository.getReferenceById(productId);

        ReviewId reviewId = new ReviewId();
        reviewId.setProductId(product);
        reviewId.setUsername(user);

        Review review = reviewRepository.findById(reviewId).orElseThrow();
        review.setReview(request.getReview());
        review.setDate(request.getDate());
        return reviewRepository.save(review);
    }


    // LIKES
    public int getLikes(int productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return product.getLikes();
    }


    public Pair<Integer, Boolean> giveLike(String username, int productId) {
        Pair<Integer, Boolean> integerBooleanPair;

        User user = userService.getUserByName(username);
        Product product = productRepository.findById(productId).orElseThrow();
        LikesId likeId = new LikesId();
        likeId.setProductId(product);
        likeId.setUserId(user);
        Likes like = likesRepository.findById(likeId).orElse(null);

        // create like from start
        if (like == null) {
            createLike(product, likeId);
            Integer numbLikes = product.getLikes() + 1;
            product.setLikes(numbLikes);
            productRepository.save(product);
            return Pair.of(numbLikes, true);
        }

        Likes existingLike = like;
        existingLike.setLiked(!like.isLiked());

        int currentLike = product.getLikes();
        if (existingLike.isLiked()) {
            currentLike += 1;
        } else {
            currentLike -= 1;
        }
        product.setLikes(currentLike);

        productRepository.save(product);
        likesRepository.save(existingLike);

        return Pair.of(currentLike, like.isLiked());
    }

    private Likes createLike(Product product, LikesId likeId) {
        Likes likes = likesRepository.save(Likes.builder().id(likeId).liked(true).build());
        return likes;
    }

    public void deleteReview(String username, int productId){
        User user = userService.getUserByName(username);
        Product product = productRepository.getReferenceById(productId);

        ReviewId reviewId = new ReviewId();
        reviewId.setProductId(product);
        reviewId.setUsername(user);
        reviewRepository.deleteById(reviewId);
    }


}