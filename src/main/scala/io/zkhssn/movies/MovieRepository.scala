package io.zkhssn.movies

import io.zkhssn.movies.models.Movie
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.{AttributeValue, PutItemRequest, PutItemResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.CollectionConverters._
import scala.jdk.javaapi.FutureConverters.asScala

class MovieRepository(client: DynamoDbAsyncClient, moviesTable: String, reviews: String) {


  def putMovie(movie: Movie) = {
    val item = Map("movie-id" -> AttributeValue.builder().s(movie.movieId).build(),
      "title" -> AttributeValue.builder().s(movie.title).build(),
      "year" -> AttributeValue.builder().n(movie.yearOfRelease.fold("")(_ => identity().toString())).build(),
      "director" -> AttributeValue.builder().s(movie.directior).build(),
      "genre" -> AttributeValue.builder().s(movie.genre.toString()).build())
    putItemRequest(moviesTable, item)
  }

  //  def addMovie(movie: Movie) = ???
  //
  //  def getMovie(movideId: String) = ???
  //
  //  def addReview(movieReview: Review) = ???


  private def putItemRequest(tableName: String, item: Map[String, AttributeValue]): Future[PutItemResponse] = {
    val putItemRequest = PutItemRequest.builder().tableName(tableName).item(item.asJava).build()
    asScala(client.putItem(putItemRequest))
  }

}

object MovieRepository {

  def apply(dbClient: DynamoDbAsyncClient, movieTable: String, reviewClient: String) = new MovieRepository(dbClient, movieTable, reviewClient)

}
