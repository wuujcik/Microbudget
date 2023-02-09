package com.wuujcik.microbudget.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.core.os.bundleOf

data class Screen(@IdRes val fragmentId: Int, val args: Bundle? = bundleOf())
