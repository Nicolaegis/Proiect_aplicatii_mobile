package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_add_user.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


class AddUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_REPLY = "addUser"
        private const val IMAGE_PICK_CODE = 100
        private const val PERMISSION_CODE = 1001
    }

    private lateinit var img: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val addImageBtn = findViewById<Button>(R.id.addImageButton)
        addImageBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED
                    ) {
                        val permissions =
                            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    } else {
                        pickImageFromGallery()
                    }
                }
            } else {
                pickImageFromGallery()
            }
        }

        val addBtn = findViewById<FloatingActionButton>(R.id.addNewUser)
        addBtn.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(userAddName.text) ||
                TextUtils.isEmpty(userAddUsername.text) ||
                TextUtils.isEmpty(userAddEmail.text) ||
                TextUtils.isEmpty(userAddPhoneNumber.text) ||
                TextUtils.isEmpty(userAddWebsite.text) ||
                img.isEmpty()
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val str = arrayOfNulls<String>(6)
                str[0] = userAddName.text.toString()
                str[1] = userAddUsername.text.toString()
                str[2] = userAddEmail.text.toString()
                str[3] = userAddPhoneNumber.text.toString()
                str[4] = userAddWebsite.text.toString()
                str[5] = img

                replyIntent.putExtra(EXTRA_REPLY, str)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            if (data != null) {
                img = saveImage(MediaStore.Images.Media.getBitmap(this.contentResolver, data.data))
            }

            val file = File(img)
            val bmp: Bitmap = BitmapFactory.decodeStream(FileInputStream(file))
            imagePickImageView.setImageBitmap(bmp)
        }
    }

    private fun saveImage(imageBitmap: Bitmap): String {
        val contextWrapper = ContextWrapper(applicationContext)
        val dir: File = contextWrapper.getDir("imageDir", Context.MODE_PRIVATE)
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val imageName = "Image-$n.jpg"
        val file = File(dir, imageName)
        return try {
            val out = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.flush()
            out.close()
            file.path
        } catch (e: Exception) {
            Toast.makeText(this, "Save image failed", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            String()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.logoutButton) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, 0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        intent = Intent(this, MainActivity::class.java)
        startActivityForResult(intent, 0)
    }
}
