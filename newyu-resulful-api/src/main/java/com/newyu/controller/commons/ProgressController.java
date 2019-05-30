package com.newyu.controller.commons;

import com.google.common.collect.Lists;
import com.newyu.utils.cache.MemoryCache;
import com.newyu.utils.progress.DefaultProgressListener;
import com.newyu.utils.progress.ProgressEvent;
import com.newyu.utils.progress.Progresses;
import com.newyu.utils.spring.ModelAndViewFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * ClassName: ProgressController <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 17-11-27 下午2:27 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
public class ProgressController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/progress", method = RequestMethod.GET)
    public ModelAndView process(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("开始获取进度");

        String onlyKey = request.getParameter("onlyKey");
        long timestamp = 0L;
        if (request.getParameter("timestamp") != null) {
            timestamp = Long.parseLong(request.getParameter("timestamp"));
        }
        Progresses progresses = MemoryCache.instance().getValue(onlyKey);
        List<ProgressEvent> progress = Lists.newArrayList();
        boolean isOver = true;
        if (progresses != null) {
            DefaultProgressListener listener = new DefaultProgressListener();
            progresses.register(listener, timestamp);
            progress = listener.getEvents();
            isOver = listener.isOver();
            if (isOver) {
                MemoryCache.instance().remove(onlyKey);
            }
        } else {
            logger.debug("onlyKey获取的对象为null");
            ProgressEvent event = new ProgressEvent(100, 100, "完成了", true);
            progress.add(event);
        }

        logger.debug("获取进度完毕");

        return ModelAndViewFactory.instance("")
                .with("progresses", progress)
                .with("isOver", isOver)
                .build();
    }
}
