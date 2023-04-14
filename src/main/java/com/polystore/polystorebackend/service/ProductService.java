package com.polystore.polystorebackend.service;
import com.polystore.polystorebackend.model.*;
import com.polystore.polystorebackend.repository.LikesRepository;
import com.polystore.polystorebackend.repository.ProductRepository;
import com.polystore.polystorebackend.repository.ReviewRepository;
import com.polystore.polystorebackend.repository.SceneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
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
        if (k < 0 || k > 50){
            throw new IllegalArgumentException();
        }
        return productRepository.findAll(PageRequest.of(1, k).getSort());
    }


    public Collection<Product> getAll() {
        return productRepository.findAll();
    }


    public Product findProductById(int id) {
        Product product = productRepository.findById(id).orElse(new Product());
        String owner = product.getOwner().getUsername();
        product.setOwner(User.builder().username(owner).build());
        return product;
    }


    public Product deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.deleteById(id);
        return product;
    }

    public Product createProduct(Product product) {


        User user = userService.getUserByName(product.getOwner().getUsername());
        product.setOwner(user);

        return productRepository.save(product);
    }
    public List<Product> getProductsFromUsername(String username){
        return productRepository.getProductsByUser(username);
    }


    // SCENE

    public Scene ceateScene(Scene scene) {
        Product product = productRepository.getReferenceById(scene.getProduct().getProductId());
        scene.setProduct(product);
        return sceneRepository.save(scene);
    }

    public Scene updateScene(Scene sceneRequest) {
        Scene existingScene = sceneRepository.findById(sceneRequest.getSceneId()).orElseThrow();
        existingScene.setFov(sceneRequest.getFov());
        existingScene.setAutoRotate(sceneRequest.getAutoRotate());
        existingScene.setColor(sceneRequest.getColor());
        existingScene.setHdriUrl(existingScene.getHdriUrl());
        existingScene.setCameraPosition(existingScene.getCameraPosition());
        return sceneRepository.save(existingScene);
    }


    public void deleteScene(int sceneId) {
        sceneRepository.deleteById(sceneId);
    }

    public Scene getSceneByProductId(int productId) {
        return sceneRepository.getSceneByProductId(productId).orElse(new Scene());
    }


    public Scene getSceneById(int sceneId) {
        return sceneRepository.findById(sceneId).orElseThrow();
    }


    // REVIEW
    public Review createtReview(Review review) {
        productRepository.findById(review.getReviewId().getProductId().getProductId()).orElseThrow();
        Review newReview = reviewRepository.save(review);
        return newReview;
    }

    public List<Review> getReviewsByProductId(int productId) {
        return reviewRepository.findByProductId(productId);
    }


    // LIKES
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
            like = createLike(product, likeId);
            Integer numbLikes = product.getLikes() + 1;
            product.setLikes(numbLikes);
            productRepository.save(product);
            return Pair.of(numbLikes, true);
        }

        Likes existingLike = like;
        existingLike.setLiked(!like.isLiked());

        int currentLike = product.getLikes();
        if (existingLike.isLiked()){
            currentLike -= 1;
        } else {
            currentLike += 1;
        }
        product.setLikes(currentLike);

        productRepository.save(product);
        likesRepository.save(existingLike);

        return Pair.of(currentLike, like.isLiked());
    }

    private Likes createLike(Product product, LikesId likeId) {
        productRepository.save(product);
        Likes likes = likesRepository.save(Likes.builder().id(likeId).liked(true).build());
        return likes;
    }

}