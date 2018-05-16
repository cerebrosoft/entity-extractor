package cerebrosoft.ner.pattern;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.regex.Pattern;

import cerebrosoft.ner.Entity;

/**
 * Defines a regex pattern and maintains a compiled version of the pattern
 */
public class PatternDef {

    private Pattern pattern;
    private String type;

    /**
     * Constructor
     * 
     * @param patternStr the regex pattern string
     * @param type the entity type
     * @param ignoreCase when {@code true}, case is ignored
     */
    public PatternDef(String patternStr, String type, boolean ignoreCase) {
        int flags = ignoreCase ? Pattern.CASE_INSENSITIVE : 0;
        pattern = Pattern.compile(patternStr, flags);
        this.type = checkNotNull(type);
    }

    /**
     * Constructor
     * 
     * @param patternStr the regex pattern string
     * @param ignoreCase when {@code true}, case is ignored
     */
    public PatternDef(String patternStr, boolean ignoreCase) {
        this(patternStr, Entity.BASE_TYPE, ignoreCase);
    }

    /**
     * @return the compiled regex pattern
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * @return the entity type
     */
    public String getType() {
        return type;
    }

}
