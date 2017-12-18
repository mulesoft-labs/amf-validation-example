# AMF Validation Kit

This repository include resources to test AMF validation support

## Documentation

Located in the [documentation](documentation) directory.

A small introduction to [AMF](documentation/amf_intro.pdf).

[Detailed description](documentation/validation.md) of the validation mechanism, how to use it from the command line and programmatically as well as how to create custom validations.

## Example API and validation profile

Located in the [examples](examples) directory.
A sample [API](examples/api.raml) and custom [validation profile](examples/profile.raml) that can be used to test the library and as a starting point to test more complex validations.

## Programmatic examples

Located in the [src](src) directory.
Sample Java examples that use AMF to work with the validation.

- [ValidationExample.java](src/main/java/com/mulesoft/amf/examples/ValidationExample.java)

Shows how validation and custom validation can be executed programmatically

- [DataExample.java](src/main/java/com/mulesoft/amf/examples/DataExample.java)

Shows how AMF can be used to create a data model graph with the information from the API and the validation profile.

Equivalent JavaScript examples can be found at [ValidationExample.js](src/main/js/ValidationExample.js) and [DataExample.js](src/main/js/DataExample.js).

Sample queries over the data model graph can be found in the [queries](queries) directory
