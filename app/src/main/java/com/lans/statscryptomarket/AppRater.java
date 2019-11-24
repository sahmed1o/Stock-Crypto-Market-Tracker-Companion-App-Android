package com.lans.statscryptomarket;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class AppRater {

	private final static int DAYS_UNTIL_PROMPT = 0;
	private final static int LAUNCHES_UNTIL_PROMPT = 2;

	public static void showRateDialog(final Context context) {
		showRateDialog(context, null);
	}

	public static void app_launched(Context mContext) {
		SharedPreferences prefs = mContext.getSharedPreferences("appraterxoyuscpm", 0);
		if (prefs.getBoolean("dontshowagainxoyuscpm", false)) {
			return;
		}

		SharedPreferences.Editor editor = prefs.edit();

		// Increment launch counter
		long launch_count = prefs.getLong("launch_countxoyuscpm", 0) + 1;
		editor.putLong("launch_countxoyuscpm", launch_count);

		// Get date of first launch
		Long date_firstLaunch = prefs.getLong("date_firstlaunchxoyuscpm", 0);
		if (date_firstLaunch == 0) {
			date_firstLaunch = System.currentTimeMillis();
			editor.putLong("date_firstlaunchxoyuscpm", date_firstLaunch);
		}

		// Wait at least n days before opening
		if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
			if (System.currentTimeMillis() >= date_firstLaunch
					+ (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
				showRateDialog(mContext, editor);
                editor.putLong("launch_countxoyuscpm", 0);
			}
		}

		editor.commit();
	}

	public static void showRateDialog(final Context context,
			final SharedPreferences.Editor editor) {

		final Dialog dialog = new Dialog(context);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setContentView(R.layout.rate_dialog);
		
		TextView tvheading = (TextView) dialog.findViewById(R.id.tvheading);
		TextView tvcontent = (TextView) dialog.findViewById(R.id.tvcontent);
		Button brate = (Button) dialog.findViewById(R.id.brate);
		Button blater = (Button) dialog.findViewById(R.id.blater);
		Button bnothanks = (Button) dialog.findViewById(R.id.bnothanks);
		
		tvheading.setText(context.getResources().getString(R.string.rate_dialog_title));
		tvcontent.setText(context.getResources().getString(R.string.rate_message));

		brate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id="
								+ context.getPackageName().toString())));
				if (editor != null) {
					editor.putBoolean("dontshowagainxoyuscpm", true);
					editor.commit();
				}
				dialog.dismiss();				
			}
		});
		blater.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (editor != null) {
					Long date_firstLaunch = System.currentTimeMillis();
					editor.putLong("date_firstlaunchxoyuscpm", date_firstLaunch);
					editor.commit();
				}
				dialog.dismiss();
				
			}
		});
		bnothanks.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (editor != null) {
					editor.putBoolean("dontshowagainxoyuscpm", true);
					editor.commit();
				}
				dialog.dismiss();
				
			}
		});
		
		
		dialog.show();

	}

	
}
