package org.thiki.kanban.registration;

/**
 * Created by joeaniu on 6/21/16.
 */
public class UserRegistration {

    private String id;
    private String userId;
    private String password;
    private String recoveryEmail;
    private int status;
    private long creationTime;
    private long modificationTime;

    public UserRegistration() {
    }

    /**
     *
     * @param userId 用户Id
     * @param password 密码
     * @param recoveryEmail 邮件
     * @param status 状态
     * @param creationTime 创建时间
     * @param modificationTime 最后修改时间
     */
    public UserRegistration(String userId, String password, String recoveryEmail, int status, long creationTime, long modificationTime) {
        this.userId = userId;
        this.password = password;
        this.recoveryEmail = recoveryEmail;
        this.status = status;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
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


}
