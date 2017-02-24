package com.optrak.auth

import java.security.Principal

/**
  * This will wrap our user data to fit it into lagom model RequestHeader
  * @param name name of our user
  */
class SimpleUserPrincipal(name: String) extends Principal {
  def getName(): String = name
}
