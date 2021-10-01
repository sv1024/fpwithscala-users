package co.s4ncampus.fpwithscala.users

import cats.syntax.all._
import io.circe._
import io.circe.syntax._
import io.circe.generic.semiauto._
import cats.effect._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import domain.User
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UsersTest extends AnyFlatSpec with Matchers {


    "POST -> /users" should "add a user" in {
    val request = org.http4s.Request(Method.POST, uri("http://localhost:8000/users")).withBody(
      """{"legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}""").run

    val responseTask: Task[org.http4s.Response] = UsersController.service.run(request)

    val response: Option[scodec.bits.ByteVector] = responseTask.run.body.runLast.run

    response.get.decodeUtf8.
    
    right.get shouldEqual """{"id":1,legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}"""
  }

  "GET-> /users/123" should "get a user" in {
    val request = org.http4s.Request(Method.GET, uri("http://localhost:8000/users/123")).run 

    val responseTask: Task[org.http4s.Response] = UsersController.service.run(request)

    val response: Option[scodec.bits.ByteVector] = responseTask.run.body.runLast.run

    response.get.decodeUtf8.right.get shouldEqual """{"id":1,legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}"""
  }

  "PUT -> /users/123" should "update a user" in {
    val request = org.http4s.Request(Method.PUT, uri("http://localhost:8000/users/123")).withBody(
      """{"legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}""").run

    val responseTask: Task[org.http4s.Response] = UsersController.service.run(request)

    val response: Option[scodec.bits.ByteVector] = responseTask.run.body.runLast.run

    response.get.decodeUtf8.right.get shouldEqual """User updated"""
  }

  "GET-> /users/123" should "get a user" in {
    val request = org.http4s.Request(Method.GET, uri("http://localhost:8000/users/123")).run 

    val responseTask: Task[org.http4s.Response] = UsersController.service.run(request)

    val response: Option[scodec.bits.ByteVector] = responseTask.run.body.runLast.run

    response.get.decodeUtf8.right.get shouldEqual """{"id":1,legalId":"123","firstName":"Pancha","lastName":"Perez","email":"alguien@seven4n.com","phone":"3009004050"}"""
  }

  "DELETE-> /users/123" should "delete a user" in {
    val request = org.http4s.Request(Method.GET, uri("http://localhost:8000/users/123")).run 

    val responseTask: Task[org.http4s.Response] = UsersController.service.run(request)

    val response: Option[scodec.bits.ByteVector] = responseTask.run.body.runLast.run

    response.get.decodeUtf8.right.get shouldEqual """--- user deleted"""
  }

  "GET-> /users/123" should "show an error" in {
    val request = org.http4s.Request(Method.GET, uri("http://localhost:8000/users/123")).run 

    val responseTask: Task[org.http4s.Response] = UsersController.service.run(request)

    val response: Option[scodec.bits.ByteVector] = responseTask.run.body.runLast.run

    response.get.decodeUtf8.right.get shouldEqual """The user with legal id 123 doesnt exists"""
  }

}

/* def testCreateUser(userService: UserService[F]): HttpRoutes[F] = 
    HttpRoutes.of[F] {
      case req @ POST -> Root =>
        val action = for {
          user <- req.as[User]
          result <- userService.create(user).value
        } yield result
        action.flatMap {
          case Right(saved) => Ok(saved.asJson)
          case Left(UserAlreadyExistsError(existing)) => Conflict(s"The user with legal id ${existing.legalId} already exists")
        }
    } */

/* package com.example.users

// import org.scalatest._
// import io.circe._
// import io.circe.generic.auto._
// import io.circe.syntax._

import org.http4s._
import org.scalatest._
import matchers.should.Matchers._ */

// import org.http4s.circe._
// import org.http4s.server._
// import org.http4s.dsl._

/* class usersTest extends AnyFlatSpec { 
    /* val meMato: HttpApp = {
        case Request(POST, Uri("/users", asJson"""{"legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}""")) => 
            Response(OK, "Mierdaaa")
        case _ => 
            Response(NotFound)
    } */
    /* val req = Request(POST, Uri("/users"), asJson"""{"legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}""") 
    assert("Mierdaaa", meMato(req).body) */


    "POST -> /users" should "add a user" in {
    // org.http4s.Request.withBody returns a task that can be run to generate the request
    val request = org.http4s.Request(Method.POST, uri("/users")).withBody(
      """{"legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}""").run

    val responseTask: Task[org.http4s.Response] = UsersController.service.run(request)

    val response: Option[scodec.bits.ByteVector] = responseTask.run.body.runLast.run

    response.get.decodeUtf8.right.get shouldEqual """{"id":1,legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}"""
    }
} */

// package co.s4ncampus.fpwithscala.tests
/* class UsersTest
import org.scalatest.AnyFunSuite

class UsersHttpRequest extends AnyFunSpec {

  describe("POST -> http://localhost:8000/users") {
    it("should create user") {
      assert(org.http4s.Request(Method.POST, uri("http://localhost:8000/users")).withBody(
          """{"legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}""").run == 
          """{"id":1,legalId":"123","firstName":"Isabela","lastName":"Lujan","email":"isabelalujan@seven4n.com","phone":"3009004050"}""")

    }
  }
} */

/* class CalculatorSuite extends AnyFunSuite {
  val calculator = new Calculator
  test("Anythin multiplied by cero should always be cero") {
    assert(calculator.multiply(2, 0) == 0)
  }
} */
