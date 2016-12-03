package com.framework.runtime.application.mvc;

public interface RoleChecker {

	boolean check(UserWrapper user, String role, String resource);
}
