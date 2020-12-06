package io.zkhssn.movies

import io.zkhssn.movies.models.{Movie, MovieAdded}
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.jdk.CollectionConverters._
import scala.jdk.javaapi.FutureConverters.asScala

class MovieRepository(client: DynamoDbAsyncClient, moviesTable: String, reviews: String) {


  def putMovie(movie: Movie): Future[MovieAdded] = {
    val item = Map("movie-id" -> AttributeValue.builder().s(movie.show_id).build(),
      "title" -> AttributeValue.builder().s(movie.title).build(),
      "year" -> AttributeValue.builder().n(movie.yearOfRelease.toString).build(),
      "director" -> AttributeValue.builder().s(movie.director).build(),
      "genre" -> AttributeValue.builder().s(movie.genre.toString()).build())
    putItemRequest(moviesTable, item).map(_ => {
      MovieAdded(movie.show_id)
    })
  }

  def getMovie(movideId: String): Future[GetItemResponse] = {
    val keyAtt = Map("movie-id" -> AttributeValue.builder().s(movideId).build())
    getItemRequest(moviesTable, keyAtt)
  }

  private def putItemRequest(tableName: String, item: Map[String, AttributeValue]): Future[PutItemResponse] = {
    val putItemRequest = PutItemRequest.builder().tableName(tableName).item(item.asJava).build()
    asScala(client.putItem(putItemRequest))
  }

  private def getItemRequest(tableName: String, key: Map[String, AttributeValue]) = {
    val getItemRequest = GetItemRequest.builder().key(key.asJava).build()
    asScala(client.getItem(getItemRequest))
  }

}

object MovieRepository {

  def apply(dbClient: DynamoDbAsyncClient, movieTable: String, reviewClient: String) = new MovieRepository(dbClient, movieTable, reviewClient)

}
