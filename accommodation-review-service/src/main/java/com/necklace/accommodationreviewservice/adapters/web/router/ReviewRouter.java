package com.necklace.accommodationreviewservice.adapters.web.router;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.necklace.accommodationreviewservice.adapters.web.handler.ReviewHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ReviewRouter {

  @Bean
  public RouterFunction<ServerResponse> reviewsRoute(ReviewHandler reviewHandler) {
    return route().nest(path("/v1/reviews"), builder -> builder
            .POST("", request -> reviewHandler.addReview(request))
            .GET("", request -> reviewHandler.getReviews(request))
            .GET("/{id}", request -> reviewHandler.getReviewById(request))
            .DELETE("/{id}", request -> reviewHandler.deleteReviewById(request))
        )
        .build();
  }

}
