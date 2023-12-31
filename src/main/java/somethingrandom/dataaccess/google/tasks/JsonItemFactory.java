package somethingrandom.dataaccess.google.tasks;

import org.json.JSONException;
import org.json.JSONObject;
import somethingrandom.entity.ActionableItem;
import somethingrandom.entity.DelayedItem;
import somethingrandom.entity.Item;
import somethingrandom.entity.ReferenceItem;
import somethingrandom.usecase.DataAccessException;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class JsonItemFactory {
    private static class UnknownItem extends Item {
        private final String kind;

        public UnknownItem(String name, UUID id, Instant creationDate, String kind) {
            super(name, id, creationDate);
            this.kind = kind;
        }

        @Override
        public String getItemKind() {
            return kind;
        }
    }

    public static Item createItem(UUID uuid, JSONObject object) throws DataAccessException {
        try {
            if (!object.getString("kind").equals("tasks#task")) {
                throw new DataAccessException("Invalid Task kind (bug at Google?)");
            }

            return createItemHelper(uuid, object);
        } catch (JSONException | DateTimeParseException | NumberFormatException e) {
            throw new DataAccessException(e);
        }
    }

    private static Item createItemHelper(UUID uuid, JSONObject object) throws DataAccessException {
        String name = object.getString("title");
        String[] notes = object.getString("notes").split("\n");

        Instant createdDate = Instant.now();
        Duration neededTime = null;
        String kind = "UNKNOWN";
        String description = "";
        Instant remindDate = null;
        for (String line : notes) {
            if (line.startsWith("Creation Date:")) {
                createdDate = Instant.parse(line.split(" ")[2]);
            }

            if (line.startsWith("Needed time:")) {
                neededTime = Duration.of(Long.parseLong(line.split(" ")[2]), ChronoUnit.SECONDS);
            }

            if (line.startsWith("Item kind:")) {
                kind = line.split(":")[1].strip();
            }

            if (line.startsWith("Description:")) {
                description = line.split(":", 2)[1].strip();
            }

            if (line.startsWith("Remind Date:")) {
                remindDate = Instant.parse(line.split(":", 2)[1].strip());
            }
        }

        if (name.isBlank()) {
            return null;
        }

        if (kind.equals("ACTIONABLE")) {
            return new ActionableItem(name, uuid, createdDate, neededTime);
        }

        if (kind.equals("REFERENCE")) {
            return new ReferenceItem(name, uuid, createdDate, description);
        }

        if (kind.equals("DELAYED")) {
            return new DelayedItem(name, uuid, createdDate, remindDate);
        }

        return new UnknownItem(name, uuid, createdDate, kind);
    }
}
