package com.ya.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

/**
 * 打印请求方的信息
 * @author hqh
 */
@Slf4j
@Aspect
@Component
public class HttpAspect {

    @Pointcut("execution(* com.ya.controller.*.*(..))")
    private void controllerAspect() {
    }

    @Before(value = "controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("url:{}", request.getRequestURL());
        log.info("args:{}", joinPoint.getArgs());
        log.info("method:{}", request.getMethod());
        log.info("ip:{}", request.getRemoteAddr());
        log.info("classMethod:{}.{}", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName());
    }

//    @After(value = "controllerAspect()")
//    public void doAfter(JoinPoint joinPoint) {
//    }

    @AfterReturning(returning = "object", value = "controllerAspect()")
    public void doAfterReturning(Object object) {
        log.info("response:{}", object.toString());
    }

//    /**
//     * 打印类method的名称以及参数
//     * @param point 切面
//     */
//    private void printMethodParams(JoinPoint point) {
//        if(point == null) {
//            logger.info("JoinPoint is null");
//            return;
//        }
//
//        /**
//         * Signature 包含了方法名、申明类型以及地址等信息
//         */
//        String class_name = point.getTarget().getClass().getName();
//        String method_name = point.getSignature().getName();
//        //重新定义日志
//        logger = LoggerFactory.getLogger(point.getTarget().getClass());
//        logger.info("class_name = {}", class_name);
//        logger.info("method_name = {}", method_name);
//
//        /**
//         * 获取方法的参数值数组。
//         */
//        Object[] method_args = point.getArgs();
//
//        try {
//            String[] paramNames = getFieldsName(class_name, method_name);
//            logParam(paramNames, method_args);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 使用javassist来获取方法参数名称
//     * @param class_name    类名
//     * @param method_name   方法名
//     * @return
//     * @throws Exception
//     */
//    private String[] getFieldsName(String class_name, String method_name) throws Exception {
//        Class<?> clazz = Class.forName(class_name);
//        String clazz_name = clazz.getName();
//        ClassPool pool = ClassPool.getDefault();
//        ClassClassPath classPath = new ClassClassPath(clazz);
//        pool.insertClassPath(classPath);
//
//        CtClass ctClass = pool.get(clazz_name);
//        CtMethod ctMethod = ctClass.getDeclaredMethod(method_name);
//        MethodInfo methodInfo = ctMethod.getMethodInfo();
//        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
//        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
//        if(attr == null) {
//            return null;
//        }
//        String[] paramsArgsName = new String[ctMethod.getParameterTypes().length];
//        int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
//        for (int i = 0; i < paramsArgsName.length; i++) {
//            paramsArgsName[i] = attr.variableName(i + pos);
//        }
//        return paramsArgsName;
//    }
//
//
//    /**
//     * 判断是否为基本类型：包括String
//     * @param clazz clazz
//     * @return true：是; false：不是
//     */
//    private boolean isPrimite(Class<?> clazz) {
//        return clazz.isPrimitive() || clazz == String.class;
//    }
//
//    /**
//     * 打印方法参数值  基本类型直接打印，非基本类型需要重写toString方法
//     * @param paramsArgsName    方法参数名数组
//     * @param paramsArgsValue   方法参数值数组
//     */
//    private void logParam(String[] paramsArgsName, Object[] paramsArgsValue) {
//        if(ArrayUtils.isEmpty(paramsArgsName) || ArrayUtils.isEmpty(paramsArgsValue)) {
//            logger.info("该方法没有参数");
//            return;
//        }
//
//        StringBuffer buffer = new StringBuffer();
//        for (int i = 0; i < paramsArgsName.length; i++) {
//            //参数名
//            String name = paramsArgsName[i];
//            //参数值
//            Object value = paramsArgsValue[i];
//            buffer.append(name +" = ");
//
//            if(isPrimite(value.getClass())) {
//                buffer.append(value + "  ,");
//            } else {
//                buffer.append(value.toString() + "  ,");
//            }
//        }
//
//        logger.info(buffer.toString());
//    }
}
