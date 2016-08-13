package com.yfd.appTest.Utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yfd.appTest.Activity.BaseApplication;
import com.yfd.appTest.Beans.FormFile;

public class UploadMore {
	private static String bmsg;

	/**
	 * 锟斤拷锟侥硷拷锟较达拷 直锟斤拷通锟斤拷HTTP协锟斤拷锟结交锟斤拷莸锟斤拷锟斤拷锟斤拷锟�实锟斤拷锟斤拷锟斤拷锟斤拷?锟结交锟斤拷锟斤拷:
	 * <FORM METHOD=POST
	 * ACTION="http://192.168.1.101:8083/upload/servlet/UploadServlet"
	 * enctype="multipart/form-data"> <INPUT TYPE="text" NAME="name"> <INPUT
	 * TYPE="text" NAME="id"> <input type="file" name="imagefile"/> <input
	 * type="file" name="zip"/> </FORM>
	 * 
	 * @param path
	 *            锟较达拷路锟斤拷(注锟斤拷锟斤拷锟斤拷使锟斤拷localhost锟斤拷127.0.0.1
	 *            锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷裕锟斤拷锟轿
	 *            拷锟斤拷指锟斤拷锟街伙拷模锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟绞癸拷锟絟ttp://
	 *            www.iteye.cn锟斤拷http://192.168.1.101:8083锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷锟�
	 * @param params
	 *            锟斤拷锟斤拷锟斤拷锟�key为锟斤拷锟斤拷锟斤拷,value为锟斤拷锟斤拷值
	 * @param file
	 *            锟较达拷锟侥硷拷
	 */

