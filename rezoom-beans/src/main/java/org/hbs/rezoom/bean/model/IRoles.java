package org.hbs.rezoom.bean.model;

import java.util.Set;

import org.hbs.rezoom.util.EnumInterface;
import org.hbs.rezoom.util.ICRUDBean;

public interface IRoles extends ICommonBeanFields, ICRUDBean
{
	public enum ERole implements EnumInterface
	{
		Admin, Consumer, Dummy, Employee, Producer, SuperAdminRole;
	}

	public String getEnumKey();

	public Boolean getIsAdminRole();

	public Set<MenuRole> getMenuRoles();

	public String getRoleDescription();

	public String getRoleId();

	public String getRoleLongName();

	public String getRoleName();

	public String getRoleShortName();

	public String getRoleType();

	public void setEnumKey(String enumKey);

	public void setIsAdminRole(Boolean isAdminRole);

	public void setMenuRoles(Set<MenuRole> menuRoles);

	public void setRoleDescription(String roleDescription);

	public void setRoleId(String roleId);

	public void setRoleLongName(String roleLongName);

	public void setRoleName(String roleName);

	public void setRoleShortName(String roleShortName);

	public void setRoleType(String roleType);

	public Set<PortletsRoles> getPortletRoles();

	public void setPortletRoles(Set<PortletsRoles> portletRoles);

}