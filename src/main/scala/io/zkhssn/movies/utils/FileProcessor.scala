package io.zkhssn.movies.utils

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import scala.io.Source

class FileProcessor(fileName: String) {

  def retrieveFile(fileName: String): Future[Iterator[String]] = Future(Source.fromFile(fileName, "UTF-8").getLines())

}

object FileProcessor {
  def apply(fileName: String) = new FileProcessor(fileName).retrieveFile(fileName)
}