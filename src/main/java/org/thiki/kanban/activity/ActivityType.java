package org.thiki.kanban.activity;

import org.thiki.kanban.foundation.application.DomainOrder;

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
    CARD_UNDO_ARCHIVED("009", "卡片撤销归档");
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
