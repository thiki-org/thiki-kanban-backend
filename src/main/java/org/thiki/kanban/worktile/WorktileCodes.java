package org.thiki.kanban.worktile;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 11/1/16.
 */
public enum WorktileCodes {
    FILE_IS_EMPTY("001", "导入文件为空,请重新提交文件。"),
    FILE_CONTENT_FORMAT_INVALID("002", "文件内容格式错误,请检查修复后重新提交文件。"),
    FILE_FORMAT_INVALID("003", "文件格式错误,请检查修复后重新提交文件。"),
    FILE_NAME_INVALID("004", "文件名称错误,请确保文件名及拓展名完整。"),
    FILE_TYPE_INVALID("005", "文件类型错误,请上传以.json结尾的文本文件"),
    FILE_CONTENT_READ_ERROR("006", "读取文件文本时出错,请确认文件内容及格式。"),
    FILE_IS_UN_UPLOAD("007", "未上传文件!");

    private String code;
    private String message;

    WorktileCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.BOARD + code);
    }

    public String message() {
        return message;
    }
}
