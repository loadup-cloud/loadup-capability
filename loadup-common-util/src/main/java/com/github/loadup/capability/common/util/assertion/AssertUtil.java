package com.github.loadup.capability.common.util.assertion;

/*-
 * #%L
 * loadup-common-util
 * %%
 * Copyright (C) 2022 - 2023 loadup_cloud
 * %%
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
 * #L%
 */

import com.github.loadup.capability.common.exception.AssertionException;
import com.github.loadup.capability.common.response.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 断言工具类
 * </ul>
 */
public class AssertUtil {

    /**
     * 断言表达式的值为true，否则抛出指定错误信息。
     *
     * @param expValue   断言表达式
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void isTrue(final boolean expValue, final ResultCode resultCode,
                              final Object... objs) {

        if (!expValue) {
            String logString = getLogString(objs);
            String resultMsg = StringUtils.isBlank(logString) ? resultCode.getMessage()
                    : logString;
            AssertionException exception = new AssertionException(resultCode, resultMsg);
            exception.setResultCode(resultCode);
            throw exception;
        }
    }

    /**
     * 断言表达式的值为false，否则抛出指定错误信息。
     *
     * @param expValue   断言表达式
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void isFalse(final boolean expValue, final ResultCode resultCode,
                               final Object... objs) {
        isTrue(!expValue, resultCode, objs);
    }

    /**
     * 断言两个对象相等，否则抛出指定错误信息。
     *
     * @param obj1       待比较对象
     * @param obj2       待比较对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void equals(final Object obj1, final Object obj2,
                              final ResultCode resultCode, final Object... objs) {

        isTrue(obj1 == null ? obj2 == null : obj1.equals(obj2), resultCode, objs);
    }

    /**
     * 断言两个对象不等，否则抛出指定错误信息。
     *
     * @param obj1       待比较对象
     * @param obj2       待比较对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notEquals(final Object obj1, final Object obj2,
                                 final ResultCode resultCode, final Object... objs) {

        isTrue(obj1 == null ? obj2 != null : !obj1.equals(obj2), resultCode, objs);
    }

    /**
     * 断言两个对象相同，否则抛出指定错误信息。
     *
     * @param base       待比较对象
     * @param target     待比较对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void is(final Object base, final Object target,
                          final ResultCode resultCode, final Object... objs) {

        isTrue(base == target, resultCode, objs);
    }

    /**
     * 断言两个对象不相同，否则抛出指定错误信息。
     *
     * @param base       待比较对象
     * @param target     待比较对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void isNot(final Object base, final Object target,
                             final ResultCode resultCode, final Object... objs) {

        isTrue(base != target, resultCode, objs);
    }

    /**
     * 断言指定对象在容器中。否则抛出指定错误信息。
     *
     * <p>注意：这里的"在"是指"equals"，不是指对象=。
     *
     * @param base       待查对象
     * @param collection 容器集合
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void contains(final Object base, final Collection<?> collection,
                                final ResultCode resultCode, final Object... objs) {
        notEmpty(collection, resultCode, objs);
        isTrue(collection.contains(base), resultCode, objs);
    }

    /**
     * 断言指定对象在容器中。否则抛出指定错误信息。
     *
     * <p>注意：这里的"在"是指"="，不是指对象equals。
     *
     * @param base       待查对象
     * @param collection 容器集合
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void in(final Object base, final Object[] collection,
                          final ResultCode resultCode, final Object... objs) {

        notNull(collection, resultCode, objs);

        boolean hasEqual = false;
        for (Object obj2 : collection) {

            if (base == obj2) {
                hasEqual = true;
                break;
            }
        }

        isTrue(hasEqual, resultCode, objs);
    }

    /**
     * 断言指定对象不在容器中。否则抛出指定错误信息。
     *
     * <p>注意：这里的"在"是指"="，不是指对象equals。
     *
     * @param base       待查对象
     * @param collection 容器集合
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notIn(final Object base, final Object[] collection,
                             final ResultCode resultCode, final Object... objs) {

        if (null == collection) {
            return;
        }

        for (Object obj2 : collection) {
            isTrue(base != obj2, resultCode, objs);
        }
    }

    /**
     * 断言对象为空，否则抛出指定错误信息。
     *
     * @param str        断言字符串
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void blank(final String str, final ResultCode resultCode,
                             final Object... objs) {

        isTrue(StringUtils.isBlank(str), resultCode, objs);
    }

    /**
     * 断言对象为非空，否则抛出指定错误信息。
     *
     * @param str        断言字符串
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notBlank(final String str, final ResultCode resultCode,
                                final Object... objs) {

        isTrue(StringUtils.isNotBlank(str), resultCode, objs);
    }

    /**
     * 断言对象为null，否则抛出指定错误信息。
     *
     * @param object     待检查对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void isNull(final Object object, final ResultCode resultCode,
                              final Object... objs) {

        isTrue(object == null, resultCode, objs);
    }

    /**
     * 断言对象非null，否则抛出指定错误信息。
     *
     * @param object     待检查对象
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notNull(final Object object, final ResultCode resultCode,
                               final Object... objs) {

        isTrue(object != null, resultCode, objs);
    }

    /**
     * 断言集合不为空或null，否则抛出指定错误信息。
     *
     * @param collection 待检查集合
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notEmpty(final Collection collection, final ResultCode resultCode,
                                final Object... objs) {

        isTrue(!CollectionUtils.isEmpty(collection), resultCode, objs);
    }

    /**
     * 断言集合为空或null，否则抛出指定错误信息。
     *
     * @param collection 待检查集合
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void empty(final Collection collection, final ResultCode resultCode,
                             final Object... objs) {
        isTrue(CollectionUtils.isEmpty(collection), resultCode, objs);
    }

    /**
     * 断言map不为空或null，否则抛出指定错误信息。
     *
     * @param map        待检查map
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void notEmpty(final Map map, final ResultCode resultCode,
                                final Object... objs) {

        isTrue(!CollectionUtils.isEmpty(map), resultCode, objs);
    }

    /**
     * 断言map为空或null，否则抛出指定错误信息。
     *
     * @param map        待检查map
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void empty(final Map map, final ResultCode resultCode,
                             final Object... objs) {

        isTrue(CollectionUtils.isEmpty(map), resultCode, objs);
    }

    /**
     * 推断在条件成立下，str非空。否则抛出指定错误信息。
     *
     * @param condition  推断条件
     * @param str        断言字符串
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void deductNotBlank(final boolean condition, final String str,
                                      final ResultCode resultCode, final Object... objs) {

        if (!condition) {
            return;
        }

        notBlank(str, resultCode, objs);
    }

    /**
     * 推断在条件成立下，expValue为真。否则抛出指定错误信息。
     *
     * @param condition  推断条件
     * @param expValue   断言表达式
     * @param resultCode 错误码
     * @param objs       任意个异常描述信息的参数
     */
    public static void deductTrue(final boolean condition, final boolean expValue,
                                  final ResultCode resultCode, final Object... objs) {

        if (!condition) {

            return;
        }

        isTrue(expValue, resultCode, objs);
    }

    /**
     * 生成输出到日志的字符串
     *
     * @param objs 任意个要输出到日志的参数
     * @return 日志字符串
     */
    private static String getLogString(Object... objs) {
        return Arrays.stream(objs).map(Object::toString).collect(Collectors.joining());
    }

}
