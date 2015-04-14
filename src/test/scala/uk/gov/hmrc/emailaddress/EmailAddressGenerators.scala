package uk.gov.hmrc.emailaddress

import org.scalacheck.Gen._

trait EmailAddressGenerators {

  val nonEmptyString = nonEmptyListOf(alphaChar).map(_.mkString)

  val validMailbox = nonEmptyString

  val validDomain = for {
    topLevelDomain <- nonEmptyString
    otherParts <- listOf(nonEmptyString)
  } yield (otherParts :+ topLevelDomain).mkString

  val validEmailAddresses = for {
    mailbox <- validMailbox
    domain <- validDomain
  } yield s"$mailbox@$domain"
}
