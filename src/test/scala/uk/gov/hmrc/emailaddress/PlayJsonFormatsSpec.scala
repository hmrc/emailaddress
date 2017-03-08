/*
 * Copyright 2017 HM Revenue & Customs
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

import org.scalatest.{WordSpec, Matchers}
import play.api.libs.json.{Json, JsError, JsSuccess, JsString}

class PlayJsonFormatsSpec extends WordSpec with Matchers {

  import PlayJsonFormats._

  "Reading an EmailAddress from JSON" should {

    "work for a valid email address" in {
      val result = JsString("a@b.com").validate[EmailAddress]
      result shouldBe a [JsSuccess[_]]
      result.get should be (EmailAddress("a@b.com"))
    }

    "fail for a invalid email address" in {
      val result = JsString("ab.com").validate[EmailAddress]
      result shouldBe a [JsError]
    }
  }

  "Writing an EmailAddress to JSON" should {

    "work!" in {
      Json.toJson(EmailAddress("a@b.com")) should be (JsString("a@b.com"))
    }
  }
}
