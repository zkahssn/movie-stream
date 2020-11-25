package io.zkhssn.movies.models

final case class Review(reviewId: String, movieId: String, rating: Int, review: String)