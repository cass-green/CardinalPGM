package in.twizmwaz.cardinal.module.modules.filter.type;

import in.twizmwaz.cardinal.module.modules.filter.FilterModule;
import in.twizmwaz.cardinal.module.modules.filter.FilterState;
import in.twizmwaz.cardinal.module.modules.filter.parsers.CauseFilterParser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import static in.twizmwaz.cardinal.module.modules.filter.FilterState.*;

public class CauseFilter extends FilterModule {

    private final EventCause cause;

    public CauseFilter(final CauseFilterParser parser) {
        super(parser.getName(), parser.getParent());
        this.cause = parser.getCause();
    }

    @Override
    public FilterState evaluate(final Object... objects) {
        for (Object object : objects) {
            EventCause eventCause = null;
            if (object instanceof Player) {
                eventCause = EventCause.PLAYER;
            } else if (object instanceof Entity) {
                if (((Entity) object).getType().equals(EntityType.PRIMED_TNT)) {
                    eventCause = EventCause.TNT;
                }
            }
            if (cause.equals(eventCause))
                return getParent() == null ? ALLOW : (getParent().evaluate(objects).equals(DENY) ? DENY : ALLOW);
            else if (eventCause != null)
                return DENY;
        }
        return (getParent() == null ? ABSTAIN : getParent().evaluate(objects));
    }

    public enum EventCause {

        /**
         * The event was generated be a player action.
         */
        PLAYER(),
        /**
         * The event was generated by TNT.
         */
        TNT();

        public static EventCause getEventCause(String string) {
            switch (string.toLowerCase().replaceAll(" ", "")) {
                case "player":
                    return PLAYER;
                case "tnt":
                    return TNT;
                default:
                    return null;
            }
        }

    }

}
