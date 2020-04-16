package com.github.guava.basic;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

/**
 * @author hangs.zhang
 * @date 19-7-25 上午10:48
 * *********************
 * function:
 */
public class ObjectsTest {

    @Test
    public void objectsTest() {
        System.out.println(Objects.equal(null, null));
        System.out.println(Objects.hashCode(null, null));

        System.out.println(MoreObjects.firstNonNull(null, "string"));
        MoreObjects.toStringHelper(this.getClass());

        // 都不为空
        System.out.println(ObjectUtils.allNotNull("a", null));
        // 任意一个不为空
        System.out.println(ObjectUtils.anyNotNull("a", null));
    }

}
