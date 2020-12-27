package com.hong.neo4j.service;

import java.io.InputStream;

/**
 * @Author : KongJHong
 * @Date : 2020-12-25 13:46
 * @Version : 1.0
 * Description     : 上传服务
 */
public interface UploadService {

	boolean processUploading(InputStream inputStream);
}
