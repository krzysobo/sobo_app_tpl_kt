package com.krzysobo.soboapptpl.pubres

import com.krzysobo.soboapptpl.generated.resources.Res
import com.krzysobo.soboapptpl.generated.resources.password
import com.krzysobo.soboapptpl.generated.resources.password_required

/* here we put a "passthrough" for those resources which we want to make public,
eg. labels for password, previous/next etc */

object PubRes {
    object string {
        val password = Res.string.password
        val password_required = Res.string.password_required
    }
}

