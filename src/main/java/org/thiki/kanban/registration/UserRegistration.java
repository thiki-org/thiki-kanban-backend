package org.thiki.kanban.registration;

import org.thiki.kanban.user.UserProfile;

/**
 * Created by joeaniu on 6/21/16.
 */
public class UserRegistration {

    private String id;
    private String userId;
    private String password;
    private String recoveryEmail;
    private String recoveryPhone;
    private int status;
    private long creationTime;
    private long modificationTime;

    /** 正常可使用 */
    public static final int STATUS_NORMAL = 0;
    /** 已停用 */
    public static final int STATUS_INVALID = -1;

    public UserRegistration() {
    }

    /**
     *
     * @param userProfile 用户信息
     * @param password 密码
     */
    public UserRegistration(UserProfile userProfile, String password){
        this.userId = userProfile.getId();
        this.password = password;
        this.recoveryEmail = userProfile.getEmail();
        this.recoveryPhone = userProfile.getPhone();
        this.status = STATUS_NORMAL;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecoveryEmail() {
        return recoveryEmail;
    }

    public void setRecoveryEmail(String recoveryEmail) {
        this.recoveryEmail = recoveryEmail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(long modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getRecoveryPhone() {
        return recoveryPhone;
    }

    public void setRecoveryPhone(String recoveryPhone) {
        this.recoveryPhone = recoveryPhone;
    }
}
