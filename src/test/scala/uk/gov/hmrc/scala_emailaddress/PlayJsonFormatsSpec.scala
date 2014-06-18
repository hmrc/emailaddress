package uk.gov.hmrc.scala_emailaddress

import org.scalatest.{WordSpec, Matchers}
import play.api.libs.json.{Json, JsError, JsSuccess, JsString}

class PlayJsonFormatsSpec extends WordSpec with Matchers {
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
