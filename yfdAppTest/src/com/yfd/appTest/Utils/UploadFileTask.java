package com.yfd.appTest.Utils;

import java.io.File;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.yfd.appTest.Activity.BaseApplication;

/**
 * 
 * AsyncTask���÷� 
	      �ڿ���AndroidӦ��ʱ�������ص��߳�ģ�͵�ԭ�� Android UI�����������̰߳�ȫ�Ĳ�����Щ����������UI�߳���ִ�С��ڵ��߳�ģ����ʼ��Ҫ��ס�������� 
	1. ��Ҫ����UI�߳� 
	2. ȷ��ֻ��UI�߳��з���Android UI���߰� 
	      ��һ�������һ������ʱ��Android��ͬʱ����һ����Ӧ�����߳�(Main Thread)�����߳���Ҫ��������UI��ص��¼����磺�û��İ����¼����û��Ӵ���Ļ���¼��Լ���Ļ��ͼ�¼���������ص��¼��ַ�����Ӧ��������д��?�������߳�ͨ���ֱ�����UI�̡߳� 
	      ����˵�����ϻ�ȡһ����ҳ����һ��TextView�н���Դ������ʾ�����������漰����������ĳ���һ�㶼����Ҫ��һ���߳����������ʣ������ڻ��ҳ��Դ����ǲ���ֱ������������߳��е���TextView.setText()��.��Ϊ�����߳����ǲ���ֱ�ӷ�����UI�̳߳�Ա ��
	
	android�ṩ�˼����������߳��з���UI�̵߳ķ����� 
	Activity.runOnUiThread( Runnable ) 
	View.post( Runnable ) 
	View.postDelayed( Runnable, long ) 
	Hanlder 
	��Щ��򷽷�ͬ���ʹ��Ĵ���ܸ��Ӻ�����⡣Ȼ������Ҫʵ��һЩ�ܸ��ӵĲ�������ҪƵ���ظ���UIʱ����ø���⡣ 
	
	     Ϊ�˽��������⣬Android 1.5�ṩ��һ�������ࣺAsyncTask����ʹ������Ҫ���û����潻���ĳ�ʱ�����е������ø�򵥡������˵AsyncTask��������һЩ�������ڼ򵥵��첽���?����Ҫ�����̺߳�Handler����ʵ�֡� 
	AsyncTask�ǳ�����.AsyncTask���������ַ������� Params��Progress��Result�� 
	����Params ��������ִ�е�����������HTTP�����URL�� 
	����Progress ��̨����ִ�еİٷֱȡ� 
	����Result ��ִ̨���������շ��صĽ�����String�� 
	
	     AsyncTask��ִ�з�Ϊ�ĸ����裬ÿһ������Ӧһ���ص���������Щ������Ӧ����Ӧ�ó�����ã���������Ҫ���ľ���ʵ����Щ������ 
	����1) ���໯AsyncTask 
	����2) ʵ��AsyncTask�ж��������һ���򼸸����� 
	����   onPreExecute(), �÷�������ִ��ʵ�ʵĺ�̨����ǰ��UI thread���á������ڸ÷�������һЩ׼�����������ڽ�������ʾһ��������� 
	����  doInBackground(Params...), ����onPreExecute ����ִ�к�����ִ�У��÷��������ں�̨�߳��С����ｫ��Ҫ����ִ����Щ�ܺ�ʱ�ĺ�̨���㹤�������Ե��� publishProgress����������ʵʱ�������ȡ��÷����ǳ��󷽷����������ʵ�֡� 
	����  onProgressUpdate(Progress...),��publishProgress���������ú�UI thread��������������Ӷ��ڽ�����չʾ����Ľ�չ���������ͨ��һ�����������չʾ�� 
	����  onPostExecute(Result), ��doInBackground ִ����ɺ�onPostExecute ��������UI thread���ã���̨�ļ�����ͨ��÷������ݵ�UI thread. 
	
	Ϊ����ȷ��ʹ��AsyncTask�࣬�����Ǽ����������ص�׼�� 
	����1) Task��ʵ�������UI thread�д��� 
	����2) execute����������UI thread�е��� 
	����3) ��Ҫ�ֶ��ĵ���onPreExecute(), onPostExecute(Result)��doInBackground(Params...), onProgressUpdate(Progress...)�⼸������ 
	����4) ��taskֻ�ܱ�ִ��һ�Σ������ε���ʱ��������쳣 
	      doInBackground������onPostExecute�Ĳ�������Ӧ��������������AsyncTask�����ķ��Ͳ����б���ָ������һ��ΪdoInBackground���ܵĲ���ڶ���Ϊ��ʾ��ȵĲ���ڵ����ΪdoInBackground���غ�onPostExecute����Ĳ��� 


 */
public class UploadFileTask extends AsyncTask<String, Void, String>{
	
	
	public static final String requestURL=BaseApplication.SAOMIAO_URL;
	/**
    *  �ɱ䳤�����������AsyncTask.exucute()��Ӧ
    */
   private Handler handler;
   
   private  Activity context=null;
    public UploadFileTask(Activity ctx,Handler handler){
    	this.context=ctx;
    	this.handler=handler;
   
    }

	@Override
    protected void onPostExecute(String result) {
        // ����HTMLҳ�������
   
        if(UploadUtils.SUCCESS.equalsIgnoreCase(result.split("\\|")[0])){
        	
        	Message msg=new Message();
        	msg.obj=result.split("\\|")[1];        	
        	handler.sendMessage(msg);
        }else{
        	Message msg=new Message();
        	msg.obj=result.split("\\|")[1];        	
        	handler.sendMessage(msg);
        }
    }

	  @Override
	  protected void onPreExecute() {
	  }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

		@Override
		protected String doInBackground(String... params) {
			File file=new File(params[0]);
			return UploadUtils.uploadFile( file, requestURL);
		}
	       @Override
	       protected void onProgressUpdate(Void... values) {
	       }

	
}