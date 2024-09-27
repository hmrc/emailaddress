## emailaddress

This library is Deprecated.
You can reach out Digital-Contact team for further assistance.<br>
Alternatively you can write you own EmailAddress class as in the following example: https://github.com/hmrc/preferences-frontend/blob/main/app/emailaddress/EmailAddress.scala


[![Join the chat at https://gitter.im/hmrc/emailaddress](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/hmrc/emailaddress?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge) [![Build Status](https://travis-ci.org/hmrc/emailaddress.svg?branch=master)](https://travis-ci.org/hmrc/emailaddress) [ ![Download](https://api.bintray.com/packages/hmrc/releases/emailaddress/images/download.svg) ](https://bintray.com/hmrc/releases/emailaddress/_latestVersion)

Scala micro-library for typing, validating and obfuscating email addresses

### Address Typing & Validation
The `EmailAddress` class will only accept valid addresses:

```scala
scala> import uk.gov.hmrc.emailaddress._
import uk.gov.hmrc.emailaddress._

scala> EmailAddress("example@test.com")
res0: uk.gov.hmrc.emailaddress.EmailAddress = example@test.com

scala> EmailAddress("not_a_meaningful_address")
java.lang.IllegalArgumentException: requirement failed: 'not_a_meaningful_address' is not a valid email address
```

You can also use `EmailAddress.isValid(...)`:

```scala
scala> EmailAddress.isValid("example@test.com")
res2: Boolean = true

scala> EmailAddress.isValid("not_a_meaningful_address")
res3: Boolean = false
```

### Accessing the domain and mailbox

You can access the mailbox and domain of a given address:

```scala
scala> EmailAddress("example@test.com").domain
res0: uk.gov.hmrc.emailaddress.EmailAddress.Domain = test.com

scala> EmailAddress("example@test.com").mailbox
res1: uk.gov.hmrc.emailaddress.EmailAddress.Mailbox = example
```

These compare equal as you might expect:

```scala
scala> EmailAddress("example@test.com").domain == EmailAddress("another@test.com").domain
res2: Boolean = true

scala> EmailAddress("example@test.com").domain == EmailAddress("another@test.co.uk").domain
res3: Boolean = false
```

### Obfuscation
Addresses are obfuscated by starring out all of their mailbox part, apart from the first and last letters:

```scala
scala> ObfuscatedEmailAddress("example@test.com")
res4: uk.gov.hmrc.emailaddress.ObfuscatedEmailAddress = e*****e@test.com
```
Unless there are only two letters:

```scala
scala> ObfuscatedEmailAddress("ex@test.com")
res7: uk.gov.hmrc.emailaddress.ObfuscatedEmailAddress = **@test.com```

```

You can also create them directly from an `EmailAddress`:

```scala
scala> EmailAddress("example@test.com").obfuscated
res6: uk.gov.hmrc.emailaddress.ObfuscatedEmailAddress = e*****e@test.com
```


### Converting back to `String`
All classes `toString` and implicitly convert to `String`s nicely:

```scala
scala> val someString: String = EmailAddress("example@test.com")
someString: String = example@test.com

scala> val someString = EmailAddress("example@test.com").toString
someString: String = example@test.com

scala> val someString: String = ObfuscatedEmailAddress("example@test.com")
someString: String = e*****e@test.com

scala> val someString = ObfuscatedEmailAddress("example@test.com").toString
someString: String = e*****e@test.com

scala> EmailAddress("example@test.com").domain.toString
res4: String = test.com

scala> val s: String = EmailAddress("example@test.com").domain
s: String = test.com

scala> EmailAddress("example@test.com").mailbox.toString
res5: String = example

scala> val s: String = EmailAddress("example@test.com").mailbox
s: String = example
```

### Installing

Include the following dependency in your SBT build **before v4.0.0**

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "emailaddress" % "<INSERT VERSION>"
```

Include one the following dependencies in your SBT build for **v4.0.0 or after** depending on whether you are using Play 2.8, 
Play 2.9 or Play 3.0
```scala
libraryDependencies += "uk.gov.hmrc" %% "emailaddress-play-28" % "<INSERT VERSION>"

OR

libraryDependencies += "uk.gov.hmrc" %% "emailaddress-play-29" % "<INSERT VERSION>"

OR

libraryDependencies += "uk.gov.hmrc" %% "emailaddress-play-30" % "<INSERT VERSION>"
```

## Run the tests and sbt fmt before raising a PR

Format:

`sbt fmt`

Then run the tests and coverage report:

`sbt clean coverage test coverageReport`

If your build fails due to poor test coverage, *DO NOT* lower the test coverage threshold, instead inspect the generated report located here on your local repo: `/target/scala-2.12/scoverage-report/index.html`

Then run the integration tests:

`sbt it:test`

## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
