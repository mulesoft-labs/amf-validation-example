var Promise = require("promise");
var amf = require("amf-client-js");

// We initialise AMF
amf.plugins.document.WebApi.register();
amf.plugins.features.AMFValidation.register();

amf.Core.init().then(function () {
  // create a RAML parser
  var parser = amf.Core.parser("RAML 1.0", "application/yaml");

  // we parse the model and run validations
  parser.parseFileAsync("file://examples/api.raml")
    .then(function(model) { return validate("RAML", model) }) // Validating using the default RAML validation profile
    .then(function(model) { return validate("OpenAPI", model) }) // Validating using the OpenAPI validatinp rofile
    .then(function(model) { return validateCustom("Banking","file://examples/profile.raml", model) }) // Validating using a custom profile
    .catch(function(err) {
      console.log("Error validating")
      console.log(err);
    });
});

/*
 In a standard validation we just need to provide the right value for one of the standard
 supported profiles: RAML, OpenAPI or AMF
*/
function validate(profile, model) {
  return new Promise(function(resolve, reject){
    amf.Core.validate(model, profile, profile)
      .then(function(report) {
        printReport(profile, report)
        resolve(model);
      })
      .catch(reject)
  })
}

/*
 In a custom validation, we first need to load the validation profile.
 Then we can use the profile name to validate a parsed model.
*/
function validateCustom(dialect, profile, model) {
  return new Promise(function(resolve, reject) {
    amf.Core.loadValidationProfile(profile)
      .then(function() {
        amf.Core.validate(model, dialect, dialect)
          .then(function(report) {
            printReport(dialect, report)
            resolve();
          })
          .catch(reject)
      }).catch(reject)
  })
}

function printReport(dialect, report) {
  console.log(report.toString())
  /*
  if (report.conforms == true) {
    console.log("Document validates profile " + dialect);
  } else {
    console.log("Document DOES NOT validate profile " + dialect);
    console.log(report.toString());
  }
  */
}