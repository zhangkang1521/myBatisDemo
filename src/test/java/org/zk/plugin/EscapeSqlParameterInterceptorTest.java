package org.zk.plugin;

import org.junit.Test;
import org.zk.param.UserParam;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class EscapeSqlParameterInterceptorTest {

    @Test
    public void escapeNull() {
        EscapeSqlParameterInterceptor interceptor = new EscapeSqlParameterInterceptor();
        assertNull(interceptor.escapeParamObject(null));
    }


    @Test
    public void escapeString() {
        EscapeSqlParameterInterceptor interceptor = new EscapeSqlParameterInterceptor();
        assertEquals("aa", interceptor.escapeParamObject("aa"));
        assertEquals("\\%", interceptor.escapeParamObject("%"));
        assertEquals("a\\%b", interceptor.escapeParamObject("a%b"));
    }

    @Test
    public void escapeMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "%");
        map.put("b", 1);
        EscapeSqlParameterInterceptor interceptor = new EscapeSqlParameterInterceptor();
        interceptor.escapeParamObject(map);
        assertEquals("\\%", map.get("a"));
        assertEquals(1, map.get("b"));
    }

    @Test
    public void escapeMapObject() {
        UserParam userParam = new UserParam();
        userParam.setId(1);
        userParam.setUsername("%");
        Map<String, Object> map = new HashMap<>();
        map.put("user", userParam);
        map.put("param1", userParam);
        EscapeSqlParameterInterceptor interceptor = new EscapeSqlParameterInterceptor();
        interceptor.escapeParamObject(map);
        UserParam escapedUserParam = (UserParam) map.get("user");
        assertEquals("\\%", escapedUserParam.getUsername());
        assertEquals(1, (int)escapedUserParam.getId());
    }

    @Test
    public void escapeObject() {
        UserParam userParam = new UserParam();
        userParam.setId(1);
        userParam.setUsername("%");
        EscapeSqlParameterInterceptor interceptor = new EscapeSqlParameterInterceptor();
        interceptor.escapeParamObject(userParam);
        assertEquals("\\%", userParam.getUsername());
        assertEquals(1, (int)userParam.getId());
    }

}