package org.okten.demo.repository;

import org.bson.types.ObjectId;
import org.okten.demo.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

    List<Review> findAllByProductId(Long productId);
}
