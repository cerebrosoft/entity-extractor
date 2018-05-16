package cerebrosoft.ner;

/**
 * A named entity
 */
public interface Entity {
    
    String BASE_TYPE = "Entity";

    /**
     * @return the name of the entity
     */
    String getName();
    
    /**
     * @return the type of the entity
     */
    String getType();

}
