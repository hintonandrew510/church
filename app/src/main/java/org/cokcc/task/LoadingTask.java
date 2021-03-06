package org.cokcc.task;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.cokcc.R;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.cokcc.model.RootObject;

public class LoadingTask extends AsyncTask<String, Integer, Integer> {
	int myProgress;
	Context mContext;

	public interface LoadingTaskFinishedListener {
		void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
	}

	// This is the progress bar you want to update while the task is in progress
	private final ProgressBar progressBar;
	private final TextView mTextView;
	// This is the listener that will be told when this task is finished
	private final LoadingTaskFinishedListener finishedListener;

	/**
	 * A Loading task that will load some resources that are necessary for the app to start
	 * @param progressBar - the progress bar you want to update while the task is in progress
	 * @param finishedListener - the listener that will be told when this task is finished
	 */
	public LoadingTask(ProgressBar progressBar, LoadingTaskFinishedListener finishedListener,Context context) {
		this.mContext = context;
		Activity activity = (Activity)context;
		mTextView =(TextView) activity.findViewById(R.id.textView);;


		this.progressBar = progressBar;
		this.finishedListener = finishedListener;
	}



	@Override
	protected Integer doInBackground(String... params) {
		Log.d("Tutorial", "Starting task with url: "+params[0]);
		RootObject rootObject;
		while(myProgress<100){
			myProgress++;
			this.progressBar.setProgress(myProgress);
//			mTextView.setText(myProgress+"/"+progressBar.getMax());
		//	publishProgress(myProgress);
			SystemClock.sleep(10);
		}
		try {
			final String url = "http://cokcc.org/mobile/event.json";
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			rootObject= restTemplate.getForObject(url, RootObject.class);
			Log.i("JSON", "got it: ");
			if (rootObject != null) {
				Log.i("JSON", "check in : " + rootObject.getEvents().getToday().get(0).getTitle());
			}
			int a = 7;
			//return greeting;
		} catch (Exception e) {
			Log.e("LoadingTask", e.getMessage(), e);
		}

		//if(resourcesDontAlreadyExist()){
			//downloadResources();
		//}
		// Perhaps you want to return something to your post execute
		return 1234;
	}

	private boolean resourcesDontAlreadyExist() {
		// Here you would query your app's internal state to see if this download had been performed before
		// Perhaps once checked save this in a shared preference for speed of access next time
		return true; // returning true so we show the splash every time
	}


	private void downloadResources() {
		// We are just imitating some process thats takes a bit of time (loading of resources / downloading)
		int count = 10;
		for (int i = 0; i < count; i++) {

			// Update the progress bar after every step
			int progress = (int) ((i / (float) count) * 100);
			publishProgress(progress);

			// Do some long loading things
			try { Thread.sleep(1000); } catch (InterruptedException ignore) {}
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		this.progressBar.setProgress(values[0]); // This is ran on the UI thread so it is ok to update our progress bar ( a UI view ) here
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		Toast.makeText(this.mContext,
				"onPreExecute", Toast.LENGTH_LONG).show();
		myProgress = 0;
	}


	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		Toast.makeText(this.mContext,"onPostExecute", Toast.LENGTH_LONG).show();
		finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
	}
}