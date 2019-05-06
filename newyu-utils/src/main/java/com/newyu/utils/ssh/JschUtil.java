package com.newyu.utils.ssh;

import com.google.common.base.Preconditions;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: JschUtil <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-3-21 上午11:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class JschUtil {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Session sshSession = null;
    private ChannelSftp sftp = null;

    private JschUtil(String host, int port, String user, String password) {
        init(host, port, user, password);
    }

    private JschUtil(String url) {
        //root@host:port:password
        ParseUrl parseUrl = new ParseUrl(url);
        init(parseUrl.getHost(), parseUrl.getPort(), parseUrl.getUser(), parseUrl.getPassword());
    }

    private void init(String host, int port, String user, String password) {
        logger.debug("host[{}],port[{}],user[{}],password[{}]", host, port, user, password);
        Preconditions.checkArgument(!StringUtils.isEmpty(host), "服务其不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(user), "服务器用户名不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(password), "服务器密码不能为空");

        JSch jsch = new JSch();
        try {
            sshSession = jsch.getSession(user, host, port);
            sshSession.setConfig("PreferredAuthentications", "password");
            sshSession.setConfig("StrictHostKeyChecking", "no");
            sshSession.setPassword(password);
            sshSession.connect();

            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            throw new JschException("建立链接ssh session失败", e);
        }
    }

    public boolean uploadFile(String src, String desc) {
        try {
            sftp.put(new FileInputStream(new File(src)), desc);
        } catch (Exception e) {
            throw new JschException("上传文件" + src + "到服务" + desc + "失败", e);
        }
        return true;
    }
    public boolean uploadFile(InputStream in,String desc) {
        try {
            sftp.put(in, desc);
        } catch (Exception e) {
            throw new JschException("上传本地流文件到服务" + desc + "失败", e);
        }
        return true;
    }

    public boolean dowloadFile(String src, String desc) {
        try {
            sftp.get(src, desc);
        } catch (Exception e) {
            throw new JschException("下载文件" + src + "到本地" + desc + "失败", e);
        }
        return true;
    }

    public ByteArrayOutputStream dowloadFile(String src) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            sftp.get(src, out);
        } catch (Exception e) {
            throw new JschException("下载文件" + src + "到本流中出错失败", e);
        }
        return out;
    }

    public boolean openDir(String directory) {
        try {
            sftp.cd(directory);
            return true;
        } catch (SftpException e) {
            return false;
        }
    }

    public boolean deleteDir(String directory) {
        try {
            sftp.rmdir(directory);
        } catch (SftpException e) {
            throw new JschException("删除服务器目录" + directory + "出错", e);
        }
        return true;
    }

    public boolean noExistMkdirs(String dirs) {
        Path path = Paths.get(dirs);
        return noExistMkdirs(path);
    }

    public boolean noExistMkdirs(Path dirs) {
        Iterator<Path> it = dirs.iterator();
        try {
            String nowDir = sftp.pwd();
            sftp.cd("/");
            while (it.hasNext()) {
                String dir = it.next().toString();
                boolean dirExists = openDir(dir);
                if (!dirExists) {
                    sftp.mkdir(dir);
                    openDir(dir);
                }
            }
            sftp.cd(nowDir);
        } catch (Exception e) {
            throw new JschException("创建目录失败", e);
        }
        return true;
    }


    public void close() {
        if (sftp != null) {
            sftp.disconnect();
        }
        if (sshSession != null) {
            sshSession.disconnect();
        }
    }


    public static JschUtil newInstance(String host, int port, String user, String password) {
        return new JschUtil(host, port, user, password);
    }

    public static JschUtil newInstance(String url) {
        return new JschUtil(url);
    }

    private class ParseUrl {
        private Pattern pattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}|:[0-9]+:");
        private String user;
        private String host;
        private int port;
        private String password;

        public ParseUrl(String url) {
            //url格式:root@192.168.1.252:22:easyt&tnewa

            parse(url);
        }

        private void parse(String url) {

            Matcher matcher = pattern.matcher(url);

            int num = 0;
            while (matcher.find()) {
                ++num;

                String value = matcher.group();
                if (num == 1) {
                    host = value;
                    int start = matcher.start();
                    if (start == 0) {
                        Preconditions.checkArgument(false, "url[%s]格式不对", url);
                    }
                    user = url.substring(0, start - 1);
                } else if (num == 2) {
                    int len = value.length();
                    try {
                        port = Integer.parseInt(value.substring(1, len - 1));
                    } catch (Exception e) {
                        Preconditions.checkArgument(false, "url[%s]格式不对", url);
                    }
                    int end = matcher.end();
                    password = url.substring(end);
                }

                if (num > 2) {
                    Preconditions.checkArgument(false, "url[%s]格式不对", url);
                    break;
                }
            }

            Preconditions.checkArgument(num == 2, "url[%s]格式不对", url);
        }

        public String getUser() {
            return user;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getPassword() {
            return password;
        }
    }
}
