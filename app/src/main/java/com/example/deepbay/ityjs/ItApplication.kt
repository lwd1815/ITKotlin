package com.example.deepbay.ityjs

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants


/**
 * @author     IT烟酒僧
 * created   2018/10/25 18:19
 * desc:
 */
open class ItApplication: TinkerApplication {

    constructor():super(ShareConstants.TINKER_ENABLE_ALL, "com.example.deepbay.ityjs.MyApplication",
            "com.tencent.tinker.loader.TinkerLoader", false){
    }
}