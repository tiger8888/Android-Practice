package com.example.micrecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class SoundRecorder {
	static final int freq = 44100;
	static final int channelConfiguration = AudioFormat.CHANNEL_IN_MONO;
	static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	int recBufsize, playBufSize;
	private AudioRecord audioRecord;
	private MediaRecorder mRecorder;
    private boolean isRecording = false;
	private BufferedWriter buf;
    //private FileOutputStream buf = null;
	SimpleDateFormat sDateFormat, timeFormat;
	String time, filename;
	File svFile;
	private double soundValue = 0;
	
	public SoundRecorder() {
		recBufsize = AudioRecord.getMinBufferSize(freq, channelConfiguration, audioEncoding);
		playBufSize=AudioTrack.getMinBufferSize(freq, channelConfiguration, audioEncoding);  
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, freq, channelConfiguration, audioEncoding, recBufsize);
		sDateFormat = new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss");
	}
	
	public void startRecording() {
		// Mediarecorder
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setAudioSamplingRate(freq);
		mRecorder.setAudioEncodingBitRate(12800);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mRecorder.setOutputFile(newFileName());
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e("Mic Recording", "prepare() failed");
		}
		mRecorder.start();
	}
	
	public void stopRecording() {
		mRecorder.stop();
		mRecorder.reset();
		mRecorder.release();
		mRecorder = null;
	}
	
	private void writeDataToFile() {
		byte sData[] = new byte[recBufsize];
		int r = audioRecord.read(sData, 0, recBufsize);
		int v = 0;
		for (int i = 0; i < sData.length; i++) {
			v+=sData[i]*sData[i];
		}
		//double dB = 10*Math.log10(v/(double)r);
		double dB = v;
		sDateFormat.applyPattern("mm,ss,SSS");
		time = sDateFormat.format(new java.util.Date());
		long nanoSec = System.nanoTime();
		String svData = time + "," + dB + "," + recBufsize + "," + sData.length + "," + v;// + "," + nanoSec;
		saveData(svFile, svData);
/*		try {
			buf.write(sData, 0, recBufsize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Bundle b = new Bundle(1);
		b.putDouble("V1", dB);
		Message msg = new Message();
		msg.setData(b);
		handler.sendMessage(msg);
	}

	private void updateVolume() {
		byte sData[] = new byte[recBufsize];
		int r = audioRecord.read(sData, 0, recBufsize);
		int v = 0;
		for (int i = 0; i < sData.length; i++) {
			v+=sData[i]*sData[i];
		}

		double dB = v;
		Bundle b = new Bundle(1);
		b.putDouble("V1", dB);
		Message msg = new Message();
		msg.setData(b);
		handler.sendMessage(msg);
		Log.d("Mic Recording", "Volume->" + dB + " sData->" + sData.length);	
	}	
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Bundle b = msg.getData();
			soundValue = b.getDouble("V1"); 
			super.handleMessage(msg);
		}
	};

	
	public double getSoundValue() {
		return this.soundValue;
	}
	
	public boolean getStatus() {
		return this.isRecording;
	}
	
	private void createFile(String filename) {

		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ChenSong");
		if (!dir.exists()) {
			dir.mkdirs();
		}		
		if (isExternalStorageWritable()) {
			svFile = new File(dir, filename);
		}
	}
	private boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			//Toast.makeText(this, "SD Ready", Toast.LENGTH_LONG).show();
			return true;
		}
		return false;
	}
	
	private void saveData(File file, String data) {
		// TODO Auto-generated method stub
		try {
			buf = new BufferedWriter(new FileWriter(file, true));
			buf.append(data);
			buf.newLine();
			buf.close();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public String newFileName() {
		String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();

		String s = new SimpleDateFormat("hhmmss").format(new Date());
		return mFileName += "/ChenSong/rcd_" + s + ".mp4";
	}
}

