package io.zkhssn.movies.models

sealed trait MovieResponse
final case class MovieAdded(movieId: String)  extends  MovieResponse

final case class Movie(show_id: String, title: String, yearOfRelease: Int, director: String, genre: List[String], showType: String)


