package cerebrosoft.ner;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An occurrence of an entity in a document
 */
public class Extraction implements Comparable<Extraction>, TextLocation {
    private final Entity extractedItem;
    private final int offset;
    private final int length;
    private final ExtractorKind extractorKind;

    /**
     * Constructor
     * 
     * @param extractedItem the entity that was extracted
     * @param offset the offset in the document
     * @param length the length of the extraction
     * @param extractorKind the type of extractor
     */
    public Extraction(Entity extractedItem, int offset, int length, ExtractorKind extractorKind) {
        this.extractedItem = checkNotNull(extractedItem);
        this.offset = offset;
        this.length = length;
        this.extractorKind = checkNotNull(extractorKind);
    }

    /**
     * @return the extracted item
     */
    public Entity getExtractedItem() {
        return extractedItem;
    }

    /**
     * @return the type of extractor that produced this extraction
     */
    public ExtractorKind getExtractorKind() {
        return extractorKind;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int compareTo(Extraction other) {
        int result = new Integer(offset).compareTo(other.getOffset());
        if (result != 0) return result;
        if (length == other.getLength()) return 0;
        if (length < other.length) return -1;
        return 1;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("extractedItem", extractedItem.getName())
            .add("offset", offset)
            .add("length", length).toString();
    }
}
