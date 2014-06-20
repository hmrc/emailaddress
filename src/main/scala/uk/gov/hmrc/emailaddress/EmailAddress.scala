package uk.gov.hmrc.emailaddress


case class EmailAddress(value: String) {
  require(EmailAddress.isValid(value), s"'$value' is not a valid email address")

  override def toString: String = value

  lazy val obfuscated = ObfuscatedEmailAddress.apply(value)
}

object EmailAddress {
  final private[emailaddress] val validEmail = """\b([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+)@([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)\b""".r

  def isValid(email: String) = email match {
    case validEmail(_,_) => true
    case invalidEmail => false
  }

  implicit def emailToString(e: EmailAddress): String = e.value

  lazy implicit val emailAddressFormat = PlayJsonFormats.emailAddressFormat

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
  implicit val emailAddressFormat = Format(emailAddressReads, emailAddressWrites)
}
