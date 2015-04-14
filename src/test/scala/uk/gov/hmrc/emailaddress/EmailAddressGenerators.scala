package uk.gov.hmrc.emailaddress

import org.scalacheck.Gen

trait EmailAddressGenerators {
  val nonEmptyString = Gen.nonEmptyListOf(Gen.alphaChar).map(_.mkString(""))

  val validMailbox = nonEmptyString

  val validDomain = for {
    tld <- nonEmptyString
    otherParts <- Gen.listOf(nonEmptyString)
  } yield (otherParts :+ tld).mkString(".")

  val validEmailAddresses = for {
    mailbox <- validMailbox
    domain <- validDomain
  } yield s"$mailbox@$domain"
}
