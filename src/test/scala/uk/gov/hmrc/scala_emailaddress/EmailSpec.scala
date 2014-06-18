package uk.gov.hmrc.scala_emailaddress

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}

class EmailSpec extends WordSpec with Matchers with PropertyChecks {

  val validEmailAddresses = for {
    mailbox <- Gen.alphaStr if !mailbox.isEmpty
    domain <- Gen.nonEmptyListOf(Gen.alphaStr.suchThat(!_.isEmpty))
  } yield s"$mailbox@${domain.mkString(".")}"

  "Creating an Email class" should {
    "work for a valid email" in {
      forAll (validEmailAddresses) { (address: String) =>
        Email(address).value should be(address)
      }
    }

    "throw an exception for an invalid email" in {
      an [IllegalArgumentException] should be thrownBy { Email("sausages") }
    }

    "throw an exception for an empty email" in {
      an [IllegalArgumentException] should be thrownBy { Email("") }
    }

    "throw an exception for a repeated email" in {
      an[IllegalArgumentException] should be thrownBy { Email("test@domain.comtest@domain.com") }
    }

    "throw an exception when the '@' is missing" in {
      forAll { s: String => whenever(!s.contains("@")) {
        an[IllegalArgumentException] should be thrownBy { Email(s) }
      }}
    }
  }

  "An Email class" should {
    "implicitly convert to a String of the address" in {
      val e: String = Email("test@domain.com")
      e should be ("test@domain.com")
    }
    "toString to a String of the address" in {
      val e = Email("test@domain.com")
      e.toString should be ("test@domain.com")
    }
    "be obfuscatable" in {
      Email("abcdef@example.com").obfuscated.value should be("a****f@example.com")
    }
  }
}
