# AMF Validation Kit

This repository include resources to test AMF validation support

## Install

*Node JS*:
```sh
$ npm install
```

*Java*:
```sh
$ mvn install
```

## Documentation

- a short introduction to [AMF](documentation/amf_intro.pdf)
- the [validation mechanism](documentation/validation.md): how to use it from both the command line and programmatically, as well as how to create custom validations

## Examples

- Validation Example([JavaScript](src/main/js/ValidationExample.js)/[Java](src/main/java/com/mulesoft/amf/examples/ValidationExample.java)): Shows how validation and custom validation can be executed programmatically
- Data Example([JavaScript](src/main/js/DataExample.js)/[Java](src/main/java/com/mulesoft/amf/examples/DataExample.java)): Shows how AMF can be used to create a data model graph with the information from the API and the validation profile

E.g.
```sh
$ node src/main/js/DataExample.js
(...)
```

The sample RAML API file used in the examples is located [here](examples/api.raml). The [here]](examples/profile.raml) is the custom validation profile that can be used to test the library and as a starting point to test more complex validations. Also, sample (SPARQL) queries over the data model graph can be found in the [queries](queries) directory
