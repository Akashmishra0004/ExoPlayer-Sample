package com.loyalty.player.ui.dailogs

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.loyalty.player.R
import com.loyalty.player.databinding.DialogLoaderBinding


class LoaderDialog(private val message: Int) : DialogFragment() {
    private var binding: DialogLoaderBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent);
        val displayRectangle = Rect()
        dialog!!.window!!.decorView
            .getWindowVisibleDisplayFrame(displayRectangle)
        dialog?.window?.setLayout(displayRectangle.width(), displayRectangle.height());

        isCancelable = false
        return DialogLoaderBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}