package org.thiki.kanban.procedure;

/**
 * Created by xubt on 2/10/17.
 */
public enum ViewType {
    VIEW_TYPE_SPRINT("sprint", ProcedureCodes.PROCEDURE_TYPE_IN_PLAN),
    VIEW_TYPE_FULL_VIEW("fullView", -1),
    VIEW_TYPE_ROAD_MAP("roadMap", ProcedureCodes.PROCEDURE_TYPE_IN_PLAN),
    VIEW_TYPE_ARCHIVE("archive", ProcedureCodes.PROCEDURE_TYPE_ARCHIVE);

    private String type;
    private Integer value;

    ViewType(String type, Integer value) {
        this.type = type;
        this.value = value;
    }

    public static ViewType value(String type) {
        for (ViewType viewType : values()) {
            if (viewType.type().equals(type)) {
                return viewType;
            }
        }
        return ViewType.VIEW_TYPE_SPRINT;
    }

    public String type() {
        return type;
    }

    public Integer value() {
        return value;
    }
}
