package cerebrosoft.ner;

/**
 * A location of a piece of text inside a document
 */
public interface TextLocation {

    /**
     * @return the offset into the document
     */
    int getOffset();

    /**
     * @return the length of the text
     */
    int getLength();

    /**
     * Determines whether there is any overlap between the given TextLocation and this one
     * 
     * @param other the other TextLocation
     * @return {@code true} if there is an overlap
     */
    default boolean intersects(TextLocation other) {
        int x1 = other.getOffset();
        int x2 = other.getOffset() + other.getLength() - 1;

        int y1 = getOffset();
        int y2 = getOffset() + getLength() - 1;
       
        return x1 <= y2 && y1 <= x2;
    }

}
