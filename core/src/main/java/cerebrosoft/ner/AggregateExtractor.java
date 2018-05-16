package cerebrosoft.ner;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs entity extractions using one or more extractors
 */
public class AggregateExtractor {

    private ExtractorFactory factory;

    /**
     * Constructor
     */
    public AggregateExtractor() {
        factory = new ExtractorFactory();
    }

    /**
     * Extracts entities from the given text using the given extractor definitions
     * 
     * @param document the document text
     * @param defs the extractor definitions to use
     * @return an extraction manifest
     */
    public ExtractionManifest extract(String document, ExtractorDefinition... defs) {
        List<ExtractionManifest> manifests = new ArrayList<>();
        for (ExtractorDefinition def : defs) {
            EntityExtractor extractor = factory.createExtractor(def);
            try {
                ExtractionManifest manifest = extractor.performExtraction(def, document);
                manifests.add(manifest);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ExtractionManifest.merge(manifests);
    }

}
