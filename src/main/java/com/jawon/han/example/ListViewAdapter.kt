package com.jawon.han.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.jawon.han.widget.HanTextView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ListViewAdapter(val List: MutableList<File>) : BaseAdapter() {
    override fun getCount(): Int {
        return List.size
    }

    override fun getItem(position: Int): Any {
        return List[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent?.context).inflate(R.layout.list_view_item, parent, false)
        }
        val content = convertView!!.findViewById(R.id.textViewItem) as HanTextView
        var folderImg = convertView!!.findViewById(R.id.folderImage) as ImageView
        val formatter: SimpleDateFormat = SimpleDateFormat("YY.MM.dd/HH:mm", Locale.KOREA)
        if (List[position].isFile) {
            content.text =
                    List[position].name + "(${formatter.format(Date(List[position].lastModified()))})"
            convertView.contentDescription = List[position].name + "(${formatter.format(Date(List[position].lastModified()))})"
            folderImg.setImageResource(R.drawable.file)
        } else if (!List[position].isFile) {
            content.text =
                    List[position].name + "(${formatter.format(Date(List[position].lastModified()))})"
            convertView.contentDescription = List[position].name + "(${formatter.format(Date(List[position].lastModified()))})"
            folderImg.setImageResource(R.drawable.folder)
        }
        return convertView
    }
}

