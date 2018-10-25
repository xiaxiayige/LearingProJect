package demo.xiaxiayige.com.ffmpegdemo

import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var ffmpeg: FFmpeg
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        aaa.setOnClickListener { start() }
        initFfmpeg()
    }

    private fun initFfmpeg() {
        ffmpeg = FFmpeg.getInstance(this)
        try {
            ffmpeg.loadBinary(object : LoadBinaryResponseHandler() {

                override fun onFinish() {
                    super.onFinish()
                }

                override fun onSuccess() {
                    super.onSuccess()
                }

                override fun onFailure() {
                    super.onFailure()
                }

                override fun onStart() {
                    super.onStart()
                }
            })
        } catch (e: FFmpegNotSupportedException) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults != null && grantResults.isNotEmpty()) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call()
                } else {
                    Toast.makeText(this, "1111111111111111", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun start() {
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                100
            )
        } else {
            call()
        }
    }

    private fun call() {
        //保存音频流到本地
        val cmd = arrayOf(
            "-i",
            "http://media.adxs.civaonline.cn/upload/audio/201711/14/1711141551294450843.mp3",
            "-f",
            "mp3",
            "-y",
            "${Environment.getExternalStorageDirectory().path}/c.mp3"
        )
        //合并2个音频流
        val cmd2 = arrayOf(
            "-i",
            "http://media.adxs.civaonline.cn/upload/audio/201711/14/1711141551294450843.mp3",
            "-i",
            "http://media.adxs.civaonline.cn/upload/audio/201711/23/1711231413443356943.mp3",
            "-filter_complex",
            "amix=inputs=2:duration=longest",
            "-f",
            "mp3",
            "-y",
            "${Environment.getExternalStorageDirectory().path}/d.mp3"
        )

        ffmpeg.execute(cmd2, object : ExecuteBinaryResponseHandler() {
            override fun onFinish() {
                super.onFinish()
                Log.i("AAA", " onFinish")
            }

            override fun onSuccess(message: String?) {
                super.onSuccess(message)
                Log.i("AAA", " onSuccess ${message}")
            }

            override fun onFailure(message: String?) {
                super.onFailure(message)
                Log.i("AAA", " onFailure ${message}")
            }

            override fun onProgress(message: String?) {
                super.onProgress(message)
                Log.i("AAA", " onProgress ${message}")
            }

            override fun onStart() {
                super.onStart()
                Log.i("AAA", " onStart ")
            }
        })
    }
}
