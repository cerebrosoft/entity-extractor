package cerebrosoft.ner;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of {@link Entity}
 */
public class DefaultEntity implements Entity {
    private final String name;
    private String type;
    private String displayString;
    private Map<String, String> props;

    /**
     * Constructor
     * 
     * @param name the entity name
     */
    public DefaultEntity(String name) {
        this.name = checkNotNull(name);
    }

    private DefaultEntity(Builder builder) {
        this.name = builder.name;
        this.displayString = builder.displayString;
        this.type = builder.type;
        this.props = builder.properties;
    }

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the entity type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the display string for this entity
     */
    public String getDisplayString() {
        if (displayString != null) return displayString;
        return name;
    }

    /**
     * @return the general properties for this entity
     */
    public Map<String, String> getProps() {
        return props;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("name", name).add("type", type).toString();
    }

    public static class Builder {
        private final String name;
        private String displayString;
        private String type = Entity.BASE_TYPE;
        private final Map<String, String> properties;

        public Builder(String name) {
            this.name = checkNotNull(name);
            properties = new HashMap<>();
        }

        public Builder type(String type) {
            this.type = checkNotNull(type);
            return this;
        }

        public Builder displayString(String displayString) {
            this.displayString = displayString;
            return this;
        }

        public Builder property(String name, String value) {
            properties.put(name, value);
            return this;
        }

        public DefaultEntity build() {
            return new DefaultEntity(this);
        }
    }
}
