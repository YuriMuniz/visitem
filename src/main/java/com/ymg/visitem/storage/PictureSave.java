package com.ymg.visitem.storage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class PictureSave {

	private static String putPictureInStorage(String loggedStoreEmail, byte[] byteArray, String fileType) {
		
		UUID uuid = UUID.randomUUID();
		String myRandom = uuid.toString();
		myRandom.substring(0,7);
		
		String fileName = loggedStoreEmail + myRandom + "." + fileType;
		
		try {
			StorageService.uploadStream(fileName, "image/jpeg", new ByteArrayInputStream(byteArray));
			return StorageService.API_NAME + StorageService.BUCKET_NAME + "/" + fileName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return StorageService.API_NAME + StorageService.BUCKET_NAME + StorageService.IMG_DEFAULT_NAME;
		
	}
	
	public static String sendPicture(MultipartFile picture, String storeEmail) {
		
		String fileType = "";
		
		if (picture.getContentType() == "image/jpeg") {
			
			fileType = "jpg";
		}
		else if(picture.getContentType() == "image/png") {
			
			fileType = "png";
		}
		
		if (!picture.isEmpty()) {
			
			byte[] arrayBytes = null;
			
			try {
				arrayBytes = picture.getBytes();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			String photoPath = PictureSave.putPictureInStorage(storeEmail, arrayBytes, fileType);
			
			return photoPath;
		}
		else {
			
			return StorageService.API_NAME + StorageService.BUCKET_NAME + "/" + StorageService.IMG_DEFAULT_NAME;
		}
	}
}
