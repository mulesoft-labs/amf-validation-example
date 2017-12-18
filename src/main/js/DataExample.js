var Promise = require("promise");
var rdfstore = require("rdfstore");
var rdflib = require("rdflib");
var amf = require("@mulesoft/amf-client-js");
var fs = require("fs");


// We initialise AMF
amf.plugins.document.WebApi.register();
amf.plugins.document.Vocabularies.register(); // we are going to parse a RAML Vocabulary file, we need the Vocabulary Plugin
amf.plugins.features.AMFValidation.register(); // we are going to perform custom validations, we need the validation Plugin

var profileUrl = "file://examples/profile.raml";
var apiUrl =  "file://examples/api.raml";

amf.Core.init().then(function () {
  var parser = amf.Core.parser("RAML Vocabularies", "application/yaml")

  // we parse all the models
  parser.parseFileAsync(apiUrl).then(function(apiModel) {
    parser.parseFileAsync(profileUrl).then(function(profileModel) {
      // we load the models in a RDF store
      store(apiModel, profileModel)
      // we run some queries
      .then(query("queries/endpoints_xsd_string.sparql"))
      .catch(function(err) {
        console.log("Error running queries");
        console.log(err);
      });
    });
  })
});

function store(apiModel, profileModel) {
  return new Promise(function(resolve, reject) {
    rdfstore.create(function(err, store) {
      if (err)
        reject(err)
      else
        load(store, apiModel)
        .then(resolve)
        .catch(reject)
     });
  });
}


function load(store, model) {
  return new Promise(function(resolve, reject) {
    store.load("application/ld+json", jsonld(model), function(err, res) {
      if (err != null)
        reject(err);
      else
        resolve(store);
    });
  });
}

function jsonld(model) {
  return amf.Core.generator("AMF Graph", "application/ld+json").generateString(model);
}

function query(queryFile) {
  return function(store) {
    return new Promise(function(resolve, reject) {
      var query = fs.readFileSync(queryFile).toString();
      console.log("Query " + queryFile);
      console.log(query);
      store.execute(query, function(err, results) {
        if (err != null)
          reject(err);
        else {
          console.log("\n")
          for(var i=0; i<results.length; i++)
            console.log(JSON.stringify(results[i]));
          console.log("\n")
          console.log("Found " + results.length +" results");
          resolve(store);
        }
      });
    });
  };
}