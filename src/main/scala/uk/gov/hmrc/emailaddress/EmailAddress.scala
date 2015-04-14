package uk.gov.hmrc.emailaddress

trait StringValue {
  val value: String
  override def toString: String = value
}
object StringValue {
  import scala.language.implicitConversions
  implicit def stringValueToString(e: StringValue): String = e.value
}

case class EmailAddress(value: String) extends StringValue {

  val (mailbox, domain): (EmailAddress.Mailbox, EmailAddress.Domain) = value match {
    case EmailAddress.validEmail(m, d) => (EmailAddress.Mailbox(m), EmailAddress.Domain(d))
    case invalidEmail => throw new IllegalArgumentException(s"'$invalidEmail' is not a valid email address")
  }

  lazy val obfuscated = ObfuscatedEmailAddress.apply(value)
}

object EmailAddress {
  final private[emailaddress] val validDomain = """\b([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)\b""".r
  final private[emailaddress] val validEmail = """\b([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+)@([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)\b""".r

  def isValid(email: String) = email match {
    case validEmail(_,_) => true
    case invalidEmail => false
  }

  case class Mailbox private[EmailAddress] (value: String) extends StringValue
  case class Domain(value: String) extends StringValue {
    value match {
      case EmailAddress.validDomain(_) => //
      case invalidDomain => throw new IllegalArgumentException(s"'$invalidDomain' is not a valid email domain")
    }
  }
}

object PlayJsonFormats {
  import play.api.libs.json._

  implicit val emailAddressReads = new Reads[EmailAddress] {
    def reads(js: JsValue): JsResult[EmailAddress] = js.validate[String].flatMap {
      case s if EmailAddress.isValid(s) => JsSuccess(EmailAddress(s))
      case s => JsError("not a valid email address")
    }
  }
  implicit val emailAddressWrites = new Writes[EmailAddress] {
    def writes(e: EmailAddress): JsValue = JsString(e.value)
  }
}
