package uk.gov.hmrc.scala_emailaddress

import org.scalatest.{Matchers, WordSpec}

class ObfuscateedEmailSpec extends WordSpec with Matchers {

  "Obfuscating an email address" should {
    "work for a valid email address with a long mailbox" in {
      ObfuscatedEmail("abcdef@example.com").value should be("a****f@example.com")
    }

    "work for a valid email address with a three letter mailbox" in {
      ObfuscatedEmail("abc@example.com").value should be("a*c@example.com")
    }

    "work for a valid email address with a two letter mailbox" in {
      ObfuscatedEmail("ab@example.com").value should be("**@example.com")
    }

    "work for a valid email address with a single letter mailbox" in {
      ObfuscatedEmail("a@example.com").value should be("*@example.com")
    }

    "generate an exception for an invalid email address" in {
      an[IllegalArgumentException] should be thrownBy { ObfuscatedEmail("sausages") }
    }

    "generate an exception for an empty email" in {
      an[IllegalArgumentException] should be thrownBy { ObfuscatedEmail("") }
    }
  }
}
