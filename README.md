emailaddress
==================

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
EmailAddress classes `toString` and implicitly convert to `String`s nicely:

```scala
scala> val someString: String = EmailAddress("example@test.com")
someString: String = example@test.com

scala> val someString = EmailAddress("example@test.com").toString
someString: String = example@test.com

scala> val someString: String = ObfuscatedEmailAddress("example@test.com")
someString: String = e*****e@test.com

scala> val someString = ObfuscatedEmailAddress("example@test.com").toString
someString: String = e*****e@test.com
```

### Installing

Include the following dependency in your SBT build

```scala
resolvers += Resolver.bintrayRepo("hmrc", "releases")

libraryDependencies += "uk.gov.hmrc" %% "emailaddress" % "x.x.x"
```

## License ##
 
This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
