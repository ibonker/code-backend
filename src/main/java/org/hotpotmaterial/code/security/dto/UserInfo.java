package org.hotpotmaterial.code.security.dto;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collection;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfo implements UserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7016361673282926804L;

	private String id;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 登录名
	 */
	private String username;
	/**
	 * 权限
	 */
	private Set<GrantedAuthority> authorities;
	/**
	 * 帐号未过期
	 */
	private boolean accountNonExpired;
	/**
	 * 帐号未锁定
	 */
	private boolean accountNonLocked;
	/**
	 * 密码未过期
	 */
	private boolean credentialsNonExpired;
	/**
	 * 是否可用
	 */
	private boolean enabled;

	private String orgId;

	private String orgName;

	private String name;

	private String orgFullId;

	private String orgFullName;

	private String token;

	public UserInfo() {
	}

	public UserInfo(HotpotUserSeniorDTO hopotUser, Collection<? extends GrantedAuthority> authorities) {
		this(hopotUser, true, true, true, true, authorities);
	}

	public UserInfo(JSONObject resUserInfo, String token, Collection<? extends GrantedAuthority> authorities) {
		this(resUserInfo, true, true, true, true, token, authorities);
	}

	public UserInfo(JSONObject resUserInfo, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, String token, Collection<? extends GrantedAuthority> authorities) {

		try {
			this.username = resUserInfo.getString("loginID");

			if (((username == null) || "".equals(username))) {
				throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
			}
			this.id = resUserInfo.getString("userId");
			this.token = token;

			this.orgId = resUserInfo.getString("departmentId");
			this.name = resUserInfo.getString("userFullName");

			this.enabled = enabled;
			this.accountNonExpired = accountNonExpired;
			this.credentialsNonExpired = credentialsNonExpired;
			this.accountNonLocked = accountNonLocked;

			Decoder de64 = Base64.getDecoder();
			JSONObject jo = new JSONObject(new String(de64.decode(resUserInfo.getString("userDesc"))));
			String org = jo.getString("departmentName");
			this.orgName = org.substring(org.lastIndexOf("_") + 1);
			this.orgFullName = org.replaceAll("_", "/");

		} catch (JSONException e) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

	}

	public UserInfo(HotpotUserSeniorDTO hotpotUser, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {

		if (hotpotUser.getHotpotUser() != null) {
			this.username = hotpotUser.getHotpotUser().getUserName();
			// this.password = hotpotUser.getHotpotUser().getPassword();
			if (((username == null) || "".equals(username))) {
				throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
			}
			this.id = hotpotUser.getHotpotUser().getId();
			this.orgId = hotpotUser.getHotpotUser().getOrgId();
			this.name = hotpotUser.getHotpotUser().getName();
		} else {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.credentialsNonExpired = credentialsNonExpired;
		this.accountNonLocked = accountNonLocked;

		if (hotpotUser.getHotpotOrganization() != null) {
			this.orgName = hotpotUser.getHotpotOrganization().getOrgName();
			this.orgFullId = hotpotUser.getHotpotOrganization().getOrgFullId();
			this.orgFullName = hotpotUser.getHotpotOrganization().getOrgFullName();
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrgFullId() {
		return orgFullId;
	}

	public void setOrgFullId(String orgFullId) {
		this.orgFullId = orgFullId;
	}

	public String getOrgFullName() {
		return orgFullName;
	}

	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}