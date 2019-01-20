package com.pyg.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pyg.utils.FastDFSClient;
import com.pyg.utils.PygResult;

@RestController
@RequestMapping("/shop")
public class UploadController {
	
	// 注入常亮配置
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	/**
	 * 	需求:商家上传图片
	 * 	请求:upload
	 * 	参数:MultipartFile
	 * 	返回值:PygResult
	 */
	
	
	@RequestMapping("/upload")
	public PygResult uploadPic(MultipartFile file) {

		try {
			// 获取文件名称
			String originalfile = file.getOriginalFilename();
			// 截取文件扩展名,不要.
			String extName = originalfile.substring(originalfile.lastIndexOf(".")+1);
			
			// 创建工具类对象
			FastDFSClient fdfs = new FastDFSClient("classpath:config/client.conf");
			// 上传文件
			// gif,bmp,jpg..
			// 返回文件上传成功的地址:从group开始的
			String url = fdfs.uploadFile(file.getBytes(),extName);
			
			// 组合图片上传成功绝对地址
			url = IMAGE_SERVER_URL + url;
			
			// 上传成功
			return new PygResult(true, url);
		} catch (Exception e) {
			e.printStackTrace();
			// 上传失败
			return new PygResult(false, "上传失败");
		}
	}
}
