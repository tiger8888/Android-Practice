package com.example.micrecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

public class LogData {
	private File svFile;
	
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		Log.d("My::","No SD.");
		return false;
	}

	public File createFile(String filename) {		
		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ChenSong/");
		if (!dir.exists()) {
			dir.mkdirs();
		}		
        if (isExternalStorageWritable()) {
        	svFile = new File(dir, filename);
    		Log.d("My::","Create a file.");
        }
        return svFile;
	}
	
	public void saveData(File file, String data) {
		// TODO Auto-generated method stub
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
			buf.append(data);
			buf.newLine();
			buf.close();
			Log.d("My::","Save data once.");
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
