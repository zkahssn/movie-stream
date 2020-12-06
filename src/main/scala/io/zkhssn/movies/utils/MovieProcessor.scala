package io.zkhssn.movies.utils

import io.zkhssn.movies.models.Movie

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

class MovieProcessor {

  def processMovies(movieList: Iterator[String]) = Future(
    movieList.flatMap(line => {
      for {
        movieDetails <- matchLine(line)
        maybeFilm <- Try(Movie(movieDetails(0), movieDetails(2), movieDetails(7).toInt, movieDetails(3), movieDetails(10).split(",").toList, movieDetails(1))).toOption
      } yield maybeFilm
    }
    )
  )

  def matchLine(line: String) = line match {
    case "show_id,type,title,director,cast,country,date_added,release_year,rating,duration,listed_in,description"
    => None
    case s@_ if (s.length > 0) => Some(s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"))
    case _ => None
  }


}


object MovieProcessor {

  def apply() = new MovieProcessor()
}