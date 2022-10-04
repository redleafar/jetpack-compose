package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
private fun MyApp() {
    // With "by" instead of "=" we use a property delegate that saves you from typing
    // .value every time
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

// LazyColumn is the equivalent of RecyclerView in Compose, using Column it would be
// very slow.
// LazyColumn doesn't recycle its children like RecyclerView, it emits new Composables as you scroll
// and is still performant as emitting composables is relatively cheap compared to instantiating
// Android Views

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

//@Composable
//private fun Greetings(names: List<String> = listOf("World", "Compose")) {
//    Column(modifier = Modifier.padding(4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
//        }
//    }
//}


// Material components such as Surface automatically are built to make
// the experience better, so for instance it can change a text color if the background change of
// color to make it more visible

@Composable
fun Greeting(name: String) {
    // "Remember" is used to guard against recomposition so the state is not reset.
    // Internal states like this work as "private" variables in a class, so
    // you can have different UI elements each with its own version of the state

    // Using rememberSaveable instead of remember, the state will survive with configurations changes
    // such as rotations or changing to dark mode
    val expanded = rememberSaveable { mutableStateOf(false) }
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

// Compose transform data into UI by calling composable functions. The data changes re-execute
// these functions with new data. That is recomposition. Compose only recompose components
// whose data has changed
// Composable functions can execute frequently and in any order, you must not rely on the
// ordering in which the code is executed, or on how many times this function will be recomposed

//@Preview is used to use Android Studio Preview
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    ComposeTutorialTheme {
        MyApp()
    }
}

// In Compose you don't hide UI elements. Instead, you simply don't add them to the composition,
// so they're not added to the UI tree that Compose generates

// They call to hoist, to propagate a value from a child composable to its parent.
// A State that doesn't need to be controlled by a composable's parent should not be hoisted.
// The source of truth belongs to whoever creates and controls that state
// For hoisting we use callbacks (lambdas)

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    ComposeTutorialTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}
