package com.newyu.controller.commons;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.google.common.collect.Lists;
import com.newyu.utils.spring.ModelAndViewFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * ClassName: LoggerController <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 17-11-29 上午9:00 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LoggerController {


    @RequestMapping("/info")
    public ModelAndView logInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.debug("get log/info");
        ILoggerFactory factory = LoggerFactory.getILoggerFactory();
        LoggerContext context = (LoggerContext) factory;
        List<Logger> loggers = context.getLoggerList();
        List<String[]> logInfos = Lists.newArrayList();
        for (ch.qos.logback.classic.Logger log : loggers) {
            if (log.getLevel() != null) {
                logInfos.add(new String[]{log.getName(), log.getLevel().toString()});
            }
        }
        log.debug("get log/info end");
        return ModelAndViewFactory.instance("").with("logInfos", logInfos).build();
    }

    @RequestMapping(value = "/setting", method = RequestMethod.POST)
    public ModelAndView logSetting(@RequestBody Map<String, String> logData, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        log.debug("post /log/setting .....");
        String logName = logData.get("logName");
        String levelName = logData.get("levelName");
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(logName);
        logger.setLevel(getLevelWithLevelName(levelName));
        log.debug("post /log/setting end");
        return ModelAndViewFactory.instance().build();
    }

    private Level getLevelWithLevelName(String levelName) {
        if ("OFF".equalsIgnoreCase(levelName)) {
            return Level.OFF;
        } else if ("ERROR".equalsIgnoreCase(levelName)) {
            return Level.ERROR;
        } else if ("WARN".equalsIgnoreCase(levelName)) {
            return Level.WARN;
        } else if ("DEBUG".equalsIgnoreCase(levelName)) {
            return Level.DEBUG;
        } else if ("TRACE".equalsIgnoreCase(levelName)) {
            return Level.TRACE;
        } else if ("ALL".equalsIgnoreCase(levelName)) {
            return Level.ALL;
        } else {
            return Level.ERROR;
        }
    }

}
