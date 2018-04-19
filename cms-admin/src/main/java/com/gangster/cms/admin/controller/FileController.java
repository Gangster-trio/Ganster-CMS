package com.gangster.cms.admin.controller;

import com.gangster.cms.admin.annotation.SystemControllerLog;
import com.gangster.cms.admin.dto.AjaxData;
import com.gangster.cms.admin.dto.MessageDto;
import com.gangster.cms.admin.service.SettingService;
import com.gangster.cms.admin.service.WebFileService;
import com.gangster.cms.admin.service.web.FileUploadService;
import com.gangster.cms.common.constant.CmsConst;
import com.gangster.cms.common.pojo.WebFile;
import com.gangster.cms.common.pojo.WebFileExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Yoke
 * Created on 2018/4/16
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private WebFileService webFileService;
    @Autowired
    private SettingService settingService;

    @SystemControllerLog(description = "添加文件")
    @PostMapping("/upload/{id}")
    public MessageDto uploadFile(@PathVariable(required = false) Integer id, @Param("file") MultipartFile file) {
        String s = fileUploadService.uploadFile(id, file);
        return MessageDto.success(s);
    }

    @SystemControllerLog(description = "下载文件")
    @PostMapping("/download/{id}")
    public void download(@PathVariable("id") Integer id, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/ocet-stream");
        FileInputStream inputStream = null;
        try {
            WebFile webFile = webFileService.selectByPrimaryKey(id);
            String webPath = webFile.getFileName();
            String downPath = settingService.get(CmsConst.FILE_PATH) + webPath.split("/")[2];
            File file = new File(downPath);
            inputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SystemControllerLog(description = "列出某个网站的文件")
    @GetMapping("/list")
    public AjaxData list(@RequestParam Integer siteId, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        WebFileExample webFileExample = new WebFileExample();
        webFileExample.or().andFileSiteIdEqualTo(siteId);
        PageInfo<WebFile> pageInfo = PageHelper.startPage(page, limit).doSelectPageInfo(() -> webFileService.selectByExample(webFileExample));
        if (null == pageInfo) {
            return new AjaxData(1, "failed", 0, null);
        }
        return new AjaxData(0, "success", pageInfo.getTotal(), pageInfo.getList());
    }

    @GetMapping("/delete/{id}")
    public MessageDto delete(@PathVariable Integer id) {
        WebFile file = webFileService.selectByPrimaryKey(id);
        if (file == null) {
            return MessageDto.fail(1, "要删除的文件不存在");
        }
        webFileService.deleteByPrimaryKey(id);
        return MessageDto.success(null);
    }
}