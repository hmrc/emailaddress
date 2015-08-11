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

import org.joda.time.LocalDate
import org.scalatest.{ Matchers, WordSpec }
import play.api.data.FormError

class EmailAddressFormatterSpecs extends WordSpec with Matchers {

  "EmailAddress formatter" should {

    "unbind correct Email" in {
      EmailAddressFormatter.emailAddress().unbind("email", EmailAddress("example@example.com")) shouldBe Map("email" -> "example@example.com")
    }

    "create a mapping for a valid Email" in {
      val result = EmailAddressFormatter.emailAddress().bind("email", Map("email" -> "example@example.com"))
      result.isRight shouldBe true
      result.right.get shouldBe EmailAddress("example@example.com")
    }

    "create a mapping for a valid Email (no TLD)" in {
      val result = EmailAddressFormatter.emailAddress().bind("email", Map("email" -> "example@example.com"))
      result.isRight shouldBe true
      result.right.get shouldBe EmailAddress("example@example.com")
    }

    "create a mapping for a valid Email with trailing and leading spaces" in {
      val result = EmailAddressFormatter.emailAddress().bind("email", Map("email" -> "  example@example.com  "))
      result.isRight shouldBe true
      result.right.get shouldBe EmailAddress("example@example.com")
    }

    "create a mapping for a valid Email if field size limit is not exceeded" in {
      val result = EmailAddressFormatter.emailAddress(maxLength = Some(19)).bind("email", Map("email" -> "  example@example.com  "))
      result.isRight shouldBe true
      result.right.get shouldBe EmailAddress("example@example.com")
    }

    "return a validation error if Email exceeds field size limit, and default error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress(maxLength = Some(18)).bind("email", Map("email" -> "example@example.com"))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("error.max-length-violation"), List()))
    }

    "return a validation error if Email is not provided, and default error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress().bind("email", Map())
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("error.required"), List()))
    }

    "return a validation error if Email field is empty, and default error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress().bind("email", Map())
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("error.required"), List()))
    }

    "return a validation error if Email is invalid, and default error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress().bind("email", Map("email" -> "example"))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("error.email"), List()))
    }

    def customMessageKeyBuilder(key:String, error: String): String = {
      s"custom-error.form.field.${error}.${key}"
    }

    "return a validation error if Email exceeds field size limit, and custom error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress(maxLength = Some(18), messageKeyBuilder = Some(customMessageKeyBuilder)).bind("email", Map("email" -> "example@example.com"))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("custom-error.form.field.max-length-violation.email"), List()))
    }

    "return a validation error if Email is not provided, and custom error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress(messageKeyBuilder = Some(customMessageKeyBuilder)).bind("email", Map())
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("custom-error.form.field.required.email"), List()))
    }

    "return a validation error if Email field is empty, and custom error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress(messageKeyBuilder = Some(customMessageKeyBuilder)).bind("email", Map())
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("custom-error.form.field.required.email"), List()))
    }

    "return a validation error if Email is invalid, and custom error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress(messageKeyBuilder = Some(customMessageKeyBuilder)).bind("email", Map("email" -> "example"))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("custom-error.form.field.email.email"), List()))
    }

    val emailRestriction = """^([a-zA-Z0-9\-\_]+[.])*[a-zA-Z0-9\-\_]+@([a-zA-Z0-9-]{2,}[.])+[a-zA-Z0-9-]+$""".r

    "return a validation error if Email is valid but fails additional validation, and default error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress(emailRestriction = Some(emailRestriction)).bind("email", Map("email" -> "example@example"))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("error.email"), List()))
    }

    "return a validation error if Email is valid but fails additional validation, and custom error message key generator is used" in {
      val result = EmailAddressFormatter.emailAddress(emailRestriction = Some(emailRestriction), messageKeyBuilder = Some(customMessageKeyBuilder)).bind("email", Map("email" -> "example@example"))
      result.isLeft shouldBe true
      result.left.get shouldBe Seq(FormError("email", List("custom-error.form.field.email.email"), List()))
    }
  }
}
