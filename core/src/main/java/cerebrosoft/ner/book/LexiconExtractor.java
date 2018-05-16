package cerebrosoft.ner.book;

import static cerebrosoft.ner.ExtractorKind.LEXICON;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Iterables.getFirst;
import static com.google.common.collect.Lists.newArrayListWithExpectedSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import com.google.common.collect.HashMultimap;

import cerebrosoft.ner.EntityExtractor;
import cerebrosoft.ner.Extraction;
import cerebrosoft.ner.ExtractionManifest;
import cerebrosoft.ner.ExtractorDefinition;

/**
 * Extracts entities from text based on a supplied lexicon
 */
public class LexiconExtractor implements EntityExtractor {

    private List<InputToken> tokenizedDocument;
    private HashMultimap<String, Integer> tokenIndex;
    private final Analyzer analyzer;

    /**
     * Constructor
     * 
     * @param analyzer the analyzer to use to analyze the document text
     */
    public LexiconExtractor(Analyzer analyzer) {
        this.analyzer = checkNotNull(analyzer);
    }

    /**
     * Constructor, builds with a {@link StandardAnalyzer}
     */
    public LexiconExtractor() {
        this(new StandardAnalyzer());
    }

    @Override
    public ExtractionManifest performExtraction(ExtractorDefinition def, String text) throws Exception {
        if (!(def instanceof EntityBook)) throw new IllegalArgumentException("wrong type of definition");
        EntityBook book = (EntityBook) def;
        processText(text);

        List<Extraction> extractions = new ArrayList<>();
        for (TokenizedEntity tokenizedItem : book.getTokenizedEntities()) {
            List<String> entityTokens = tokenizedItem.getTokens();
            String firstToken = getFirst(entityTokens, null);

            // for the document: these are all the indexes of the first token of the entity
            Set<Integer> indicies = tokenIndex.get(firstToken);

            // no match at all, so move to the next entity
            if (indicies == null) continue;

            // otherwise, let's loop through each occurrence of the first token of the entity and see if we have a full
            // match
            for (int index : indicies) {
                List<String> docTokens = tokenizedDocument.subList(index, index + entityTokens.size())
                    .stream().map(token -> token.getItem())
                    .collect(Collectors.toList());

                boolean completeMatch = isEqualList(entityTokens, docTokens);

                // no match, move on
                if (!completeMatch) continue;

                int startOffset = tokenizedDocument.get(index).getStart();
                int endOffset;
                if (entityTokens.size() == 1) {
                    endOffset = tokenizedDocument.get(index).getEnd();
                }
                else {
                    int end = index + entityTokens.size() - 1;
                    if (end > tokenizedDocument.size() - 1) {
                        end = tokenizedDocument.size() - 1;
                    }
                    endOffset = tokenizedDocument.get(end).getEnd();
                }
                int len = endOffset - startOffset;
                extractions.add(new Extraction(tokenizedItem.getEntity(), startOffset, len, LEXICON));
            }
        }

        return new ExtractionManifest(extractions);
    }

    private void processText(String text) throws IOException {
        tokenizedDocument = newArrayListWithExpectedSize(text.length() / 8);
        tokenIndex = HashMultimap.create();
        try (TokenStream ts = analyzer.tokenStream("ignored", text)) {
            int count = 0;
            ts.reset();
            while (ts.incrementToken()) {
                String s = ts.getAttribute(CharTermAttribute.class).toString();
                OffsetAttribute offsetAttribute = ts.addAttribute(OffsetAttribute.class);
                int startOffset = offsetAttribute.startOffset();
                int endOffset = offsetAttribute.endOffset();
                tokenIndex.put(s, count);
                tokenizedDocument.add(new InputToken(s, startOffset, endOffset));
                count++;
            }
        }
    }

    private boolean isEqualList(final Collection<String> list1, final Collection<String> list2) {
        Iterator<String> it1 = list1.iterator();
        Iterator<String> it2 = list2.iterator();
        Object obj1 = null;
        Object obj2 = null;
        while (it1.hasNext() && it2.hasNext()) {
            obj1 = it1.next();
            obj2 = it2.next();
            if (!obj1.equals(obj2)) return false;
        }
        return true;
    }

    private static class InputToken {
        private final String item;
        private final int start;
        private final int end;

        private InputToken(String item, int start, int end) {
            this.item = checkNotNull(item);
            this.start = start;
            this.end = end;
        }

        private String getItem() {
            return this.item;
        }

        private int getStart() {
            return this.start;
        }

        private int getEnd() {
            return this.end;
        }
    }

}
