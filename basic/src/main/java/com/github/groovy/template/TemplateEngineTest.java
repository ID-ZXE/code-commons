package com.github.groovy.template;

import com.github.groovy.mock.JavaService;
import com.google.common.collect.Maps;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

public class TemplateEngineTest {

    public static void main(String[] args) throws Exception {
        SimpleTemplateEngine engine = new SimpleTemplateEngine();
        String param = "param";
        JavaService service = new JavaService();
        ThreadLocal<String> threadLocal = new ThreadLocal<>();

        // 测试方法调用
        String text = "${param.substring(0, 1)}" + "*****" + "${service.echo(message)}";
        // 测试逻辑
        String text2 = "${service.testCondition(false) || service.testCondition(true)}";
        // context, 方法连续调用
        String text3 = "${context.set(service.echo(\"data\"))}";
        // 三目运算符, service.echo()输出为null
        String text4 = "${service.echo() == \"echo\" ? \"data1\" : \"data2\"}";
        String text5 = "${service.obj() == service.object ? \"data1\" : \"data2\"}";
        String text6 = "${service.obj().equals(service.object) ? \"result1\" : \"result2\"}";
        String text7 = "${service.obj().equals(service.object) ? service.echo() : \"result2\"}";

        // 创建模板很耗时
        Template template = engine.createTemplate(text);

        Map<String, Object> map = Maps.newHashMap();
        map.put("param", param);
        map.put("message", "message");
        map.put("service", service);
        map.put("context", threadLocal);

        Writer writer = new StringWriter();
        template.make(map).writeTo(writer);
        String result = writer.toString();

        System.out.println("result:" + result);
        System.out.println("context data:" + threadLocal.get());
    }

}
