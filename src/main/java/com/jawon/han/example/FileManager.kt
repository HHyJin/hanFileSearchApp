package com.jawon.han.example

import java.io.File

class FileManager(fileDir: String) {
    var fileDir: String = fileDir // 현재 디렉토리
    private set
    var fileNameList = mutableListOf<File>() //리스트뷰에 보여질 파일 객체 리스트
    private set
    private val rootDir: String = fileDir // root 디렉토리
    private lateinit var fileList: Array<File> //디렉토리 내 실제 파일 객체 리스트
    private var currentType = "" //현재 라디오 버튼에 체크된 타입
    private var currentText = ""//현재 editText에 입력되어 찾기 버튼으로 들어온 값

    init {
        fileList = File(rootDir).listFiles()
        fileNameListUpdate(currentType, currentText)
    }

    // 전 디렉토리로 이동하기
    fun exitFolder(): Boolean {
        val fileDirArr = fileDir.split("/")
        var newDir: String = ""
        for (i in 0..fileDirArr.size - 2) {//전 디렉토리 newDir:String 생성
            newDir += fileDirArr[i]
            if (i != fileDirArr.size - 2)//최종 폴더 경로가 아니라면 / 붙이기
                newDir += "/"
        }
        if (newDir.length >= rootDir.length) { //초기 디렉토리가 아니라면
            fileDir = newDir
            fileList = File(fileDir).listFiles()
            fileNameListUpdate(currentType, currentText)
            return true
        }
        return false
    }


    // 폴더 들어가기, 파일 선택했으면 SKIP //return(폴더에 들어가는데 성공했는지)
    fun enterFolder(position: Int): Boolean {
        if (!fileNameList[position].isFile()) {
            fileDir = fileDir + '/' + fileNameList[position].name
            fileList = File(fileDir).listFiles()
            fileNameListUpdate(currentType, currentText)
//            Log.d("FileM","다음 디렉토리 이동")
            return true
        }
        return false
    }

    // 선택한 type인 item만 남도록 ListView의 list 업데이트
    fun selectFileType(type: String="") {
        fileNameListUpdate(type, currentText)
    }

    // 작성한 text인 item만 남도록 ListView의 list 업데이트
    fun findFile(fileName: String) {
        fileNameListUpdate(currentType, fileName)
    }

    // List View에 보여질 파일 리스트 업데이트
    private fun fileNameListUpdate(currentType: String, currentText: String) {
        this.currentType = currentType
        this.currentText = currentText
        fileNameList.clear()
        for (i in fileList.indices) {
            if (fileList[i].name.startsWith('.'))
                continue
                if (fileList[i].name.toUpperCase().endsWith(currentType.toUpperCase())||!fileList[i].isFile) {
                    if (fileList[i].name.toUpperCase().contains(currentText.toUpperCase()))
                        fileNameList.add(fileList[i])
                }
        }

        fileNameList.sortWith(compareBy<File> { it.isFile }.thenBy { it.name })
        fileList.sortWith(compareBy<File> { it.isFile }.thenBy { it.name })
    }

}