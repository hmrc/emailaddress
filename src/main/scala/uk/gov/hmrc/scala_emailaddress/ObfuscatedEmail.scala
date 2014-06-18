package uk.gov.hmrc.scala_emailaddress

trait ObfuscatedEmail {
  val value: String
  override def toString: String = value
}

object ObfuscatedEmail {
  final private val shortMailbox = "(.{1,2})".r
  final private val longMailbox = "(.)(.*)(.)".r

  import Email.validEmail

  def apply(email: String): ObfuscatedEmail = new ObfuscatedEmail {
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

