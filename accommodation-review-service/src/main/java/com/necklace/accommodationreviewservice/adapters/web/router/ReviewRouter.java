package com.necklace.accommodationreviewservice.adapters.web.router;

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
    return route()
        .POST("/v1/reviews", request -> reviewHandler.addReview(request))
        .build();
  }

}
