package uk.gov.hmrc.scala_emailaddress

case class EmailAddress(value: String) {
  require(EmailAddress.isValid(value), s"'$value' is not a valid email address")

  override def toString: String = value

  lazy val obfuscated = ObfuscatedEmailAddress.apply(value)
}

object EmailAddress {
  final private[scala_emailaddress] val validEmail = """\b([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+)@([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)\b""".r

  def isValid(email: String) = email match {
    case validEmail(_,_) => true
    case invalidEmail => false
  }

  implicit def emailToString(e: EmailAddress): String = e.value
}