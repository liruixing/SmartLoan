package com.mmt.smartloan.view

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log

/**
 * 格式化手机号码输入框（3-4-4）
 *
 * @author chenyong@tungee.com
 */
class PhoneFormatEditText : ClearEditText {
    private val WHITE_SPACE = " "
    private val regex = Regex("[^0-9]")

    private var textChangedListener: TextWatcher? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // 只能输入数字
        inputType = InputType.TYPE_CLASS_NUMBER
        // 只能输入12位（10位手机号 + 2位空格） 10+2空格
        filters = arrayOf(InputFilter.LengthFilter(12))
    }

    /**
     * 获取手机号码
     */
    fun getPhone(): String {
        return super.getText().toString().replace(WHITE_SPACE, "")
    }

    /**
     * 重写，使得该输入框只能是手机号码
     */
    override fun setInputType(type: Int) {
        super.setInputType(InputType.TYPE_CLASS_PHONE)
    }

    override fun addTextChangedListener(watcher: TextWatcher?) {
        super.addTextChangedListener(watcher)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        super.beforeTextChanged(s, start, count, after)
        textChangedListener?.beforeTextChanged(s, start, count, after)
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
        textChangedListener?.onTextChanged(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable) {
        if (s.isNotEmpty()) {
            // 光标位置
            val selectionPos = selectionEnd
            autoFormatText(s.toString(), selectionPos)
        }
        super.afterTextChanged(s)
        textChangedListener?.afterTextChanged(s)
    }

    /**
     * 自动格式化字符串
     */
    private fun autoFormatText(text: String, selectionPos: Int) {
        Log.w("PhoneFormatEditText", "autoFormatText: text = $text")
        // 1、计算光标前面的有效字符数（非空格）
        val validCharCount = calcValidCharCount(text, selectionPos)
        Log.w("PhoneFormatEditText", "autoFormatText: validCharCount = $validCharCount")
        // 2、格式化字符串
        val formatText = formatText(text)
        Log.w("PhoneFormatEditText", "autoFormatText: formatText = $formatText")
        // 3、计算字符串格式化之后光标的位置
        val realSelectionPos = calcRealSelectionPos(formatText, validCharCount)
        Log.w("PhoneFormatEditText", "autoFormatText: realSelectionPos = $realSelectionPos")
        // 4、设置格式化后字符串和光标
        updateText(formatText, this)
        var t = getText()
        if (t != null) {
            var txt = t.toString()
            if (txt.length > realSelectionPos) {
                setSelection(realSelectionPos)
            } else {
                setSelection(txt.length)
            }
        }
    }

    /**
     * 计算字符串中光标位置前有效字符数
     */
    private fun calcValidCharCount(text: String, selectionPos: Int): Int {
        if (text.isNullOrEmpty() || selectionPos == 0) {
            return 0
        }

        var result = 0
        for ((i, char) in text.withIndex()) {
            if (char in '0'..'9') {
                result++
            }

            if (i + 1 == selectionPos) {
                break
            }
        }

        return result
    }

    /**
     * 格式化字符串（3-4-4）格式
     */
    private fun formatText(text: String): String {
        val str = text.trim().replace(regex, "")
        Log.w("PhoneFormatEditText", "formatText: str = $str")
        val sb = StringBuilder()
        for ((i, char) in str.withIndex()) {
            if (i == 3 || i == 6) {
                sb.append(WHITE_SPACE).append(char)
            } else {
                sb.append(char)
            }
        }

        return sb.toString()
    }

    /**
     * 根据有效字符数计算光标的真实位置
     *
     * @param formatText String  格式化后字符串
     * @param validCharCount Int  有效字符数
     */
    private fun calcRealSelectionPos(formatText: String, validCharCount: Int): Int {
        if (validCharCount <= 0) {
            return 0
        }

        var count = 0
        for ((i, char) in formatText.withIndex()) {
            if (WHITE_SPACE == char.toString()) {
                continue
            }

            count++
            if (count == validCharCount) {
                return i + 1
            }
        }

        return formatText.length
    }

    private fun updateText(text: String, watcher: TextWatcher) {
        removeTextChangedListener(watcher)
        setText(text)
        addTextChangedListener(watcher)
    }
}