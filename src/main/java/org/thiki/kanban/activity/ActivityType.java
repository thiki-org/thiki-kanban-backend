package org.thiki.kanban.activity;

/**
 * Created by xubt on 17/01/2017.
 */
public enum ActivityType {
    CARD_CREATION("001", "卡片创建"),
    CARD_MODIFYING("002", "卡片修改"),
    CARD_DELETING("003", "删除卡片"),
    ACCEPTANCE_CRITERIA_CREATION("004", "增加验收标准"),
    ACCEPTANCE_CRITERIA_MODIFYING("005", "更新验收标准"),
    ACCEPTANCE_CRITERIA_DELETING("006", "删除验收标准"),
    TAG_MODIFYING("007", "更新标签"),
    COMMENT_CREATION("008", "创建评论"),
    CARD_ARCHIVED("009", "卡片归档"),
    CARD_UNDO_ARCHIVED("010", "卡片撤销归档"), CARD_RESORT("011", "卡片排序"), CARD_MOVING("012", "卡片挪动"), ACCEPTANCE_CRITERIA_RESORTING("013", "重新排序验收标准");
    private String code;
    private String type;

    ActivityType(String code, String type) {
        this.code = code;
        this.type = type;
    }

    public String code() {
        return code;
    }

    public String type() {
        return type;
    }
}
