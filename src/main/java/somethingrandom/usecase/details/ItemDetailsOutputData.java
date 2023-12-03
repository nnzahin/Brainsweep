package somethingrandom.usecase.details;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;

/**
 * Stores information about an item that would be useful in the details pane.
 */
public class ItemDetailsOutputData {
    private final @NotNull String title;
    private final @NotNull Instant creationTime;
    private @Nullable String description = null;
    private @Nullable Duration expectedDuration = null;
    private @Nullable Instant remindDate = null;

    public ItemDetailsOutputData(@NotNull String title, @NotNull Instant creationTime) {
        this.title = title;
        this.creationTime = creationTime;
    }

    public ItemDetailsOutputData(@NotNull String title, @Nullable String description, @NotNull Instant creationTime) {
        this.title = title;
        this.description = description;
        this.creationTime = creationTime;
    }

    public ItemDetailsOutputData(@NotNull String title, @Nullable Duration duration, @NotNull Instant creationTime) {
        this.title = title;
        this.creationTime = creationTime;
        this.expectedDuration = duration;
    }

    public ItemDetailsOutputData(@NotNull String title, @Nullable Instant remindDate, @NotNull Instant creationTime) {
        this.title = title;
        this.creationTime = creationTime;
        this.remindDate = remindDate;
    }

    /**
     * Gets the title of the item.
     *
     * @return The item's title.
     */
    public @NotNull String getTitle() {
        return title;
    }

    /**
     * Gets the description of the item, or null if it does not have one.
     *
     * @return The item's description, or null.
     */
    public @Nullable String getDescription() {
        return description;
    }

    /**
     * Returns the time when the item was created.
     *
     * @return The item's creation date.
     */
    public @NotNull Instant getCreationTime() {
        return creationTime;
    }

    /**
     * Returns the duration that the user expects the task to take.
     *
     * @return The item's duration.
     */
    public @Nullable Duration getExpectedDuration() {
        return expectedDuration;
    }

    /**
     * Gets the date the user needs the task done by.
     *
     * @return The item's due date.
     */
    public @Nullable Instant getRemindDate() {
        return remindDate;
    }
}
