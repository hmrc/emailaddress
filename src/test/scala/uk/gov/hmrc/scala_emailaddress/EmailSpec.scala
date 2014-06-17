package uk.gov.hmrc.scala_emailaddress

import org.scalatest.{Matchers, WordSpec}

class EmailSpec extends WordSpec with Matchers {

  "Creating an Email class" should {
    "work for a valid email" in {
      val e = Email("test@domain.com")
      e.value should be ("test@domain.com")
    }

    "throw an exception for an invalid email" in {
      evaluating { Email("sausages") } should produce[IllegalArgumentException]
    }

    "throw an exception for an empty email" in {
      evaluating { Email("") } should produce[IllegalArgumentException]
    }
  }

  "Obfuscating an email address" should {
    "work for a valid email address with a long mailbox" in {
      Email.obfuscate("abcdef@example.com").value should be ("a****f@example.com")
    }

    "work for a valid email address with a three letter mailbox" in {
      Email.obfuscate("abc@example.com").value should be ("a*c@example.com")
    }

    "work for a valid email address with a two letter mailbox" in {
      Email.obfuscate("ab@example.com").value should be ("**@example.com")
    }

    "work for a valid email address with a single letter mailbox" in {
      Email.obfuscate("a@example.com").value should be ("*@example.com")
    }

    "generate an exception for an invalid email address" in {
        evaluating { Email.obfuscate("sausages") } should produce[IllegalArgumentException]
    }

    "generate an exception for an empty email" in {
      evaluating { Email.obfuscate("") } should produce[IllegalArgumentException]
    }

    "work directly from the class" in {
      Email("abcdef@example.com").obfuscated.value should be ("a****f@example.com")
    }
  }
}
