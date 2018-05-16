package cerebrosoft.ner.pattern;

import static cerebrosoft.ner.ExtractorKind.PATTERN;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import cerebrosoft.ner.ExtractorDefinition;
import cerebrosoft.ner.ExtractorKind;

/**
 * A collection of regex patterns
 */
public class PatternBook implements ExtractorDefinition {

    private Collection<PatternDef> patterns;

    /**
     * Constructor
     * 
     * @param patterns a collection of regex pattern definitions
     */
    public PatternBook(Collection<PatternDef> patterns) {
        this.patterns = checkNotNull(patterns);
    }

    /**
     * @return a collection of regex patterns
     */
    public Collection<PatternDef> getPatterns() {
        return patterns;
    }

    @Override
    public ExtractorKind getKind() {
        return PATTERN;
    }

}
