package uk.gov.hmrc.emailaddress

object StringValue {
  import scala.language.implicitConversions
  implicit def stringValueToString(e: StringValue): String = e.value
}

trait StringValue {
  val value: String
  override def toString: String = value
}