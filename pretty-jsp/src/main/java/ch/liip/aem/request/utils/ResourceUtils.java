package ch.liip.aem.request.utils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;

public final class ResourceUtils {

    private ResourceUtils() {
        // no instance allowed for this utility class
    }

    public static <T> T getResourceProperty(Resource resource, String property, Class<T> type) {
        ValueMap valueMap = org.apache.sling.api.resource.ResourceUtil.getValueMap(resource);
        return valueMap.get(property, type);
    }

    public static Resource getResourceChild(Resource resource, String key) {
        if (key == null || key.isEmpty()) {
            return resource;
        }

        String currentKey;
        if (key.startsWith("./")) {
            currentKey = key.substring(2);
        } else {
            currentKey = key;
        }
        int slashIndex = currentKey.indexOf('/');
        String childKey;
        String keyTail;
        if (slashIndex == -1) {
            childKey = currentKey;
            keyTail = null;
        } else {
            childKey = currentKey.substring(0, slashIndex);
            keyTail = currentKey.substring(slashIndex + 1);
        }

        Resource resourceChild = getResourceChild(resource.getChild(childKey), keyTail);
        Preconditions.checkNotNull(resourceChild, String.format("Impossible to find path '%s' on resource '%s'", key, resource));
        return resourceChild;
    }

}
