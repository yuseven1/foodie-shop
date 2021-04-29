package com.yuseven.exception;

import com.yuseven.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author Yu Qifeng
 * @version v1.0
 * @date 2021/3/29 23:45
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    // MaxUploadSizeExceededException
    // 上传文件超过500K，捕获异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return JSONResult.errorMsg("文件上传大小不能超过500K，请压缩图片或降低图片质量");
    }
}
