package com.krzysobo.soboapptpl.pubres

import com.krzysobo.soboapptpl.generated.resources.Res
import com.krzysobo.soboapptpl.generated.resources.actions
import com.krzysobo.soboapptpl.generated.resources.current_password
import com.krzysobo.soboapptpl.generated.resources.current_password_required
import com.krzysobo.soboapptpl.generated.resources.email
import com.krzysobo.soboapptpl.generated.resources.error_passwords_dont_match
import com.krzysobo.soboapptpl.generated.resources.first_name
import com.krzysobo.soboapptpl.generated.resources.first_name_required
import com.krzysobo.soboapptpl.generated.resources.full_name
import com.krzysobo.soboapptpl.generated.resources.last_name
import com.krzysobo.soboapptpl.generated.resources.last_name_required
import com.krzysobo.soboapptpl.generated.resources.new_password
import com.krzysobo.soboapptpl.generated.resources.new_password_confirmation
import com.krzysobo.soboapptpl.generated.resources.new_password_confirmation_required
import com.krzysobo.soboapptpl.generated.resources.new_password_required
import com.krzysobo.soboapptpl.generated.resources.password
import com.krzysobo.soboapptpl.generated.resources.password_confirmation
import com.krzysobo.soboapptpl.generated.resources.password_confirmation_required
import com.krzysobo.soboapptpl.generated.resources.password_required
import com.krzysobo.soboapptpl.generated.resources.your_current_password
import com.krzysobo.soboapptpl.generated.resources.your_first_name
import com.krzysobo.soboapptpl.generated.resources.your_last_name
import com.krzysobo.soboapptpl.generated.resources.your_new_password
import com.krzysobo.soboapptpl.generated.resources.your_new_password_confirmation
import com.krzysobo.soboapptpl.generated.resources.your_password_confirmation

/* here we put a "passthrough" for those resources which we want to make public,
eg. labels for password, previous/next etc */

object PubRes {
    object string {
        val password = Res.string.password
        val password_required = Res.string.password_required
        val password_confirmation = Res.string.password_confirmation
        val your_password_confirmation = Res.string.your_password_confirmation
        val password_confirmation_required = Res.string.password_confirmation_required
        val error_passwords_dont_match = Res.string.error_passwords_dont_match

        val new_password = Res.string.new_password
        val your_new_password = Res.string.your_new_password
        val new_password_required = Res.string.new_password_required

        val current_password = Res.string.current_password
        val your_current_password = Res.string.your_current_password
        val current_password_required = Res.string.current_password_required

        val new_password_confirmation = Res.string.new_password_confirmation
        val your_new_password_confirmation = Res.string.your_new_password_confirmation
        val new_password_confirmation_required = Res.string.new_password_confirmation_required


        val email = Res.string.email
        val first_name = Res.string.first_name
        val your_first_name = Res.string.your_first_name
        val first_name_required = Res.string.first_name_required

        val last_name = Res.string.last_name
        val your_last_name = Res.string.your_last_name
        val last_name_required = Res.string.last_name_required
        val full_name = Res.string.full_name
        val actions = Res.string.actions
    }
}

