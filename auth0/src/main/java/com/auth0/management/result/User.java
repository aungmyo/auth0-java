/*
 * User.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.management.result;


import com.auth0.authentication.result.UserIdentity;
import com.auth0.util.CheckHelper;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Class that holds the information of a user in Auth0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    protected String email;
    protected Boolean emailVerified;
    protected String username;
    protected String phoneNumber;
    protected Boolean phoneVerified;
    protected String userId;
    protected Date createdAt;
    protected Date updatedAt;
    protected List<UserIdentity> identities;
    protected Map<String, Object> appMetadata;
    protected Map<String, Object> userMetadata;
    protected String pictureURL;
    protected String name;
    protected String nickname;
    protected List<String> multifactor;
    protected String lastIp;
    protected Date lastLogin;
    protected Integer loginsCount;
    protected Boolean blocked;
    protected Map<String, Object> extraInfo;

    protected User() {
    }

    @JsonCreator
    @SuppressWarnings("unchecked")
    public User(Map<String, Object> values) {
        CheckHelper.checkArgument(values != null, "must supply non-null values");
        HashMap<String, Object> info = new HashMap<String, Object>(values);
        this.email = (String) info.remove("email");
        this.emailVerified = (Boolean) info.remove("email_verified");
        this.username = (String) info.remove("username");
        this.phoneNumber = (String) info.remove("phone_number");
        this.phoneVerified = (Boolean) info.remove("phone_verified");
        this.userId = (String) info.remove("user_id");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            String created_at = (String) info.remove("created_at");
            this.createdAt = created_at != null ? sdf.parse(created_at) : null;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid created_at value", e);
        }
        try {
            String updated_at = (String) info.remove("updated_at");
            this.updatedAt = updated_at != null ? sdf.parse(updated_at) : null;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid updated_at value", e);
        }
        this.identities = buildIdentities((List<Map<String, Object>>) info.remove("identities"));
        this.appMetadata = (Map<String, Object>) info.remove("app_metadata");
        this.userMetadata = (Map<String, Object>) info.remove("user_metadata");
        this.pictureURL = (String) info.remove("picture");
        this.name = (String) info.remove("name");
        this.nickname = (String) info.remove("nickname");
        this.multifactor = (List<String>) info.remove("multifactor");
        this.lastIp = (String) info.remove("last_ip");
        try {
            String last_login = (String) info.remove("last_login");
            this.lastLogin = last_login != null ? sdf.parse(last_login) : null;
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid last_login value", e);
        }
        this.loginsCount = (Integer) info.remove("logins_count");
        this.blocked = (Boolean) info.remove("blocked");
        this.extraInfo = info;
    }

    public String getId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getPhoneVerified() {
        return phoneVerified;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Map<String, Object> getAppMetadata() {
        return appMetadata;
    }

    public Map<String, Object> getUserMetadata() {
        return userMetadata;
    }

    public List<String> getMultifactor() {
        return multifactor;
    }

    public String getLastIp() {
        return lastIp;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public Integer getLoginsCount() {
        return loginsCount;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    /**
     * Returns extra information of the user that could not be included
     *
     * @return a map with user's extra information
     */
    public Map<String, Object> getExtraInfo() {
        return new HashMap<>(extraInfo);
    }

    /**
     * List of the identities from a Identity Provider associated to the user.
     *
     * @return a list of identity provider information.
     */
    public List<UserIdentity> getIdentities() {
        return identities;
    }

    private List<UserIdentity> buildIdentities(List<Map<String, Object>> values) {
        if (values == null) {
            return Collections.emptyList();
        }
        List<UserIdentity> identities = new ArrayList<>(values.size());
        for (Map<String, Object> value : values) {
            identities.add(new UserIdentity(value));
        }
        return identities;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", username='" + username + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneVerified=" + phoneVerified +
                ", userId='" + userId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", identities=" + identities +
                ", appMetadata=" + appMetadata +
                ", userMetadata=" + userMetadata +
                ", pictureURL='" + pictureURL + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", multifactor=" + multifactor +
                ", lastIp='" + lastIp + '\'' +
                ", lastLogin=" + lastLogin +
                ", loginsCount=" + loginsCount +
                ", blocked=" + blocked +
                ", extraInfo=" + extraInfo +
                '}';
    }
}
