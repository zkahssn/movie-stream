package io.zkhssn.movies.utils

import io.zkhssn.movies.models.Movie

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

class MovieProcessor {

  def processMovies(movieList: Iterator[String])= Future(
    movieList.map(line => {
      matchLine(line).map(movieDetails => {
        Movie(movieDetails(0), movieDetails(1), Try(movieDetails(3).toInt).toOption, movieDetails(9), movieDetails(5).split(",").toList)
      })
    }
    )
  )

  def matchLine(line: String) = line match {
    case "imdb_title_id,title,original_title,year,date_published,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote,votes,budget,usa_gross_income,worlwide_gross_income,metascore,reviews_from_users,reviews_from_critics"
    => None
    case s@_ if (s.length > 0) => Some(s.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)"))
    case _ => None
  }


}


object MovieProcessor {

  def apply = new MovieProcessor()
}