/*
 * Copyright 2022 HM Revenue & Customs
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

import org.scalacheck.Gen
import org.scalacheck.Gen._

trait EmailAddressGenerators {

  def nonEmptyString(char: Gen[Char]): Gen[String] =
    nonEmptyListOf(char)
      .map(_.mkString)
      .suchThat(_.nonEmpty)

  def chars(chars: String): Gen[Char] = Gen.choose(0, chars.length - 1).map(chars.charAt)

  val validMailbox: Gen[String] = nonEmptyString(oneOf(alphaChar, chars(".!#$%&’'*+/=?^_`{|}~-"))).label("mailbox")

  val validDomain: Gen[String] = (for {
    topLevelDomain <- nonEmptyString(alphaChar)
    otherParts <- listOf(nonEmptyString(alphaChar))
  } yield (otherParts :+ topLevelDomain).mkString(".")).label("domain")

  def validEmailAddresses(mailbox: Gen[String] = validMailbox, domain: Gen[String] = validDomain): Gen[String] =
    for {
      mailbox <- mailbox
      domain <- domain
    } yield s"$mailbox@$domain"
}
