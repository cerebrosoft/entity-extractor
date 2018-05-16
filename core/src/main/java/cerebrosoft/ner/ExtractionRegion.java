package cerebrosoft.ner;

import static com.google.common.collect.Iterables.getFirst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

/**
 * A region of text that contains one or more entities
 */
public class ExtractionRegion implements TextLocation {
    private final List<Extraction> extractions;

    /**
     * Constructor
     */
    public ExtractionRegion() {
        extractions = new ArrayList<>();
    }

    /**
     * @return the list of entities found within this region of text
     */
    public List<Entity> getItems() {
        List<Entity> items = new ArrayList<>();
        for (Extraction e : this.extractions) {
            items.add(e.getExtractedItem());
        }
        return ImmutableList.copyOf(items);
    }

    /**
     * Add a new extraction
     * 
     * @param extraction
     */
    public void add(Extraction extraction) {
        extractions.add(extraction);
    }

    /**
     * @return the list of extractions for this region
     */
    public List<Extraction> getExtractions() {
        return extractions;
    }

    /**
     * @return the longest extraction in this region
     */
    public Extraction getLongestExtraction() {
        Extraction maxByLength = extractions
            .stream()
            .max(Comparator.comparing(Extraction::getLength))
            .orElseThrow(NoSuchElementException::new);
        return maxByLength;
    }

    /**
     * @return the type shared by all entities in this region. If there is more than one type, the base type is
     *         returned.
     */
    public String getCommonType() {
        if (extractions.size() == 1) return extractions.get(0).getExtractedItem().getType();
        HashSet<String> types = new HashSet<>();
        for (Extraction e : this.extractions) {
            types.add(e.getExtractedItem().getType());
            if (types.size() > 1) return Entity.BASE_TYPE;
        }
        return Iterables.getFirst(types, Entity.BASE_TYPE);
    }

    @Override
    public int getOffset() {
        return getFirst(this.extractions, null).getOffset();
    }

    @Override
    public int getLength() {
        int offset = getOffset();
        int maxEnd = 0;
        for (Extraction extraction : extractions) {
            int end = extraction.getOffset() + extraction.getLength();
            if (end > maxEnd) {
                maxEnd = end;
            }
        }
        return maxEnd - offset;
    }
}
