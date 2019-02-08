/*
 * Copyright 2019 ksilin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.azione

import java.io.IOException

import scalaz.zio.{ App, IO }
import scalaz.zio.console._

import scala.util.Try

object FirstApp extends App {
  override def run(
      args: List[String]
  ): IO[Nothing, ExitStatus] =
    myAppLogic.attempt.map(_.fold(_ => 1, _ => 0)).map(ExitStatus.ExitNow(_))

  def myAppLogic: IO[IOException, Unit] =
    for {
      _    <- putStrLn("Hi, what is your name?")
      name <- getStrLn
      _    <- putStrLn(s"Hi $name, nice to meet you")
    } yield ()

  val strToInt: String => Either[Throwable, Int] = str => Try(Integer.parseInt(str)).toEither
  val printIntOrError: Either[Throwable, Int] => Unit = strOrErr => {
    case Left(err) => println(s"failed: $err")
    case Right(i)  => println(s"yay: $i")
  }


  val curried: String => Int => String = str => {
//    val i: Integer = Integer.getInteger(str)
    i => i.toString()
  }

  val combined: String => Unit = strToInt andThen printIntOrError
  val combined2: String => Unit = printIntOrError compose strToInt




  //
//  def myAppLogic2: IO[IOException, Unit] =
//    putStrLn("Hi, what is your name?")
//      .flatMap(_ => getStrLn)
//      .flatMap(name => putStrLn(s"Hi $name, nice to meet you"))
}
