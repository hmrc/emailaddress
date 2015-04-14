package uk.gov.hmrc.emailaddress

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
