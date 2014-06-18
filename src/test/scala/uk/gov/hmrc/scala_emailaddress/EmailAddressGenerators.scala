package uk.gov.hmrc.scala_emailaddress

import org.scalacheck.Gen

trait EmailAddressGenerators {
  val validMailbox = Gen.alphaStr.suchThat(!_.isEmpty)
  val validDomain = Gen.nonEmptyListOf(Gen.alphaStr.suchThat(!_.isEmpty)).map(_.mkString("."))
  val validEmailAddresses = for {
    mailbox <- validMailbox
    domain <- validDomain
  } yield s"$mailbox@$domain"
}
