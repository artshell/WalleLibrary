package mo.wall.org

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.Toast
import mo.wall.org.datausage.DataUsageSummaryActivity
import mo.wall.org.ntp.NtpActivity
import org.wall.mo.utils.ntp.NtpHelper
import org.wall.mo.utils.ntp.LowNtpTrustedTime
import org.ziqi.librarydatausagesummary.DataUsageSummaryHelper

class MainActivity : AppCompatActivity() {

    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    var rvMain: RecyclerView? = null;

    var lists: ArrayList<Entity>? = null;

    var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rv_main)
        rvMain!!.layoutManager = LinearLayoutManager(this);

        adapter = MainAdapter(this)
        rvMain!!.adapter = adapter;

        lists = ArrayList()
        val itemFrameWork = Entity()
        itemFrameWork.content = ""
        itemFrameWork.title = "Framework源码学习"
        itemFrameWork.type = 0
        lists?.add(itemFrameWork)

        val itemDataUsage = Entity()
        itemDataUsage.content = "这个功能需要系统区，这是基于4.4源码扣出来的"
        itemDataUsage.title = "流量监控API"
        itemDataUsage.type = 1
        itemDataUsage.clazz = DataUsageSummaryActivity().javaClass.name
        lists?.add(itemDataUsage)


        val itemNtp = Entity()
        itemNtp.content = "修改setting的ntp地址，这是基于4.4源码扣出来的"
        itemNtp.title = "NTP协议"
        itemNtp.type = 1
        itemNtp.clazz = NtpActivity().javaClass.name
        lists?.add(itemNtp)

        adapter!!.setItems(lists)
        adapter!!.setOnItemClickListener(MainAdapter.OnItemClickListener({ view, pos ->
            val item = lists!!.get(pos)
            val clazz = item.clazz
            if (!TextUtils.isEmpty(clazz)) {
                var intent = Intent()
                var componet = ComponentName(applicationContext.packageName, clazz)
                intent.setComponent(componet)
                intent.putExtra("title", item.title)
                startActivity(intent)
            }
        }))

        //测试是否正常
        //ACache.get(this).put("aaa", "aaa");
        //val asString = ACache.get(this).getAsString("aaa");
    }
}