	public static boolean post(String path, Map<String, String> params,
			FormFile[] files, Handler handler) {
		final String BOUNDARY = UUID.randomUUID().toString(); // 锟斤拷莘指锟斤拷锟�
		final String endline = "--" + BOUNDARY + "--\r\n";// 锟斤拷萁锟斤拷锟斤拷志

		int fileDataLength = 0;
		String strSessionId = String.valueOf(new String(new SimpleDateFormat(
				"yyyyMMddHHmmss").format(new Date())));

		for (int i = 0; i < files.length; i++) {
			FormFile uploadFile = null;
			if (files[i] != null) {
				uploadFile = files[i];
			} else {
				continue;
			}

			StringBuilder fileExplain = new StringBuilder();
			fileExplain.append("--");
			fileExplain.append(BOUNDARY);
			fileExplain.append("\r\n");
			fileExplain.append("Content-Disposition: form-data;name=\""
					+ uploadFile.getParameterName() + "\";filename=\""
					+ uploadFile.getFilname() + "\"\r\n");
			fileExplain.append("Content-Type: " + uploadFile.getContentType()
					+ "\r\n\r\n");
			if(BaseApplication.isMainPic){
				 fileExplain.append("Cookie: " + "JSESSIONID="
						 + BaseApplication.loginbeans.getMsg().getSessionId()
						 + "\r\n\r\n");
			}
			
		
			fileExplain.append("\r\n");
			fileDataLength += fileExplain.length();
			if (uploadFile.getInStream() != null) {
				fileDataLength += uploadFile.getFile().length();
			} else {
				fileDataLength += uploadFile.getData().length;
			}
		}
		// for(FormFile uploadFile : files){//锟矫碉拷锟侥硷拷锟斤拷锟斤拷锟斤拷莸锟斤拷艹锟斤拷锟�
		// StringBuilder fileExplain = new StringBuilder();
		// fileExplain.append("--");
		// fileExplain.append(BOUNDARY);
		// fileExplain.append("\r\n");
		// fileExplain.append("Content-Disposition: form-data;name=\""+
		// uploadFile.getParameterName()+"\";filename=\""+
		// uploadFile.getFilname() + "\"\r\n");
		// fileExplain.append("Content-Type: "+
		// uploadFile.getContentType()+"\r\n\r\n");
		// fileExplain.append("Cookie: "+ "JSESSIONID="+
		// BaseApplication.loginbeans.getMsg().getSessionId()+"\r\n\r\n");
		// fileExplain.append("\r\n");
		// fileDataLength += fileExplain.length();
		// if(uploadFile.getInStream()!=null){
		// fileDataLength += uploadFile.getFile().length();
		// }else{
		// fileDataLength += uploadFile.getData().length;
		// }
		// }
		StringBuilder textEntity = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {// 锟斤拷锟斤拷锟侥憋拷锟斤拷锟酵诧拷锟斤拷锟绞碉拷锟斤拷锟斤拷
			textEntity.append("--");
			textEntity.append(BOUNDARY);
			textEntity.append("\r\n");
			textEntity.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"\r\n\r\n");
			textEntity.append(entry.getValue());
			textEntity.append("\r\n");
		}
		// 锟斤拷锟姐传锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷艹锟斤拷锟�
		int dataLength = textEntity.toString().getBytes().length
				+ fileDataLength + endline.getBytes().length;
		try {

			URL url = new URL(path);
			System.out.println("port:" + url.getPort());
			int port = url.getPort() == -1 ? 80 : url.getPort();
			Socket socket = new Socket(InetAddress.getByName(url.getHost()),
					port);
			socket.setSoTimeout(10 * 1000);
			OutputStream outStream = socket.getOutputStream();
			// 锟斤拷锟斤拷锟斤拷锟紿TTP锟斤拷锟斤拷头锟侥凤拷锟斤拷
			String requestmethod = "POST " + url.getPath() + " HTTP/1.1\r\n";
			outStream.write(requestmethod.getBytes());
			String accept = "Accept: image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*\r\n";
			outStream.write(accept.getBytes());
			String language = "Accept-Language: zh-CN\r\n";
			outStream.write(language.getBytes());
			String contenttype = "Content-Type: multipart/form-data; boundary="
					+ BOUNDARY + "\r\n";
			outStream.write(contenttype.getBytes());
			String contentlength = "Content-Length: " + dataLength + "\r\n";
			outStream.write(contentlength.getBytes());
			String alive = "Connection: Keep-Alive\r\n";
			outStream.write(alive.getBytes());
			if (BaseApplication.isMainPic) {
				String sessionid = "Cookie:JSESSIONID="
						+ BaseApplication.loginbeans.getMsg().getSessionId()
						+ "\r\n";

				outStream.write(sessionid.getBytes());
			}

			String host = "Host: " + url.getHost() + ":" + port + "\r\n";
			outStream.write(host.getBytes());
			// 写锟斤拷HTTP锟斤拷锟斤拷头锟斤拷锟斤拷HTTP协锟斤拷锟斤拷写一锟斤拷锟截筹拷锟斤拷锟斤拷
			outStream.write("\r\n".getBytes());
			// 锟斤拷锟斤拷锟斤拷锟侥憋拷锟斤拷锟酵碉拷实锟斤拷锟斤拷莘锟斤拷统锟斤拷锟�
			outStream.write(textEntity.toString().getBytes());
			// 锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷锟酵碉拷实锟斤拷锟斤拷莘锟斤拷统锟斤拷锟�
			for (int j = 0; j < files.length; j++) {
				FormFile uploadFile = null;
				if (files[j] != null) {
					uploadFile = files[j];
				} else {
					continue;
				}
				StringBuilder fileEntity = new StringBuilder();
				fileEntity.append("--");
				fileEntity.append(BOUNDARY);
				fileEntity.append("\r\n");
				fileEntity.append("Content-Disposition: form-data;name=\""
						+ uploadFile.getParameterName() + "\";filename=\""
						+ uploadFile.getFilname() + "\"\r\n");
				fileEntity.append("Content-Type: "
						+ uploadFile.getContentType() + "\r\n\r\n");
				outStream.write(fileEntity.toString().getBytes());
				if (uploadFile.getInStream() != null) {
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = uploadFile.getInStream()
							.read(buffer, 0, 1024)) != -1) {
						outStream.write(buffer, 0, len);
					}
					uploadFile.getInStream().close();
				} else {
					outStream.write(uploadFile.getData(), 0,
							uploadFile.getData().length);
				}
				outStream.write("\r\n".getBytes());
			}
			// for(FormFile uploadFile : files){
			// StringBuilder fileEntity = new StringBuilder();
			// fileEntity.append("--");
			// fileEntity.append(BOUNDARY);
			// fileEntity.append("\r\n");
			// fileEntity.append("Content-Disposition: form-data;name=\""+
			// uploadFile.getParameterName()+"\";filename=\""+
			// uploadFile.getFilname() + "\"\r\n");
			// fileEntity.append("Content-Type: "+
			// uploadFile.getContentType()+"\r\n\r\n");
			// outStream.write(fileEntity.toString().getBytes());
			// if(uploadFile.getInStream()!=null){
			// byte[] buffer = new byte[1024];
			// int len = 0;
			// while((len = uploadFile.getInStream().read(buffer, 0,
			// 1024))!=-1){
			// outStream.write(buffer, 0, len);
			// }
			// uploadFile.getInStream().close();
			// }else{
			// outStream.write(uploadFile.getData(), 0,
			// uploadFile.getData().length);
			// }
			// outStream.write("\r\n".getBytes());
			// }
			// 锟斤拷锟芥发锟斤拷锟斤拷萁锟斤拷锟斤拷志锟斤拷锟斤拷示锟斤拷锟斤拷丫锟斤拷锟斤拷锟�
			outStream.write(endline.getBytes());

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			ByteArrayOutputStream outStreammg = new ByteArrayOutputStream();

			byte[] data = new byte[1024];
			int lenb = 0;
			InputStream inStream = socket.getInputStream();
			String backmsg = "";

			while ((lenb = inStream.read(data, 0, 1024)) != -1) {

				outStreammg.write(data, 0, lenb);

				backmsg = new String(outStreammg.toByteArray());

				if (backmsg.contains("state") && backmsg.contains("msg")) {
					Log.i("-------",
							backmsg.substring(backmsg.indexOf("{"),
									backmsg.lastIndexOf("}") + 1));

					backmsg = backmsg.substring(backmsg.indexOf("{"),
							backmsg.lastIndexOf("}") + 1);

					break;
				}
			}
			Message msgss = new Message();
			msgss.obj = backmsg;
			handler.sendMessage(msgss);

			outStream.flush();
			outStream.close();

			socket.close();

		} catch (Exception e) {
			// TODO: handle exception
			Log.i("-------", e.toString());
			Message msgss = new Message();
			msgss.obj = null;
			handler.sendMessage(msgss);
			e.printStackTrace();

		}
		return true;
	}

	/**
	 * 锟斤拷锟侥硷拷锟较达拷 锟结交锟斤拷莸锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param path
	 *            锟较达拷路锟斤拷
	 * @param params
	 *            锟斤拷锟斤拷锟斤拷锟�key为锟斤拷锟斤拷锟斤拷,value为锟斤拷锟斤拷值
	 * @param file
	 *            锟较达拷锟侥硷拷
	 */
	// public static boolean post(String path, Map<String, String> params,
	// FormFile file,Handler handler) throws Exception{
	// return post(path, params, new FormFile[]{file},handler);
	// }
}