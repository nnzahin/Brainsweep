package somethingrandom.entity;

import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

/**
 * The type Actionable item.
 */
public class ActionableItem extends Item {

    /**
     * The amount of time needed to complete the task.
     */
    @Nullable
    private Duration neededTime;

    /**
     * Instantiates a new Actionable item.
     *
     * @param name         the name
     * @param id           the id
     * @param creationDate the creation date
     * @param neededTime   the needed time
     */
    public ActionableItem(String name, UUID id, Instant creationDate, @Nullable Duration neededTime) {
        super(name, id, creationDate);
        this.neededTime = neededTime;
    }

    /**
     * Returns item kind.
     *
     * @return the item kind
     */
    public String getItemKind() {
        return "ACTIONABLE";
    }

    /**
     * Returns the needed time.
     *
     * @return the needed time
     */
    @Nullable
    public Duration getNeededTime() {
        return this.neededTime;
    }

    /**
     * Set needed time.
     *
     * @param neededTime the needed time
     */
    public void setNeededTime(Duration neededTime) {
        this.neededTime = neededTime;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return the string
     */
    public String toString(){
        if (this.neededTime == null) {
            return super.toString();
        } else {
            return super.toString() + "\nNeeded time: " + this.neededTime.getSeconds();
        }
    }
}
