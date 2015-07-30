package com.liip.aem.taglib;

import com.liip.aem.exceptions.TaglibExceptionException;
import com.liip.aem.request.RequestObjects;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.jsp.JspException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.liip.aem.request.utils.Preconditions.checkFalse;

/**
 * @author Fabrice Hong
 */
public class IfHasPermissionTag extends ComponentTagSupport {

    private String path;
    private String permissions;

    private String[] allowedPermissions = new String[]{
            Session.ACTION_ADD_NODE,
            Session.ACTION_READ,
            Session.ACTION_REMOVE,
            Session.ACTION_SET_PROPERTY,
    };

    private Set<String> allowedPermissionSet = new HashSet<String>(Arrays.asList(allowedPermissions));

    @Override
    public int doStartTag() throws JspException {
        validatePermissionList();
        RequestObjects requestObjects = createRequestObjects();
        Session jcrSession = requestObjects.getJcrSession();
        try {
            return jcrSession.hasPermission(path, permissions)?EVAL_BODY_INCLUDE:SKIP_BODY;
        } catch (RepositoryException e) {
            throw new TaglibExceptionException(e);
        }
    }

    private void validatePermissionList() {
        String[] permissionArray = permissions.split(",");
        for (String permission : permissionArray) {
            checkFalse(permission.contains(" "), String.format("Malformed permission list : '%s'. Exemple of allowed format : 'read,add_node' ", this.permissions));
            if(!allowedPermissionSet.contains(permission.trim())) {
                throw new TaglibExceptionException(String.format("Unknown permission provided to the tag : %s. Allowed permissions : %s", permission, Arrays.toString(this.allowedPermissions)));
            }
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
