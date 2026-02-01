package com.krzysobo.soboapptpl.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krzysobo.soboapptpl.pubres.PubRes
import com.krzysobo.soboapptpl.service.AnyRes
import com.krzysobo.soboapptpl.service.anyResText

data class LangOpt(val code: String, val label: String) {
    override fun toString(): String {
        return label
    }
}


@Composable
fun SettingUseSystemLang(useSysLang: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier
                .padding(end = 10.dp)
                .align(Alignment.CenterVertically)
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            text = anyResText(AnyRes(PubRes.string.use_device_language)),
            style = TextStyle(fontSize = 18.sp)
        )

        Switch(
            modifier = Modifier
                .weight(1f)
                .wrapContentWidth(Alignment.Start),
            checked = useSysLang.value,
            onCheckedChange = {
                useSysLang.value = !useSysLang.value
            })
    }

}

@Composable
fun SettingSelectLanguage(
    langList: List<LangOpt>,
    lang: MutableState<String>,
    comboTextModifier: Modifier = Modifier.width(300.dp),
) {

    var curOptionIndex = langList.indexOfFirst { it.code == lang.value }
    if (curOptionIndex == -1) {
        curOptionIndex = 0
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier
                .padding(end = 10.dp)
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = anyResText(AnyRes(PubRes.string.language)),
            style = TextStyle(fontSize = 18.sp)
        )
        soboEasyCombo(
            wholeElementModifier = Modifier.weight(1f),
            optionsForMenu = langList,
            onClick = { ind ->
                lang.value = langList[ind].code
            },
            selOptionIndex = curOptionIndex,
            textModifier = comboTextModifier
        )
    }
}
