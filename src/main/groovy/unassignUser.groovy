/**
 * Assign user to Nexus repository API script
 */

// Getting arguments from Post parameters
def argsMap = args.split('&').inject([:]) { map, token ->
    token.split('=').with { map[it[0]] = it[1] }
    map
};

def roleName = argsMap.getAt("roleName").toString();
def userId = argsMap.getAt("userId").toString();
def user = security.securitySystem.getUser(userId);

List<String> roles = new ArrayList<>();
user.getRoles().each {
    if (it.getRoleId() != roleName) {
        roles.add(it.getRoleId());
    }
};
user = security.setUserRoles(userId, roles);
return user;