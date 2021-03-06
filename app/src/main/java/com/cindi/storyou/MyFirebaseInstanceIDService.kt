package com.cindi.storyou
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Message
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cindi.storyou.create.CreateFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random
const val channelId = "notification_channel"
const val channelName = "com.cindi.storyou"
class MyFirebaseInstanceIDService : FirebaseMessagingService (){
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val intent = Intent(this, CreateFragment::class.java)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = Random.nextInt()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
           createNotificationChannel(notificationManager)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(p0.data["title"])
            .setContentText(p0.data["nama"])
            .setContentText(p0.data["message"])
            .setSmallIcon(R.drawable.logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
        notificationManager.notify(notificationId, notification)
        //showNotification(p0.notification?.title.toString(), p0.notification?.body.toString())
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channelName = "channelName"
        val channel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH).apply {
            description = "My channel description"
            enableLights(true)
            lightColor = Color.GRAY
        }
        notificationManager.createNotificationChannel(channel)
    }

    //    override fun onNewToken(p0: String) {
//        super.onNewToken(p0)
//        val refreshToken = FirebaseMessaging.getInstance().token
//        Log.e ("refresh", refreshToken.toString())
//        Log.i ("refresh", refreshToken.toString())
//
//        println("===========================================")
//        println(refreshToken.toString())
//        println("===========================================")
//
//    }

//    fun showNotification (tittle:String, message: String){
//        val builder = NotificationCompat.Builder(this, "test notif")
//            .setContentTitle(tittle)
//            .setContentText(message)
//
//        val manager = NotificationManagerCompat.from(this)
//        manager.notify(222, builder.build())
//
//    }

//        @RequiresApi(Build.VERSION_CODES.O)
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        if (remoteMessage.getNotification() != null)
//            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
//    }
//
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun generateNotification (tittle : String, message : String){
//        val intent = Intent (this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
//
//        //channel id channel name
//        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(R.drawable.logo)
//            .setAutoCancel(true)
//            .setVibrate(longArrayOf(2000, 2000, 2000, 2000))
//            .setOnlyAlertOnce(true)
//            .setContentIntent(pendingIntent)
//
//        builder= builder.setContent(getRemoteView(tittle, message))
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//        notificationManager.notify(0, builder.build())
//
//
//    }
//
//    @SuppressLint("RemoteViewLayout")
//    private fun getRemoteView(tittle: String, message: String): RemoteViews {
//        val remoteViews = RemoteViews("com.cindi.storyou", R.layout.notification)
//        remoteViews.setTextViewText(R.id.tittle, tittle)
//        remoteViews.setTextViewText(R.id.message, message)
//        remoteViews.setImageViewResource(R.id.applogo, R.drawable.logo)
//
//        return  remoteViews
//
//    }

}