package org.thiki.kanban.registration;

/**
 * Created by joeaniu on 6/21/16.
 */
public interface RegistractionPersistence {
    /**
     * 是否已经存在了相同的用户, 名字、手机、邮箱任一项相同即返回true
     * @param name
     * @param mobile
     * @param mail
     * @return
     */
    boolean existsUser(String name, String mobile, String mail);
}
