package com.mulesoft.amf.examples;


import amf.core.client.Parser;
import amf.validation.AMFValidationReport;
import amf.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

/**
 * Validation example, parsing and then validating using different profiles
 */
public class ValidationExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // We initialise AMF
        amf.plugins.document.WebApi.register(); // we need the WebAPI plugin to parse RAML specs
        amf.plugins.features.AMFValidation.register(); // we need the AMF Validation plugin for custom validations
        amf.Core.init().get(); // we block here

        // Create a RAML parser
        final Parser parser = amf.Core.parser("RAML 1.0", "application/yaml");

        // We parse the model
        final BaseUnit model = parser.parseFileAsync("file://examples/api.raml").get();

        // Validating using the default RAML validation profile
        validate("RAML", model);

        // Validating using the default OpenAPI validation profile
        validate("OpenAPI", model);

        // Custom profile validation
        validateCustom("Banking", "file://examples/profile.raml", model);
    }

    /*
      In a standard validation we just need to provide the right value for one of the standard
      supported profiles: RAML, OpenAPI or AMF
     */
    private static void validate(String profile, BaseUnit model) throws ExecutionException, InterruptedException {
        final AMFValidationReport report = amf.Core.validate(model, profile, profile).get();
        printReport(profile, report);
    }

    /*
      In a custom validation, we first need to load the validation profile.
      Then we can use the profile name to validate a parsed model.
     */
    private static void validateCustom(String dialect, String profile, BaseUnit model) throws ExecutionException, InterruptedException {
        amf.Core.loadValidationProfile(profile).get();
        final AMFValidationReport report = amf.Core.validate(model, dialect, "AMF").get();
        printReport(dialect, report);
    }

    private static void printReport(String dialect, AMFValidationReport report) {
        if (report.conforms()) {
            out.println("Document validates profile " + dialect);
        } else {
            out.println("Document DOES NOT validate profile " + dialect);
            out.println(report);
        }
    }

}
