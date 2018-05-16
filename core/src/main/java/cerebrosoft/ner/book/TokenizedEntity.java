package cerebrosoft.ner.book;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

import cerebrosoft.ner.Entity;

/**
 * An entity along with its tokenized name
 */
public class TokenizedEntity {
    private final Entity entity;
    private final List<String> tokens;

    /**
     * Constructor
     * 
     * @param entity the underlying entity
     * @param tokens the tokens generated from the entity's name
     */
    public TokenizedEntity(Entity entity, List<String> tokens) {
        this.entity = checkNotNull(entity);
        this.tokens = ImmutableList.copyOf(tokens);
    }

    /**
     * @return a list of tokens
     */
    public List<String> getTokens() {
        return tokens;
    }

    /**
     * @return the underlying entity
     */
    public Entity getEntity() {
        return entity;
    }
}
