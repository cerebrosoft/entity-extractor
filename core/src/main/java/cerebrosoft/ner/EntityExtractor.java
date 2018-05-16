package cerebrosoft.ner;

public interface EntityExtractor {

    /**
     * Performs an entity extraction against the given document text using the given lexicon
     * 
     * @param book the lexicon
     * @param text the document text
     * @return an {@link ExtractionManifest}
     * @throws Exception
     */
    ExtractionManifest performExtraction(ExtractorDefinition definition, String text) throws Exception;

}
