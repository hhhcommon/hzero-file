package org.hzero.file.api.controller.v1;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hzero.file.app.service.PreviewService;
import org.hzero.file.config.FileSwaggerApiConfig;
import org.hzero.file.infra.util.CodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.iam.ResourceLevel;
import io.choerodon.swagger.annotation.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 文件预览API
 *
 * @author xianzhi.chen@hand-china.com 2019年2月15日上午11:29:45
 */
@Api(tags = FileSwaggerApiConfig.FILE_PREVIEW)
@RestController("filePreviewController.v1")
@RequestMapping("/v1/{organizationId}")
public class FilePreviewController {

    private final PreviewService filePreviewService;

    @Autowired
    public FilePreviewController(PreviewService filePreviewService) {
        this.filePreviewService = filePreviewService;
    }

    @Permission(permissionLogin = true, level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据文件key预览文件")
    @GetMapping("/file-preview/by-key")
    public void previewByKey(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
            @ApiParam(value = "文件Key", required = true) @RequestParam(value = "fileKey") String fileKey) {
        fileKey = CodeUtils.decode(fileKey);
        filePreviewService.previewFileByKey(request, response, organizationId, fileKey);
    }

    @Permission(permissionLogin = true, level = ResourceLevel.ORGANIZATION)
    @ApiOperation(value = "根据文件url预览文件")
    @GetMapping("/file-preview/by-url")
    public void previewByUrl(
            HttpServletRequest request, HttpServletResponse response,
            @ApiParam(value = "租户ID", required = true) @PathVariable Long organizationId,
            @ApiParam(value = "bucketName", required = true) @RequestParam("bucketName") String bucketName,
            @ApiParam(value = "存储配置编码") @RequestParam(value = "storageCode", required = false) String storageCode,
            @ApiParam(value = "文件url", required = true) @RequestParam("url") String url) {
        url = CodeUtils.decode(url);
        filePreviewService.previewFileByUrl(request, response, organizationId, storageCode, bucketName, url);
    }
}
