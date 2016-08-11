package org.thiki.kanban.foundation.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xubt on 8/7/16.
 */
public class TemplateFactory {
    // 日志记录对象
    private static Logger log = LoggerFactory.getLogger(TemplateFactory.class);
    // 模板文件路径
    private static String templatePath = "/mail/template";
    // 模板文件内容编码
    private static final String ENCODING = "utf-8";
    // 模板生成配置
    private static Configuration conf = new Configuration();
    // 邮件模板缓存池
    private static Map<String, Template> tempMap = new HashMap<String, Template>();

    static {
        // 设置模板文件路径
        conf.setClassForTemplateLoading(TemplateFactory.class, templatePath);
    }

    /**
     * 通过模板文件名称获取指定模板
     *
     * @param name 模板文件名称
     * @return Template
     * @throws IOException
     */
    private static Template getTemplateByName(String name) throws IOException {
        if (tempMap.containsKey(name)) {
            log.debug("the template is already exist in the map,template name :"
                    + name);
            // 缓存中有该模板直接返回
            return tempMap.get(name);
        }
        // 缓存中没有该模板时，生成新模板并放入缓存中
        Template template = conf.getTemplate(name);
        tempMap.put(name, template);
        log.debug("the template is not found  in the map,template name :"
                + name);
        return template;
    }

    /**
     * 根据指定模板将内容输出到控制台
     *
     * @param name 模板文件名称
     * @param map  与模板内容转换对象
     * @return void
     */
    public static void outputToConsole(String name, Map<String, String> map) {
        try {
            // 通过Template可以将模板文件输出到相应的流
            Template temp = getTemplateByName(name);
            temp.setEncoding(ENCODING);
            temp.process(map, new PrintWriter(System.out));
        } catch (TemplateException e) {
            log.error(e.toString(), e);
        } catch (IOException e) {
            log.error(e.toString(), e);
        }
    }

    /**
     * 根据指定模板将内容直接输出到文件
     *
     * @param name    模板文件名称
     * @param map     与模板内容转换对象
     * @param outFile 输出的文件绝对路径
     * @return void
     */
    public static void outputToFile(String name, Map<String, String> map,
                                    String outFile) {
        FileWriter out = null;
        try {
            out = new FileWriter(new File(outFile));
            Template temp = getTemplateByName(name);
            temp.setEncoding(ENCODING);
            temp.process(map, out);
        } catch (IOException e) {
            log.error(e.toString(), e);
        } catch (TemplateException e) {
            log.error(e.toString(), e);
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                log.error(e.toString(), e);
            }
        }
    }

    /**
     * @param name 模板文件的名称
     * @param map  与模板内容转换对象
     * @return String
     * @throws IOException
     * @throws TemplateException
     */
    public static String generateHtmlFromFtl(String name,
                                             Map<String, String> map) throws IOException, TemplateException {
        Writer out = new StringWriter(2048);
        Template temp = getTemplateByName(name);
        temp.setEncoding(ENCODING);
        temp.process(map, out);
        return out.toString();
    }
}
