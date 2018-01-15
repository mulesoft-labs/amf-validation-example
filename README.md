# AMF Validation Example

This repository includes examples to test AMF "validation" feature.

## Install

*Node JS*:
```sh
$ npm install
```

*Java*:
```sh
$ mvn install
```

## Run the examples

*Node JS*:
```sh
$ node src/main/js/DataExample.js
(...)
```

*Java*:
```sh
$ java -cp ./target/amf-validation-example-1.0-SNAPSHOT-jar-with-dependencies.jar com.mulesoft.amf.examples.ValidationExample
(...)
```

- Validation Example ([JavaScript](src/main/js/ValidationExample.js)/[Java](src/main/java/com/mulesoft/amf/examples/ValidationExample.java)): Shows how validation and custom validation can be executed programmatically
- Data Example ([JavaScript](src/main/js/DataExample.js)/[Java](src/main/java/com/mulesoft/amf/examples/DataExample.java)): Shows how AMF can be used to create a data model graph with the information from the API and the validation profile

The [examples](examples) folder contains a sample [RAML API](examples/api.raml) as well as a sample custom [Validation Profile](examples/profile.raml) that can be used to test the library and as a starting point to test more complex validations. Also, sample (SPARQL) queries over the data model graph can be found in the [queries](queries) directory.

## Documentation

- a short introduction to [AMF](documentation/amf_intro.pdf)
- the [validation mechanism](documentation/validation.md): how to use it from both the command line and programmatically, as well as how to create custom validations