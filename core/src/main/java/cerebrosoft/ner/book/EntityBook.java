package cerebrosoft.ner.book;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.google.common.collect.ImmutableList;

import cerebrosoft.ner.Entity;
import cerebrosoft.ner.ExtractorDefinition;
import cerebrosoft.ner.ExtractorKind;

/**
 * A collection of entities- a lexicon.
 */
public class EntityBook implements ExtractorDefinition {

    private final List<TokenizedEntity> tokenizedEntities;
    private Analyzer analyzer;

    /**
     * Constructor
     * 
     * @param entities the collection of entities
     * @param analyzer the analyzer to use to process the entity names
     */
    public EntityBook(Collection<? extends Entity> entities, Analyzer analyzer) {
        this.tokenizedEntities = new ArrayList<>();
        this.analyzer = checkNotNull(analyzer);
        try {
            processEntities(entities);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor- creates with a {@link StandardAnalyzer}
     * 
     * @param entities the collection of entities
     */
    public EntityBook(Collection<? extends Entity> entities) {
        this(entities, new StandardAnalyzer());
    }

    /**
     * @return a list of tokenized entities
     */
    public List<TokenizedEntity> getTokenizedEntities() {
        return ImmutableList.copyOf(tokenizedEntities);
    }

    private void processEntities(Collection<? extends Entity> entities) throws IOException {
        for (Entity entity : entities) {
            List<String> sublist = new ArrayList<>();
            try (TokenStream ts = analyzer.tokenStream("ignored", entity.getName())) {
                ts.reset();
                while (ts.incrementToken()) {
                    String s = ts.getAttribute(CharTermAttribute.class).toString();
                    sublist.add(s);
                }
                if (!sublist.isEmpty()) {
                    tokenizedEntities.add(new TokenizedEntity(entity, sublist));
                }
            }
        }
    }

    @Override
    public ExtractorKind getKind() {
        return ExtractorKind.LEXICON;
    }

}
