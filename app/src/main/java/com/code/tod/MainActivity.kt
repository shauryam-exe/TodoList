    package com.code.tod

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), Adapter.TodoListClickListener {

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 10
    }

    lateinit var recyclerView: RecyclerView
    lateinit var fab: FloatingActionButton
    private val listDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)

        val list = listDataManager.readList()

        recyclerView = findViewById(R.id.recycler_View)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = Adapter(list, this)

        fab.setOnClickListener() { _ ->
            showCreateTodoListDialog()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                var list = data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)!!
                listDataManager.saveList(list)
                updateList()
            }
        }
    }

    private fun updateList() {
        val list = listDataManager.readList()
        recyclerView.adapter = Adapter(list, this)
    }

    private fun showCreateTodoListDialog() {
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS

        val dialogTitle = getString(R.string.dialogTitle)
        val positiveButtonTitle = getString(R.string.positiveButtonTitle)

        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)

        myDialog.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val adapter = recyclerView.adapter as Adapter
            val list = TaskList(todoTitleEditText.text.toString())
            listDataManager.saveList(list)
            adapter.addList(list)
            dialog.dismiss()
            showTaskListItems(list)
        }

        myDialog.create().show()
    }

    private fun showTaskListItems(list: TaskList) {
        val taskListItem = Intent(this, DetailActivity::class.java)
        taskListItem.putExtra(INTENT_LIST_KEY, list)
        startActivityForResult(taskListItem, LIST_DETAIL_REQUEST_CODE)
    }

    override fun listItemClicked(list: TaskList) {
        showTaskListItems(list)
    }

}