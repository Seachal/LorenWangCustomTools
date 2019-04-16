package javabase.lorenwang.tools.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import javabase.lorenwang.tools.JtlwLogUtils;
import javabase.lorenwang.tools.common.JtlwCheckVariateUtils;

/**
 * 创建时间：2019-01-28 下午 20:19:47
 * 创建人：王亮（Loren wang）
 * 功能作用：文件操作工具类
 * 思路：
 * 方法：1、读取图片文件并获取字节
 * 2、从指定路径的文件中读取Bytes
 * 3、从File中读取Bytes
 * 4、从InputStream中读取Bytes
 * 5、将InputStream写入File
 * 6、将文本写入文件
 * 7、将文本写入文件，同时决定是否为追加写入
 * 8、复制单个文件
 * 9、删除文件
 * 10、获取文件大小，单位B
 * 11、删除文件夹以及目录下的文件
 * 12、获取绝对路径下最后一个文件夹名称
 * 13、根据正则获取指定目录下的所有文件列表(使用递归扫描方式)
 * 14、根据正则获取指定目录下的所有文件列表(使用队列扫描方式)
 *
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 */

public class JtlwFileOptionUtils {
    private final String TAG = "FileOptionUtils";
    private static JtlwFileOptionUtils baseUtils;
    /**
     * 线程安全的队列,用于文件操作
     */
    private ConcurrentLinkedQueue<File> fileOptionsLinkedQueue;
    /**
     * 流转换的缓存大小
     */
    private final int BUFFER_SIZE = 1024;

    public static JtlwFileOptionUtils getInstance() {
        if (baseUtils == null) {
            baseUtils = new JtlwFileOptionUtils();
        }
        return (JtlwFileOptionUtils) baseUtils;
    }

    /******************************************读取部分*********************************************/

    /**
     * 读取图片文件并获取字节
     *
     * @param isCheckFile 是否检查文件
     * @param filePath    文件地址
     * @return
     */
    public byte[] readImageFileGetBytes(Boolean isCheckFile, String filePath) {
        if (isCheckFile && JtlwCheckVariateUtils.getInstance().checkFileIsExit(filePath)
                && JtlwCheckVariateUtils.getInstance().checkFileIsImage(filePath)) {
            return null;
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            return bytes;
        } catch (Exception e) {
            JtlwLogUtils.logE(TAG, "图片读取异常");
            return null;
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从指定路径的文件中读取Bytes
     */
    public byte[] readBytes(String path) {
        try {
            File file = new File(path);
            return readBytes(file);
        } catch (Exception e) {
            JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            return new byte[]{};
        }
    }

    /**
     * 从File中读取Bytes
     */
    public byte[] readBytes(File file) {
        FileInputStream fis = null;
        try {
            if (!file.exists()) {
                return null;
            }
            fis = new FileInputStream(file);
            return readBytes(fis);
        } catch (Exception e) {
            JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            return new byte[]{};
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
                JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            }

        }
    }

    /**
     * 从InputStream中读取Bytes
     */
    public byte[] readBytes(InputStream inputStream) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int length = inputStream.read(buffer);
            while (length != -1) {
                baos.write(buffer, 0, length);
                baos.flush();
                length = inputStream.read(buffer);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            return new byte[]{};
        } finally {
            try {
                baos.close();
            } catch (Exception e) {
                JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            }

        }
    }


    /******************************************写入部分*********************************************/

