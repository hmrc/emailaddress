package uk.gov.hmrc.emailaddress

import play.api.data.FormError
import org.scalatest.prop.PropertyChecks
import org.scalatest.Matchers
import org.scalatest.WordSpec
import play.api.data.Forms.optional

class PlayFormFormatterSpec extends WordSpec with Matchers with PropertyChecks with EmailAddressGenerators {

  "PlayFormFormatter.emailAddress mapping" should {

    "accept valid emails" in forAll(validEmailAddresses) {
      address =>
        emailAddressMapping.bind(Map("email" -> address)).right.get shouldBe EmailAddress(address)
    }

    "accept valid emails with padding" in forAll(validEmailAddresses) {
      address =>
        emailAddressMapping.bind(Map("email" -> s" ${address} ")).right.get shouldBe EmailAddress(address)
    }

    "reject missing or empty field" in {
      emptyEmailInputData.foreach(
        address =>
          emailAddressMapping.bind(data = address).left.get shouldBe List(FormError("email", List("error.required"), List())))
    }

    "reject missing or empty field (using custom error message)" in {
      emptyEmailInputData.foreach(
        address =>
          emailAddressCustomErrorsMapping.bind(data = address).left.get shouldBe List(FormError("email", List("field.required"), List())))
    }

    "allow optional emailAddress mapping" in {
      optionalEmailAddressMapping.bind(data = Map()).right.get shouldBe None
      optionalEmailAddressMapping.bind(data = Map("email" -> "")).right.get shouldBe None
      optionalEmailAddressMapping.bind(data = Map("email" -> "example@example")).right.get shouldBe Some(EmailAddress("example@example"))
    }

    "reject invalid email" in {
      invalidEmailInputData.foreach(
        address =>
          emailAddressMapping.bind(data = address).left.get shouldBe List(FormError("email", List("error.email"), List())))
    }

    "reject invalid email (using custom error message)" in {
      invalidEmailInputData.foreach(
        address =>
          emailAddressCustomErrorsMapping.bind(data = address).left.get shouldBe List(FormError("email", List("field.invalid"), List())))
    }

    "reject too long email address (when using 'emailMaxLength' constraint)" in {
      emailAddressMapping.verifying { maxLengthConstraint }.bind(Map("email" -> "aaa@bbb.ccc")).left.get shouldBe
        List(FormError("email", List("error.maxLength"), List(10)))
    }

    "reject too long email address (when using 'emailMaxLength' constraint and custom error)" in {
      emailAddressMapping.verifying { maxLengthConstraintWithError }.bind(Map("email" -> "aaa@bbb.ccc")).left.get shouldBe
        List(FormError("email", List("field.exceeds"), List(10)))
    }

    "reject email address which does not match given regex" in {
      val errors: Seq[FormError] = emailAddressMapping.verifying { patternConstraint }.bind(Map("email" -> "example@example")).left.get
      errors.size shouldBe 1
      val headError = errors.head
      headError.key shouldBe "email"
      headError.messages.size shouldBe 1
      headError.messages.head shouldBe "error.pattern"
      headError.args.size shouldBe 1
      headError.args.head.toString() shouldBe """(.+)(\.[a-zA-Z0-9-]*)$"""
    }

    "reject email address which does not match given regex (with custom error)" in {
      val errors: Seq[FormError] = emailAddressMapping.verifying { patternConstraintWithError }.bind(Map("email" -> "example@example")).left.get
      errors.size shouldBe 1
      val headError = errors.head
      headError.key shouldBe "email"
      headError.messages.size shouldBe 1
      headError.messages.head shouldBe "field.pattern"
      headError.args.size shouldBe 1
      headError.args.head.toString() shouldBe """(.+)(\.[a-zA-Z0-9-]*)$"""
    }
  }

  def emailAddressMapping =
    PlayFormFormatter.emailAddress.withPrefix("email")

  def emailAddressCustomErrorsMapping =
    PlayFormFormatter.emailAddress(errorRequired = "field.required", errorEmail = "field.invalid").withPrefix("email")

  def maxLengthConstraint =
    PlayFormFormatter.emailMaxLength(maxLength = 10)

  def maxLengthConstraintWithError =
    PlayFormFormatter.emailMaxLength(maxLength = 10, error = "field.exceeds")

  def optionalEmailAddressMapping =
    optional[EmailAddress](PlayFormFormatter.emailAddress.withPrefix("email"))

  def patternConstraint = {
    val HAS_TLD = """(.+)(\.[a-zA-Z0-9-]*)$""".r
    PlayFormFormatter.emailPattern(HAS_TLD)
  }

  def patternConstraintWithError = {
    val HAS_TLD = """(.+)(\.[a-zA-Z0-9-]*)$""".r
    PlayFormFormatter.emailPattern(HAS_TLD, error = "field.pattern")
  }

  def emptyEmailInputData: Seq[Map[String, String]] =
    Seq(Map(), Map("email" -> ""), Map("email" -> " "))

  def invalidEmailInputData: Seq[Map[String, String]] =
    Seq(Map("email" -> "test"), Map("email" -> "test@"), Map("email" -> "@test"), Map("email" -> "test@example.comtest@example.com"))
}