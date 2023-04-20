package com.jawon.han.example

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.KeyEvent
import android.widget.RadioButton
import com.jawon.han.HanActivity
import com.jawon.han.key.HanBrailleKey
import com.jawon.han.output.HanBeep
import com.jawon.han.widget.*
import kotlin.system.measureTimeMillis

class MainActivity() : HanActivity() {
    private lateinit var listView: HanListView //리스트 뷰
    private lateinit var fileManager: FileManager //파일 관리 클래스
    private lateinit var editTextCurrentDir: HanEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.ListVIewFileList) as HanListView
        listView.emptyView = findViewById(R.id.ListViewNoItem) as HanListEmptyView
        fileManager = FileManager(Environment.getExternalStorageDirectory().absolutePath)
        listView.adapter = ListViewAdapter(fileManager.fileNameList)
        editTextCurrentDir = findViewById(R.id.editTextCurrentDir) as HanEditText
        editTextCurrentDir.setText(fileManager.fileDir)
        listView.setNavigationMode(HanListView.LIST_NAVIGATION_TYPE_NO_ROTATION)
        setOnLisntener()
    }

    //ListView Adapter에게 data 변경사실 통보
    private fun upDateListView() {
        editTextCurrentDir.setText(fileManager.fileDir)
        val listViewAdapter = listView.adapter as ListViewAdapter
        listViewAdapter.notifyDataSetChanged()
    }

    // backSpace누를시 폴더 나오기
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val keyCode = event.scanCode
        if (keyCode == HanBrailleKey.HK_BACKSPACE && event.action == KeyEvent.ACTION_UP) {
            if (currentFocus.id == R.id.ListVIewFileList || currentFocus.id == R.id.ListViewNoItem) {

                if (fileManager.exitFolder()) {
                    upDateListView()
                }
            }
        } else if (keyCode == HanBrailleKey.HK_ENTER && event.action == KeyEvent.ACTION_UP) {
            if (currentFocus.id == R.id.editTextFileName) {
                HanBeep.alertBeep(this, HanBeep.TYPE_KOR_MODE)
            }
        }
        return super.dispatchKeyEvent(event)
    }


    // listener 함수들 정의
    private fun setOnLisntener() {
        val buttonFind = findViewById(R.id.buttonFind) as HanButton
        val buttonExit = findViewById(R.id.buttonExit) as HanButton
        val editTextFileName = findViewById(R.id.editTextFileName) as HanEditText
        val radioGroupFileType = findViewById(R.id.radioGroupFileType) as HanRadioGroup
        val radioButtonFileType1 = findViewById(R.id.radioButtonFileType1) as RadioButton
        val radioButtonFileType2 = findViewById(R.id.radioButtonFileType2) as RadioButton
        val radioButtonFileType3 = findViewById(R.id.radioButtonFileType3) as RadioButton
        val radioButtonFileType4 = findViewById(R.id.radioButtonFileType4) as RadioButton
        val radioButtonFileType5 = findViewById(R.id.radioButtonFileType5) as RadioButton

        buttonFind.setOnClickListener {
            val elapsed:Long= measureTimeMillis {
                fileManager.findFile(editTextFileName.text.toString())
                upDateListView()
            }
            Log.d("메인 파일 찾기",elapsed.toString())
        }


        buttonExit.setOnClickListener {
            // 종료 버튼 누를시
            finish()
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val elapsed:Long= measureTimeMillis {
                if (fileManager.enterFolder(position)) //폴더에 들어가는데 성공했으면
                {
                    upDateListView()
                }
            }
            Log.d("메인 폴더 들어가기",elapsed.toString())
        }

// 라디오 버튼으로 리스트뷰 파일 목록 필터링
        radioGroupFileType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                radioButtonFileType1.id -> {
                    fileManager.selectFileType()
                    upDateListView()
                }
                radioButtonFileType2.id -> {
                    fileManager.selectFileType(radioButtonFileType2.text.toString())
                    upDateListView()
                }
                radioButtonFileType3.id -> {
                    fileManager.selectFileType(radioButtonFileType3.text.toString())
                    upDateListView()
                }
                radioButtonFileType4.id -> {
                    fileManager.selectFileType(radioButtonFileType4.text.toString())
                    upDateListView()
                }
                radioButtonFileType5.id -> {
                    fileManager.selectFileType(radioButtonFileType5.text.toString())
                    upDateListView()
                }
            }
        }
    }
}