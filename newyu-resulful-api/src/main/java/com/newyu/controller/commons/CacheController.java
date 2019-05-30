package com.newyu.controller.commons;

import com.newyu.utils.cache.MemoryCache;
import com.newyu.utils.spring.ModelAndViewFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * ClassName: CacheController <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: ADD REASON(可选). <br/>
 * date: 17-11-29 上午9:36 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@RestController
public class CacheController {

    @RequestMapping("/cache/info")
    public ModelAndView cacheInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Set<String> keys = MemoryCache.instance().keys();
        return ModelAndViewFactory.instance("").with("keys", keys).build();
    }

    @RequestMapping(value = "/cache/remove/{key}")
    public ModelAndView cacheRemvoe(@PathVariable String key, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        MemoryCache.instance().remove(key);
        return ModelAndViewFactory.instance().build();
    }

}
