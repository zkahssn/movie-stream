import java.net.URI

import io.zkhssn.movies.MovieRepository
import io.zkhssn.movies.models.Movie
import io.zkhssn.movies.utils.{FileProcessor, MovieProcessor}
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object Boot extends App {

  //val dynamoDbClient = DynamoDbAsyncClient.builder().region(Region.EU_WEST_1).endpointOverride(new URI("http://localhost:8000")).build()

//  val movieRepository = MovieRepository(dynamoDbClient, "movies", "")
//  val movieProcessor = MovieProcessor.apply
//  val allMovies = for {
//    movieFile <- FileProcessor("/Users/zkhssn/work/movie-stream/archive/IMDb-movies.csv")
//    movies <- movieProcessor.processMovies(movieFile)
//  } yield movies

  val movieFile = Await.result(FileProcessor("/Users/zkhssn/work/movie-stream/archive/IMDb-movies.csv"), scala.concurrent.duration.FiniteDuration.apply(1, "minute"))

  //    .map(maybeMovie => {
  //    maybeMovie.fold(())(movie => movieRepository.putMovie(movie) onComplete {
  //      case Success(msg) => println("Added the film to the db")
  //      case Failure(err) => println("Failed to add the film to the db")
  //    })
  //  })

//  allMovies.andThen {
//    case Success(films) => films.map(maybeFilm => maybeFilm.fold(()) { film => println(film) })
//    case Failure(err) => println("Couldnt get any films")
//  }


}
