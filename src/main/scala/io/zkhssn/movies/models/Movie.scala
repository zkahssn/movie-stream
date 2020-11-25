package io.zkhssn.movies.models


final case class Movie(movieId: String, title: String, yearOfRelease: Option[Int], directior: String, genre: List[String])
