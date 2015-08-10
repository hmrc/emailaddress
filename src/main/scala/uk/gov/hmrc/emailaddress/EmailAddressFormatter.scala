/*
 * Copyright 2015 HM Revenue & Customs
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

package uk.gov.hmrc.emailaddress

import scala.Left
import scala.Right
import scala.util.matching.Regex

import play.api.data.FormError
import play.api.data.format.Formatter

object EmailAddressFormatter {

  def emailAddress(messageKeyBuilder: Option[(String, String) => String] = None,
                   emailRestriction: Option[Regex] = None,
                   maxLength: Option[Int] = None) =
    new Formatter[EmailAddress] {
      def unbind(key: String, value: EmailAddress) = Map(key -> value.value)
      def bind(key: String, data: Map[String, String]): Either[Seq[FormError], EmailAddress] =
        data.get(key) match {
          case None => 
            Left(List(FormError(key, buildMessageKey(messageKeyBuilder, key, "required"))))
          case Some(userEnteredEmail) => userEnteredEmail.trim match {
            case emptyEmail if (emptyEmail.isEmpty()) =>
              Left(List(FormError(key, buildMessageKey(messageKeyBuilder, key, "required"))))
            case longEmail if (maxLength.isDefined && longEmail.length() > maxLength.get) =>
              Left(List(FormError(key, buildMessageKey(messageKeyBuilder, key, "max-length-violation"))))
            case email if (EmailAddress.isValid(email) && (emailRestriction.isEmpty || email.matches(emailRestriction.get.regex))) =>
              Right(EmailAddress(email))
            case _ =>
              Left(List(FormError(key, buildMessageKey(messageKeyBuilder, key, "email"))))
          }
        }
    }

  private def defaultMessageKeyBuilder(key: String, error: String): String = {
    s"error.${error}"
  }

  private def buildMessageKey(messageKeyBuilder: Option[(String, String) => String], key: String, error: String): String = {
    messageKeyBuilder.getOrElse[(String, String) => String](defaultMessageKeyBuilder).apply(key, error)
  }
}
