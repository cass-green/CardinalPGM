package in.twizmwaz.cardinal.module.modules.filter.type.old;

import in.twizmwaz.cardinal.module.ModuleCollection;
import in.twizmwaz.cardinal.module.modules.filter.FilterModule;
import in.twizmwaz.cardinal.module.modules.filter.FilterState;
import in.twizmwaz.cardinal.module.modules.filter.parsers.ChildrenFilterParser;

import static in.twizmwaz.cardinal.module.modules.filter.FilterState.*;

public class DenyFilter extends FilterModule {

    private final ModuleCollection<FilterModule> children;

    public DenyFilter(final ChildrenFilterParser parser) {
        super(parser.getName(), parser.getParent());
        this.children = parser.getChildren();
    }

    @Override
    public FilterState evaluate(final Object... objects) {
        boolean abstain = true;
        if (children != null) {
            for (Object object : objects) {
                for (FilterModule child : children) {
                    if (!child.evaluate(object).equals(ABSTAIN)) abstain = false;
                    if (child.evaluate(object).equals(ALLOW)) return DENY;
                }
            }
        }
        if (abstain) return (getParent() == null ? ABSTAIN : getParent().evaluate(objects));
        return getParent() == null ? ALLOW : (getParent().evaluate(objects).equals(DENY) ? DENY : ALLOW);
    }
}
