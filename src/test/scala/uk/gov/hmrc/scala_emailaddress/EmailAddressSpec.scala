package uk.gov.hmrc.scala_emailaddress

import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}

class EmailAddressSpec extends WordSpec with Matchers with PropertyChecks with EmailAddressGenerators {

  "Creating an EmailAddress class" should {
    "work for a valid email" in {
      forAll (validEmailAddresses) { address =>
        EmailAddress(address).value should be(address)
      }
    }

    "throw an exception for an invalid email" in {
      an [IllegalArgumentException] should be thrownBy { EmailAddress("sausages") }
    }

    "throw an exception for an empty email" in {
      an [IllegalArgumentException] should be thrownBy { EmailAddress("") }
    }

    "throw an exception for a repeated email" in {
      an[IllegalArgumentException] should be thrownBy { EmailAddress("test@domain.comtest@domain.com") }
    }

    "throw an exception when the '@' is missing" in {
      forAll { s: String => whenever(!s.contains("@")) {
        an[IllegalArgumentException] should be thrownBy { EmailAddress(s) }
      }}
    }
  }

  "An EmailAddress class" should {
    "implicitly convert to a String of the address" in {
      val e: String = EmailAddress("test@domain.com")
      e should be ("test@domain.com")
    }
    "toString to a String of the address" in {
      val e = EmailAddress("test@domain.com")
      e.toString should be ("test@domain.com")
    }
    "be obfuscatable" in {
      EmailAddress("abcdef@example.com").obfuscated.value should be("a****f@example.com")
    }
  }
}
