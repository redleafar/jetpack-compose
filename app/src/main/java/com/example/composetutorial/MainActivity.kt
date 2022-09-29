package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This if to define the layout. Instead of an xml file you call composable functions
        setContent {
            // Note mine: Remember that this name "ComposeTutorialTheme" is based on the name
            // given to the project. It styles the composable functions
            ComposeTutorialTheme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}

// Material components such as Surface automatically are built to make
// the experience better, so for instance it can change a text color if the background change of
// color to make it more visible

@Composable
fun Greeting(name: String) {
    // "Remember" is used to guard against recomposition so the state is not reset.
    // Internal states like this work as "private" variables in a class, so
    // you can have different UI elements each with its own version of the state
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    // The composable function will automatically be "subscribed" to the state. If the state
    // changes, composables that read these fields will be recomposed to display the updates

    // A surface container using the 'background' color from the theme
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            //Modifiers tell a UI element how to lay out, display or behave within is parent layout
            // there are dozen of modifiers
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                Text(
                    if (expanded.value)
                        "Show less"
                    else "Show more"
                )
            }
        }

    }
}

//@Preview is used to use Android Studio Preview
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeTutorialTheme {
        MyApp()
    }
}

// Compose transform data into UI by calling composable functions. The data changes re-execute
// these functions with new data. That is recomposition. Compose only recompose components
// whose data has changed
// Composable functions can execute frequently and in any order, you must not rely on the
// ordering in which the code is executed, or on how many times this function will be recomposed