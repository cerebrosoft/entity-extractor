package cerebrosoft.ner;

/**
 * Supplies the information that a n extractor needs to execute
 */
public interface ExtractorDefinition {

    /**
     * @return the kind of extractor this provides a definition for
     */
    ExtractorKind getKind();

}
