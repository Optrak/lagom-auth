package com.optrak.auth

import scala.concurrent.Future

trait Authenticator[C,M[_],U] {
  def authenticate(userCredentials: C): Future[M[U]]
}

trait SimpleSetAuthenticator extends Authenticator[(String,String), Option, String] {
  var allowedUsers = Set("yar" -> "ray", "tim" -> "mit")
  def authenticate(userCredentials: (String,String)): Future[Option[String]] = {
    Future.successful(
      allowedUsers.find(_ == userCredentials)
      .map(_._1)
    )
  }
}
