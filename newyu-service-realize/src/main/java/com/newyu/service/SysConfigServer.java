package com.newyu.service;

import com.newyu.domain.commons.SysConfig;
import org.springframework.stereotype.Service;

/**
 * ClassName: SysConfigServer <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-30 上午10:44 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class SysConfigServer {

    public SysConfig getSysConfig() {
        SysConfig sysConfig = new SysConfig();
        sysConfig.setUploadDir("/home/liuyu/tmp/excle/a");
        return sysConfig;
    }

}
