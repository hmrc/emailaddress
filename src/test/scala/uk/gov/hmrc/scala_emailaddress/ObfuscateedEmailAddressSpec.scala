package uk.gov.hmrc.scala_emailaddress

import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}

class ObfuscateedEmailAddressSpec extends WordSpec with Matchers with PropertyChecks with EmailAddressGenerators {

  "Obfuscating an email address" should {
    "work for a valid email address with a long mailbox" in {
      ObfuscatedEmailAddress("abcdef@example.com").value should be("a****f@example.com")
    }

    "work for a valid email address with a three letter mailbox" in {
      ObfuscatedEmailAddress("abc@example.com").value should be("a*c@example.com")
    }

    "work for a valid email address with a two letter mailbox" in {
      ObfuscatedEmailAddress("ab@example.com").value should be("**@example.com")
    }

    "work for a valid email address with a single letter mailbox" in {
      ObfuscatedEmailAddress("a@example.com").value should be("*@example.com")
    }

    "work for valid email addresses" in {
      forAll (validEmailAddresses) { address =>
        ObfuscatedEmailAddress(address).value should ((not be address) and include("*"))
      }
    }

    "generate an exception for an invalid email address" in {
      an[IllegalArgumentException] should be thrownBy { ObfuscatedEmailAddress("sausages") }
    }

    "generate an exception for empty" in {
      an[IllegalArgumentException] should be thrownBy { ObfuscatedEmailAddress("") }
    }
  }
}
