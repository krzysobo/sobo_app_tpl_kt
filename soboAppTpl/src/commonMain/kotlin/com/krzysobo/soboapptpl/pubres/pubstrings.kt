package com.krzysobo.soboapptpl.pubres

import com.krzysobo.soboapptpl.generated.resources.Res
import com.krzysobo.soboapptpl.generated.resources.about
import com.krzysobo.soboapptpl.generated.resources.about_s
import com.krzysobo.soboapptpl.generated.resources.actions
import com.krzysobo.soboapptpl.generated.resources.back
import com.krzysobo.soboapptpl.generated.resources.cancel
import com.krzysobo.soboapptpl.generated.resources.clear_all
import com.krzysobo.soboapptpl.generated.resources.close_menu
import com.krzysobo.soboapptpl.generated.resources.color_scheme
import com.krzysobo.soboapptpl.generated.resources.color_scheme_dark
import com.krzysobo.soboapptpl.generated.resources.color_scheme_light
import com.krzysobo.soboapptpl.generated.resources.contact_info
import com.krzysobo.soboapptpl.generated.resources.copy
import com.krzysobo.soboapptpl.generated.resources.current_password
import com.krzysobo.soboapptpl.generated.resources.current_password_required
import com.krzysobo.soboapptpl.generated.resources.dark
import com.krzysobo.soboapptpl.generated.resources.delete
import com.krzysobo.soboapptpl.generated.resources.edit
import com.krzysobo.soboapptpl.generated.resources.email
import com.krzysobo.soboapptpl.generated.resources.error_passwords_dont_match
import com.krzysobo.soboapptpl.generated.resources.first_name
import com.krzysobo.soboapptpl.generated.resources.first_name_required
import com.krzysobo.soboapptpl.generated.resources.forward
import com.krzysobo.soboapptpl.generated.resources.full_name
import com.krzysobo.soboapptpl.generated.resources.go_back
import com.krzysobo.soboapptpl.generated.resources.help
import com.krzysobo.soboapptpl.generated.resources.help_s
import com.krzysobo.soboapptpl.generated.resources.home
import com.krzysobo.soboapptpl.generated.resources.language
import com.krzysobo.soboapptpl.generated.resources.last_name
import com.krzysobo.soboapptpl.generated.resources.last_name_required
import com.krzysobo.soboapptpl.generated.resources.light
import com.krzysobo.soboapptpl.generated.resources.list
import com.krzysobo.soboapptpl.generated.resources.log_in
import com.krzysobo.soboapptpl.generated.resources.login
import com.krzysobo.soboapptpl.generated.resources.logout
import com.krzysobo.soboapptpl.generated.resources.more_at_url_s
import com.krzysobo.soboapptpl.generated.resources.new_password
import com.krzysobo.soboapptpl.generated.resources.new_password_confirmation
import com.krzysobo.soboapptpl.generated.resources.new_password_confirmation_required
import com.krzysobo.soboapptpl.generated.resources.new_password_required
import com.krzysobo.soboapptpl.generated.resources.no
import com.krzysobo.soboapptpl.generated.resources.ok
import com.krzysobo.soboapptpl.generated.resources.open_menu
import com.krzysobo.soboapptpl.generated.resources.page_size
import com.krzysobo.soboapptpl.generated.resources.password
import com.krzysobo.soboapptpl.generated.resources.password_confirmation
import com.krzysobo.soboapptpl.generated.resources.password_confirmation_required
import com.krzysobo.soboapptpl.generated.resources.password_required
import com.krzysobo.soboapptpl.generated.resources.paste
import com.krzysobo.soboapptpl.generated.resources.s_about
import com.krzysobo.soboapptpl.generated.resources.s_help
import com.krzysobo.soboapptpl.generated.resources.s_settings
import com.krzysobo.soboapptpl.generated.resources.settings
import com.krzysobo.soboapptpl.generated.resources.settings_updated_ok
import com.krzysobo.soboapptpl.generated.resources.settings_updated_ok_desc
import com.krzysobo.soboapptpl.generated.resources.settings_updating_error
import com.krzysobo.soboapptpl.generated.resources.total_items_n
import com.krzysobo.soboapptpl.generated.resources.update_settings
import com.krzysobo.soboapptpl.generated.resources.use_device_language
import com.krzysobo.soboapptpl.generated.resources.welcome
import com.krzysobo.soboapptpl.generated.resources.welcome_s
import com.krzysobo.soboapptpl.generated.resources.yes
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
        val welcome = Res.string.welcome
        val welcome_s = Res.string.welcome_s

        val about = Res.string.about
        val about_s = Res.string.about_s
        val s_about = Res.string.s_about

        val help = Res.string.help
        val help_s = Res.string.help_s
        val s_help = Res.string.s_help
        val settings = Res.string.settings
        val s_settings = Res.string.s_settings
        val settings_updated_ok = Res.string.settings_updated_ok
        val settings_updated_ok_desc = Res.string.settings_updated_ok_desc
        val settings_updating_error = Res.string.settings_updating_error
        val update_settings = Res.string.update_settings

        val login = Res.string.login
        val log_in = Res.string.log_in
        val home = Res.string.home
        val logout = Res.string.logout
        val go_back = Res.string.go_back

        val back = Res.string.back
        val forward = Res.string.forward
        val yes = Res.string.yes
        val no = Res.string.no
        val ok = Res.string.ok
        val cancel = Res.string.cancel
        val page_size = Res.string.page_size
        val total_items_n = Res.string.total_items_n
        val dark = Res.string.dark
        val light = Res.string.light
        val color_scheme = Res.string.color_scheme
        val color_scheme_dark = Res.string.color_scheme_dark
        val color_scheme_light = Res.string.color_scheme_light
        val contact_info = Res.string.contact_info
        val open_menu = Res.string.open_menu
        val close_menu = Res.string.close_menu
        val edit = Res.string.edit
        val delete = Res.string.delete
        val list = Res.string.list
        val more_at_url_s = Res.string.more_at_url_s


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
        val copy = Res.string.copy
        val paste = Res.string.paste
        val clear_all = Res.string.clear_all
        val language = Res.string.language
        val use_device_language = Res.string.use_device_language
    }
}

