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

import org.scalatest.prop.PropertyChecks
import org.scalatest.{Matchers, WordSpec}

class ObfuscateedEmailAddressSpec extends WordSpec with Matchers with PropertyChecks with EmailAddressGenerators {

  "Obfuscating an email address" should {
    "work for a valid email address with a long mailbox" in {
      ObfuscatedEmailAddress("abcdef@example.com").value should be("a****f@example.com")
    }

    "work for a valid email address with a single letter mailbox" in {
      ObfuscatedEmailAddress("a@example.com").value should be("*@example.com")
    }

    "work for a valid email address with a two letter mailbox" in {
      ObfuscatedEmailAddress("ab@example.com").value should be("**@example.com")
    }

    "work for a valid email address with a three letter mailbox" in {
      ObfuscatedEmailAddress("abc@example.com").value should be("a*c@example.com")
    }

    "do nothing for a valid email address with a three letter mailbox with * in the middle" in {
      ObfuscatedEmailAddress("a*c@example.com").value should be("a*c@example.com")
    }

    "work for valid email addresses with a mailbox longer than three chars" in {
      forAll (validEmailAddresses(mailbox = validMailbox.suchThat(_.length > 3))) { address =>
        EmailAddress(address).obfuscated.value should ((not be address) and include("*"))
      }
    }

    "generate an exception for an invalid email address" in {
      an[IllegalArgumentException] should be thrownBy { ObfuscatedEmailAddress("sausages") }
    }

    "generate an exception for empty" in {
      an[IllegalArgumentException] should be thrownBy { ObfuscatedEmailAddress("") }
    }
  }
  "An ObfuscatedEmailAddress class" should {
    "implicitly convert to an obfuscated String of the address" in {
      val e: String = ObfuscatedEmailAddress("test@domain.com")
      e should be ("t**t@domain.com")
    }
    "toString to an obfuscated String of the address" in {
      val e = ObfuscatedEmailAddress("test@domain.com")
      e.toString should be ("t**t@domain.com")
    }
  }
}
