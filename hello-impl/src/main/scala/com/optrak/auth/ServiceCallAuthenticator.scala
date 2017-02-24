package com.optrak.auth


import com.lightbend.lagom.scaladsl.api.transport.Forbidden
import com.lightbend.lagom.scaladsl.server.ServerServiceCall
import org.apache.commons.codec.binary.Base64

trait SimpleServerSecurity extends Authenticator[(String,String),Option,String] {

  def authenticated[Request, Response](serviceCall: String => ServerServiceCall[Request, Response]) =
    ServerServiceCall.compose { requestHeader =>
      val basicAuthData = """Basic (.*)""".r
      requestHeader.headers.find(_._1 == "Authorization").fold(throw Forbidden("Credentials not provided")){
        case (_, basicAuthData(encoded)) =>
          val plainCredentials = new String(Base64.decodeBase64(encoded.getBytes("utf-8")))
          val c = plainCredentials.split(":")
          val userName = c(0)
          //val futureUser = authenticate(c(0) -> c(1))
          serviceCall(userName)
        case _ => throw Forbidden("Invalid credentials")
      }
    }

}