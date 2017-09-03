FROM hseeberger/scala-sbt

WORKDIR /emailaddress

ADD . /emailaddress

CMD sbt run

RUN sbt test package