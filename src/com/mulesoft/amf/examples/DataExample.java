package com.mulesoft.amf.examples;

import amf.client.AmfGenerator;
import amf.client.RamlParser;
import amf.model.Document;
import amf.remote.JvmPlatform;
import amf.validation.core.ValidationDialectText;
import org.apache.commons.io.IOUtils;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.topbraid.spin.util.JenaUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

public class DataExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        final String profileUrl = "file://examples/profile.raml";
        final String apiUrl =  "file://examples/api.raml";

        // Configuring the platform
        final JvmPlatform platform = JvmPlatform.instance();
        platform.dialectsRegistry().registerDialect("http://raml.org/vocabularies/validation", ValidationDialectText.text());

        final RamlParser parser = new RamlParser();

        // Parsing the vocabulary
        final Document apiModel = (Document) parser.parseFileAsync(apiUrl, platform).get();
        final Document profileModel = (Document) parser.parseFileAsync(profileUrl, platform).get();

        // We load the vocabulary and the example in the same model
        final Model db = store(apiModel);
        store(db, profileModel);

        // We run a declarative SPARQL queries over the data from both models

        // Get all the endpoints with a 200 GET response
        query("queries/endpoints.sparql", db);

        // Find the code of all JS validations
        query("queries/js_validations.sparql", db);
    }

    private static Model store(Document document) throws ExecutionException, InterruptedException {
        final Model model = JenaUtil.createMemoryModel();
        model.read(IOUtils.toInputStream(jsonld(document), Charset.defaultCharset()), document.location(), "JSON-LD");
        return model;
    }

    private static void store(Model model, Document document) throws ExecutionException, InterruptedException {
        model.read(IOUtils.toInputStream(jsonld(document), Charset.defaultCharset()), document.location(), "JSON-LD");
    }

    private static String jsonld(Document document) throws ExecutionException, InterruptedException {
        return new AmfGenerator().generateString(document).get();
    }


    private static void query(String queryFile, Model model) throws IOException {
        String queryText = new String(Files.readAllBytes(FileSystems.getDefault().getPath(queryFile)));
        out.println("\n\n- Query:\n\n" + queryText + "\n");
        Query query = QueryFactory.create(queryText);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect() ;
            int count = 0;
            out.println("- Results:\n");
            while (results.hasNext()) {
                QuerySolution soln = results.nextSolution() ;
                out.println(soln);
                count++;
            }
            out.println("\nFound " + count + " results\n\n");
        }
    }

}
