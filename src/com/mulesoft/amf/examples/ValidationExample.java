package com.mulesoft.amf.examples;

import amf.client.RamlParser;
import amf.validation.AMFValidationReport;

import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

/**
 * Validation example, parsing and then validating using different profiles
 */
public class ValidationExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Create a RAML parser
        final RamlParser parser = new RamlParser();

        // We parse the model
        parser.parseFileAsync("file://examples/api.raml").get();

        // Validating using the default RAML validation profile
        validate("RAML", parser);

        // Validating using the default OpenAPI validation profile
        validate("OpenAPI", parser);

        // Custom profile validation
        validateCustom("Banking", "file://examples/profile.raml", parser);
    }

    private static void validate(String profile, RamlParser parser) throws ExecutionException, InterruptedException {
        final AMFValidationReport report = parser.reportValidation(profile).get();
        printReport(profile, report);
    }

    private static void validateCustom(String dialect, String profile, RamlParser parser) throws ExecutionException, InterruptedException {
        final AMFValidationReport report = parser.reportCustomValidation(dialect, profile).get();
        printReport(dialect, report);
    }

    private static void printReport(String dialect, AMFValidationReport report) {
        if (report.conforms()) {
            out.println("Document validates profile " + dialect);
        } else {
            out.println("Document DOES NOT validates profile " + dialect);
            out.println(report);
        }
    }

}