    /**
     * 将InputStream写入File
     */
    public Boolean writeToFile(File file, InputStream inputStream, Boolean append) {
        if (file.exists()) {
            file.delete();
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, append);
            byte[] buffer = new byte[BUFFER_SIZE];
            int length = inputStream.read(buffer);
            while (length != -1) {
                fos.write(buffer, 0, length);
                fos.flush();
                length = inputStream.read(buffer);
            }
            return true;
        } catch (Exception e) {
            JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            return false;
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            }
        }
    }

    /**
     * 将文本写入文件
     */
    public Boolean writeToFile(File file, String text) {
        return writeToFile(file, text, "UTF-8", false);
    }

    /**
     * 将文本写入文件，同时决定是否为追加写入
     */
    public Boolean writeToFile(File file, String text, String encoding, Boolean append) {
        try {
            return writeToFile(file, new ByteArrayInputStream(text.getBytes(encoding)), append);
        } catch (UnsupportedEncodingException e) {
            JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            return false;
        }

    }

    /**
     * 将byte数组写入文件
     *
     * @param file   文件
     * @param buffer 要存入的数组
     * @return
     */
    public boolean writeToFile(File file, byte[] buffer) {
        return writeToFile(file, buffer, false);
    }

    /**
     * 将byte数组写入文件，是否追加
     *
     * @param file   文件
     * @param buffer 要存入的数组
     * @param append 是否追加
     * @return
     */
    public boolean writeToFile(File file, byte[] buffer, boolean append) {
        FileOutputStream fos = null;
        try {
            if (file.exists()) {
                file.delete();
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            fos = new FileOutputStream(file, append);
            fos.write(buffer);
            return true;
        } catch (Exception e) {
            JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                JtlwLogUtils.logE(TAG, JtlwCheckVariateUtils.getInstance().isEmpty(e) ? "" : e.getMessage());
            }
        }
    }




    /******************************************其他文件操作部分**************************************/

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public Boolean copyFile(String oldPath, String newPath) {
        FileOutputStream fs = null;
        try {
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                FileInputStream inStream = new FileInputStream(oldPath); //读入原文件
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length = inStream.read(buffer);
                while (length != -1) {
                    fs.write(buffer, 0, length);
                    fs.flush();
                    length = inStream.read(buffer);
                }
                inStream.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 删除文件
     */
    public Boolean deleteFile(String url) {
        boolean result = false;
        File file = new File(url);
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /**
     * 获取文件大小，单位B
     *
     * @param file
     * @param filtrationDir
     * @return
     */
    public Long getFileSize(File file, String filtrationDir) {
        long fileSize = 0L;
        if (file.isFile() && file.exists()) {
            fileSize += file.length();
        } else if (file.exists()) {
            for (File file1 : file.listFiles()) {
                if (file1.isFile()) {
                    fileSize += file1.length();
                } else if (file.isDirectory() || filtrationDir == null || filtrationDir != file.getAbsolutePath()) {
                    fileSize += getFileSize(file1, filtrationDir);
                }
            }
        }
        return fileSize;
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public Boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) {
            return false;
        } else {
            return dirFile.delete();
        }
        //删除当前空目录
    }

    /**
     * 获取绝对路径下最后一个文件夹名称
     *
     * @param absolutePath
     * @return
     */
    public String getLastDirctoryName(String absolutePath) {
        if (absolutePath == null) {
            return "";
        }
        //创建新的，防止由于使用同一个对象对于调用该方法的值产生影响
        String path = absolutePath.intern();
        //判断是不是文件，是文件的话获取父文件夹路径
        File file = new File(path);
        if (file.isFile()) {
            path = file.getParentFile().getAbsolutePath();
        }

        if (path.contains("/")) {
            //循环移除末尾的“/”，防止一个路径下有多个“/”
            while (path.substring(path.lastIndexOf("/")).intern().equals("/")) {
                path = path.substring(0, path.length() - 1);
            }
            path = path.substring(path.lastIndexOf("/") + 1);
        }
        return path;
    }



    /**
     * 根据正则获取指定目录下的所有文件列表(使用递归扫描方式)
     *
     * @param scanPath     要扫描的问题件路径
     * @param matchRegular 文件正则
     * @return 文件列表
     */
    public List<File> getFileListForMatchRecursionScan(String scanPath, String matchRegular) {
        List<File> list = new ArrayList<>();
        if (!JtlwCheckVariateUtils.getInstance().isHaveEmpty(scanPath, matchRegular)) {
            File file = new File(scanPath);
            if (file.exists()) {
                for (File childFile : file.listFiles()) {
                    if (childFile.isDirectory()) {
                        if (!file.getName().matches("^\\..*")) {
                            list.addAll(getFileListForMatchRecursionScan(childFile.getAbsolutePath(), matchRegular));
                        }
                    } else if (childFile.isFile()) {
                        if (childFile.getName().matches(matchRegular)) {
                            list.add(childFile);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 根据正则获取指定目录下的所有文件列表(使用队列扫描方式)
     *
     * @param scanPath     要扫描的文件路径
     * @param matchRegular 要返回的文件的正则格式
     * @return 扫描到的文件列表
     */
    public synchronized List<File> getFileListForMatchLinkedQueueScan(String scanPath, final String matchRegular) {
        final List<File> list = new ArrayList<>();
        if (!JtlwCheckVariateUtils.getInstance().isHaveEmpty(scanPath, matchRegular)) {
            if (fileOptionsLinkedQueue == null) {
                fileOptionsLinkedQueue = new ConcurrentLinkedQueue<>();
            }
            File file = new File(scanPath);
            if (file.exists() && !file.isFile()) {
                //获取到根目录下的文件和文件夹
                final File[] files = file.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File file, String s) {
                        //过滤掉隐藏文件
                        return !file.getName().trim().startsWith(".");
                    }
                });
                //临时存储任务,便于后面全部投递到线程池
                List<Runnable> runnableList = new ArrayList<>();
                //创建信号量(最多同时有10个线程可以访问)
                final Semaphore semaphore = new Semaphore(100);
                for (File f : files) {
                    if (f.isDirectory()) {
                        //把目录添加进队列
                        fileOptionsLinkedQueue.offer(f);
                        //创建的线程的数目是根目录下文件夹的数目
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                //开启文件夹扫描，使用多线程异步扫描进行处理，只有当全部处理完成是返回
                                list.addAll(startFileScan(matchRegular));
                            }
                        };
                        runnableList.add(runnable);
                    } else if (f.isFile()) {
                        if (f.getName().matches(matchRegular)) {
                            list.add(f);
                        }
                    }
                }

                //固定数目线程池(最大线程数目为cpu核心数,多余线程放在等待队列中)
                final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                for (Runnable runnable : runnableList) {
                    executorService.submit(runnable);
                }
                //不允许再添加线程
                executorService.shutdown();
                //等待线程池中的所有线程运行完成
                while (true) {
                    if (executorService.isTerminated()) {
                        break;
                    }
                    try {
                        //休眠1s
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        fileOptionsLinkedQueue = null;
        return list;
    }

    /**
     * 获取指定文件夹下的所有文件信息，要扫描的文件夹从队列当中获取，不使用递归，使用队列处理，当检测到是文件夹的话则将文件夹加入到队列当中继续执行循环
     * 知道当前队列内的所有文件夹当中的所有文件都被取出记录
     *
     * @param matchRegular 要取出文件的正则
     * @return 符合正则的集合
     */
    private List<File> startFileScan(String matchRegular) {
        List<File> list = new ArrayList<>();
        //对目录进行层次遍历，知道队列内容全部执行完成
        while (!fileOptionsLinkedQueue.isEmpty()) {
            //队头出队列
            final File tmpFile = fileOptionsLinkedQueue.poll();
            final File[] fileArray = tmpFile.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    //过滤掉隐藏文件
                    return !file.getName().trim().startsWith(".");
                }
            });
            for (File f : fileArray) {
                if (f.isDirectory()) {
                    //把目录添加进队列
                    fileOptionsLinkedQueue.offer(f);
                } else {
                    if (f.getName().matches(matchRegular)) {
                        list.add(f);
                    }
                }
            }
        }
        return list;
    }
}
