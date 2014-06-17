package uk.gov.hmrc.scala_emailaddress

case class Email(value: String) {
  require(Email.isValid(value), s"'$value' is not a valid email address")

  override def toString: String = value

  lazy val obfuscated = Email.obfuscate(value)
}

trait ObfuscatedEmail {
  val value: String
  override def toString: String = value
}

object Email {
  final private[this] val validEmail = """\b([a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+)@([a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*)\b""".r

  def isValid(email: String) = email match {
    case validEmail(_,_) => true
    case invalidEmail => false
  }

  implicit def emailToString(e: Email): String = e.value

  final private val shortMailbox = "(.{1,2})".r
  final private val longMailbox = "(.)(.*)(.)".r

  def obfuscate(email: String): ObfuscatedEmail = new ObfuscatedEmail {
    val value = email match {
      case validEmail(shortMailbox(m), domain) =>
        s"${obscure(m)}@$domain"

      case validEmail(longMailbox(firstLetter,middle,lastLetter), domain) =>
        s"$firstLetter${obscure(middle)}$lastLetter@$domain"

      case invalidEmail =>
        throw new IllegalArgumentException(s"Cannot obfuscate invalid email address $invalidEmail")
    }
  }

  private def obscure(text: String) = "*" * text.length
}