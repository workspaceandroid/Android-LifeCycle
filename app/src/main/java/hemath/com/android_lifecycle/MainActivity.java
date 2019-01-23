package hemath.com.android_lifecycle;

import android.app.*;
import android.os.*;
import android.support.annotation.*;
import android.support.v7.app.AppCompatActivity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String TRACER = "tracer";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createChannel();
        notify("onCreate");

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        notify("onPause");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        notify("onResume");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        notify("onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        notify("onDestroy");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        notify("onRestoreInstanceState");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        notify("onSaveInstanceState");
    }

    private void notify(String methodName)
    {
        String name = this.getClass().getName();
        String[] strings = name.split("\\.");
        Notification.Builder notificationBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(this, TRACER);
        } else {
            //noinspection deprecation
            notificationBuilder = new Notification.Builder(this);
        }

        Notification notification = notificationBuilder
                .setContentTitle(methodName + " " + strings[strings.length - 1])
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(name).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel()
    {
        NotificationManager mNotificationManager = getSystemService(NotificationManager.class);
        // The id of the channel.
        String id = TRACER;
        // The user-visible name of the channel.
        CharSequence name = "Activity livecycle tracer";
        // The user-visible description of the channel.
        String description = "Allows to trace the activity lifecycle";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        mChannel.setDescription(description);

        mNotificationManager.createNotificationChannel(mChannel);
    }
}
