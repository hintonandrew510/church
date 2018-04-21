package org.cokcc.task;

/**
 * Created by LabUser on 4/21/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.cokcc.R;

import java.net.URL;

public class LoadingTodayImage extends AsyncTask<String, Integer, Integer> {
    int myProgress;
    Context mContext;
    ImageView mImageView;

    public interface LoadingTaskFinishedListener {
        void onTaskFinished(); // If you want to pass something back to the listener add a param to this method
    }

    // This is the progress bar you want to update while the task is in progress

    // This is the listener that will be told when this task is finished
    private final LoadingTodayImage.LoadingTaskFinishedListener finishedListener;

    /**
     * A Loading task that will load some resources that are necessary for the app to start

     * @param finishedListener - the listener that will be told when this task is finished
     */
    public LoadingTodayImage( LoadingTodayImage.LoadingTaskFinishedListener finishedListener, Context context) {
        this.mContext = context;
        Activity activity = (Activity)context;

      this.mImageView = (ImageView) activity.findViewById(R.id.image);;

        //   this.mImageView = (ImageView) context.get
        this.finishedListener = finishedListener;
    }



    @Override
    protected Integer doInBackground(String... params) {
        Log.d("Tutorial", "Starting task with url: "+params[0]);

        try {
            URL thumb_u = new URL("https://gallery.mailchimp.com/38ae22269446262109d471ea9/images/9e6c1a34-79c4-4bb1-bc73-5e92251d5c32.jpg");
            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
            this.mImageView.setImageDrawable(thumb_d);
          //  myImageView.setImageDrawable(thumb_d);
            //return greeting;
        } catch (Exception e) {
            Log.e("LoadingTodayImage", e.getMessage(), e);
        }

        //if(resourcesDontAlreadyExist()){
        //downloadResources();
        //}
        // Perhaps you want to return something to your post execute
        return 1234;
    }







    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        Toast.makeText(this.mContext,
                "onPreExecute", Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        Toast.makeText(this.mContext,"onPostExecute", Toast.LENGTH_LONG).show();
        finishedListener.onTaskFinished(); // Tell whoever was listening we have finished
    }
}