/**
 * Assign user to Nexus repository API script
 */

import org.sonatype.nexus.security.user.User
import org.sonatype.nexus.security.user.UserManager;
import org.sonatype.nexus.security.role.NoSuchRoleException;

// Getting arguments from Post parameters
def argsMap = args.split('&').inject([:]) { map, token ->
    token.split('=').with { map[it[0]] = it[1] }
    map
};

def roleName = argsMap.getAt("roleName").toString();
def privilegeName = argsMap.getAt("privilegeName").toString();
def userId = argsMap.getAt("userId").toString();
def user = security.securitySystem.getUser(userId);

def authManager = security.getSecuritySystem().getAuthorizationManager(UserManager.DEFAULT_SOURCE);
def role = null;
try {
    role = authManager.getRole(roleName);
} catch (NoSuchRoleException e) {
    print "There is no such role, it will be created!";
}
List<String> privileges = new ArrayList<>();
privileges.add(privilegeName);

if (role == null) {
    role = security.addRole(roleName, roleName, "", privileges, new ArrayList<String>());
}
List<String> roles = new ArrayList<>();
roles.add(role.getRoleId());
user.getRoles().each {
    roles.add(it.getRoleId());
};
user = security.setUserRoles(userId, roles);
return user;


