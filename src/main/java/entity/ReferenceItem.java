package entity;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Reference item.
 */
public class ReferenceItem extends Item {

    /**
     * The Description.
     */
    private String description;

    /**
     * Instantiates a new Reference item.
     *
     * @param name         the name
     * @param id           the id
     * @param description  the description
     * @param creationDate the creation date
     */
    public ReferenceItem(String name, UUID id, Instant creationDate, String description) {
        super(name, id, creationDate);
        this.description = description;
    }

    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public ItemKind getItemKind() {
        return ItemKind.REFERENCE;
    }

    /**
     * Get the item's description string.
     *
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets description.
     *
     * @param description the description date
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
