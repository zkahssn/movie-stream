import java.net.URI

import io.zkhssn.movies.MovieRepository
import io.zkhssn.movies.models.MovieAdded
import io.zkhssn.movies.utils.{FileProcessor, MovieProcessor}
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import scala.util.{Failure, Success}

object Boot extends App {
  val dbClient = DynamoDbAsyncClient.builder().endpointOverride(new URI("http://localhost:8000")).build()
  val movieProcessor = MovieProcessor()
  val movieRepository = MovieRepository(dbClient, "movies", "")
  val storedMovies: Future[Iterator[MovieAdded]] = for {
    processedFile <- FileProcessor("/Users/zkhssn/work/movie-stream/netflix_titles.csv")
    processedMovies <- movieProcessor.processMovies(processedFile)
    movies = processedMovies
    storedMovies <- Future.traverse(movies)(movieRepository.putMovie)
  } yield storedMovies

  storedMovies.onComplete {
    case Success(succ) => println(s"${succ} FINISHED PROCCESSING THE FILMS")
    case Failure(failure) => println(s"GOT A FAILURE ${failure}")
  }
}
