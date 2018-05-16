package cerebrosoft.ner;

import static cerebrosoft.ner.ExtractorKind.LEXICON;
import static cerebrosoft.ner.ExtractorKind.PATTERN;

import cerebrosoft.ner.book.LexiconExtractor;
import cerebrosoft.ner.pattern.PatternExtractor;

/**
 * Creates instances of extractors
 */
public class ExtractorFactory {

    /**
     * Creates an extractor for the given definition
     * 
     * @param definition the extractor definition
     * @return an extractor suitable for the given definition
     */
    public EntityExtractor createExtractor(ExtractorDefinition definition) {
        if (definition.getKind().equals(LEXICON)) return new LexiconExtractor();
        if (definition.getKind().equals(PATTERN)) return new PatternExtractor();
        return null;
    }

}
