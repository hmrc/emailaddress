/*
 * Copyright 2019 HM Revenue & Customs
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

case class EmailAddress(value: String) extends StringValue {

  val (mailbox, domain): (EmailAddress.Mailbox, EmailAddress.Domain) = value match {
    case EmailAddress.validEmail(m, d) => (EmailAddress.Mailbox(m), EmailAddress.Domain(d))
    case invalidEmail => throw new IllegalArgumentException(s"'$invalidEmail' is not a valid email address")
  }

  lazy val obfuscated = ObfuscatedEmailAddress.apply(value)
}

object EmailAddress {
  final private[emailaddress] val validDomain = """^([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)$""".r
  final private[emailaddress] val validEmail = """^((?!\.)(?!.*?\.\.)(?!.*\.$)[a-zA-Z0-9.!#$%&’'*+/=?^_`{|}~-]+)@([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)$""".r

  def isValid(email: String) = email match {
    case validEmail(_,_) => true
    case invalidEmail => false
  }

  case class Mailbox private[EmailAddress] (value: String) extends StringValue
  case class Domain(value: String) extends StringValue {
    value match {
      case EmailAddress.validDomain(_) => //
      case invalidDomain => throw new IllegalArgumentException(s"'$invalidDomain' is not a valid email domain")
    }
  }
}
