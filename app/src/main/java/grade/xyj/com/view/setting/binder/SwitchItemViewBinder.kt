package grade.xyj.com.view.setting.binder

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.TypedValue
import android.view.*
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import grade.xyj.com.R
import grade.xyj.com.view.setting.bean.SwitchItem
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class SwitchItemViewBinder constructor(private val onCheckItemCheckChange: (SwitchItem, Boolean) -> Unit) : ItemViewBinder<SwitchItem, SwitchItemViewBinder.ViewHolder>() {
    val color = R.color.red
    private inline fun ViewManager.appCompatCheckBox(init: AppCompatCheckBox.() -> Unit) = ankoView({ AppCompatCheckBox(it) }, theme = 0) { init() }

    @SuppressLint("RestrictedApi")
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val view = AnkoContext.create(parent.context).apply {
            linearLayout {
                id = R.id.anko_layout
                val outValue = TypedValue()
                context.theme.resolveAttribute(R.attr.selectableItemBackground, outValue, true)
                backgroundResource = outValue.resourceId
                lparams(matchParent, dip(64))
                textView {
                    id = R.id.anko_text_view
                    textColorResource = R.color.dark_text_color
                    textSize = 16f
                }.lparams(0, wrapContent) {
                    gravity = Gravity.CENTER_VERTICAL
                    marginStart = dip(16)
                    weight = 1f
                }
                appCompatCheckBox {
                    id = R.id.anko_check_box
                    val color = ContextCompat.getColor(context,color)
                    val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
                    val colors = intArrayOf(color, Color.GRAY)
                    supportButtonTintList = ColorStateList(states, colors)
                }.lparams(wrapContent, wrapContent) {
                    gravity = Gravity.CENTER_VERTICAL
                    marginEnd = dip(16)
                }
            }
        }.view
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: SwitchItem) {
        holder.setIsRecyclable(false)
        holder.tvTitle.text = item.title
        holder.checkbox.isChecked = item.checked
        holder.checkbox.setOnCheckedChangeListener { _, isChecked -> onCheckItemCheckChange.invoke(item, isChecked) }
        holder.layout.setOnClickListener {
            holder.checkbox.isChecked = !holder.checkbox.isChecked
        }
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.find(R.id.anko_text_view)
        val checkbox: CheckBox = itemView.find(R.id.anko_check_box)
        val layout: LinearLayout = itemView.find(R.id.anko_layout)
    }
}