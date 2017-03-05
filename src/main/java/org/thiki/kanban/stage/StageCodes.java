package org.thiki.kanban.stage;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 8/8/16.
 */
public enum StageCodes {
    TITLE_IS_ALREADY_EXISTS("001", "该名称已经被使用,请使用其它名称。"),
    IS_NOT_CURRENT_PROJECT_MEMBER("002", "当前团队并非团队成员。"),
    STAGE_IS_NOT_EXIST("003", "你正在操作的环节不存在。"),
    DONE_STAGE_IS_ALREADY_EXIST("004", "完成环节已经存在。"),
    STAGE_TYPE_IS_NOT_IN_SPRINT("005", "当前环节非迭代中的环节，不可以设置完成列。"),
    NOT_ALLOW_SET_STAGE_TO_ARCHIVE_DIRECTLY("006", "不允许直接将环节设置为归档状态。"),
    STAGE_IS_NOT_IN_DONE_STATUS("007", "当前环节并非处于完成状态，不允许撤销归档。"),
    NO_DONE_STAGE_WAS_FOUND("008", "当前看板尚未设置完成列，不允许撤销归档。"),
    NEW_ARCHIVED_STAGE_WAS_FOUND("009", "已经存在较新的归档，不允许撤销归档。"),
    NO_ARCHIVED_STAGE_WAS_FOUND("010", "归档不存在，无法进行撤销归档操作。"),
    DONE_STAGE_IS_NOT_EXIST("011", "完成环节不存在。");
    public static final String titleIsRequired = "环节名称不能为空。";
    public static final String titleIsInvalid = "环节名称长度超限,请保持在30个字符以内。";
    public static final String descriptionIsInvalid = "环节描述长度超限,请保持在100个字符以内。";
    public static final Integer STAGE_STATUS_TODO = 0;
    public static final Integer STAGE_STATUS_DOING = 1;
    public static final Integer STAGE_STATUS_DONE = 9;
    public static final Integer STAGE_TYPE_IN_PLAN = 1;
    public static final Integer STAGE_TYPE_ARCHIVE = 9;

    public static final String VIEW_TYPE_SPRINT = "sprintView";
    public static final String VIEW_TYPE_FULL_VIEW = "fullView";
    public static final String VIEW_TYPE_ROAD_MAP = "roadMapView";
    public static final String VIEW_TYPE_ARCHIVE = "archiveView";
    private String code;
    private String message;

    StageCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.STAGE + "" + code);
    }

    public String message() {
        return message;
    }
}
