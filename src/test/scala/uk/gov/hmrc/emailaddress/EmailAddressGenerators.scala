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

import org.scalacheck.Gen._
import org.scalacheck.{Gen, Shrink}

trait EmailAddressGenerators {
  def noShrink[T] = Shrink[T](_ => Stream.empty)
  implicit val dontShrinkStrings: Shrink[String] = noShrink[String]

  def nonEmptyString(char: Gen[Char]) =
    nonEmptyListOf(char)
      .map(_.mkString)
      .suchThat(!_.isEmpty)

  def chars(chars: String) = Gen.choose(0, chars.length - 1).map(chars.charAt)

  val validMailbox = nonEmptyString(oneOf(alphaChar, chars(".!#$%&’'*+/=?^_`{|}~-"))).label("mailbox")

  val validDomain = (for {
    topLevelDomain <- nonEmptyString(alphaChar)
    otherParts <- listOf(nonEmptyString(alphaChar))
  } yield (otherParts :+ topLevelDomain).mkString(".")).label("domain")

  def validEmailAddresses(mailbox: Gen[String] = validMailbox, domain: Gen[String] = validDomain) =
    for {
      mailbox <- mailbox
      domain <- domain
    } yield s"$mailbox@$domain"
}
